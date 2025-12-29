package be.uantwerpen.sd.project.observer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.builder.Recipe;

public class GroceryList implements Observer {
    private final Map<String, Ingredient> items =  new HashMap<>();

    public GroceryList() {

    }
    @Override
    public void update(String event, Object payload) {
        this.items.clear();
        if (event.equals("new_dayplan")) {
            WeeklyPlan plan = (WeeklyPlan) payload;
            for (DayPlan day : plan.getDays()) {
                for (Recipe r: day.getRecipes()) {
                    for (Ingredient i : r.getIngredients()) {
                        addItem(i.getName(), i);
                    }
                }
            }
        }
    }

    public void addItem(String name, Ingredient i) {
        items.merge(name,i, Ingredient::add);
    }

    public void checkOffItem(Ingredient a) {
        items.remove(a.getName());
    }

    public List<Ingredient> getItems() {
        return new ArrayList<>(items.values());
    }
    // public void uncheckItem(String name) {
    
    // }
}
