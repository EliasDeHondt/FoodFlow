/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project;
import java.util.Arrays;
import java.util.List;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.controller.Controller;

public class DemoDataLoader {
    public static void loadDemoRecipes(Controller controller) {
        addRecipe(controller, "Pancakes", "Fluffy American-style pancakes with maple syrup",
            Arrays.asList(
                new Ingredient("Flour", 200.0, "g"),
                new Ingredient("Eggs", 2.0, "pcs"),
                new Ingredient("Milk", 250.0, "ml"),
                new Ingredient("Butter", 50.0, "g"),
                new Ingredient("Sugar", 30.0, "g")
            ),
            Arrays.asList("Breakfast", "American"));

        addRecipe(controller, "Eggs Benedict", "Poached eggs with hollandaise sauce on English muffins",
            Arrays.asList(
                new Ingredient("Eggs", 4.0, "pcs"),
                new Ingredient("Butter", 100.0, "g"),
                new Ingredient("English Muffins", 2.0, "pcs"),
                new Ingredient("Ham", 100.0, "g"),
                new Ingredient("Lemon", 1.0, "pcs")
            ),
            Arrays.asList("Breakfast"));

        addRecipe(controller, "Oatmeal with Berries", "Healthy oatmeal topped with fresh berries",
            Arrays.asList(
                new Ingredient("Oats", 100.0, "g"),
                new Ingredient("Milk", 300.0, "ml"),
                new Ingredient("Blueberries", 150.0, "g"),
                new Ingredient("Honey", 30.0, "g"),
                new Ingredient("Almonds", 50.0, "g")
            ),
            Arrays.asList("Breakfast", "Healthy", "Vegan"));

        addRecipe(controller, "Caesar Salad", "Classic Caesar salad with crispy croutons and parmesan",
            Arrays.asList(
                new Ingredient("Romaine Lettuce", 300.0, "g"),
                new Ingredient("Parmesan Cheese", 100.0, "g"),
                new Ingredient("Croutons", 100.0, "g"),
                new Ingredient("Caesar Dressing", 150.0, "ml"),
                new Ingredient("Chicken Breast", 200.0, "g")
            ),
            Arrays.asList("Lunch", "Salad"));

        addRecipe(controller, "Grilled Chicken Sandwich", "Juicy grilled chicken breast with fresh vegetables",
            Arrays.asList(
                new Ingredient("Chicken Breast", 250.0, "g"),
                new Ingredient("Bread", 2.0, "pcs"),
                new Ingredient("Tomato", 1.0, "pcs"),
                new Ingredient("Lettuce", 50.0, "g"),
                new Ingredient("Mayonnaise", 30.0, "g")
            ),
            Arrays.asList("Lunch", "Quick"));

        addRecipe(controller, "Vegetable Stir Fry", "Colorful mix of fresh vegetables with soy sauce",
            Arrays.asList(
                new Ingredient("Broccoli", 200.0, "g"),
                new Ingredient("Bell Peppers", 200.0, "g"),
                new Ingredient("Carrots", 150.0, "g"),
                new Ingredient("Soy Sauce", 50.0, "ml"),
                new Ingredient("Ginger", 20.0, "g")
            ),
            Arrays.asList("Lunch", "Vegan", "Healthy"));

        addRecipe(controller, "Spaghetti Carbonara", "Classic Italian pasta with bacon and creamy sauce",
            Arrays.asList(
                new Ingredient("Spaghetti", 400.0, "g"),
                new Ingredient("Bacon", 200.0, "g"),
                new Ingredient("Eggs", 3.0, "pcs"),
                new Ingredient("Parmesan Cheese", 150.0, "g"),
                new Ingredient("Black Pepper", 10.0, "g")
            ),
            Arrays.asList("Dinner", "Italian"));

        addRecipe(controller, "Grilled Salmon", "Fresh salmon fillet with lemon and herbs",
            Arrays.asList(
                new Ingredient("Salmon Fillet", 400.0, "g"),
                new Ingredient("Lemon", 1.0, "pcs"),
                new Ingredient("Olive Oil", 50.0, "ml"),
                new Ingredient("Garlic", 3.0, "pcs"),
                new Ingredient("Dill", 20.0, "g")
            ),
            Arrays.asList("Dinner", "Healthy", "Fish"));

        addRecipe(controller, "Beef Tacos", "Mexican-style beef tacos with all the toppings",
            Arrays.asList(
                new Ingredient("Ground Beef", 500.0, "g"),
                new Ingredient("Tortillas", 8.0, "pcs"),
                new Ingredient("Cheddar Cheese", 150.0, "g"),
                new Ingredient("Tomato", 2.0, "pcs"),
                new Ingredient("Sour Cream", 100.0, "g")
            ),
            Arrays.asList("Dinner", "Mexican"));

        addRecipe(controller, "Vegetable Curry", "Spiced curry with seasonal vegetables",
            Arrays.asList(
                new Ingredient("Chickpeas", 400.0, "g"),
                new Ingredient("Coconut Milk", 400.0, "ml"),
                new Ingredient("Spinach", 200.0, "g"),
                new Ingredient("Curry Powder", 30.0, "g"),
                new Ingredient("Onion", 2.0, "pcs")
            ),
            Arrays.asList("Dinner", "Vegan", "Healthy", "Spicy"));

        addRecipe(controller, "Fruit Smoothie", "Refreshing blend of fresh fruits and yogurt",
            Arrays.asList(
                new Ingredient("Banana", 1.0, "pcs"),
                new Ingredient("Berries", 200.0, "g"),
                new Ingredient("Yogurt", 200.0, "g"),
                new Ingredient("Honey", 20.0, "g"),
                new Ingredient("Milk", 100.0, "ml")
            ),
            Arrays.asList("Snack", "Healthy"));

        addRecipe(controller, "Hummus and Vegetables", "Creamy hummus with fresh vegetable sticks",
            Arrays.asList(
                new Ingredient("Chickpeas", 200.0, "g"),
                new Ingredient("Tahini", 50.0, "g"),
                new Ingredient("Carrots", 200.0, "g"),
                new Ingredient("Celery", 150.0, "g"),
                new Ingredient("Lemon", 1.0, "pcs")
            ),
            Arrays.asList("Snack", "Vegan", "Healthy"));

        addRecipe(controller, "Cheese and Crackers", "Assorted cheeses with crispy crackers",
            Arrays.asList(
                new Ingredient("Cheddar Cheese", 200.0, "g"),
                new Ingredient("Brie Cheese", 150.0, "g"),
                new Ingredient("Crackers", 200.0, "g"),
                new Ingredient("Grapes", 200.0, "g"),
                new Ingredient("Nuts", 100.0, "g")
            ),
            Arrays.asList("Snack"));
    }

    private static void addRecipe(Controller controller, String title, String description,
                                   List<Ingredient> ingredients, List<String> tags) {
        Recipe recipe = Recipe.builder()
            .title(title)
            .description(description)
            .ingredients(ingredients)
            .tags(tags)
            .build();

        controller.AddRecipe(recipe);
    }
}