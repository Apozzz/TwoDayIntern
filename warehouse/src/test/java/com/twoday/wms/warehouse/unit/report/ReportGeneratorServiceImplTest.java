package com.twoday.wms.warehouse.unit.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.purchase.Purchase;
import com.twoday.wms.warehouse.report.ReportGeneratorServiceImpl;
import com.twoday.wms.warehouse.user.User;

public class ReportGeneratorServiceImplTest {
    
    @InjectMocks
    private ReportGeneratorServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateCSV() {
        Product product1 = new Product(1L, "Laptop", "New", 999.00f, 2);
        User user1 = new User(10L, "JohnDoe", "123456");
        Purchase purchase1 = new Purchase(100L, user1, product1, 1, 9.99f, LocalDateTime.parse("2023-01-01T10:10:10"));

        Product product2 = new Product(2L, "Phone\"Special", "New", 499.00f, 2);
        User user2 = new User(20L, "JaneSmith", "1234");
        Purchase purchase2 = new Purchase(101L, user2, product2, 2, 9.99f, LocalDateTime.parse("2023-01-02T10:10:10"));

        List<Purchase> purchases = Arrays.asList(purchase1, purchase2);
        String result = service.generateCSV(purchases);
        
        String expected = 
            "Purchase ID,User ID,User Name,Product ID,Product Name,Quantity,Final Price,Time Stamp\n" +
            "100,10,JohnDoe,1,\"Laptop\",1,9.99,2023-01-01T10:10:10\n" +
            "101,20,JaneSmith,2,\"Phone\"\"Special\",2,9.99,2023-01-02T10:10:10\n";

        assertEquals(expected, result);
    }

}
