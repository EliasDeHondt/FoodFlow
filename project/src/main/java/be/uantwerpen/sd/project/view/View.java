package be.uantwerpen.sd.project.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.controller.Controller;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.model.RegistrationEventType;
import javafx.stage.Stage;

public class View implements  PropertyChangeListener{
    private final Model model;
    private final Controller controller;
    private final RenderPort ui;
    private final Stage stage;
    
    public View(Model model, Controller controller, RenderPort ui, Stage stage) {
        this.model = model;
        this.controller = controller;
        this.ui = ui;
        this.stage = stage;
        this.model.addPropertyChangeListener(this);
        refreshAll();
    }

    // public void GetRecipe(String day,MealType mealtype) {
    //     try {
    //         this.controller.chooseRecipe(day,mealtype);
    //     } catch (Exception e) {
    //         this.ui.showError(e.getMessage());
    //     }
    // }

    public void onSetRecipe(String day,MealType mealtype,Recipe r) {
        try {
            this.controller.updateRecipe(day, mealtype, r);
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    public void onSetStrategy(String strategy) {
        try {
            this.controller.setStrategy(strategy);
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    public void onGenerateWeeklyPlan() {
        try {
            this.controller.generateWeeklyPlan();
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    public void onAddRecipe(String title,String descr,Map<String, Double> I, List<String> tags ) {
        try {
            List<Ingredient> i = I.entrySet()
                .stream()
                .map(entry -> new Ingredient(entry.getKey(), entry.getValue(),""))
                .collect(Collectors.toList());
            Recipe r = Recipe.builder()
                .title(title)
                .description(descr)
                .ingredients(i)
                .tags(tags)
                .build();
            this.controller.AddRecipe(r);
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object nv = evt.getNewValue();
        if (!(nv instanceof RegistrationEventType re)) return;
    }

    private void refreshAll() {}
}
