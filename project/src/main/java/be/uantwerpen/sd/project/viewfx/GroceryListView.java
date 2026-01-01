/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.viewfx;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import be.uantwerpen.sd.project.Ingredient;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GroceryListView extends BorderPane implements RenderPort {
    private final ListView<Ingredient> GroceryList = new ListView<>();
    private final Map<String, Boolean> checkedItems = new HashMap<>();

    private final Button changePageButton = new Button("Meal Planner");
    private final Button addGroceryButton = new Button("Add Item");

    private final TextField nameField = new TextField();
    private final TextField qtyField = new TextField();
    private final ComboBox<String> UnitBox = new ComboBox<>();
    private final VBox formBox = new VBox(8);

    private View logic;

    public GroceryListView() {
        VBox top = new VBox(12, changePageButton);
        top.setPadding(new Insets(12));
        top.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        changePageButton.setStyle("-fx-font-size: 12; -fx-padding: 10 20 10 20; -fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-border-radius: 4;");
        setTop(top);

        Label centerTitle = new Label("Shopping List");
        centerTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        VBox centerHeader = new VBox(centerTitle);
        centerHeader.setPadding(new Insets(12));
        centerHeader.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        VBox center = new VBox();
        center.getChildren().addAll(centerHeader, GroceryList);
        center.setStyle("-fx-background-color: #ffffff;");
        VBox.setVgrow(GroceryList, Priority.ALWAYS);
        setCenter(center);

        VBox right = new VBox(12);
        right.setPadding(new Insets(12));
        right.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #d0d0d0; -fx-border-width: 0 0 0 1;");

        addGroceryButton.setStyle("-fx-font-size: 12; -fx-padding: 10 16 10 16; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand; -fx-border-radius: 4;");
        addGroceryButton.setOnAction(e -> toggleForm(true));

        buildGroceryForm();

        right.getChildren().addAll(addGroceryButton, formBox);
        setRight(right);

        GroceryList.setCellFactory(lv -> new ListCell<Ingredient>() {
            private final CheckBox checkBox = new CheckBox();
            private final Label label = new Label();
            private final HBox hbox = new HBox(10);

            {
                checkBox.setPrefWidth(30);
                checkBox.setStyle("-fx-font-size: 14;");
                label.setFont(Font.font("Segoe UI", 13));
                label.setWrapText(true);

                hbox.setPadding(new Insets(10, 12, 10, 12));
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0; -fx-background-color: white;");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                hbox.getChildren().addAll(checkBox, label, spacer);
            }

            @Override
            protected void updateItem(Ingredient item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                String itemName = item.getName();
                String text = String.format("%s  •  %.1f %s",
                    itemName,
                    item.getQuantity(),
                    item.getUnit());
                label.setText(text);

                boolean isChecked = checkedItems.getOrDefault(itemName, false);
                checkBox.setSelected(isChecked);

                if (isChecked) {
                    label.setStyle("-fx-text-fill: #999999; -fx-strikethrough: true;");
                } else {
                    label.setStyle("-fx-text-fill: #1a1a1a; -fx-strikethrough: false;");
                }

                checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    checkedItems.put(itemName, newVal);
                    if (newVal) {
                        label.setStyle("-fx-text-fill: #999999; -fx-strikethrough: true;");
                    } else {
                        label.setStyle("-fx-text-fill: #1a1a1a; -fx-strikethrough: false;");
                    }
                });

                setGraphic(hbox);
            }
        });

        GroceryList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (logic != null) logic.onSelectionChanged(newV);
            toggleForm(false);
        });

        changePageButton.setOnAction(e -> {
            if (logic != null) logic.ToMealPlanner();
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

        nameField.setStyle("-fx-padding: 8 10 8 10; -fx-border-color: #e0e0e0; -fx-border-radius: 3; -fx-font-size: 12;");
        nameField.setPromptText("Item name");

        qtyField.setStyle("-fx-padding: 8 10 8 10; -fx-border-color: #e0e0e0; -fx-border-radius: 3; -fx-font-size: 12;");
        qtyField.setPromptText("Quantity");

        UnitBox.setStyle("-fx-padding: 8 10 8 10; -fx-border-color: #e0e0e0; -fx-border-radius: 3; -fx-font-size: 12;");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.add(new Label("Name"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Amount"), 0, 1);
        grid.add(qtyField, 1, 1);
        grid.add(new Label("Unit"), 0, 2);
        grid.add(UnitBox, 1, 2);

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.setStyle("-fx-padding: 8 16 8 16; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12;");
        cancel.setStyle("-fx-padding: 8 16 8 16; -fx-background-color: #95a5a6; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12;");

        save.setOnAction(e -> {
            if (logic != null) logic.onAddgrocery(nameField.getText(), qtyField.getText(), UnitBox.getValue());
            clearInputs();
        });

        cancel.setOnAction(e -> {
            clearInputs();
        });

        HBox buttons = new HBox(8, save, cancel);
        formBox.getChildren().setAll(new Label("Add New Item"), grid, buttons);
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
            GroceryList.getItems().setAll(g);
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
    public void setActionsEnabled(boolean hasSelection) {}

    @Override
    public void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}