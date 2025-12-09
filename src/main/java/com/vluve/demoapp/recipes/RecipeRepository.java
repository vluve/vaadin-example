package com.vluve.demoapp.recipes;

import com.vluve.demoapp.pantry.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
