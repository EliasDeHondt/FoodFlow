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

public class IngredientTest {
    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @BeforeEach
    public void setUp() {
        ingredient1 = new Ingredient("Flour", 500.0, "g");
        ingredient2 = new Ingredient("Flour", 250.0, "g");
    }

    @Test
    public void testIngredientCreation() {
        assertEquals("Flour", ingredient1.getName());
        assertEquals(500.0, ingredient1.getQuantity());
        assertEquals("g", ingredient1.getUnit());
    }

    @Test
    public void testIngredientAdd() {
        Ingredient result = ingredient1.add(ingredient2);
        assertEquals("Flour", result.getName());
        assertEquals(750.0, result.getQuantity());
        assertEquals("g", result.getUnit());
    }

    @Test
    public void testIngredientAddWithDifferentNames() {
        Ingredient sugar = new Ingredient("Sugar", 100.0, "g");
        assertThrows(IllegalArgumentException.class, () -> ingredient1.add(sugar));
    }

    @Test
    public void testIngredientEquality() {
        Ingredient ingredient3 = new Ingredient("Flour", 100.0, "kg");
        assertEquals(ingredient1, ingredient3);
    }

    @Test
    public void testIngredientHashCode() {
        Ingredient ingredient3 = new Ingredient("Flour", 100.0, "kg");
        assertEquals(ingredient1.hashCode(), ingredient3.hashCode());
    }

    @Test
    public void testIngredientCaseInsensitivity() {
        Ingredient uppercase = new Ingredient("FLOUR", 100.0, "g");
        assertEquals(ingredient1, uppercase);
    }
}