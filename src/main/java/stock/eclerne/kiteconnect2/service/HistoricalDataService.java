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

    public void fetchAndStoreHistoricalData(String instrumentToken, Date fromDate) throws IOException, KiteException {
        //historicalDataRepository.truncateTable();

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

            List<HistoricalData> historicalDataList = kiteConnect.getHistoricalData(fromDate, toDate, instrumentToken, "5minute", continuous, oi).dataArrayList;

            for (HistoricalData data : historicalDataList) {
                HistoricalDataStg historicalDataStg = new HistoricalDataStg();
                historicalDataStg.setInstrumentToken(instrumentToken);

                // Check if data.timeStamp is null or empty before setting the timestamp
                if (data.timeStamp != null && !data.timeStamp.isEmpty()) {
                    try {
                        // The correctedTimestamp includes timezone (e.g., +0530)
                        String correctedTimestamp = data.timeStamp;

                        if (correctedTimestamp.endsWith("+0530")) {
                            correctedTimestamp = correctedTimestamp.replace("+0530", "+05:30");
                        }

                        // Parse the timestamp with offset
                        OffsetDateTime offsetDateTime = OffsetDateTime.parse(correctedTimestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                        // Convert to Timestamp and set it
                        Timestamp timestamp = Timestamp.from(offsetDateTime.toInstant());

                        // Set the timestamp in the entity
                        historicalDataStg.setTimestamp(timestamp);
                    } catch (Exception e) {
                        // Handle the case where the timestamp format is incorrect
                        System.err.println("Invalid timestamp format for data: " + data.timeStamp);
                        historicalDataStg.setTimestamp(new Timestamp(System.currentTimeMillis())); // Default to current time
                    }
                } else {
                    // If the timestamp is null or empty, set the current time or skip this entry
                    historicalDataStg.setTimestamp(new Timestamp(System.currentTimeMillis()));
                }

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
}
