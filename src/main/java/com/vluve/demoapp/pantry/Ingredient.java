package com.vluve.demoapp.pantry;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity;

    private LocalDate packagedDate;

    private LocalDate shelfLife;

    private LocalDate expirationDate;

    public Ingredient(String name, int quantity, LocalDate packagedDate, LocalDate shelfLife, LocalDate expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.packagedDate = packagedDate;
        this.shelfLife = shelfLife;
        this.expirationDate = expirationDate;
    }

    public Ingredient() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getPackagedDate() {
        return packagedDate;
    }

    public void setPackagedDate(LocalDate packagedDate) {
        this.packagedDate = packagedDate;
    }

    public LocalDate getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(LocalDate shelfLife) {
        this.shelfLife = shelfLife;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
