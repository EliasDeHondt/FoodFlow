package be.uantwerpen.sd.project.builder;

import java.util.ArrayList;
import java.util.List;

import be.uantwerpen.sd.project.Ingredient;

public class RecipeBuilder {
    private String title;
    private String description;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();

    public RecipeBuilder() {
    }

    public RecipeBuilder(String title,String description) {
        this.title = title;
        this.description = description;
    }

    public RecipeBuilder setTitle(String t) {
        this.title = t;
        return this;
    }

    public RecipeBuilder setDescription(String d) {
        this.description = d;
        return this;
    }

    public RecipeBuilder addIngredient(Ingredient i) {
        this.ingredients.add(i);
        return this;
    }

    public RecipeBuilder addTag(String tag) {
        this.tags.add(tag);
        return this;
    }

    public Recipe build() {
        if (this.tags.isEmpty()) {
            return new Recipe(this.title,this.description,this.ingredients);
        }
        return new Recipe(this.title,this.description,this.ingredients,this.tags);
    }
}
