package com.softip.inventory_app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // DB primary key (auto)

    @Column(name = "asset_id", nullable = false, unique = true)
    private Long assetId;      // ID from CSV

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private AssetType type;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "in_date", nullable = false)
    private LocalDate inDate;

    @Column(name = "out_date")
    private LocalDate outDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private State state;

    // --- getters/setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public AssetType getType() { return type; }
    public void setType(AssetType type) { this.type = type; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getInDate() { return inDate; }
    public void setInDate(LocalDate inDate) { this.inDate = inDate; }

    public LocalDate getOutDate() { return outDate; }
    public void setOutDate(LocalDate outDate) { this.outDate = outDate; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }
}
