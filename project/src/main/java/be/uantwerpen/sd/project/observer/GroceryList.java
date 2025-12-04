package be.uantwerpen.sd.project.observer;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;

import java.util.List;

public class GroceryList implements Observer {
    private final Map<String,Double> items = new HashMap<>();

    public GroceryList() {

    }
    @Override
    public void update(String event, Object payload) {
        if (event.equals("new_dayplan")) {
            DayPlan plan = (DayPlan) payload;
            List<Recipe> recipes = new ArrayList<>();
            recipes.add(plan.getMeal(MealType.BREAKFAST));
            recipes.add(plan.getMeal(MealType.LUNCH));
            recipes.add(plan.getMeal(MealType.DINNER));
            recipes.add(plan.getMeal(MealType.SNACK));
            for (Recipe r: recipes) {
                for (Ingredient i : r.getIngredients()) {
                    addItem(i.getName(), i.getQuantity());
                }
            }
        }
    }

    public void addItem(String name, Double quantity) {
        items.merge(name,quantity,Double::sum);
    }
    public void checkOffItem(String name) {
        items.remove(name);
    }
    // public void uncheckItem(String name) {
    
    // }
}
