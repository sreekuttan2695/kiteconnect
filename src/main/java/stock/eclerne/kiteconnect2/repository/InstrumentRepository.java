package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stock.eclerne.kiteconnect2.model.InstrumentEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<InstrumentEntity, Long> {

    @Query("SELECT i.instrumentToken FROM InstrumentEntity i WHERE i.tradingSymbol LIKE %:tradingSymbol")
    ArrayList<Long> findInstrumentTokensByTradingSymbol(String tradingSymbol);
}
