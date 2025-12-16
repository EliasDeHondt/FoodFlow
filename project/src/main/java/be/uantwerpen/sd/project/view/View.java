package be.uantwerpen.sd.project.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import be.uantwerpen.sd.project.controller.Controller;
import be.uantwerpen.sd.project.model.Model;

public class View implements  PropertyChangeListener{
    private final Model model;
    private final Controller controller;
    private final RenderPort ui;
    
    public View(Model model, Controller controller, RenderPort ui) {
        this.model = model;
        this.controller = controller;
        this.ui = ui;
        this.model.addPropertyChangeListener(this);
        refreshAll();
    }

    public void setStrategy(String strategy) {
        try {
            this.controller.setStrategy(strategy);
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    public void generateWeeklyPlan() {
        try {
            this.controller.generateWeeklyPlan();
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    private void refreshAll() {}
}
