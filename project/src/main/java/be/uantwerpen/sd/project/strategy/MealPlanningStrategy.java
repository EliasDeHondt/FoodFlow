package be.uantwerpen.sd.project.strategy;

import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.observer.WeeklyPlan;

public interface MealPlanningStrategy {
    public void generatePlan(WeeklyPlan weeklyplan,RecipeRepository recipeRepo);
}
