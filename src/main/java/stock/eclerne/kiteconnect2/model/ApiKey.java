package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;

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

    @Column(name = "totp_key", nullable = false)
    private String totpKey;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTotpKey() {
        return totpKey;
    }

    public void setTotpKey(String totpKey) {
        this.totpKey = totpKey;
    }
}
