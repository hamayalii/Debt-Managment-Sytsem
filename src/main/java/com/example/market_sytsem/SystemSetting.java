package com.example.market_sytsem;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Entity
@Table(name = "system_settings")
public class SystemSetting {
    @Id
    private Long id = 1L;

    private String storeName;
    private Double customUsdRate;
    private Boolean useCustomRate;

    public SystemSetting() {

        this.storeName = "دەفتەری قەرزەکان";
        this.customUsdRate = 153000.0;
        this.useCustomRate = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getCustomUsdRate() {
        return customUsdRate;
    }

    public void setCustomUsdRate(Double customUsdRate) {
        this.customUsdRate = customUsdRate;
    }

    public Boolean isUseCustomRate() {
        return useCustomRate;
    }

    public void setUseCustomRate(Boolean useCustomRate) {
        this.useCustomRate = useCustomRate;
    }
}
