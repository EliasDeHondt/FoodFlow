package be.uantwerpen.sd.project.viewfx;

import java.util.HashMap;
import java.util.Map;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.builder.RecipeBuilder;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.view.RenderPort;
import be.uantwerpen.sd.project.view.View;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MealPlannerView extends BorderPane implements RenderPort {
    

    private final TextField nameField = new TextField();
    private final TextField descriptionField = new TextField();
    private final VBox formBox = new VBox(8);

    private View logic;

    public MealPlannerView() {

        buildRecipeForm();
        displayWeeklyPlan();

        
    }

    public void attachLogic(View logic) {
        this.logic = logic;
    }

    private void buildRecipeForm() {
        VBox ingredientsBox = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(ingredientsBox);
        scrollPane.setPrefHeight(200);
        scrollPane.setFitToWidth(true);

        Button addIngredientBtn = new Button("Add Ingredient");
        addIngredientBtn.setOnAction(e -> addIngredientRow(ingredientsBox));

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.add(new Label("Name"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Ingredients"), 0, 2);
        grid.add(scrollPane, 1, 2);
        grid.add(addIngredientBtn, 1, 3);
        

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.setOnAction(e -> {
            if (logic != null) logic.onAddRecipe(nameField.getText(),descriptionField.getText(),getIngredients(ingredientsBox));
        });

        cancel.setOnAction(e -> {
            nameField.clear();
            descriptionField.clear();
            toggleForm(false);
        });

        HBox buttons = new HBox(8, save, cancel);
        formBox.getChildren().setAll(new Label("Add recipe"), grid, buttons);
    }

    private void addIngredientRow(VBox ingredientsBox) {
        HBox row = new HBox(5);
        TextField title = new TextField();
        title.setPromptText("Ingredient");

        TextField amount = new TextField();
        amount.setPromptText("Amount");

        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction(e -> ingredientsBox.getChildren().remove(row));

        row.getChildren().addAll(title, amount, removeBtn);
        ingredientsBox.getChildren().add(row);
    }

    private Map<String, Double> getIngredients(VBox ingredientsBox) {
        Map<String, Double> ingredientsMap = new HashMap<>();

        for (var node : ingredientsBox.getChildren()) {
            if (node instanceof HBox row) {
                // Assuming first TextField = name, second = amount
                TextField nameT = (TextField) row.getChildren().get(0);
                TextField amountT = (TextField) row.getChildren().get(1);

                String name = nameT.getText().trim();
                String amountText = amountT.getText().trim();
                if (amountText.isEmpty()) {
                    amountText = "1";
                }
                if (!name.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountText);
                        ingredientsMap.put(name, amount);  
                    } catch (Exception e) {showError(e.getMessage());}
                }
            }
        }

        return ingredientsMap;
    }

    private void displayWeeklyPlan() {
        WeeklyPlan plan = new WeeklyPlan();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        String[] dayNames = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        MealType[] meals = {MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK};

        // Headers voor dagen
        for (int col = 0; col < 7; col++) {
            grid.add(new Label(dayNames[col]), col + 1, 0);
        }

        // Headers voor maaltijden
        for (int row = 0; row < meals.length; row++) {
            grid.add(new Label(meals[row].name()), 0, row + 1);
        }

        // Vul de grid met data
        for (int col = 0; col < 7; col++) {
            DayPlan day = plan.getDay(dayNames[col]);

            for (int row = 0; row < meals.length; row++) {
                Label label;

                if (day == null) {
                    label = new Label("No plan");
                } else {
                    Recipe recipe = day.getMeal(meals[row]);
                    if (recipe == null) {
                        label = new Label("No meal");
                    } else {
                        label = new Label(recipe.getTitle());
                    }
                }

                label.setMinWidth(100);
                label.setAlignment(Pos.CENTER);
                grid.add(label, col + 1, row + 1);
            }
        }

        this.setCenter(grid);
    }

    private void toggleForm(boolean show) {
        for (Node n : formBox.getChildren()) n.setDisable(!show);
        formBox.setVisible(show);
        formBox.setManaged(show);
    }

    private void addBackgroundDeselect(ListView<?> lv) {
        lv.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            Node n = e.getPickResult().getIntersectedNode();
            while (n != null && n != lv && !(n instanceof ListCell)) n = n.getParent();
            if (n == lv || n == null) {
                lv.getSelectionModel().clearSelection();
                e.consume();
            }
        });
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, message).showAndWait());
    }
}