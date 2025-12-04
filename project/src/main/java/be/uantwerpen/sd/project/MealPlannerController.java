package be.uantwerpen.sd.project;

public class MealPlannerController {
    private WeeklyPlan weeklyplan;
    private MealPlanningStrategy mealplanstrategy;
    private RecipeRepository recipeRepo = RecipeRepository.getInstance();
    private MealPlannerView view;

    public MealPlannerController() {}

    public Recipe chooseRecipe() {
        return new Recipe(null, null, null);
    }

    public void setStrategy(MealPlanningStrategy strategy) {}

    public void generateWeeklyPlan() {}
}
