package com.softuni.espresso.tracker.repository;

import com.softuni.espresso.tracker.model.entities.BakeryEntity;
import com.softuni.espresso.tracker.model.entities.CoffeeEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DbTestConfig.class,
        HibernateJpaAutoConfiguration.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BakeryRepositoryTest {

    @Autowired
    private BakeryRepository bakeryRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private EntityManager entityManager;

    private static final String TEST_BAKERY_NAME_HAVING_COFFEES = "Bakery 1";
    private static final String TEST_BAKERY_NAME_HAVING_NO_COFFEES = "Bakery 2";

    private long testIdBakeryHavingCoffees;
    private long testIdBakeryHavingNoCoffees;

    @BeforeAll
    public void setup () {
        BakeryEntity entity1 = mockEntity(TEST_BAKERY_NAME_HAVING_COFFEES);
        BakeryEntity entity2 = mockEntity(TEST_BAKERY_NAME_HAVING_NO_COFFEES);
        mockCoffeeList(entity1);
        List<BakeryEntity> bakeries = bakeryRepository.findAll();
        assertTrue(bakeries.isEmpty());
        testIdBakeryHavingCoffees = bakeryRepository.saveAndFlush(entity1).getId();
        testIdBakeryHavingNoCoffees = bakeryRepository.saveAndFlush(entity2).getId();
        coffeeRepository.saveAllAndFlush(entity1.getCoffees());
    }

    @Test
    void findByName_shouldFetchCorrectly() {
        Optional<BakeryEntity> bakeryEntity = bakeryRepository.findByName(TEST_BAKERY_NAME_HAVING_COFFEES);
        assertNotNull(bakeryEntity);
    }

    @Test
    @Transactional
    void findByIdWithCoffees_whenNoCoffees_shouldReturnEmptyList() {
        Optional<BakeryEntity> havingNoCoffees = bakeryRepository
                .findByIdWithCoffees(testIdBakeryHavingNoCoffees);
        assertTrue(havingNoCoffees.isPresent());
        assertTrue(havingNoCoffees.get().getCoffees().isEmpty());
    }

    @Test
    @Transactional
    void findByIdWithCoffees_whenCoffees_shouldReturnCoffeeList() {
        Optional<BakeryEntity> havingCoffees = bakeryRepository
                .findByIdWithCoffees(testIdBakeryHavingCoffees);
        assertTrue(havingCoffees.isPresent());
        assertEquals(5, havingCoffees.get().getCoffees().size());
    }

    private BakeryEntity mockEntity(String name) {
        BakeryEntity entity = new BakeryEntity();
        entity.setName(name);
        entity.setAddress("Test address " + name);
        entity.setImgSrc("img source " + name);
        entity.setPhoneNumber("phone number " + name);
        entity.setWebSite("www" + name + "com");
        return entity;
    }

    private void mockCoffeeList(BakeryEntity bakery) {
        List<CoffeeEntity> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CoffeeEntity entity = new CoffeeEntity();
            entity.setBakery(bakery);
            entity.setName(bakery.getName() + " coffee");
            entity.setDateCreated(LocalDate.now());
            entity.setRoastedOnDate(LocalDate.now());
            entity.setOrigin("origin");
            result.add(entity);
        }
        bakery.setCoffees(result);
    }

}