package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Margin;
import com.zerodhatech.models.MarginCalculationData;
import com.zerodhatech.models.MarginCalculationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.model.MarginCalculation;
import stock.eclerne.kiteconnect2.model.UserBalance;
import stock.eclerne.kiteconnect2.repository.MarginCalculationRepository;
import stock.eclerne.kiteconnect2.repository.UserBalanceRepository;
import com.zerodhatech.models.Margin.Available;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class MarginService {

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private MarginCalculationRepository marginCalculationRepository;

    public void getMargin() throws KiteException, IOException {
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        kiteConnect.setAccessToken(apiKey.getAccessToken());

        Map<String, Margin> margins = kiteConnect.getMargins();
        Margin equityMargin = margins.get("equity");

        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(apiKey.getUserId());
        userBalance.setBalance(equityMargin.net);
        userBalanceRepository.save(userBalance);
    }

    public void getMarginCalculation(List<MarginCalculationParams> params) throws KiteException, IOException {
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        kiteConnect.setAccessToken(apiKey.getAccessToken());

        List<MarginCalculationData> marginCalculations = kiteConnect.getMarginCalculation(params);

        for (MarginCalculationData margin : marginCalculations) {
            Double requiredMargin = margin.total; // Ensure this field is correctly accessed
            UserBalance userBalance = userBalanceRepository.findByUserId(apiKey.getUserId());

            MarginCalculation marginCalculation = new MarginCalculation();
            marginCalculation.setUserId(apiKey.getUserId());
            marginCalculation.setMarginRequired(BigDecimal.valueOf(requiredMargin));
            marginCalculation.setMarginFlag(userBalance.getBalance().compareTo(BigDecimal.valueOf(requiredMargin)) >= 0 ? 1 : 0);
            marginCalculationRepository.save(marginCalculation);
        }
    }
}
