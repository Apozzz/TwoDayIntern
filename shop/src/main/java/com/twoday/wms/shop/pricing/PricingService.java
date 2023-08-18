package com.twoday.wms.shop.pricing;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final PricingProperties pricingProperties;

    public Float calculatePriceWithMargin(Float basePrice) {
        Float result = basePrice + (basePrice * pricingProperties.getProfitMargin());
        return Math.round(result * 100.0f) / 100.0f;
    }

    public Float calculateDiscountedPrice(Float price, Integer quantity) {
        Float discountedPrice;
        
        if (quantity >= pricingProperties.getDiscountQuantity()) {
            discountedPrice = price - (price * pricingProperties.getDiscountAmount());
        } else {
            discountedPrice = price;
        }

        return Math.round(discountedPrice * 100.0f) / 100.0f;
    }

    public Float getFinalPrice(Float basePrice, Float finalPrice) {
        return finalPrice >= basePrice ? finalPrice : basePrice;
    }

}
