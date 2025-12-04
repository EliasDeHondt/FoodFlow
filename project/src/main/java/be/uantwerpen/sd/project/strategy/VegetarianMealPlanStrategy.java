package be.uantwerpen.sd.project.strategy;

import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.observer.WeeklyPlan;

public class VegetarianMealPlanStrategy implements MealPlanningStrategy {

    public VegetarianMealPlanStrategy() {}
    @Override
    public void generatePlan(WeeklyPlan weeklyplan,RecipeRepository recipeRepo) {

    }
}
