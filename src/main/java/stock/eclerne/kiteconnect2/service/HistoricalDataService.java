package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.HistoricalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.HistoricalDataStg;
import stock.eclerne.kiteconnect2.repository.HistoricalDataRepository;
import stock.eclerne.kiteconnect2.model.ApiKey;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

@Service
public class HistoricalDataService {

    @Autowired
    private HistoricalDataRepository historicalDataRepository;

    @Autowired
    private ApiKeyService apiKeyService;

    // Existing method for 5-minute candle data
    public void fetchAndStoreHistoricalData(String instrumentToken, Date fromDate) throws IOException, KiteException {
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        kiteConnect.setAccessToken(apiKey.getAccessToken());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        while (fromDate.before(currentDate)) {
            calendar.setTime(fromDate);
            calendar.add(Calendar.DAY_OF_YEAR, 60);
            Date toDate = calendar.getTime();
            if (toDate.after(currentDate)) {
                toDate = currentDate;
            }

            boolean continuous = false;
            boolean oi = false;

            // Fetch 5-minute candle data
            List<HistoricalData> historicalDataList = kiteConnect.getHistoricalData(fromDate, toDate, instrumentToken, "5minute", continuous, oi).dataArrayList;

            for (HistoricalData data : historicalDataList) {
                HistoricalDataStg historicalDataStg = new HistoricalDataStg();
                historicalDataStg.setInstrumentToken(instrumentToken);

                // Handle the timestamp conversion
                setTimestampForData(data, historicalDataStg);

                historicalDataStg.setOpen(BigDecimal.valueOf(data.open));
                historicalDataStg.setHigh(BigDecimal.valueOf(data.high));
                historicalDataStg.setLow(BigDecimal.valueOf(data.low));
                historicalDataStg.setClose(BigDecimal.valueOf(data.close));
                historicalDataStg.setVolume(data.volume);

                historicalDataRepository.save(historicalDataStg);
            }

            calendar.setTime(toDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fromDate = calendar.getTime();
        }
    }

    // New method for daily candle data
    public void fetchAndStoreDailyHistoricalData(String instrumentToken, Date fromDate) throws IOException, KiteException {
        historicalDataRepository.truncateTable();
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        kiteConnect.setAccessToken(apiKey.getAccessToken());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        while (fromDate.before(currentDate)) {
            calendar.setTime(fromDate);
            calendar.add(Calendar.DAY_OF_YEAR, 60);
            Date toDate = calendar.getTime();
            if (toDate.after(currentDate)) {
                toDate = currentDate;
            }

            boolean continuous = false;
            boolean oi = false;

            // Fetch daily candle data
            List<HistoricalData> historicalDataList = kiteConnect.getHistoricalData(fromDate, toDate, instrumentToken, "day", continuous, oi).dataArrayList;

            for (HistoricalData data : historicalDataList) {
                HistoricalDataStg historicalDataStg = new HistoricalDataStg();
                historicalDataStg.setInstrumentToken(instrumentToken);

                // Handle the timestamp conversion
                setTimestampForData(data, historicalDataStg);

                historicalDataStg.setOpen(BigDecimal.valueOf(data.open));
                historicalDataStg.setHigh(BigDecimal.valueOf(data.high));
                historicalDataStg.setLow(BigDecimal.valueOf(data.low));
                historicalDataStg.setClose(BigDecimal.valueOf(data.close));
                historicalDataStg.setVolume(data.volume);

                historicalDataRepository.save(historicalDataStg);
            }

            calendar.setTime(toDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fromDate = calendar.getTime();
        }
    }

    // New method to fetch and store 5-minute candle data for a specific day (9:15 AM to 3:30 PM)
    public void fetchAndStoreSpecificDayHistoricalData(String instrumentToken, Date specificDate) throws IOException, KiteException {
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        kiteConnect.setAccessToken(apiKey.getAccessToken());

        // Set the from time to 9:15 AM and the to time to 3:30 PM on the same day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(specificDate);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 15);
        Date fromDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        Date toDate = calendar.getTime();

        boolean continuous = false;
        boolean oi = false;

        // Fetch 5-minute candle data for the specific date
        List<HistoricalData> historicalDataList = kiteConnect.getHistoricalData(fromDate, toDate, instrumentToken, "5minute", continuous, oi).dataArrayList;

        // Store the historical data in the staging table
        for (HistoricalData data : historicalDataList) {
            HistoricalDataStg historicalDataStg = new HistoricalDataStg();
            historicalDataStg.setInstrumentToken(instrumentToken);

            // Handle the timestamp conversion
            setTimestampForData(data, historicalDataStg);

            historicalDataStg.setOpen(BigDecimal.valueOf(data.open));
            historicalDataStg.setHigh(BigDecimal.valueOf(data.high));
            historicalDataStg.setLow(BigDecimal.valueOf(data.low));
            historicalDataStg.setClose(BigDecimal.valueOf(data.close));
            historicalDataStg.setVolume(data.volume);

            historicalDataRepository.save(historicalDataStg);
        }
    }


    // Helper method for timestamp conversion
    private void setTimestampForData(HistoricalData data, HistoricalDataStg historicalDataStg) {
        if (data.timeStamp != null && !data.timeStamp.isEmpty()) {
            try {
                String correctedTimestamp = data.timeStamp;
                if (correctedTimestamp.endsWith("+0530")) {
                    correctedTimestamp = correctedTimestamp.replace("+0530", "+05:30");
                }

                OffsetDateTime offsetDateTime = OffsetDateTime.parse(correctedTimestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                Timestamp timestamp = Timestamp.from(offsetDateTime.toInstant());
                historicalDataStg.setTimestamp(timestamp);
            } catch (Exception e) {
                System.err.println("Invalid timestamp format for data: " + data.timeStamp);
                historicalDataStg.setTimestamp(new Timestamp(System.currentTimeMillis()));
            }
        } else {
            historicalDataStg.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }
    }
}
