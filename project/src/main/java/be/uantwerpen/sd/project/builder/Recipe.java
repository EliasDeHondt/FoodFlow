package be.uantwerpen.sd.project.builder;

import java.util.List;

import be.uantwerpen.sd.project.Ingredient;

public class Recipe {
    private final String title;
    private final String description;
    private List<Ingredient> ingredients = List.of();
    private List<String> tags = List.of();
    private int id;

    public static RecipeBuilder builder() {
        return new RecipeBuilder();
    }

    public static Recipe empty() {
        return Recipe.builder()
            .title("")
            .description("")
            .build();
    }

    public Recipe(String title, String description, List<Ingredient> ingredients, List<String> tags,int id) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.tags = tags;
        this.id = id;
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

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public List<String> getTags() {
        return this.tags;
    }
    public Recipe addId(Integer id) {
        return Recipe.builder()
        .title(this.title)
        .description(this.description)
        .ingredients(this.ingredients)
        .tags(this.tags)
        .id(id)
        .build();
    }
    public int getId() {
        if (id == -1) {
            throw new IllegalStateException("Recipe has no ID yet");
        }
        return id;
    }
}
