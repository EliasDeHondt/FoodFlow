package be.uantwerpen.sd.project.viewfx;

import java.util.Map;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.view.RenderPort;
import be.uantwerpen.sd.project.view.View;
import javafx.scene.layout.BorderPane;

public class MealPlannerView extends BorderPane implements RenderPort{
    private View logic;

    public MealPlannerView() {}

    public void attachLogic(View logic) {
        this.logic = logic;
    }


    @Override
    public void displayWeeklyPlan(WeeklyPlan plan) {
        
    }
    @Override
    public void displayGroceryList(Map<String, Double> items,DayPlan day) {

    }

    
}
