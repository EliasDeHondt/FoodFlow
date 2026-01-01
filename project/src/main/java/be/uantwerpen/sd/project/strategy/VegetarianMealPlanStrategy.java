/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.strategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;

public class VegetarianMealPlanStrategy implements MealPlanningStrategy {

    public VegetarianMealPlanStrategy() {}

    @Override
    public void generatePlan(WeeklyPlan weeklyplan, RecipeRepository recipeRepo) {
        List<Recipe> recipes = recipeRepo.getAll();
        System.out.println("recipes: " + recipes.size());

        List<Recipe> breakfast = new ArrayList<>();
        List<Recipe> lunch = new ArrayList<>();
        List<Recipe> dinner = new ArrayList<>();
        List<Recipe> snack = new ArrayList<>();

        for (Recipe r : recipes) {
            List<String> tags = r.getTags();
            boolean isVegetarian = tags.isEmpty() || tags.contains("vegan");

            if ((r.getTags().contains("breakfast") || r.getTags().isEmpty()) && isVegetarian) {
                breakfast.add(r);
            }
            if ((r.getTags().contains("lunch") || r.getTags().isEmpty()) && isVegetarian) {
                lunch.add(r);
            }
            if ((r.getTags().contains("dinner") || r.getTags().isEmpty()) && isVegetarian) {
                dinner.add(r);
            }
            if ((r.getTags().contains("snack") || r.getTags().isEmpty()) && isVegetarian) {
                snack.add(r);
            }
        }

        System.out.println("vegetarian breakfast: " + breakfast.size());
        System.out.println("vegetarian lunch: " + lunch.size());
        System.out.println("vegetarian dinner: " + dinner.size());
        System.out.println("vegetarian snack: " + snack.size());

        String[] dayNames = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        for (int col = 0; col < 7; col++) {
            DayPlan day = new DayPlan();
            Random random = new Random();

            if (!breakfast.isEmpty()) {
                Recipe r = breakfast.get(random.nextInt(breakfast.size()));
                day.setMeal(MealType.BREAKFAST, r);
            }
            if (!lunch.isEmpty()) {
                Recipe r = lunch.get(random.nextInt(lunch.size()));
                day.setMeal(MealType.LUNCH, r);
            }
            if (!dinner.isEmpty()) {
                Recipe r = dinner.get(random.nextInt(dinner.size()));
                day.setMeal(MealType.DINNER, r);
            }
            if (!snack.isEmpty()) {
                Recipe r = snack.get(random.nextInt(snack.size()));
                day.setMeal(MealType.SNACK, r);
            }

            weeklyplan.setDay(dayNames[col], day);
        }
    }
}