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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    private void refreshAll() {}
}
