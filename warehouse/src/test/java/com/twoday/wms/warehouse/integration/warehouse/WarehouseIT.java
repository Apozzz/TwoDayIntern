package com.twoday.wms.warehouse.integration.warehouse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.twoday.wms.warehouse.product.Product;
import com.twoday.wms.warehouse.product.interfaces.ProductRepository;
import com.twoday.wms.warehouse.warehouse.Warehouse;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseRepository;

import jakarta.transaction.Transactional;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class WarehouseIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    private static String encodedAuth;

    @Container
    public static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.25")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    public static void setupAuth() {
        String basicAuth = "ra:ra";
        encodedAuth = "Basic " + Base64.getEncoder().encodeToString(basicAuth.getBytes());
    }

    @BeforeEach
    public void setup() {
        productRepository.save(new Product(1L, "Scissors", "Sharp", 9.99f, 10));
        productRepository.save(new Product(2L, "Pencil", "Wooden", 1.99f, 100));
        productRepository.save(new Product(3L, "Eraser", "Long Lasting", 0.99f, 100));
    }

    @AfterEach
    public void tearDown() {
        warehouseRepository.deleteAll();
    }

    @Transactional
    @Test
    public void testSaveWarehouse() throws Exception {
        String warehouseJson = "{ \"name\": \"Warehouse A\", \"location\": \"Location A\", \"productIds\": [1, 2, 3] }";

        mockMvc.perform(post("/v1/warehouses")
                .header(HttpHeaders.AUTHORIZATION, encodedAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .content(warehouseJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("Warehouse A")))
                .andExpect(jsonPath("$.location", is("Location A")))
                .andExpect(jsonPath("$.productIds", containsInAnyOrder(1, 2, 3)));

        Optional<Warehouse> warehouseOptional = warehouseRepository.findByName("Warehouse A");
        assertTrue(warehouseOptional.isPresent());
        Warehouse savedWarehouse = warehouseOptional.get();
        assertEquals("Location A", savedWarehouse.getLocation());
        assertEquals(3, savedWarehouse.getProducts().size());
    }

    @Test
    public void testSaveWarehouseProduct() throws Exception {
        String productJson = "{ \"name\": \"Sample Product\", \"description\": \"Sample Description\", \"price\": 9.99, \"quantity\": 10 }";
        Long warehouseId = warehouseRepository
                .save(new Warehouse(1L, "Sample Warehouse", "Sample Location", Collections.emptyList())).getId();

        mockMvc.perform(post("/v1/warehouses/%s/products".formatted(warehouseId))
                .header(HttpHeaders.AUTHORIZATION, encodedAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Sample Product")))
                .andExpect(jsonPath("$.description", is("Sample Description")))
                .andExpect(jsonPath("$.price", is(9.99)))
                .andExpect(jsonPath("$.quantity", is(10)));

        Optional<Product> productOptional = productRepository.findByName("Sample Product");
        assertTrue(productOptional.isPresent());
        Product savedProduct = productOptional.get();
        assertEquals("Sample Description", savedProduct.getDescription());
        assertEquals(9.99f, savedProduct.getPrice());
        assertEquals(10, savedProduct.getQuantity());
    }
}