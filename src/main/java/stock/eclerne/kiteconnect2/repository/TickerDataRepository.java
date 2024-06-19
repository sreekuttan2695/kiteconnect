package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.eclerne.kiteconnect2.model.TickerData;

@Repository
public interface TickerDataRepository extends JpaRepository<TickerData, Integer> {
}
