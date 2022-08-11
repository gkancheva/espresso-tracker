package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.Bakery;
import com.softuni.espresso.tracker.model.BakeryWithCoffees;
import com.softuni.espresso.tracker.model.web.BakeryRequest;
import com.softuni.espresso.tracker.model.mapper.BakeryMapper;
import com.softuni.espresso.tracker.repository.BakeryRepository;
import com.softuni.espresso.tracker.model.entities.BakeryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.enterprise.context.ContextException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BakeryService {

    private final BakeryRepository repository;
    private final BakeryMapper mapper;

    public List<Bakery> getAllBakeries() {
        return repository.findAll().stream()
                .sorted((b1, b2) -> Long.compare(b2.getId(), b1.getId()))
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public long createBakery(BakeryRequest request) {
        Optional<BakeryEntity> optBakery = repository.findByName(request.getName());
        if (optBakery.isPresent()) {
            throw new ContextException("Bakery with such name already exists");
        }
        try {
            return repository.save(mapper.mapToEntity(request)).getId();
        } catch (Exception e) {
            log.error("Unable to persist bakery: '{}'", request);
            throw new ContextException("Unable to persists bakery: " + request.getName());
        }
    }

    @Transactional
    public BakeryWithCoffees getBakery(Long bakeryId) {
        Optional<BakeryEntity> optBakery = repository.findByIdWithCoffees(bakeryId);
        if (optBakery.isEmpty()) {
            return null;
        }
        return mapper.mapToEnhancedModel(optBakery.get());
    }

}
