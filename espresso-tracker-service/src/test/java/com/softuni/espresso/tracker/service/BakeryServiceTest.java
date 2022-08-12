package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.Bakery;
import com.softuni.espresso.tracker.model.BakeryWithCoffees;
import com.softuni.espresso.tracker.model.entities.BakeryEntity;
import com.softuni.espresso.tracker.model.mapper.BakeryMapper;
import com.softuni.espresso.tracker.model.mapper.BakeryMapperImpl;
import com.softuni.espresso.tracker.model.mapper.CoffeeMapper;
import com.softuni.espresso.tracker.model.mapper.CoffeeMapperImpl;
import com.softuni.espresso.tracker.model.web.BakeryRequest;
import com.softuni.espresso.tracker.repository.BakeryRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BakeryServiceTest {

    @InjectMocks
    private BakeryService bakeryService;

    @Mock
    private BakeryRepository repository;

    @Spy
    @InjectMocks
    private BakeryMapper bakeryMapper = new BakeryMapperImpl();

    @Spy
    private CoffeeMapper coffeeMapper = new CoffeeMapperImpl();

    @Captor
    private ArgumentCaptor<BakeryEntity> argumentCaptor;

    @Test
    void getAllBakeries_whenData_shouldSortByIdDesc() {
        when(repository.findAll()).thenReturn(mockData());
        List<Bakery> result = bakeryService.getAllBakeries();
        assertEquals(3, result.size());
        assertEquals(70, result.get(0).getId());
        assertEquals(5, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

    @Test
    void getAllBakeries_whenNoData_shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        List<Bakery> result = bakeryService.getAllBakeries();
        verify(repository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void createBakery_whenNoBakery_shouldSaveCorrectly() {
        BakeryRequest request = mockRequest();
        when(repository.findByName(request.getName())).thenReturn(Optional.empty());
        BakeryEntity expected = new BakeryEntity();
        expected.setName(request.getName());
        expected.setAddress(request.getAddress());
        when(repository.save(expected)).thenReturn(expected);
        bakeryService.createBakery(request);
        verify(repository, times(1)).save(argumentCaptor.capture());
        BakeryEntity result = argumentCaptor.getValue();
        assertNotNull(result);
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAddress(), result.getAddress());
    }

    @Test
    void createBakery_whenSuchBakeryExists_shouldThrow() {
        BakeryRequest request = mockRequest();
        when(repository.findByName(request.getName()))
                .thenReturn(Optional.of(new BakeryEntity()));
        Assertions.assertThrows(ContextException.class,
                () -> bakeryService.createBakery(request));
    }

    @Test
    void getBakery_whenNoSuchBakery_shouldReturnNull() {
        long id = 1L;
        when(repository.findByIdWithCoffees(id)).thenReturn(Optional.empty());
        BakeryWithCoffees bakery = bakeryService.getBakery(id);
        assertNull(bakery);
    }

    @Test
    void getBakery_whenBakeryExists_shouldReturnCorrect() {
        long id = 1L;
        BakeryEntity mockedEntity = mock(id);
        when(repository.findByIdWithCoffees(id)).thenReturn(Optional.of(mockedEntity));
        BakeryWithCoffees bakery = bakeryService.getBakery(id);
        assertNotNull(bakery);
        assertEquals(mockedEntity.getId(), bakery.getBakery().getId());
        assertTrue(bakery.getCoffees().isEmpty());
    }

    private BakeryRequest mockRequest () {
        BakeryRequest request = new BakeryRequest();
        request.setName("bakery");
        request.setAddress("test address");
        return request;
    }

    private List<BakeryEntity> mockData() {
        return List.of(mock(5), mock(1), mock(70));
    }

    private BakeryEntity mock(long id) {
        BakeryEntity entity = new BakeryEntity();
        entity.setId(id);
        return entity;
    }

}