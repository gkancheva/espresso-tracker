package com.softuni.espresso.tracker.repository.entities;

import com.softuni.espresso.tracker.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "coffee_machine_id")
    private CoffeeToolEntity coffeeMachine;

    @ManyToOne
    @JoinColumn(name = "grinder_id")
    private CoffeeToolEntity grinder;

    @ManyToMany
    @JoinTable(
            inverseJoinColumns = @JoinColumn(name = "coffee_id"),
            joinColumns = @JoinColumn(name = "user_id"))
    private List<CoffeeEntity> coffees = new ArrayList<>();

}
