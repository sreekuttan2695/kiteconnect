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

@Service
public class HistoricalDataService {

    @Autowired
    private HistoricalDataRepository historicalDataRepository;

    @Autowired
    private ApiKeyService apiKeyService;

    public void fetchAndStoreHistoricalData(String instrumentToken, Date fromDate) throws IOException, KiteException {
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

            List<HistoricalData> historicalDataList = kiteConnect.getHistoricalData(fromDate, toDate, instrumentToken, "5minute", continuous, oi).dataArrayList;

            for (HistoricalData data : historicalDataList) {
                HistoricalDataStg historicalDataStg = new HistoricalDataStg();
                historicalDataStg.setInstrumentToken(instrumentToken);
                historicalDataStg.setTimestamp(Timestamp.valueOf(data.timeStamp));
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
