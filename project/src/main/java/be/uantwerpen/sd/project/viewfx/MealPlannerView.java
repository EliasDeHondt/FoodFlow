package be.uantwerpen.sd.project.viewfx;

import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.view.RenderPort;
import be.uantwerpen.sd.project.view.View;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MealPlannerView extends BorderPane implements RenderPort{
    private View logic;

    private final VBox formBox = new VBox(8);

    public MealPlannerView() {
        displayWeeklyPlan();
    }

    public void attachLogic(View logic) {
        this.logic = logic;
    }


    // @Override
    private void displayWeeklyPlan() {
        //not final just for testing
        WeeklyPlan plan = new WeeklyPlan();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        String[] dayNames = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        MealType[] meals = {MealType.BREAKFAST,MealType.LUNCH,MealType.DINNER,MealType.SNACK};

        for (int col = 0; col < 7; col++) {
            grid.add(new Label(dayNames[col]), col + 1, 0);
        }

        for (int row = 0; row < meals.length; row++) {
            grid.add(new Label(meals[row].name()), 0, row + 1);
        }

        for (int col = 0; col < 7; col++) {
            DayPlan day = plan.getDay(dayNames[col]);
            for (int row = 0; row < meals.length; row++) {
                Recipe recipe = day.getMeal(meals[row]);
                Label label = new Label(recipe.getTitle());
                label.setMinWidth(100);
                label.setAlignment(Pos.CENTER);
                grid.add(label, col + 1, row + 1);
            }
        }

    }
    // @Override
    // public void displayGroceryList(Map<String, Double> items,DayPlan day) {

    // }

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
