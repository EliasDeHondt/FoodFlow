package be.uantwerpen.sd.project.model;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;

public class MealPlannerDB implements Model{
    private WeeklyPlan weeklyplan;
    private MealPlanningStrategy mealplanstrategy;
    private final RecipeRepository recipeRepo = RecipeRepository.getInstance();

    public MealPlannerDB() {}
    @Override
    public Recipe chooseRecipe(String day,MealType mealType) {
        DayPlan d = this.weeklyplan.getDay(day);
        Recipe r = d.getMeal(mealType);
        return r;
    }
    @Override
    public void setStrategy(MealPlanningStrategy strategy) {
        this.mealplanstrategy = strategy;
    }
    @Override
    public void generateWeeklyPlan() {
        this.weeklyplan = new WeeklyPlan();
        this.mealplanstrategy.generatePlan(this.weeklyplan, this.recipeRepo);
    }
    @Override
    public WeeklyPlan getWeeklyPlan() {
        return this.weeklyplan;
    }
}
