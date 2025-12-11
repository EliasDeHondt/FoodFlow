package be.uantwerpen.sd.project.model;

import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;

public interface Model {
    public Recipe chooseRecipe(String day,MealType mealType);
    public void setStrategy(MealPlanningStrategy strategy);
    public void generateWeeklyPlan();
    public WeeklyPlan getWeeklyPlan();
}
