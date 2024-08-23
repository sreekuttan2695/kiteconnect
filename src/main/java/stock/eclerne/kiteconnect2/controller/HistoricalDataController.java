package stock.eclerne.kiteconnect2.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.service.HistoricalDataService;

import java.io.IOException;
import java.util.Date;

@RestController
public class HistoricalDataController {

    @Autowired
    private HistoricalDataService historicalDataService;

    @GetMapping("/api/historical-data")
    public String fetchHistoricalData(@RequestParam String instrumentToken, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate) {
        try {
            historicalDataService.fetchAndStoreHistoricalData(instrumentToken, fromDate);
            return "Historical data fetched and stored successfully.";
        } catch (IOException | KiteException e) {
            return "Failed to fetch historical data: " + e.getMessage();
        }
    }
}
