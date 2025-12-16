package be.uantwerpen.sd.project.controller;

import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.strategy.DefaultMealPlanStrategy;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;
import be.uantwerpen.sd.project.strategy.VegetarianMealPlanStrategy;

public class MealPlannerController implements Controller{
    private final Model db;
    //extra logic voor parsen en datatransmission

    private static String norm(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    public MealPlannerController(Model db) {
        this.db = db;
    }
    @Override
    public void chooseRecipe(String day,MealType mealType) {
        if (norm(day) != null) {
        MealType m = mealType;
        db.chooseRecipe(day, m);}
        else {
            throw new IllegalArgumentException("Please provide both a day and a meal type.");
        }
    }
    @Override
    public void setStrategy(String strategy) {
        MealPlanningStrategy s = new DefaultMealPlanStrategy();
        if (strategy.equals("vegetarian")) {
            s = new VegetarianMealPlanStrategy();
        }
        this.db.setStrategy(s);
    }
    @Override
    public void generateWeeklyPlan() {
        this.db.generateWeeklyPlan();
    }
}
