/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.controller.Controller;
import be.uantwerpen.sd.project.controller.MealPlannerController;
import be.uantwerpen.sd.project.model.Model;
import be.uantwerpen.sd.project.singleton.RecipeRepository;
import be.uantwerpen.sd.project.model.MealPlannerDB;

public class MealPlannerIntegrationTest {
    private Model model;
    private Controller controller;

    @BeforeEach
    public void setUp() {
        RecipeRepository repo = RecipeRepository.getInstance();
        List<Recipe> allRecipes = new ArrayList<>(repo.getAll());
        for (Recipe r : allRecipes) repo.removeRecipe(r.getId());

        model = new MealPlannerDB();
        controller = new MealPlannerController(model);
    }

    @Test
    public void testAddRecipeAndViewRecipes() {
        Recipe recipe = Recipe.builder()
            .title("Spaghetti Bolognese")
            .description("Classic Italian pasta dish")
            .ingredients(List.of(
                new Ingredient("Spaghetti", 400.0, "g"),
                new Ingredient("Beef", 500.0, "g"),
                new Ingredient("Tomato sauce", 500.0, "ml")
            ))
            .tags(List.of("dinner"))
            .build();

        controller.AddRecipe(recipe);

        List<Recipe> recipes = model.getRecipes();
        assertEquals(1, recipes.size());
        assertEquals("Spaghetti Bolognese", recipes.get(0).getTitle());
    }

    @Test
    public void testAddGroceryItem() {
        controller.addGrocery("Milk", "1", "l");

        List<Ingredient> groceries = model.getGroceries();
        assertEquals(1, groceries.size());
        assertEquals("Milk", groceries.get(0).getName());
        assertEquals(1.0, groceries.get(0).getQuantity());
        assertEquals("l", groceries.get(0).getUnit());
    }

    @Test
    public void testUpdateMealAndCheckGroceryList() {
        Recipe recipe = Recipe.builder()
            .title("Pasta Carbonara")
            .description("Creamy pasta")
            .ingredients(List.of(
                new Ingredient("Pasta", 400.0, "g"),
                new Ingredient("Eggs", 4.0, "pcs"),
                new Ingredient("Bacon", 200.0, "g")
            ))
            .build();

        controller.AddRecipe(recipe);
        Recipe addedRecipe = model.getRecipes().get(0);

        controller.updateMeal("mon", MealType.LUNCH, addedRecipe);

        Recipe[][] weeklyPlan = model.getWeeklyPlan();
        assertEquals(addedRecipe, weeklyPlan[0][1]);
    }

    @Test
    public void testGenerateWeeklyPlan() {
        for (int i = 0; i < 3; i++) {
            Recipe recipe = Recipe.builder()
                .title("Recipe " + i)
                .description("Test recipe")
                .ingredients(List.of(new Ingredient("Ingredient " + i, 100.0, "g")))
                .build();
            controller.AddRecipe(recipe);
        }

        controller.generateWeeklyPlan();

        Recipe[][] weeklyPlan = model.getWeeklyPlan();
        assertNotNull(weeklyPlan);
        assertEquals(7, weeklyPlan.length);
        assertEquals(4, weeklyPlan[0].length);

        boolean hasRecipes = false;
        for (int day = 0; day < 7; day++) {
            for (int meal = 0; meal < 4; meal++) {
                if (weeklyPlan[day][meal] != null && !weeklyPlan[day][meal].getTitle().equals("No Recipe")) {
                    hasRecipes = true;
                }
            }
        }
        assertTrue(hasRecipes, "Weekly plan should contain at least some recipes");
    }

    @Test
    public void testRemoveMeal() {
        Recipe recipe = Recipe.builder()
            .title("Test Recipe")
            .description("Test")
            .ingredients(List.of())
            .build();

        controller.AddRecipe(recipe);
        Recipe addedRecipe = model.getRecipes().get(0);

        controller.updateMeal("tue", MealType.BREAKFAST, addedRecipe);

        controller.removeMeal("tue", MealType.BREAKFAST);

        Recipe[][] weeklyPlan = model.getWeeklyPlan();
        assertEquals("No Recipe", weeklyPlan[1][0].getTitle());
    }

    @Test
    public void testUpdateRecipe() {
        Recipe original = Recipe.builder()
            .title("Original Recipe")
            .description("Original description")
            .ingredients(List.of(new Ingredient("Ingredient", 100.0, "g")))
            .build();

        controller.AddRecipe(original);
        Recipe addedRecipe = model.getRecipes().get(0);
        int recipeId = addedRecipe.getId();

        Recipe updated = Recipe.builder()
            .title("Updated Recipe")
            .description("Updated description")
            .ingredients(List.of(new Ingredient("New Ingredient", 200.0, "g")))
            .id(recipeId)
            .build();

        controller.updateRecipe(updated);

        List<Recipe> recipes = model.getRecipes();
        assertEquals(1, recipes.size());
        assertEquals("Updated Recipe", recipes.get(0).getTitle());
        assertEquals("Updated description", recipes.get(0).getDescription());
    }

    @Test
    public void testRemoveRecipe() {
        Recipe recipe = Recipe.builder()
            .title("Test Recipe")
            .description("Test")
            .build();

        controller.AddRecipe(recipe);
        assertEquals(1, model.getRecipes().size());

        Recipe addedRecipe = model.getRecipes().get(0);
        controller.removeRecipe(addedRecipe);

        assertEquals(0, model.getRecipes().size());
    }

    @Test
    public void testSetStrategyVegetarian() {
        Recipe veganRecipe = Recipe.builder()
            .title("Vegan Salad")
            .description("Healthy vegan salad")
            .ingredients(List.of(new Ingredient("Lettuce", 200.0, "g")))
            .tags(List.of("vegan"))
            .build();

        controller.AddRecipe(veganRecipe);

        controller.setStrategy("vegetarian");

        controller.generateWeeklyPlan();

        Recipe[][] weeklyPlan = model.getWeeklyPlan();
        assertNotNull(weeklyPlan);
        assertEquals(7, weeklyPlan.length);
    }
}