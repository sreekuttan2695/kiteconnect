package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.eclerne.kiteconnect2.model.UserBalance;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {
    UserBalance findByUserId(String userId);
}
