package com.vluve.demoapp.recipes;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeRepository repo;

    public RecipeService(RecipeRepository repo) {
        this.repo = repo;
    }

    public Page<Recipe> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Recipe save(Recipe recipe) {
        return repo.save(recipe);
    }

    public Recipe find(long id) {
        return repo.findById(id).orElseThrow();
    }

    // тестовые рецепты
    @PostConstruct
    public void initRecipes() {
        if (repo.count() > 0) return;

        save(new Recipe("Паста карбонара", "Vluve",
                25, 240, 500, 20, 30,
                "https://static.tildacdn.com/tild6164-6239-4561-a130-376437623033/_4.png"));

        save(new Recipe("Греческий салат", "Vluve",
                10, 150, 180, 5, 10,
                "https://art-lunch.ru/content/uploads/2018/07/Greek_salad_01.jpg"));

        save(new Recipe("Рататуй", "Vluve",
                40, 300, 650, 25, 40,
                "https://klopotenko.com/wp-content/uploads/2019/08/Ratatyj_siteWeb.jpg"));
    }
}
