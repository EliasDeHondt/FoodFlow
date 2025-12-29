package be.uantwerpen.sd.project.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

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
    private Recipe currentSelection;
    private final Stage stage;
    
    public View(Model model, Controller controller, RenderPort ui, Stage stage) {
        this.model = model;
        this.controller = controller;
        this.ui = ui;
        this.stage = stage;
        this.model.addPropertyChangeListener(this);
        refreshAll();
    }

    public void GetRecipe(String day,MealType mealtype) {
        try {
            this.controller.getRecipe(day,mealtype);
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    public void onSetRecipe(String day,MealType mealtype,Recipe r) {
        try {
            this.controller.updateMeal(day, mealtype, r);
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

    public void onAddRecipe(String title,String descr,List<Ingredient> i, List<String> tags ) {
        try {
            Recipe r = Recipe.builder()
                .title(title)
                .description(descr)
                .ingredients(i)
                .tags(tags)
                .build();
            this.controller.AddRecipe(r);
            refreshAll();
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    public void onUpdateRecipe(Recipe updated) {
        this.controller.updateRecipe(updated);
        refreshAll();
    }

    public void onDeleteSelected(Recipe sel) {
        if (sel != null) {
            this.controller.removeRecipe(sel);
        }
    }

    public void onSelectionChanged(Recipe sel) {
        currentSelection = sel;
        ui.setActionsEnabled(sel != null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object nv = evt.getNewValue();
        if (!(nv instanceof RegistrationEventType re)) return;
        if (re == RegistrationEventType.RECIPE_ADDED
                || re == RegistrationEventType.RECIPE_REMOVED
                || re == RegistrationEventType.RECIPE_UPDATED) {
            ui.showRecipes(model.getRecipes());
        }
    }

    private void refreshAll() {
        ui.showRecipes(model.getRecipes());
        ui.setActionsEnabled(false);
    }
}
