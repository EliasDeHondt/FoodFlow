package be.uantwerpen.sd.project.view;

import java.util.Map;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.observer.WeeklyPlan;

public interface RenderPort {
    public void displayWeeklyPlan(WeeklyPlan plan);
    public void displayGroceryList(Map<String, Double> items,DayPlan day);
    void showError(String message);
}
