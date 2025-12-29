package be.uantwerpen.sd.project.builder;

import java.util.List;

import be.uantwerpen.sd.project.Ingredient;

public class RecipeBuilder {
    private String title;
    private String description;
    private List<Ingredient> ingredients = List.of();
    private List<String> tags = List.of();
    private Integer id =-1;

    public RecipeBuilder() {
    }

    public RecipeBuilder title(String t) {
        this.title = t;
        return this;
    }

    public RecipeBuilder description(String d) {
        this.description = d;
        return this;
    }

    public RecipeBuilder ingredients(List<Ingredient> i) {
        this.ingredients = i;
        return this;
    }

    public RecipeBuilder tags(List<String> t) {
        this.tags = t;
        return this;
    }

    public RecipeBuilder id(int id) {
        this.id = id;
        return this;
    }

    public Recipe build() {
        return new Recipe(this.title,this.description,this.ingredients,this.tags,this.id);
    }
}
