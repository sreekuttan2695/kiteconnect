package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.eclerne.kiteconnect2.model.OrderHistory;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    // Method to delete orders by date
    void deleteByOrderTimestampBetween(Date start, Date end);

    // Method to fetch orders by date (optional, if needed)
    List<OrderHistory> findByOrderTimestampBetween(Date start, Date end);
}
