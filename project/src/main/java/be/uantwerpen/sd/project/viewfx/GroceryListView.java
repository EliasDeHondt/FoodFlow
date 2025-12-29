package be.uantwerpen.sd.project.viewfx;

import java.util.List;

import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.view.RenderPort;
import be.uantwerpen.sd.project.view.View;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GroceryListView extends BorderPane implements RenderPort {
    private final ListView<Ingredient> GroceryList = new ListView<>();

    private final Button changePageButton = new Button("MealPlanner");

    private final Button checkOffButton = new Button("Check off");
    private final Button addGroceryButton = new Button("Add");

    private final TextField nameField = new TextField();
    private final TextField qtyField = new TextField();
    private final ComboBox<String> UnitBox = new ComboBox<>();
    private final VBox formBox = new VBox(8);

    private View logic;

    public GroceryListView() {

        VBox top = new VBox(12, changePageButton);
        top.setPadding(new Insets(8));
        setTop(top);

        Label centerTitle = new Label("Groceries");
        // HBox centerHeader = new HBox(8, centerTitle);
        VBox center = new VBox(8, centerTitle, GroceryList);
        center.setPadding(new Insets(8));
        setCenter(center);

        HBox actions = new HBox(8, addGroceryButton, checkOffButton);
        actions.setPadding(new Insets(8));

        buildGroceryForm();

        VBox right = new VBox(12, actions, formBox);
        right.setPadding(new Insets(8));
        setRight(right);

        GroceryList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (logic != null) logic.onSelectionChanged(newV);
            toggleForm(false);
        });

        GroceryList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Ingredient e, boolean empty) {
                super.updateItem(e, empty);
                if (empty || e == null) {
                    setText(null);
                    setOnMouseClicked(ev -> GroceryList.getSelectionModel().clearSelection());
                    return;
                }
                setText(e.getName()+" "+e.getQuantity()+" "+e.getUnit());
                setOnMouseClicked(null);
            }
        });

        changePageButton.setOnAction(e -> {
            if (logic != null) logic.ToMealPlanner();
        });

        addGroceryButton.setOnAction(e -> toggleForm(true));

        checkOffButton.setOnAction(e -> {
            Ingredient sel = GroceryList.getSelectionModel().getSelectedItem();
            if (logic != null) logic.onDeleteSelected(sel);
        });

        toggleForm(false);
        addBackgroundDeselect(GroceryList);

    }
    
    public void attachLogic(View logic) {
        this.logic = logic;
    }

    private void buildGroceryForm() {
        ObservableList<String> units = FXCollections.observableArrayList(
                "mg", "g", "kg", "ml", "l", "pcs"
        );
        UnitBox.setItems(units);
        UnitBox.setEditable(false);

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.add(new Label("Name"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Amount"), 0, 1);
        grid.add(qtyField, 1, 1);
        grid.add(new Label("Units"), 0, 2);
        grid.add(UnitBox, 1, 2);

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.setOnAction(e -> {
            if (logic != null) logic.onAddgrocery(nameField.getText(),qtyField.getText(),UnitBox.getValue());
        });

        cancel.setOnAction(e -> {
            nameField.clear();
            qtyField.clear();
            UnitBox.getSelectionModel().clearSelection();
            toggleForm(false);
        });

        HBox buttons = new HBox(8, save, cancel);
        formBox.getChildren().setAll(new Label("Add grocery"), grid, buttons);
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
    public void showGroceries(List<Ingredient> g) {
        Platform.runLater(() -> {
            Ingredient prev = GroceryList.getSelectionModel().getSelectedItem();
            GroceryList.getItems().setAll(g);
            if (prev != null) {
                for (Ingredient r : g) {
                    if (prev.equals(r)) {
                        GroceryList.getSelectionModel().select(r);
                        break;
                    }
                }
            }
            if (logic != null) {
                logic.onSelectionChanged(GroceryList.getSelectionModel().getSelectedItem());
            }
        });
    }
    @Override
    public void showRecipes(List<Recipe> recipes) {}
    @Override
    public void showMeals(Recipe[][] meals) {}
    @Override
    public void clearInputs() {
        Platform.runLater(() -> {
            nameField.clear();
            qtyField.clear();
            UnitBox.getSelectionModel().clearSelection();
            toggleForm(false);
        });
    }
    @Override
    public void setActionsEnabled(boolean hasSelection) {
        checkOffButton.setDisable(!hasSelection);
    }
    @Override
    public void showError(String message) {
        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, message).showAndWait());
    }
}
