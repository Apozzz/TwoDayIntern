package com.twoday.wms.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.twoday.wms.shop.pricing.PricingProperties;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(PricingProperties.class)
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}
