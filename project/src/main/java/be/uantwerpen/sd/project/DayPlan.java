package be.uantwerpen.sd.project;

import java.util.HashMap;
import java.util.Map;

public class DayPlan {
    private final Map<MealType, Recipe> meals = new HashMap<>();

    public DayPlan() {

    }

    public void setMeal(MealType type, Recipe recipe) {
        meals.put(type, recipe);
    }

    public Recipe getMeal(MealType type) {
        return meals.get(type);
    }
}
