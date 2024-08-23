package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stock.eclerne.kiteconnect2.model.HistoricalDataStg;

@Repository
public interface HistoricalDataRepository extends JpaRepository<HistoricalDataStg, Integer> {

    @Query(value = "TRUNCATE TABLE HistoricalDataStg", nativeQuery = true)
    void truncateTable();
}
