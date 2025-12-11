package be.uantwerpen.sd.project;

import be.uantwerpen.sd.project.controller.Controller;
import be.uantwerpen.sd.project.controller.MealPlannerController;
import be.uantwerpen.sd.project.model.MealPlannerDB;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.view.View;
import be.uantwerpen.sd.project.viewfx.MealPlannerView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewApp extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {

        Controller controller = new MealPlannerController();

        Model model = new MealPlannerDB();
        MealPlannerView view = new MealPlannerView();

        View viewLogic = new View(model, controller, view);

        view.attachLogic(viewLogic);

        Scene scene = new Scene(view, 960, 560);
        stage.setTitle("MealPlanner");
        stage.setScene(scene);
        stage.show();
    }

    
}
