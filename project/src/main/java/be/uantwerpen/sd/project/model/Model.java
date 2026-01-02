/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.model;
import java.beans.PropertyChangeListener;
import java.util.List;

import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;

public interface Model {
    public List<Ingredient> getGroceries();
    public void addGrocery(Ingredient i);
    public void removeGrocery(Ingredient i);
    public void updateMeal(String day,MealType mealType,Recipe r);
    public void removeMeal(String day,MealType mealType);
    public void setStrategy(MealPlanningStrategy strategy);
    public void generateWeeklyPlan();
    public Recipe[][] getWeeklyPlan();
    public void AddRecipe(Recipe r);
    public void updateRecipe(Recipe r);
    public void removeRecipe(Recipe r);
    public List<Recipe> getRecipes();
    public void addPropertyChangeListener(PropertyChangeListener l);
    public void removePropertyChangeListener(PropertyChangeListener l);
}