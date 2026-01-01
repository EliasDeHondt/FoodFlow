/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project;
import be.uantwerpen.sd.project.controller.Controller;
import be.uantwerpen.sd.project.controller.MealPlannerController;
import be.uantwerpen.sd.project.model.MealPlannerDB;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.view.View;
import be.uantwerpen.sd.project.viewfx.GroceryListView;
import be.uantwerpen.sd.project.viewfx.MealPlannerView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewApp extends Application {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        showStartupDialog(stage);
    }

    private void showStartupDialog(Stage stage) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("FoodFlow - Welcome");
        dialogStage.setWidth(400);
        dialogStage.setHeight(250);
        dialogStage.setResizable(false);
        dialogStage.centerOnScreen();

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f8f9fa;");

        Label titleLabel = new Label("Welcome to FoodFlow");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label descriptionLabel = new Label("Would you like to start with demo data?");
        descriptionLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #666666; -fx-wrap-text: true;");

        Button demoButton = new Button("Demo");
        demoButton.setPrefWidth(150);
        demoButton.setStyle(
            "-fx-font-size: 13; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-border-radius: 4; " +
            "-fx-cursor: hand;"
        );

        Button normalButton = new Button("Normal");
        normalButton.setPrefWidth(150);
        normalButton.setStyle(
            "-fx-font-size: 13; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-color: #95a5a6; " +
            "-fx-text-fill: white; " +
            "-fx-border-radius: 4; " +
            "-fx-cursor: hand;"
        );

        demoButton.setOnAction(e -> {
            dialogStage.close();
            startApplication(stage, true);
        });

        normalButton.setOnAction(e -> {
            dialogStage.close();
            startApplication(stage, false);
        });

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(demoButton, normalButton);

        root.getChildren().addAll(titleLabel, descriptionLabel, buttonBox);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    private void startApplication(Stage stage, boolean loadDemo) {
        Model model = new MealPlannerDB();
        Controller controller = new MealPlannerController(model);

        if (loadDemo) DemoDataLoader.loadDemoRecipes(controller);

        MealPlannerView view = new MealPlannerView();
        GroceryListView view2 = new GroceryListView();

        View viewLogic = new View(model, controller, view, view2, stage);

        view.attachLogic(viewLogic);
        view2.attachLogic(viewLogic);

        Scene scene = new Scene(view, WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setTitle("FoodFlow - Meal Planner");
        stage.setScene(scene);
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setResizable(false);
        stage.centerOnScreen();

        stage.show();
    }
}