package stock.eclerne.kiteconnect2.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.service.InstrumentService;

import java.io.IOException;

@RestController
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @GetMapping("/api/instruments/download")
    public String downloadInstruments(@RequestParam String exchange) throws KiteException {
        try {
            instrumentService.downloadAndSaveInstruments(exchange);
            return "Instruments downloaded and saved successfully.";
        } catch (IOException e) {
            return "Failed to download instruments: " + e.getMessage();
        }
    }
}
