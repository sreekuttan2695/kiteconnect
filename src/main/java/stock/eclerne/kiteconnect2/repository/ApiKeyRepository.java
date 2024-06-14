package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stock.eclerne.kiteconnect2.model.ApiKey;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {

    @Query("SELECT a FROM ApiKey a WHERE a.apiKey = :apiKey")
    ApiKey findByApiKey(String apiKey);

    @Query("SELECT a FROM ApiKey a WHERE a.apiSecret = :apiSecret")
    ApiKey findByApiSecret(String apiSecret);

    @Query("SELECT a FROM ApiKey a WHERE a.userId = :userId")
    ApiKey findByUserId(String userId);

    @Query("SELECT a from ApiKey a where a.id=1")
    ApiKey findFirstApiKey();
}
