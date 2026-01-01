/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import be.uantwerpen.sd.project.builder.Recipe;

public class DayPlan {
    private final Map<MealType, Recipe> meals = new HashMap<>();

    public DayPlan() {
        for (MealType type : MealType.values()) meals.put(type, Recipe.empty());
    }

    public void setMeal(MealType type, Recipe recipe) {
        this.meals.put(type, recipe);
    }

    public Recipe getMeal(MealType type) {
        return this.meals.get(type);
    }

    public List<Recipe> getRecipes() {
        List<Recipe> r = new ArrayList<>(this.meals.values());
        return r;
    }
}