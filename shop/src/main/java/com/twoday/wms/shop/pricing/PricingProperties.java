package com.twoday.wms.shop.pricing;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "shop")
@Data
@NoArgsConstructor
public class PricingProperties {
    
    private Float profitMargin;
    private Integer discountQuantity;
    private Float discountAmount;

}
