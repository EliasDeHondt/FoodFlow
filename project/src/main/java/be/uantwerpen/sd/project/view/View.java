package be.uantwerpen.sd.project.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import be.uantwerpen.sd.project.controller.Controller;
import be.uantwerpen.sd.project.model.Model;

public class View implements  PropertyChangeListener{
    
    public View(Model model, Controller controller, RenderPort ui) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}
}
