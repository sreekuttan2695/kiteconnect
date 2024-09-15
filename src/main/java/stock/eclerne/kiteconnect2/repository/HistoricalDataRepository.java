package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import stock.eclerne.kiteconnect2.model.HistoricalDataStg;

@Repository
public interface HistoricalDataRepository extends JpaRepository<HistoricalDataStg, Integer> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE Historical_Data_Stg", nativeQuery = true)
    void truncateTable();
}
