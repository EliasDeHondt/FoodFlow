/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.builder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import be.uantwerpen.sd.project.Ingredient;

public class RecipeTest {
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = Recipe.builder()
            .title("Pasta Carbonara")
            .description("Classic Italian pasta dish")
            .ingredients(List.of(
                new Ingredient("Pasta", 400.0, "g"),
                new Ingredient("Eggs", 4.0, "pcs"),
                new Ingredient("Bacon", 200.0, "g")
            ))
            .tags(List.of("dinner", "quick"))
            .id(1)
            .build();
    }

    @Test
    public void testRecipeBuilderCreation() {
        assertEquals("Pasta Carbonara", recipe.getTitle());
        assertEquals("Classic Italian pasta dish", recipe.getDescription());
        assertEquals(3, recipe.getIngredients().size());
        assertEquals(2, recipe.getTags().size());
        assertEquals(1, recipe.getId());
    }

    @Test
    public void testRecipeEmptyConstructor() {
        Recipe empty = Recipe.empty();
        assertEquals("No Recipe", empty.getTitle());
        assertEquals("", empty.getDescription());
    }

    @Test
    public void testRecipeGetIngredientsReturnsNewList() {
        List<Ingredient> ingredients1 = recipe.getIngredients();
        List<Ingredient> ingredients2 = recipe.getIngredients();
        assertNotSame(ingredients1, ingredients2);
        assertEquals(ingredients1, ingredients2);
    }

    @Test
    public void testRecipeGetTagsReturnsNewList() {
        List<String> tags1 = recipe.getTags();
        List<String> tags2 = recipe.getTags();
        assertNotSame(tags1, tags2);
        assertEquals(tags1, tags2);
    }

    @Test
    public void testRecipeAddIdCreatesNewInstance() {
        Recipe recipe2 = recipe.addId(2);
        assertEquals(1, recipe.getId());
        assertEquals(2, recipe2.getId());
        assertEquals(recipe.getTitle(), recipe2.getTitle());
    }

    @Test
    public void testRecipeEquality() {
        Recipe recipe2 = Recipe.builder()
            .title("Pasta Carbonara")
            .description("Different description")
            .ingredients(List.of())
            .tags(List.of())
            .id(1)
            .build();
        assertEquals(recipe, recipe2);
    }

    @Test
    public void testRecipeHashCode() {
        Recipe recipe2 = Recipe.builder()
            .title("Pasta Carbonara")
            .description("Different description")
            .ingredients(List.of())
            .tags(List.of())
            .id(1)
            .build();
        assertEquals(recipe.hashCode(), recipe2.hashCode());
    }

    @Test
    public void testRecipeNoIdThrowsException() {
        Recipe noId = Recipe.builder()
            .title("Test")
            .description("Test")
            .build();
        assertThrows(IllegalStateException.class, noId::getId);
    }
}