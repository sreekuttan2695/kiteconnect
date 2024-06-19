package stock.eclerne.kiteconnect2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.TickerData;
import stock.eclerne.kiteconnect2.repository.TickerDataRepository;

@Service
public class TickerDataService {

    @Autowired
    private TickerDataRepository tickerDataRepository;

    public void saveTickerData(TickerData tickerData) {
        tickerDataRepository.save(tickerData);
    }
}
