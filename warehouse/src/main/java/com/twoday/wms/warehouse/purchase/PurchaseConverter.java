package com.twoday.wms.warehouse.purchase;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.dto.UserDto;
import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.product.interfaces.ProductConverter;
import com.twoday.wms.warehouse.user.User;
import com.twoday.wms.warehouse.user.interfaces.UserConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseConverter {

    private final UserConverter userConverter;
    private final ProductConverter productConverter;

    public PurchaseDto toDto(Purchase purchase) {
        PurchaseDto purchaseDto = new PurchaseDto();
        BeanUtils.copyProperties(purchase, purchaseDto);

        UserDto userDto = userConverter.toDto(purchase.getUser());
        purchaseDto.setUser(userDto);

        ProductDto productDto = productConverter.toDto(purchase.getProduct());
        purchaseDto.setProduct(productDto);

        return purchaseDto;
    }

    public Purchase fromDto(PurchaseDto purchaseDto) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDto, purchase);

        User user = userConverter.fromDto(purchaseDto.getUser());
        purchase.setUser(user);

        Product product = productConverter.fromDto(purchaseDto.getProduct());
        purchase.setProduct(product);

        return purchase;
    }
}