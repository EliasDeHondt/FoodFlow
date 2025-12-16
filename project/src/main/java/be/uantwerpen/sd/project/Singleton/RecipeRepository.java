package be.uantwerpen.sd.project.Singleton;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.uantwerpen.sd.project.builder.Recipe;

public class RecipeRepository {
    private int counter = 0;
    private final Map<Integer, Recipe> recipes = new HashMap<>();

    private static RecipeRepository instance;

    private RecipeRepository() {}

    public static synchronized RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public void addRecipe(Recipe r) {
        this.recipes.put(nextId(), r);
    }

    public void removeRecipe(Recipe r) {
        this.recipes.remove(r.getId(),r);
    }

    public void updateRecipe(Recipe updated) {
        recipes.put(updated.getId(), updated);
        //perhaps observers otherwise changing grocerylist
    }

    public List<Recipe> getAll() {
        return List.copyOf(this.recipes.values());
    }
    public int nextId() {
        return ++counter;
  }
}
