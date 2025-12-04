package be.uantwerpen.sd.project;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
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
