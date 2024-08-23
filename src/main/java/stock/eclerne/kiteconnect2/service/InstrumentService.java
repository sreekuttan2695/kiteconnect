package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.model.InstrumentEntity;
import stock.eclerne.kiteconnect2.repository.InstrumentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private ApiKeyService apiKeyService;


    public void downloadAndSaveInstruments(String exchange) throws IOException, KiteException {
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        List<Instrument> instruments = kiteConnect.getInstruments(exchange);

        List<InstrumentEntity> instrumentEntities = instruments.stream().map(this::convertToEntity).collect(Collectors.toList());
        instrumentRepository.saveAll(instrumentEntities);
    }

    private InstrumentEntity convertToEntity(Instrument instrument) {
        InstrumentEntity entity = new InstrumentEntity();
        entity.setInstrumentToken(instrument.instrument_token);
        entity.setExchangeToken(instrument.exchange_token);
        entity.setTradingSymbol(instrument.tradingsymbol);
        entity.setName(instrument.name);
        entity.setLastPrice(instrument.last_price);
        entity.setExpiry(String.valueOf(instrument.expiry));
        entity.setStrike(Double.valueOf(instrument.strike));
        entity.setTickSize(instrument.tick_size);
        entity.setLotSize(instrument.lot_size);
        entity.setInstrumentType(instrument.instrument_type);
        entity.setSegment(instrument.segment);
        entity.setExchange(instrument.exchange);
        return entity;
    }

    public ArrayList<Long> getInstrumentToken(String tradingSymbol) throws IOException, KiteException {
        return instrumentRepository.findInstrumentTokensByTradingSymbol(tradingSymbol);
    }
}
