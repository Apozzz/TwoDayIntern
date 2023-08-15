package com.twoday.warehouse.warehousemodule.warehouse;

import java.util.List;

import org.springframework.stereotype.Service;

import com.twoday.dto.dtomodule.WarehouseDto;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseConverter;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseRepository;
import com.twoday.warehouse.warehousemodule.warehouse.interfaces.WarehouseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseConverter warehouseConverter;

    @Override
    public List<WarehouseDto> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouseConverter::toDto)
                .toList();
    }

    @Override
    public WarehouseDto saveWarehouse(WarehouseDto warehouseDto) {
        return warehouseConverter.toDto(warehouseRepository.save(warehouseConverter.fromDto(warehouseDto)));
    }

}
