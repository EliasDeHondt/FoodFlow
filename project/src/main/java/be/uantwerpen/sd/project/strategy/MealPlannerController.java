package be.uantwerpen.sd.project.strategy;

import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.view.MealPlannerView;

public class MealPlannerController {
    private WeeklyPlan weeklyplan;
    private MealPlanningStrategy mealplanstrategy;
    private RecipeRepository recipeRepo = RecipeRepository.getInstance();
    private MealPlannerView view;

    public MealPlannerController() {}

    public void chooseRecipe(String day,MealType mealType, Recipe recipe) {
        // DayPlan d = this.weeklyplan.getDay(day);
        // Recipe r = d.getMeal(mealType);
    }

    public void setStrategy(MealPlanningStrategy strategy) {}

    public void generateWeeklyPlan() {}
}
