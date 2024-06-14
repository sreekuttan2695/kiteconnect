package stock.eclerne.kiteconnect2.model;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "api_secret", nullable = false)
    private String apiSecret;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "totp_key", nullable = false)
    private String totpKey;

    // Getters and Setters
}
