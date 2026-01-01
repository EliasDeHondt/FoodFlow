/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.controller;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;

public interface Controller {
    public void addGrocery(String name,String amount,String unit);
    public void removeGrocery(Ingredient i);
    public void updateMeal(String day,MealType mealType,Recipe r);
    public void removeMeal(String day,MealType mealType);
    public void setStrategy(String strategy);
    public void generateWeeklyPlan();
    public void AddRecipe(Recipe r);
    public void updateRecipe(Recipe r);
    public void removeRecipe(Recipe r);
}