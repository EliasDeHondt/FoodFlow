package be.uantwerpen.sd.project.strategy;

import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.observer.WeeklyPlan;

public class DefaultMealPlanStrategy implements MealPlanningStrategy {

    public DefaultMealPlanStrategy() {}
    @Override
    public void generatePlan(WeeklyPlan weeklyplan,RecipeRepository recipeRepo) {

    }
}
