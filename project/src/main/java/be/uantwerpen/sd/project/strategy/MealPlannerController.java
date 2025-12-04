package be.uantwerpen.sd.project.strategy;

import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.view.MealPlannerView;

public class MealPlannerController {
    private WeeklyPlan weeklyplan;
    private MealPlanningStrategy mealplanstrategy;
    private final RecipeRepository recipeRepo = RecipeRepository.getInstance();
    private MealPlannerView view;

    public MealPlannerController() {}

    public Recipe chooseRecipe(String day,MealType mealType) {
        DayPlan d = this.weeklyplan.getDay(day);
        Recipe r = d.getMeal(mealType);
        return r;
    }

    public void setStrategy(MealPlanningStrategy strategy) {
        this.mealplanstrategy = strategy;
    }

    public void setView(MealPlannerView view) {
        this.view = view;
    }

    public void generateWeeklyPlan() {
        this.weeklyplan = new WeeklyPlan();
        this.mealplanstrategy.generatePlan(this.weeklyplan, this.recipeRepo);
    }
}
