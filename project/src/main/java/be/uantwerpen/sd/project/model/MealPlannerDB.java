/**
 * @author Elias De Hondt
 * @see https://eliasdh.com
 * @since 01/01/2026
 **/

package be.uantwerpen.sd.project.model;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import be.uantwerpen.sd.project.DayPlan;
import be.uantwerpen.sd.project.Ingredient;
import be.uantwerpen.sd.project.MealType;
import be.uantwerpen.sd.project.Singleton.RecipeRepository;
import be.uantwerpen.sd.project.builder.Recipe;
import be.uantwerpen.sd.project.observer.GroceryList;
import be.uantwerpen.sd.project.observer.WeeklyPlan;
import be.uantwerpen.sd.project.strategy.DefaultMealPlanStrategy;
import be.uantwerpen.sd.project.strategy.MealPlanningStrategy;

public class MealPlannerDB implements Model{
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final WeeklyPlan weeklyplan;
    private final GroceryList grocerylist;
    private MealPlanningStrategy mealplanstrategy;
    private final RecipeRepository recipeRepo = RecipeRepository.getInstance();

    public MealPlannerDB() {
        this.weeklyplan = new WeeklyPlan();
        this.grocerylist = new GroceryList();
        this.mealplanstrategy = new DefaultMealPlanStrategy();

        this.weeklyplan.addObserver(this.grocerylist);
    }
    @Override
    public List<Ingredient> getGroceries() {
        return this.grocerylist.getItems();
    }
    @Override
    public void addGrocery(Ingredient i) {
        this.grocerylist.addItem(i.getName(), i);
        fire(RegistrationEventType.GROCERIES_CHANGED);
    }
    @Override
    public void removeGrocery(Ingredient i) {
        this.grocerylist.checkOffItem(i);
        fire(RegistrationEventType.GROCERIES_CHANGED);
    }
    @Override
    public void updateMeal(String day,MealType mealType,Recipe r) {
        DayPlan d = this.weeklyplan.getDay(day);
        d.setMeal(mealType, r);
        this.weeklyplan.setDay(day,d);
        fire(RegistrationEventType.MEAL_CHANGED);
    }
    @Override
    public void removeMeal(String day,MealType mealType) {
        DayPlan d = this.weeklyplan.getDay(day);
        d.setMeal(mealType,Recipe.empty());
        fire(RegistrationEventType.MEAL_CHANGED);
    }
    @Override
    public void setStrategy(MealPlanningStrategy strategy) {
        this.mealplanstrategy = strategy;
    }
    @Override
    public void generateWeeklyPlan() {
        this.mealplanstrategy.generatePlan(this.weeklyplan, this.recipeRepo);
        fire(RegistrationEventType.MEAL_CHANGED);
    }
    @Override
    public Recipe[][] getWeeklyPlan() {
        Recipe[][] recipeGrid = new Recipe[7][4];
        String[] dayNames = {"mon","tue","wed","thu","fri","sat","sun"};
        MealType[] meals = {MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK};

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 4; col++) {
                recipeGrid[row][col] = chooseRecipe(dayNames[row],meals[col]);
            }
        }
        return recipeGrid;
    }
    @Override
    public void AddRecipe(Recipe r) {
        this.recipeRepo.addRecipe(r);
        fire(RegistrationEventType.RECIPE_ADDED);
    }
    @Override
    public void updateRecipe(Recipe r) {
        this.recipeRepo.updateRecipe(r);
        fire(RegistrationEventType.RECIPE_UPDATED);
    }
    @Override
    public void removeRecipe(Recipe r) {
        this.recipeRepo.removeRecipe(r);
        fire(RegistrationEventType.RECIPE_REMOVED);
    }
    @Override
    public List<Recipe> getRecipes() {
        return this.recipeRepo.getAll();
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private void fire(RegistrationEventType evt) {
        pcs.firePropertyChange("registration", null, evt);
    }
    private Recipe chooseRecipe(String day,MealType mealType) {
        DayPlan d = this.weeklyplan.getDay(day);
        Recipe r = d.getMeal(mealType);
        return r;
    }
}