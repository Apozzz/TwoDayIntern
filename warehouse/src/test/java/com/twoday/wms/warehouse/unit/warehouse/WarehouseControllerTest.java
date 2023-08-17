package com.twoday.wms.warehouse.unit.warehouse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoday.wms.dto.ProductDto;
import com.twoday.wms.dto.WarehouseDto;
import com.twoday.wms.warehouse.product.interfaces.ProductService;
import com.twoday.wms.warehouse.unit.configs.TestAuthConfig;
import com.twoday.wms.warehouse.warehouse.WarehouseController;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@WebMvcTest(WarehouseController.class)
@Import(TestAuthConfig.class)
public class WarehouseControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @MockBean
        private WarehouseService warehouseService;

        @MockBean
        private ProductService productService;

        @MockBean
        private UserDetailsService userDetailsService;

        private WarehouseDto sampleWarehouseDto;
        private ProductDto sampleProductDto;
        private ListAppender<ILoggingEvent> listAppender;

        @BeforeEach
        public void setUp() {
                sampleProductDto = new ProductDto(1L, "Scissors", "Very sharp", 9.99f, 9.99f, 10);
                sampleWarehouseDto = new WarehouseDto(1L, "Amazon-WW-2", "Paneriu. g, 23", Arrays.asList(1L));
                UserDetails userDetails = new User("ra", passwordEncoder.encode("ra"), Collections.emptyList());
                Mockito.when(userDetailsService.loadUserByUsername("ra")).thenReturn(userDetails);
                Logger logger = (Logger) LoggerFactory.getLogger(WarehouseController.class);
                listAppender = new ListAppender<>();
                listAppender.start();
                logger.addAppender(listAppender);
        }

        @AfterEach
        public void tearDown() {
                listAppender.stop();
                listAppender.list.clear();
        }

        @Test
        public void testGetAllWarehouses() throws Exception {
                Mockito.when(warehouseService.getAllWarehouses()).thenReturn(Arrays.asList(sampleWarehouseDto));
                mockMvc.perform(MockMvcRequestBuilders.get("/v1/warehouses").with(httpBasic("ra", "ra")))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].name", is("Amazon-WW-2")))
                                .andExpect(jsonPath("$[0].location", is("Paneriu. g, 23")))
                                .andExpect(jsonPath("$[0].productIds", is(Arrays.asList(1))));
                assertLogMessages("Fetching all warehouses...", "Fetched 1 warehouses successfully.");
                Mockito.verify(warehouseService, Mockito.times(1)).getAllWarehouses();
        }

        @Test
        public void testGetWarehouseProducts() throws Exception {
                Mockito.when(productService.getProductsByWarehouseId(1L)).thenReturn(Arrays.asList(sampleProductDto));
                mockMvc.perform(MockMvcRequestBuilders.get("/v1/warehouses/1/products").with(httpBasic("ra", "ra")))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].id", is(1)))
                                .andExpect(jsonPath("$[0].name", is("Scissors")))
                                .andExpect(jsonPath("$[0].description", is("Very sharp")))
                                .andExpect(jsonPath("$[0].price", is(9.99)))
                                .andExpect(jsonPath("$[0].quantity", is(10)));
                assertLogMessages("Fetching products for warehouse ID: 1...",
                                "Fetched 1 products for warehouse ID: 1 successfully.");
                Mockito.verify(productService, Mockito.times(1)).getProductsByWarehouseId(1L);
        }

        @Test
        public void testGetProductsByNonExistentWarehouseId() throws Exception {
                Long nonExistentWarehouseId = 99L;
                Mockito.when(productService.getProductsByWarehouseId(nonExistentWarehouseId))
                                .thenReturn(Collections.emptyList());
                mockMvc.perform(MockMvcRequestBuilders.get("/v1/warehouses/" + nonExistentWarehouseId + "/products")
                                .with(httpBasic("ra", "ra")))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$", hasSize(0)));
                assertLogMessages("Fetching products for warehouse ID: 99...",
                                "Fetched 0 products for warehouse ID: 99 successfully.");
                Mockito.verify(productService, Mockito.times(1)).getProductsByWarehouseId(nonExistentWarehouseId);
        }

        @Test
        public void testSaveWarehouse() throws Exception {
                Mockito.when(warehouseService.saveWarehouse(Mockito.any(WarehouseDto.class)))
                                .thenReturn(sampleWarehouseDto);
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/warehouses").with(httpBasic("ra", "ra"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sampleWarehouseDto)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.name", is("Amazon-WW-2")))
                                .andExpect(jsonPath("$.location", is("Paneriu. g, 23")))
                                .andExpect(jsonPath("$.productIds", is(Arrays.asList(1))));
                assertLogMessages(
                                "Attempting to save warehouse with details: WarehouseDto [id=1, name=Amazon-WW-2, location=Paneriu. g, 23, productIds=[1]]",
                                "Successfully saved warehouse with ID: 1.");
                Mockito.verify(warehouseService, Mockito.times(1)).saveWarehouse(Mockito.any(WarehouseDto.class));
        }

        @Test
        public void testSaveWarehouseProduct() throws Exception {
                Long warehouseId = 1L;
                Mockito.when(productService.saveProductByWarehouseId(Mockito.any(Long.class),
                                Mockito.any(ProductDto.class)))
                                .thenReturn(sampleProductDto);
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/warehouses/" + warehouseId + "/products")
                                .with(httpBasic("ra", "ra"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sampleProductDto)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.name", is("Scissors")));
                assertLogMessages(
                                "Attempting to save product with details: ProductDto [id=1, name=Scissors, description=Very sharp, price=9.99, quantity=10] for warehouse ID: 1...",
                                "Successfully saved product with ID: 1 for warehouse ID: 1.");
                Mockito.verify(productService, Mockito.times(1)).saveProductByWarehouseId(Mockito.any(Long.class),
                                Mockito.any(ProductDto.class));
        }

        @Test
        public void testPurchaseProduct() throws Exception {
                Long warehouseId = 1L;
                Long productId = 2L;
                Integer quantity = 5;
                when(productService.purchaseProduct(Mockito.any(Long.class), Mockito.any(Integer.class),
                                Mockito.any(String.class), Mockito.anyFloat())).thenReturn(sampleProductDto);
                mockMvc.perform(
                                MockMvcRequestBuilders
                                                .post("/v1/warehouses/%s/products/%s/purchase".formatted(warehouseId,
                                                                productId))
                                                .with(httpBasic("ra", "ra")).param("quantity", quantity.toString())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.name", is("Scissors")))
                                .andExpect(jsonPath("$.description", is("Very sharp")))
                                .andExpect(jsonPath("$.price", is(9.99)))
                                .andExpect(jsonPath("$.quantity", is(10)));
                assertLogMessages(
                                "User ra is attempting to purchase product ID: 2 from warehouse ID: 1 with quantity: 5...",
                                "User ra successfully purchased product ID: 2 from warehouse ID: 1.");
                Mockito.verify(productService, Mockito.times(1)).purchaseProduct(Mockito.any(Long.class),
                                Mockito.any(Integer.class), Mockito.any(String.class), Mockito.anyFloat());
        }

        private void assertLogMessages(String expectedStartLog, String expectedEndLog) {
                List<ILoggingEvent> logsList = listAppender.list;
                assertEquals(2, logsList.size());
                assertEquals(expectedStartLog, logsList.get(0).getFormattedMessage());
                assertEquals(expectedEndLog, logsList.get(1).getFormattedMessage());
        }

}
