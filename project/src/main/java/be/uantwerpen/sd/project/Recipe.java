package be.uantwerpen.sd.project;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private final String title;
    private final String description;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public Recipe(String title, String description, List<Ingredient> ingredients) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
    }

    public Recipe(String title, String description, List<Ingredient> ingredients, List<String> tags) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.tags = tags;
    }

    public void addIngredient(Ingredient i) {
        this.ingredients.add(i);
    }

    public void removeIngredient(Ingredient i) {
        this.ingredients.remove(i);
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void addtag(String tag) {
        this.tags.add(tag);
    }

    public List<String> getTags() {
        return this.tags;
    }
}
