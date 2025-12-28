package be.uantwerpen.sd.project.builder;

import java.util.List;

import be.uantwerpen.sd.project.Ingredient;

public class RecipeBuilder {
    private String title;
    private String description;
    private List<Ingredient> ingredients = List.of();
    private List<String> tags = List.of();

    public RecipeBuilder() {
    }

    // public RecipeBuilder(String title,String description) {
    //     this.title = title;
    //     this.description = description;
    // }

    public RecipeBuilder title(String t) {
        this.title = t;
        return this;
    }

    public RecipeBuilder description(String d) {
        this.description = d;
        return this;
    }

    // public RecipeBuilder addIngredient(Ingredient i) {
    //     this.ingredients.add(i);
    //     return this;
    // }

    public RecipeBuilder ingredients(List<Ingredient> i) {
        this.ingredients = i;
        return this;
    }

    // public RecipeBuilder addTag(String tag) {
    //     this.tags.add(tag);
    //     return this;
    // }

    public RecipeBuilder tags(List<String> t) {
        this.tags = t;
        return this;
    }

    public Recipe build() {
        return new Recipe(this.title,this.description,this.ingredients,this.tags);
    }
}
