package com.twoday.wms.shop.pricing;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingService {
    
    private final PricingProperties pricingProperties;

    public Float calculatePriceWithMargin(Float basePrice) {
        return basePrice + (basePrice * pricingProperties.getProfitMargin());
    }

    public Float calculateDiscountedPrice(Float price, Integer quantity) {
        if (quantity >= pricingProperties.getDiscountQuantity()) {
            return price - (price * pricingProperties.getDiscountAmount());
        }

        return price;
    }

    public Float getFinalPrice(Float basePrice, Float finalPrice) {
        return finalPrice >= basePrice ? finalPrice : basePrice;
    }

}
