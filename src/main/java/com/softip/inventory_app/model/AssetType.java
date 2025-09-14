package com.softip.inventory_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asset_types")
public class AssetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer code;   // 0=IMA, 1=DIM

    @Column(nullable = false)
    private String name;

    public AssetType() {}
    public AssetType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
