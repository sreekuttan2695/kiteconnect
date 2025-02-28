package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "strategy")
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "strategy_code", nullable = false, unique = true)
    private String strategyCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "config_table")
    private String configTable;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}