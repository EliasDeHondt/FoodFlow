/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.builder;
import java.util.ArrayList;
import java.util.List;

import be.uantwerpen.sd.project.Ingredient;

public class Recipe {
    private final String title;
    private final String description;
    private final List<Ingredient> ingredients;
    private final List<String> tags;
    private final int id;

    public static RecipeBuilder builder() {
        return new RecipeBuilder();
    }

    public static Recipe empty() {
        return Recipe.builder()
            .title("No Recipe")
            .description("")
            .build();
    }

    public Recipe(String title, String description, List<Ingredient> ingredients, List<String> tags,int id) {
        this.title = title;
        this.description = description;
        this.ingredients = new ArrayList<>(ingredients != null ? ingredients : List.of());
        this.tags = new ArrayList<>(tags != null ? tags : List.of());
        this.id = id;
    }

    @Deprecated
    public void addIngredient(Ingredient i) {
        this.ingredients.add(i);
    }

    @Deprecated
    public void removeIngredient(Ingredient i) {
        this.ingredients.remove(i);
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(this.ingredients);
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    @Deprecated
    public void addtag(String tag) {
        this.tags.add(tag);
    }

    @Deprecated
    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public List<String> getTags() {
        return new ArrayList<>(this.tags);
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
        if (id == -1) throw new IllegalStateException("Recipe has no ID yet");
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && title.equals(recipe.title);
    }

    @Override
    public int hashCode() {
        return id;
    }
}