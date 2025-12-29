package be.uantwerpen.sd.project.viewfx;

import java.util.ArrayList;
import java.util.List;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
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
    private final ListView<Recipe> RecipeList = new ListView<>();

    private final Button addRecipeButton = new Button("Add");
    private final Button editRecipeButton = new Button("Edit");
    private final Button deleteRecipeButton = new Button("Delete");

    private final TextField nameField = new TextField();
    private final TextField descriptionField = new TextField();
    private final VBox ingredientsBox = new VBox(5);
    private final VBox formBox = new VBox(8);

    private View logic;

    public MealPlannerView() {

        GridPane calendar = displayWeeklyPlan();
        Label leftTitle = new Label("Weekly plan");
        VBox left = new VBox(8, leftTitle, calendar);
        left.setPadding(new Insets(8));
        setLeft(left);
        
        Label centerTitle = new Label("Recipes");
        // HBox centerHeader = new HBox(8, centerTitle);
        VBox center = new VBox(8, centerTitle, RecipeList);
        center.setPadding(new Insets(8));
        setCenter(center);

        HBox actions = new HBox(8, addRecipeButton, editRecipeButton, deleteRecipeButton);
        actions.setPadding(new Insets(8));

        buildRecipeForm();

        VBox right = new VBox(12, actions, formBox);
        right.setPadding(new Insets(8));
        setRight(right);

        RecipeList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (logic != null) logic.onSelectionChanged(newV);
            toggleForm(false);
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

        toggleForm(false);
        
    }

    public void attachLogic(View logic) {
        this.logic = logic;
    }

    private void buildRecipeForm() {
        ScrollPane scrollPane = new ScrollPane(ingredientsBox);
        scrollPane.setPrefHeight(100);
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
        grid.add(scrollPane, 0, 3, 2, 1);
        grid.add(addIngredientBtn, 1, 4);
        

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.setOnAction(e -> {
            if (logic != null) logic.onAddRecipe(nameField.getText(),descriptionField.getText(),getIngredients(ingredientsBox),new ArrayList<>());
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

        dlg.getDialogPane().setContent(gp);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dlg.setResultConverter(bt -> {
            if (bt == ButtonType.OK) return Recipe.builder().build().addId(sel.getId());
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
        TextField title = new TextField();
        title.setPromptText(nameS);

        TextField amount = new TextField();
        amount.setPromptText(amountS);

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
                    } catch (Exception e) {showError("Please use a number for amount");}
                }
            }
        }

        return ingredients;
    }

    private GridPane displayWeeklyPlan() {
        WeeklyPlan plan = new WeeklyPlan();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        String[] dayNames = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        MealType[] meals = {MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK};

        // Headers voor maaltijden
        for (int col = 0; col < meals.length; col++) {
            grid.add(new Label(meals[col].name().toLowerCase()), col + 1, 0);
        }

        // Headers voor dagen
        for (int row = 0; row < dayNames.length; row++) {
            grid.add(new Label(dayNames[row]), 0, row + 1);
        }

        // Vul de grid met data
        for (int row = 0; row < 7; row++) {
            DayPlan day = plan.getDay(dayNames[row].toLowerCase());

            for (int col = 0; col < meals.length; col++) {
                Label label;

                if (day == null) {
                    label = new Label("No plan");
                } else {
                    Recipe recipe = day.getMeal(meals[col]);
                    label = new Label(recipe == null ? "No meal" : recipe.getTitle()+"test");
                }

                label.setPrefWidth(80);
                label.setAlignment(Pos.CENTER);
                grid.add(label, col + 1, row + 1);
            }
        }

        return grid;
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

    // @Override
    // public void showEntries(java.util.List<RegisterEntry> entries) {
    //     javafx.application.Platform.runLater(() -> {
    //         java.util.List<RegisterEntry> sorted = new java.util.ArrayList<>(entries);
    //         sorted.sort(java.util.Comparator.comparing(RegisterEntry::timestamp));
    //         entryList.getItems().setAll(sorted);
    //     });
    // }

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
            addRecipeButton.setDisable(!hasSelection);
            boolean hasEntry = RecipeList.getSelectionModel().getSelectedItem() != null;
            // editEntryButton.setDisable(!hasEntry);
            // deleteEntryButton.setDisable(!hasEntry);
        });
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, message).showAndWait());
    }
}