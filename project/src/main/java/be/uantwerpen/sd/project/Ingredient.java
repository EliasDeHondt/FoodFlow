/**
 * @author Elias De Hondt
 * @author Jarn Vaerewijck
 * @see https://eliasdh.com
 * @see https://github.com/jVaerewijck
 * @since 01/01/2026
 **/

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

    public Ingredient add(Ingredient other) {
        if (!this.name.equalsIgnoreCase(other.name)) {
            throw new IllegalArgumentException("Ingredients must have the same name");
        }
        return new Ingredient(
            this.name,
            this.quantity + other.quantity,
            this.unit
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}