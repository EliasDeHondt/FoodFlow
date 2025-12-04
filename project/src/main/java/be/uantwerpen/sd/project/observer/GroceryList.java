package be.uantwerpen.sd.project.observer;


import java.util.HashMap;
import java.util.Map;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.builder.Recipe;

public class GroceryList implements Observer {
    private final Map<String,Double> items = new HashMap<>();

    public GroceryList() {

    }
    @Override
    public void update(String event, Object payload) {
        if (event.equals("new_dayplan")) {
            DayPlan plan = (DayPlan) payload;

            for (Recipe r: plan.getRecipes()) {
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

    public Map<String,Double> getItems() {
        return this.items;
    }
    // public void uncheckItem(String name) {
    
    // }
}
