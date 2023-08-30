package com.twoday.wms.warehouse.purchase;

import java.time.LocalDateTime;

import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private Integer quantity;
    private Float totalPrice;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime timeStamp;

    public Purchase(User user, Product product, Integer quantity, Float totalPrice, LocalDateTime timeStamp) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.timeStamp = timeStamp;
    }

}
