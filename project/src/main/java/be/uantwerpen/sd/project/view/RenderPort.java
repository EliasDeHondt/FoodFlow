/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.view;
import java.util.List;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.builder.Recipe;

public interface RenderPort {
    void showRecipes(List<Recipe> recipes);

    void showGroceries(List<Ingredient> g);

    void showMeals(Recipe[][] meals);

    void clearInputs();

    void setActionsEnabled(boolean hasSelection);

    void showError(String message);
}