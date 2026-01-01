/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.observer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.builder.Recipe;

public class GroceryListTest {
    private GroceryList groceryList;
    private WeeklyPlan weeklyPlan;

    @BeforeEach
    public void setUp() {
        groceryList = new GroceryList();
        weeklyPlan = new WeeklyPlan();
        weeklyPlan.addObserver(groceryList);
    }

    @Test
    public void testGroceryListAddItem() {
        Ingredient flour = new Ingredient("Flour", 500.0, "g");
        groceryList.addItem("Flour", flour);

        List<Ingredient> items = groceryList.getItems();
        assertEquals(1, items.size());
        assertEquals("Flour", items.get(0).getName());
    }

    @Test
    public void testGroceryListMergeIngredients() {
        Ingredient flour1 = new Ingredient("Flour", 500.0, "g");
        Ingredient flour2 = new Ingredient("Flour", 250.0, "g");

        groceryList.addItem("Flour", flour1);
        groceryList.addItem("Flour", flour2);

        List<Ingredient> items = groceryList.getItems();
        assertEquals(1, items.size());
        assertEquals("Flour", items.get(0).getName());
        assertEquals(750.0, items.get(0).getQuantity());
    }

    @Test
    public void testGroceryListCheckOffItem() {
        Ingredient flour = new Ingredient("Flour", 500.0, "g");
        groceryList.addItem("Flour", flour);

        assertEquals(1, groceryList.getItems().size());

        groceryList.checkOffItem(flour);
        assertTrue(groceryList.isChecked("Flour"));
    }

    @Test
    public void testGroceryListUncheckItem() {
        Ingredient flour = new Ingredient("Flour", 500.0, "g");
        groceryList.addItem("Flour", flour);
        groceryList.checkOffItem(flour);

        assertTrue(groceryList.isChecked("Flour"));

        groceryList.uncheckItem("Flour");
        assertFalse(groceryList.isChecked("Flour"));
    }

    @Test
    public void testGroceryListGetCheckedItems() {
        Ingredient flour = new Ingredient("Flour", 500.0, "g");
        Ingredient sugar = new Ingredient("Sugar", 200.0, "g");

        groceryList.addItem("Flour", flour);
        groceryList.addItem("Sugar", sugar);

        groceryList.checkOffItem(flour);

        List<Ingredient> checked = groceryList.getCheckedItems();
        assertEquals(1, checked.size());
        assertEquals("Flour", checked.get(0).getName());
    }

    @Test
    public void testGroceryListObserverUpdate() {
        Recipe recipe = Recipe.builder()
            .title("Pasta")
            .description("Pasta dish")
            .ingredients(List.of(
                new Ingredient("Pasta", 400.0, "g"),
                new Ingredient("Tomatoes", 500.0, "g")
            ))
            .build();

        DayPlan day = weeklyPlan.getDay("mon");
        day.setMeal(MealType.LUNCH, recipe);
        weeklyPlan.setDay("mon", day);

        List<Ingredient> items = groceryList.getItems();
        assertTrue(items.stream().anyMatch(i -> i.getName().equals("Pasta")));
        assertTrue(items.stream().anyMatch(i -> i.getName().equals("Tomatoes")));
    }

    @Test
    public void testGroceryListClearChecked() {
        Ingredient flour = new Ingredient("Flour", 500.0, "g");
        Ingredient sugar = new Ingredient("Sugar", 200.0, "g");

        groceryList.addItem("Flour", flour);
        groceryList.addItem("Sugar", sugar);

        groceryList.checkOffItem(flour);
        groceryList.checkOffItem(sugar);

        assertEquals(2, groceryList.getCheckedItems().size());

        groceryList.clearChecked();
        assertEquals(0, groceryList.getCheckedItems().size());
    }
}