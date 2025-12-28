package be.uantwerpen.sd.project.controller;

import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;

public interface Controller {
    // public void chooseRecipe(String day,MealType mealType);
    public void updateRecipe(String day,MealType mealType,Recipe r);
    public void setStrategy(String strategy);
    public void generateWeeklyPlan();
    public void AddRecipe(Recipe r);
}
