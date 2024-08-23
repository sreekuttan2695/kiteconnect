package stock.eclerne.kiteconnect2.service;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.*;
import com.zerodhatech.ticker.KiteTicker;
import com.zerodhatech.ticker.OnConnect;
import com.zerodhatech.models.Tick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.model.TickerData;
import java.io.IOException;

import java.util.ArrayList;

@Service
public class KiteWebSocketService {

    @Autowired
    private TickerDataService tickerDataService;

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private InstrumentService instrumentService;

    public KiteTicker tickerProvider;

    public void initializeKiteTicker() {
        ApiKey apiKey = apiKeyService.getApiKey();
        this.tickerProvider = new KiteTicker(apiKey.getAccessToken(), apiKey.getApiKey());
    }

    public void startTicker() {
        initializeKiteTicker();

        tickerProvider.setOnConnectedListener(new OnConnect() {
            @Override
            public void onConnected() {
                ArrayList<Long> instrumentTokens = null;
                try {
                    instrumentTokens = instrumentService.getInstrumentToken("NIFTY 50");
                } catch (IOException | KiteException e) {
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
                tickerDataService.saveTickerData(tickerData); // Saves to DB
            }
        });

        tickerProvider.connect();
    }
}
