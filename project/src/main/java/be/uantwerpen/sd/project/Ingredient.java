package be.uantwerpen.sd.project;

public class Ingredient {
    private final String name;
    private final Double quantity;
    private final String unit;

    public Ingredient(String name, Double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public String getUnit() {
        return this.unit;
    }
}
