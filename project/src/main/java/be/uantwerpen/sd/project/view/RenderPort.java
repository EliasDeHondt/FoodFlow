package be.uantwerpen.sd.project.view;

import java.util.List;

import be.uantwerpen.sd.project.builder.Recipe;

public interface RenderPort {
    void showRecipes(List<Recipe> recipes);

    void showMeals(Recipe[][] meals);

    void clearInputs();

    void setActionsEnabled(boolean hasSelection);

    void showError(String message);
}
