package com.twoday.wms.warehouse.unit.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.dto.UserDto;
import com.twoday.wms.warehouse.user.User;
import com.twoday.wms.warehouse.user.UserConverterImpl;

public class UserConverterImplTest {
    
    @InjectMocks
    private UserConverterImpl userConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToDto() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Sample Username");
        user.setPassword("Sample Password");
        UserDto dto = userConverter.toDto(user);
        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getPassword(), dto.getPassword());
    }

    @Test
    public void testFromDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("Test Username");
        userDto.setPassword("Test Password");
        User user = userConverter.fromDto(userDto);
        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getPassword(), user.getPassword());
    }

}
