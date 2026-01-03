/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.strategy;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.singleton.RecipeRepository;

public interface MealPlanningStrategy {
    public void generatePlan(WeeklyPlan weeklyplan,RecipeRepository recipeRepo);
}