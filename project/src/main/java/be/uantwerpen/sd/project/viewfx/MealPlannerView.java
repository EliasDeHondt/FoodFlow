/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.viewfx;

import java.util.ArrayList;
import java.util.List;

import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.view.RenderPort;
import be.uantwerpen.sd.project.view.View;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MealPlannerView extends BorderPane implements RenderPort {
    private final ListView<Recipe> RecipeList = new ListView<>();
    private final Recipe[][] plan = new Recipe[7][4];
    
    private final Button changePageButton = new Button("Grocerylist");

    private final Button addRecipeButton = new Button("Add");
    private final Button editRecipeButton = new Button("Edit");
    private final Button deleteRecipeButton = new Button("Delete");

    private final Button generatePlanButton = new Button("Generate Plan");
    private final Button removeMealButton = new Button("Remove");
    private final Button changeMealButton = new Button("Change");

    private final VBox MealBox = new VBox(8);

    private final TextField nameField = new TextField();
    private final TextField descriptionField = new TextField();
    private final VBox ingredientsBox = new VBox(5);
    private final VBox formBox = new VBox(8);

    private View logic;

    private int selectedDayIndex = -1;
    private int selectedMealIndex = -1;
    private Label selectedMealLabel;

    public MealPlannerView() {

        buildWeeklyPlan();
        Label leftTitle = new Label("Weekly plan");
        HBox leftHeader = new HBox(8, leftTitle, generatePlanButton, changeMealButton, removeMealButton);
        VBox left = new VBox(8, leftHeader, MealBox);
        left.setPadding(new Insets(8));
        setLeft(left);
        
        Label centerTitle = new Label("Recipes");
        VBox center = new VBox(8, centerTitle, RecipeList);
        center.setPadding(new Insets(8));
        setCenter(center);

        HBox actions = new HBox(8, addRecipeButton, editRecipeButton, deleteRecipeButton);
        actions.setPadding(new Insets(8));

        buildRecipeForm();

        VBox right = new VBox(12, actions, formBox);
        right.setPadding(new Insets(8));
        setRight(right);

        VBox top = new VBox(12, changePageButton);
        top.setPadding(new Insets(8));
        setTop(top);

        RecipeList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (logic != null) logic.onSelectionChanged(newV);
            toggleForm(false);
        });

        RecipeList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Recipe e, boolean empty) {
                super.updateItem(e, empty);
                if (empty || e == null) {
                    setText(null);
                    setOnMouseClicked(ev -> RecipeList.getSelectionModel().clearSelection());
                    return;
                }
                setText(e.getTitle());
                setOnMouseClicked(null);
            }
        });


        changePageButton.setOnAction(e -> {
            if (logic != null) logic.ToGroceryList();
        });
        
        addRecipeButton.setOnAction(e -> toggleForm(true));

        editRecipeButton.setOnAction(e -> {
            Recipe sel = RecipeList.getSelectionModel().getSelectedItem();
            if (sel != null) showEditRecipeDialog(sel);
        });

        deleteRecipeButton.setOnAction(e -> {
            Recipe sel = RecipeList.getSelectionModel().getSelectedItem();
            if (logic != null) logic.onDeleteSelected(sel);
        });

        generatePlanButton.setOnAction(e -> {
            if (logic != null) logic.onGenerateWeeklyPlan();
        });

        removeMealButton.setOnAction(e -> {
            if (selectedDayIndex >= 0 && selectedMealIndex >= 0) {
                String[] dayNames = {"mon","tue","wed","thu","fri","sat","sun"};
                MealType[] meals = {MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK};
                if (logic != null) {
                    logic.onSetRecipe(dayNames[selectedDayIndex], meals[selectedMealIndex]);
                }
            }
        });

        changeMealButton.setOnAction(e -> {
            if (selectedDayIndex >= 0 && selectedMealIndex >= 0) {
                Recipe sel = RecipeList.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    String[] dayNames = {"mon","tue","wed","thu","fri","sat","sun"};
                    MealType[] meals = {MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK};
                    if (logic != null) {
                        logic.onSetRecipe(dayNames[selectedDayIndex], meals[selectedMealIndex], sel);
                    }
                }
            }
        });

        toggleForm(false);
        addBackgroundDeselect(RecipeList);
        
    }

    public void attachLogic(View logic) {
        this.logic = logic;
    }

    private void buildRecipeForm() {
        ScrollPane scrollPane = new ScrollPane(ingredientsBox);
        scrollPane.setPrefHeight(100);
        scrollPane.setFitToWidth(true);

        String[] tags = {"Vegan", "Breakfast", "Lunch", "Dinner", "Snack"};

        FlowPane tagPane = new FlowPane();
        tagPane.setHgap(5);
        tagPane.setVgap(5);
        tagPane.setPrefWrapLength(0);
        for (String tag : tags) {
            CheckBox checkBox = new CheckBox(tag);
            tagPane.getChildren().add(checkBox);
        }

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
        grid.add(scrollPane, 0, 3, 2, 1);
        grid.add(addIngredientBtn, 1, 4);
        grid.add(new Label("Tags"), 0, 5);
        grid.add(tagPane, 0, 6, 2, 1);
    

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.setOnAction(e -> {
            if (logic != null) logic.onAddRecipe(nameField.getText(),descriptionField.getText(),getIngredients(ingredientsBox),getTags(tagPane));
        });

        cancel.setOnAction(e -> {
            nameField.clear();
            descriptionField.clear();
            ingredientsBox.getChildren().clear();
            toggleForm(false);
        });

        HBox buttons = new HBox(8, save, cancel);
        formBox.getChildren().setAll(new Label("Add recipe"), grid, buttons);
    }

    private void showEditRecipeDialog(Recipe sel) {
        Dialog<Recipe> dlg = new Dialog<>();
        dlg.setTitle("Edit Recipe");
        TextField name = new TextField(sel.getTitle());
        TextField description = new TextField(sel.getDescription());

        VBox ingredients = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(ingredients);
        scrollPane.setPrefHeight(100);
        scrollPane.setFitToWidth(true);
        for (Ingredient i : sel.getIngredients()) {
            addIngredientRow(ingredients,i.getName(),i.getQuantity().toString(),i.getUnit());
        }

        String[] tags = {"Vegan", "Breakfast", "Lunch", "Dinner", "Snack"};
        List<String> objTags = sel.getTags();

        FlowPane tagPane = new FlowPane();
        tagPane.setHgap(5);
        tagPane.setVgap(5);
        tagPane.setPrefWrapLength(0);
        for (String tag : tags) {
            CheckBox checkBox = new CheckBox(tag);
            tagPane.getChildren().add(checkBox);
        }
        for (Node node : tagPane.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                if (objTags.contains(checkBox.getText())) {
                    checkBox.setSelected(true);
                }
            }
        }

        Button addIngredientBtn = new Button("Add Ingredient");
        addIngredientBtn.setOnAction(e -> addIngredientRow(ingredients));

        GridPane gp = new GridPane();
        gp.setHgap(8);
        gp.setVgap(8);
        gp.add(new Label("Name"), 0, 0);
        gp.add(name, 1, 0);
        gp.add(new Label("Description"), 0, 1);
        gp.add(description, 1, 1);
        gp.add(new Label("Ingredients"), 0, 2);
        gp.add(scrollPane, 0, 3, 2, 1);
        gp.add(addIngredientBtn, 1, 4);
        gp.add(new Label("Tags"), 0, 5);
        gp.add(tagPane, 0, 6, 2, 1);

        dlg.getDialogPane().setContent(gp);
        dlg.getDialogPane().setPrefSize(500, 400);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dlg.setResultConverter(bt -> {
            if (bt == ButtonType.OK) return Recipe.builder()
                                                .title(name.getText())
                                                .description(description.getText())
                                                .ingredients(getIngredients(ingredients))
                                                .tags(getTags(tagPane))
                                                .build().addId(sel.getId());
            return null;
        });
        dlg.showAndWait().ifPresent(e -> {
            if (logic != null) logic.onUpdateRecipe(e);
        });
        
    }

    private void addIngredientRow(VBox ingredientsBox) {
        ComboBox<String> UnitBox = new ComboBox<>();
        ObservableList<String> units = FXCollections.observableArrayList(
                "mg", "g", "kg", "ml", "l", "pcs"
        );
        UnitBox.setItems(units);
        UnitBox.setEditable(false);

        HBox row = new HBox(5);
        TextField title = new TextField();
        title.setPromptText("Ingredient");

        TextField amount = new TextField();
        amount.setPromptText("Amount");

        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction(e -> ingredientsBox.getChildren().remove(row));

        row.getChildren().addAll(title, amount, UnitBox, removeBtn);
        ingredientsBox.getChildren().add(row);
    }
    
    private void addIngredientRow(VBox ingredientsBox,String nameS, String amountS, String unit) {
        ComboBox<String> UnitBox = new ComboBox<>();
        ObservableList<String> units = FXCollections.observableArrayList(
                "mg", "g", "kg", "ml", "l", "pcs"
        );
        UnitBox.setItems(units);
        UnitBox.getSelectionModel().select(unit);
        UnitBox.setEditable(false);

        HBox row = new HBox(5);
        TextField title = new TextField(nameS);
        

        TextField amount = new TextField(amountS);
        

        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction(e -> ingredientsBox.getChildren().remove(row));

        row.getChildren().addAll(title, amount, UnitBox, removeBtn);
        ingredientsBox.getChildren().add(row);
    }

    private List<Ingredient> getIngredients(VBox ingredientsBox) {
        List<Ingredient> ingredients = new ArrayList<>();

        for (var node : ingredientsBox.getChildren()) {
            if (node instanceof HBox row) {
                // Assuming first TextField = name, second = amount
                TextField nameT = (TextField) row.getChildren().get(0);
                TextField amountT = (TextField) row.getChildren().get(1);
                @SuppressWarnings("unchecked")
                ComboBox<String> unitsT = (ComboBox<String>) row.getChildren().get(2);

                String name = nameT.getText().trim();
                String amountText = amountT.getText().trim();
                String units = unitsT.getValue() != null ? unitsT.getValue() : "";
                if (amountText.isEmpty()) {
                    amountText = "1";
                }
                if (units == null) {
                    units = "";
                }
                if (!name.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountText);
                        ingredients.add(new Ingredient(name, amount, units));
                    } catch (NumberFormatException e) {showError("Please use a number for amount");}
                }
            }
        }

        return ingredients;
    }

    private List<String> getTags(FlowPane tagPane) {
        List<String> selectedTags = tagPane.getChildren().stream()
        .filter(node -> node instanceof CheckBox)
        .map(node -> (CheckBox) node)
        .filter(CheckBox::isSelected)
        .map(CheckBox::getText)
        .map(String::toLowerCase)
        .toList();
        return selectedTags;
    }

    private void buildWeeklyPlan() {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        String[] dayNames = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        MealType[] meals = {MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK};

        for (int col = 0; col < meals.length; col++) {
            grid.add(new Label(meals[col].name().toLowerCase()), col + 1, 0);
        }

        for (int row = 0; row < dayNames.length; row++) {
            grid.add(new Label(dayNames[row]), 0, row + 1);
        }

        for (int row = 0; row < 7; row++) {

            for (int col = 0; col < meals.length; col++) {
                Label label;

                Recipe recipe = plan[row][col];
                label = new Label(recipe == null ? "No meal" : recipe.getTitle());

                label.setPrefWidth(80);
                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-border-color: lightgray; -fx-padding: 5; -fx-cursor: hand;");

                final int dayIndex = row;
                final int mealIndex = col;
                label.setOnMouseClicked(e -> {
                    if (selectedMealLabel != null) {
                        selectedMealLabel.setStyle("-fx-border-color: lightgray; -fx-padding: 5; -fx-cursor: hand;");
                    }
                    selectedDayIndex = dayIndex;
                    selectedMealIndex = mealIndex;
                    selectedMealLabel = label;
                    label.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-padding: 5; -fx-cursor: hand;");
                });

                grid.add(label, col + 1, row + 1);
            }
        }

        MealBox.getChildren().setAll(grid);
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
    public void showRecipes(List<Recipe> recipes) {
        Platform.runLater(() -> {
            Recipe prev = RecipeList.getSelectionModel().getSelectedItem();
            RecipeList.getItems().setAll(recipes);
            if (prev != null) {
                for (Recipe r : recipes) {
                    if (prev.equals(r)) {
                        RecipeList.getSelectionModel().select(r);
                        break;
                    }
                }
            }
            if (logic != null) {
                logic.onSelectionChanged(RecipeList.getSelectionModel().getSelectedItem());
            }
        });
    }

    @Override
    public void showMeals(Recipe[][] meals) {
        for (int i = 0; i < plan.length; i++) {
            System.arraycopy(meals[i], 0, plan[i], 0, plan[i].length);
        }
        Platform.runLater(() -> {
            buildWeeklyPlan();
            selectedDayIndex = -1;
            selectedMealIndex = -1;
            selectedMealLabel = null;
            changeMealButton.setDisable(true);
            removeMealButton.setDisable(true);
        });
    }
    @Override  
    public void showGroceries(List<Ingredient> g) {}

    @Override
    public void clearInputs() {
        Platform.runLater(() -> {
            nameField.clear();
            descriptionField.clear();
            ingredientsBox.getChildren().clear();
            toggleForm(false);
        });
    }

    @Override
    public void setActionsEnabled(boolean hasSelection) {
        Platform.runLater(() -> {
            editRecipeButton.setDisable(!hasSelection);
            deleteRecipeButton.setDisable(!hasSelection);
            changeMealButton.setDisable(!hasSelection || selectedDayIndex < 0 || selectedMealIndex < 0);
        });
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, message).showAndWait());
    }
}