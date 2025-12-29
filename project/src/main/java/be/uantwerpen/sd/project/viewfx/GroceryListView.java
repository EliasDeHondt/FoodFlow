package be.uantwerpen.sd.project.viewfx;

import java.util.List;

import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.view.RenderPort;
import be.uantwerpen.sd.project.view.View;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GroceryListView extends BorderPane implements RenderPort {
    private final Button changePageButton = new Button("MealPlanner");

    private View logic;

    public GroceryListView() {

        VBox top = new VBox(12, changePageButton);
        top.setPadding(new Insets(8));
        setTop(top);

        changePageButton.setOnAction(e -> {
            if (logic != null) logic.ToMealPlanner();
        });
    }


    

    public void attachLogic(View logic) {
        this.logic = logic;
    }








    @Override
    public void showRecipes(List<Recipe> recipes) {}
    @Override
    public void showMeals(Recipe[][] meals) {}
    @Override
    public void clearInputs() {}
    @Override
    public void setActionsEnabled(boolean hasSelection) {}
    @Override
    public void showError(String message) {}
}
