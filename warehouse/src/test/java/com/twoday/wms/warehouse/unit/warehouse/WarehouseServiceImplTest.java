package com.twoday.wms.warehouse.unit.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.dto.WarehouseDto;
import com.twoday.wms.warehouse.warehouse.Warehouse;
import com.twoday.wms.warehouse.warehouse.WarehouseServiceImpl;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseConverter;
import com.twoday.wms.warehouse.warehouse.interfaces.WarehouseRepository;

public class WarehouseServiceImplTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private WarehouseConverter warehouseConverter;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllWarehouses() {
        Warehouse warehouse = new Warehouse();
        WarehouseDto dto = new WarehouseDto();
        when(warehouseRepository.findAll()).thenReturn(Arrays.asList(warehouse));
        when(warehouseConverter.toDto(warehouse)).thenReturn(dto);
        List<WarehouseDto> result = warehouseService.getAllWarehouses();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    public void testSaveWarehouse() {
        Warehouse warehouse = new Warehouse();
        WarehouseDto dto = new WarehouseDto();
        when(warehouseConverter.fromDto(dto)).thenReturn(warehouse);
        when(warehouseRepository.save(warehouse)).thenReturn(warehouse);
        when(warehouseConverter.toDto(warehouse)).thenReturn(dto);
        WarehouseDto result = warehouseService.saveWarehouse(dto);
        assertNotNull(result);
        assertEquals(dto, result);
    }

}
