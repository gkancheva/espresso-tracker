package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.Coffee;
import com.softuni.espresso.tracker.model.entities.BakeryEntity;
import com.softuni.espresso.tracker.model.entities.CoffeeEntity;
import com.softuni.espresso.tracker.model.mapper.CoffeeMapper;
import com.softuni.espresso.tracker.model.mapper.CoffeeMapperImpl;
import com.softuni.espresso.tracker.model.web.CoffeeRequest;
import com.softuni.espresso.tracker.repository.BakeryRepository;
import com.softuni.espresso.tracker.repository.CoffeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.enterprise.context.ContextException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoffeeServiceTest {

    @InjectMocks
    private CoffeeService coffeeService;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private BakeryRepository bakeryRepository;

    @Spy
    private CoffeeMapper coffeeMapper = new CoffeeMapperImpl();

    @Captor
    private ArgumentCaptor<CoffeeEntity> argumentCaptor;

    @Test
    void createCoffee_whenBakeryDoesNotExist_shouldThrow() {
        CoffeeRequest request = mockRequest();
        when(bakeryRepository.findById(request.getBakeryId())).thenReturn(Optional.empty());
        Assertions.assertThrows(ContextException.class,
                () -> coffeeService.createCoffee(request));
    }

    @Test
    void createCoffee_shouldSetCorrectlyAllFields() {
        CoffeeRequest request = mockRequest();
        BakeryEntity bakeryEntity = new BakeryEntity();
        bakeryEntity.setId(request.getBakeryId());
        when(bakeryRepository.findById(request.getBakeryId()))
                .thenReturn(Optional.of(bakeryEntity));
        CoffeeEntity entity = new CoffeeEntity();
        entity.setId(1L);
        when(coffeeRepository.save(any(CoffeeEntity.class))).thenReturn(entity);
        coffeeService.createCoffee(request);
        verify(coffeeRepository, times(1)).save(argumentCaptor.capture());
        CoffeeEntity result = argumentCaptor.getValue();
        assertNotNull(result);
        assertEquals(1, result.getActive());
        assertEquals(request.getBakeryId(), result.getBakery().getId());
        assertEquals(LocalDate.now(), result.getDateCreated());
    }

    @Test
    void getCoffees_whenData_shouldReturnCorrect() {
        when(coffeeRepository.findAll()).thenReturn(List.of(new CoffeeEntity()));
        List<Coffee> coffees = coffeeService.getCoffees();
        assertNotNull(coffees);
        assertFalse(coffees.isEmpty());
    }

    @Test
    void getCoffees_whenNoData_shouldReturnEmptyList() {
        when(coffeeRepository.findAll()).thenReturn(Collections.emptyList());
        List<Coffee> coffees = coffeeService.getCoffees();
        assertTrue(coffees.isEmpty());
    }

    private CoffeeRequest mockRequest() {
        CoffeeRequest request = new CoffeeRequest();
        request.setName("coffee name");
        request.setBakeryId(1L);
        return request;
    }
}