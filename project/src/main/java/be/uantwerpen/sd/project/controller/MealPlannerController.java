package be.uantwerpen.sd.project.controller;

import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;

public class MealPlannerController implements Controller{
    private Model db;
    //extra logic voor parsen en datatransmission

    public MealPlannerController() {}

    public Recipe chooseRecipe(String day,MealType mealType) {

        return db.chooseRecipe(day, mealType);
    }

    public void setStrategy(MealPlanningStrategy strategy) {
        this.db.setStrategy(strategy);
    }

    public void generateWeeklyPlan() {
        this.db.generateWeeklyPlan();
    }
}
