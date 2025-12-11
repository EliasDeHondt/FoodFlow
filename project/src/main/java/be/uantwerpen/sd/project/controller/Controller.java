package be.uantwerpen.sd.project.controller;

import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;

public interface Controller {
    public Recipe chooseRecipe(String day,MealType mealType);
    public void setStrategy(MealPlanningStrategy strategy);
    public void generateWeeklyPlan();
}
