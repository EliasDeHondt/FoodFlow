package be.uantwerpen.sd.project.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;

public class DefaultMealPlanStrategy implements MealPlanningStrategy {

    public DefaultMealPlanStrategy() {}
    @Override
    public void generatePlan(WeeklyPlan weeklyplan,RecipeRepository recipeRepo) {
        List<Recipe> recipes = recipeRepo.getAll();
        System.out.println("recipes: "+ recipes.size());
        List<Recipe> breakfast = new ArrayList<>();
        List<Recipe> lunch = new ArrayList<>();
        List<Recipe> dinner = new ArrayList<>();
        List<Recipe> snack = new ArrayList<>();
        
        for (Recipe r : recipes) {
            if (r.getTags().contains("breakfast") || r.getTags().isEmpty() || r.getTags().contains("vegan")) {
                breakfast.add(r);
            }
            if (r.getTags().contains("lunch") || r.getTags().isEmpty() || r.getTags().contains("vegan")) {
                lunch.add(r);
            }
            if (r.getTags().contains("dinner") || r.getTags().isEmpty() || r.getTags().contains("vegan")) {
                dinner.add(r);
            }
            if (r.getTags().contains("snack") || r.getTags().isEmpty() || r.getTags().contains("vegan")) {
                snack.add(r);
            }
        }
        System.out.println("breakfast: "+ breakfast.size());
        System.out.println("lunch: "+ lunch.size());
        System.out.println("dinner: "+ dinner.size());
        System.out.println("snack: "+ snack.size());

        String[] dayNames = {"mon","tue","wed","thu","fri","sat","sun"};
        for (int col = 0; col < 7; col++) {
            DayPlan day = new DayPlan();
            Random random = new Random();
            Recipe r = breakfast.get(random.nextInt(breakfast.size()));
            day.setMeal(MealType.BREAKFAST, r);
            r = lunch.get(random.nextInt(lunch.size()));
            day.setMeal(MealType.LUNCH, r);
            r = dinner.get(random.nextInt(dinner.size()));
            day.setMeal(MealType.DINNER, r);
            r = snack.get(random.nextInt(snack.size()));
            day.setMeal(MealType.SNACK, r);
            weeklyplan.setDay(dayNames[col], day);
        }
    }
}
