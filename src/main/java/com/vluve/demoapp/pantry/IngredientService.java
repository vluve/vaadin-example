package com.vluve.demoapp.pantry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class IngredientService {

    private final IngredientRepository repo;

    public IngredientService(IngredientRepository repo) {
        this.repo = repo;
    }

    public Page<Ingredient> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Ingredient create(Ingredient ing) {
        return repo.save(ing);
    }

    public void updateQuantity(long id, int qty) {
        Ingredient ing = repo.findById(id).orElseThrow();
        ing.setQuantity(qty);
        repo.save(ing);
    }

    public void save(Ingredient ing) {
        repo.save(ing);
    }
}
