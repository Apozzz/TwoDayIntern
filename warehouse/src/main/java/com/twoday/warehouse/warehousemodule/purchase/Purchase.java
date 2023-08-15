package com.twoday.warehouse.warehousemodule.purchase;

import java.time.LocalDateTime;

import com.twoday.warehouse.warehousemodule.annotations.CsvField;
import com.twoday.warehouse.warehousemodule.annotations.CsvFields;
import com.twoday.warehouse.warehousemodule.product.Product;
import com.twoday.warehouse.warehousemodule.user.User;

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
    @CsvField(name = "Purchase ID", order = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @CsvFields({
        @CsvField(name = "User ID", nestedField = "id", order = 2),
        @CsvField(name = "User Name", nestedField = "username", quote = true, order = 3)
    })
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @CsvFields({
        @CsvField(name = "Product ID", nestedField = "id", order = 4),
        @CsvField(name = "Product Name", nestedField = "name", quote = true, order = 5)
    })
    private Product product;

    @CsvField(name = "Quantity", order = 6)
    private Integer quantity;

    @Column(columnDefinition = "TIMESTAMP")
    @CsvField(name = "Time Stamp", order = 7)
    private LocalDateTime timeStamp;

    public Purchase(User user, Product product, Integer quantity, LocalDateTime timeStamp) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
    }

}
