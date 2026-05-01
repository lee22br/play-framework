package controllers;

import play.data.validation.Constraints;

/**
 * A form processing DTO that maps to the product form.
 *
 * Using a class specifically for form binding reduces the chances
 * of a parameter tampering attack and makes code clearer, because
 * you can define constraints against the class.
 */
public class ProductData {

    @Constraints.Required
    private String name;

    @Constraints.Min(0)
    private int price;

    public ProductData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
