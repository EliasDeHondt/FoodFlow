package be.uantwerpen.sd.project;


import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private final List<Recipe> recipes = new ArrayList<>();

    private static RecipeRepository instance;

    private RecipeRepository() {}

    public static synchronized RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public void addRecipe(Recipe r) {
        this.recipes.add(r);
    }

    public void removeRecipe(Recipe r) {
        this.recipes.remove(r);
    }
    public List<Recipe> getAll() {
        return this.recipes;
    }
}
