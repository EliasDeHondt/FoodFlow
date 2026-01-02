/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.builder.Recipe;

public class GroceryList implements Observer {
    private final Map<String, Ingredient> items =  new HashMap<>();
    private final Set<String> checkedItems = new HashSet<>();

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

    public void checkOffItem(Ingredient i) {
        checkedItems.add(i.getName());
    }

    public void uncheckItem(String name) {
        checkedItems.remove(name);
    }

    public boolean isChecked(String itemName) {
        return checkedItems.contains(itemName);
    }

    public List<Ingredient> getItems() {
        return new ArrayList<>(items.values());
    }

    public List<Ingredient> getCheckedItems() {
        List<Ingredient> checked = new ArrayList<>();
        for (Ingredient i : items.values()) {
            if (checkedItems.contains(i.getName())) {
                checked.add(i);
            }
        }
        return checked;
    }

    public void clearChecked() {
        checkedItems.clear();
    }
}