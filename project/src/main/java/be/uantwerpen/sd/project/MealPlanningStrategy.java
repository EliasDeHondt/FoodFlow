package be.uantwerpen.sd.project;

public interface MealPlanningStrategy {
    public void generatePlan(WeeklyPlan weeklyplan,RecipeRepository recipeRepo);
}
