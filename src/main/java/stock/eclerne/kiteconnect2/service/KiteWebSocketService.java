package stock.eclerne.kiteconnect2.service;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.*;
import com.zerodhatech.ticker.KiteTicker;
import com.zerodhatech.ticker.OnConnect;
import com.zerodhatech.ticker.OnTicks;
import com.zerodhatech.models.Tick;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.model.TickerData;
import stock.eclerne.kiteconnect2.repository.InstrumentRepository;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Service
public class KiteWebSocketService {

    @Autowired
    private TickerDataService tickerDataService;

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private InstrumentService instrumentService;

//    private InstrumentRepository;
    public KiteTicker tickerProvider;

    @PostConstruct
    public void init() {
        // Initialize the KiteTicker with necessary details
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteTicker tickerProvider = new KiteTicker(apiKey.getAccessToken(), apiKey.getApiKey());
        this.tickerProvider = tickerProvider;
    }

    public void startTicker() {
        tickerProvider.setOnConnectedListener(new OnConnect() {
            @Override
            public void onConnected() {
                ArrayList<Long> instrumentTokens = null;
                try {
                    instrumentTokens = instrumentService.getInstrumentToken("NIFTY");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (KiteException e) {
                    throw new RuntimeException(e);
                }
                tickerProvider.subscribe(instrumentTokens);
                tickerProvider.setMode(instrumentTokens, KiteTicker.modeFull);
            }
        });

        tickerProvider.setOnTickerArrivalListener(ticks -> {
            for (Tick tick : ticks) {
                TickerData tickerData = new TickerData();
                tickerData.setInstrumentToken(String.valueOf(tick.getInstrumentToken()));
                tickerData.setLastTradePrice(tick.getLastTradedPrice());
                tickerData.setOpen(tick.getOpenPrice());
                tickerData.setHigh(tick.getHighPrice());
                tickerData.setLow(tick.getLowPrice());
                tickerData.setClose(tick.getClosePrice());
                tickerDataService.saveTickerData(tickerData); //Saves to DB
            }
        });

        tickerProvider.connect();
    }
}
