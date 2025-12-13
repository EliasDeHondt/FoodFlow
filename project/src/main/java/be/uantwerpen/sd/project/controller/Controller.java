package be.uantwerpen.sd.project.controller;

import be.uantwerpen.sd.project.MealType;

public interface Controller {
    public void chooseRecipe(String day,MealType mealType);
    public void setStrategy(String strategy);
    public void generateWeeklyPlan();
}
