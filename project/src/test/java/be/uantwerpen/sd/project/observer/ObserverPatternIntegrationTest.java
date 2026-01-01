/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.observer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.controller.Controller;
import be.uantwerpen.sd.project.controller.MealPlannerController;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.model.MealPlannerDB;
import be.uantwerpen.sd.project.Singleton.RecipeRepository;

public class ObserverPatternIntegrationTest {
    private Model model;
    private Controller controller;
    private Recipe testRecipe;

    @BeforeEach
    public void setUp() {
        RecipeRepository repo = RecipeRepository.getInstance();
        List<Recipe> allRecipes = new ArrayList<>(repo.getAll());
        for (Recipe r : allRecipes) {
            repo.removeRecipe(r.getId());
        }

        model = new MealPlannerDB();
        controller = new MealPlannerController(model);

        testRecipe = Recipe.builder()
            .title("Test")
            .description("Test recipe for Observer pattern")
            .ingredients(List.of(
                new Ingredient("A", 1.0, "gram"),
                new Ingredient("B", 2.0, "kilogram")
            ))
            .tags(List.of("vegetarian"))
            .build();
    }

    @Test
    public void testGroceryListIsEmptyBeforeMealPlanGeneration() {
        List<Ingredient> groceries = model.getGroceries();
        assertEquals(0, groceries.size(), "Grocery list should be empty initially");
    }

    @Test
    public void testGroceryListIsAutomaticallyPopulatedAfterGeneratePlan() {
        controller.AddRecipe(testRecipe);
        assertEquals(1, model.getRecipes().size(), "Recipe should be added");

        List<Ingredient> groceriesBefore = model.getGroceries();
        assertEquals(0, groceriesBefore.size(), "Grocery list should still be empty before meal plan generation");

        controller.generateWeeklyPlan();

        List<Ingredient> groceriesAfter = model.getGroceries();
        assertNotEquals(0, groceriesAfter.size(),
            "Grocery list should be populated after generating meal plan via Observer pattern");

        assertTrue(groceriesAfter.stream()
            .anyMatch(i -> i.getName().equals("A")),
            "Ingredient 'A' should be in grocery list");

        assertTrue(groceriesAfter.stream()
            .anyMatch(i -> i.getName().equals("B")),
            "Ingredient 'B' should be in grocery list");
    }

    @Test
    public void testGroceryListIngredientsAreAggregated() {
        controller.AddRecipe(testRecipe);
        controller.generateWeeklyPlan();

        List<Ingredient> groceries = model.getGroceries();

        Ingredient ingredientA = groceries.stream()
            .filter(i -> i.getName().equals("A"))
            .findFirst()
            .orElse(null);

        assertNotNull(ingredientA, "Ingredient 'A' should be in grocery list");
        assertEquals(28.0, ingredientA.getQuantity(),
            "Ingredient 'A' quantity should be 28 (1 gram × 28 meals)");
        assertEquals("gram", ingredientA.getUnit());

        Ingredient ingredientB = groceries.stream()
            .filter(i -> i.getName().equals("B"))
            .findFirst()
            .orElse(null);

        assertNotNull(ingredientB, "Ingredient 'B' should be in grocery list");
        assertEquals(56.0, ingredientB.getQuantity(),
            "Ingredient 'B' quantity should be 56 (2 kilogram × 28 meals)");
        assertEquals("kilogram", ingredientB.getUnit());
    }

    @Test
    public void testGroceryListUpdatesWhenMealChanged() {
        controller.AddRecipe(testRecipe);

        controller.generateWeeklyPlan();

        List<Ingredient> initialGroceries = model.getGroceries();
        int initialSize = initialGroceries.size();
        assertEquals(2, initialSize, "Should have 2 unique ingredients (A, B)");

        controller.updateMeal("mon", MealType.BREAKFAST, testRecipe);

        List<Ingredient> updatedGroceries = model.getGroceries();
        assertTrue(updatedGroceries.size() > 0, "Grocery list should still be populated");
    }

    @Test
    public void testObserverPatternIsWiredCorrectly() {
        controller.AddRecipe(testRecipe);

        assertEquals(0, model.getGroceries().size());

        DayPlan day = new DayPlan();
        day.setMeal(MealType.BREAKFAST, testRecipe);
        day.setMeal(MealType.LUNCH, testRecipe);
        day.setMeal(MealType.DINNER, testRecipe);
        day.setMeal(MealType.SNACK, testRecipe);

        controller.updateMeal("mon", MealType.BREAKFAST, testRecipe);

        List<Ingredient> groceries = model.getGroceries();
        assertTrue(groceries.size() > 0,
            "Grocery list should be populated after Observer notification");
    }
}