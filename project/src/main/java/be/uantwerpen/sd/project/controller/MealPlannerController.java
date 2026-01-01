/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.controller;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.strategy.DefaultMealPlanStrategy;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;
import be.uantwerpen.sd.project.strategy.VegetarianMealPlanStrategy;

public class MealPlannerController implements Controller{
    private final Model db;

    private static String norm(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    public MealPlannerController(Model db) {
        this.db = db;
    }
    @Override
    public void addGrocery(String name,String amount,String unit) {
        if (norm(name) != null && norm(amount) != null && norm(unit) != null) {
            Double quantity = Double.valueOf(amount);
            Ingredient i = new Ingredient(name, quantity, unit);
            this.db.addGrocery(i);
        }
        else {
            throw new IllegalArgumentException("Please provide both a name, amount and unit.");
        }
    }
    @Override
    public void removeGrocery(Ingredient i) {
        this.db.removeGrocery(i);
    }
    @Override
    public void updateMeal(String day,MealType mealtype,Recipe r) {
        if (norm(day) != null) {
        MealType m = mealtype;
        db.updateMeal(day, m, r);}
        else {
            throw new IllegalArgumentException("Please provide both a day, meal type and recipe.");
        }
    }
    @Override
    public void removeMeal(String day,MealType mealType) {
        if (norm(day) != null) {
        MealType m = mealType;
        db.removeMeal(day, m);}
        else {
            throw new IllegalArgumentException("Please provide both a day and meal type.");
        }
    }
    @Override
    public void setStrategy(String strategy) {
        MealPlanningStrategy s = new DefaultMealPlanStrategy();
        if (strategy.equals("vegetarian")) {
            s = new VegetarianMealPlanStrategy();
        }
        this.db.setStrategy(s);
    }
    @Override
    public void generateWeeklyPlan() {
        this.db.generateWeeklyPlan();
    }
    @Override
    public void AddRecipe(Recipe r) {
        this.db.AddRecipe(r);
    }
    @Override
    public void updateRecipe(Recipe r) {
        this.db.updateRecipe(r);
    }
    @Override
    public void removeRecipe(Recipe r) {
        this.db.removeRecipe(r);
    }
}