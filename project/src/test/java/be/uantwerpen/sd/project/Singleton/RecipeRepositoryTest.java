/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.Singleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.singleton.RecipeRepository;

public class RecipeRepositoryTest {

    @BeforeEach
    public void setUp() {
        RecipeRepository.getInstance().getAll().forEach(r -> RecipeRepository.getInstance().removeRecipe(r.getId()));
    }

    @Test
    public void testSingletonPattern() {
        RecipeRepository repo1 = RecipeRepository.getInstance();
        RecipeRepository repo2 = RecipeRepository.getInstance();
        assertSame(repo1, repo2);
    }

    @Test
    public void testAddRecipe() {
        RecipeRepository repo = RecipeRepository.getInstance();
        Recipe recipe = Recipe.builder()
            .title("Test Recipe")
            .description("Test")
            .build();

        repo.addRecipe(recipe);
        List<Recipe> recipes = repo.getAll();
        assertEquals(1, recipes.size());
        assertEquals("Test Recipe", recipes.get(0).getTitle());
    }

    @Test
    public void testRemoveRecipe() {
        RecipeRepository repo = RecipeRepository.getInstance();
        Recipe recipe = Recipe.builder()
            .title("Test Recipe")
            .description("Test")
            .build();

        repo.addRecipe(recipe);
        assertEquals(1, repo.getAll().size());

        Recipe added = repo.getAll().get(0);
        repo.removeRecipe(added.getId());
        assertEquals(0, repo.getAll().size());
    }

    @Test
    public void testUpdateRecipe() {
        RecipeRepository repo = RecipeRepository.getInstance();
        Recipe original = Recipe.builder()
            .title("Original")
            .description("Original description")
            .build();

        repo.addRecipe(original);
        Recipe added = repo.getAll().get(0);

        Recipe updated = Recipe.builder()
            .title("Updated")
            .description("Updated description")
            .id(added.getId())
            .build();

        repo.updateRecipe(updated);
        List<Recipe> recipes = repo.getAll();
        assertEquals(1, recipes.size());
        assertEquals("Updated", recipes.get(0).getTitle());
    }

    @Test
    public void testThreadSafeSingleton() throws InterruptedException {
        // Test thread safety of getInstance
        Thread thread1 = new Thread(() -> {
            RecipeRepository repo = RecipeRepository.getInstance();
            assertNotNull(repo);
        });

        Thread thread2 = new Thread(() -> {
            RecipeRepository repo = RecipeRepository.getInstance();
            assertNotNull(repo);
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertTrue(RecipeRepository.getInstance() != null);
    }
}