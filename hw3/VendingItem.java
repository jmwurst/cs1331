/**
* Defines items and their prices which can be placed within a vending
* machine.
*
* @author Justin Wurst
* @version 09-28-18
*/
public enum VendingItem {
    Lays                (1.50),
    Doritos             (1.50),
    Coke                (2.50),
    Ramblin_Reck_Toy    (180.75),
    Rubiks_Cube         (30.00),
    Rat_Cap             (15.00),
    FASET_Lanyard       (10.00),
    Graphing_Calculator (120.00),
    UGA_Diploma         (0.10),
    Pie                 (3.14),
    Clicker             (55.55),
    Cheetos             (1.25),
    Sprite              (2.50),
    Red_Bull            (4.75),
    Ramen               (3.15),
    Cold_Pizza          (0.99);

    private final double price;

    /**
    * Initializes a VendingItem object based on a given price.
    *
    * @param price The price of the item to initialize
    */
    VendingItem(double price) {
        this.price = price;
    }

    /**
    *  @return the item's price (value)
    */
    public double getPrice() {
        return price;
    }

    /**
    * Generates a string representation of an item and its price.
    *
    * @return a String containing the item type and value
    */
    public String toString() {
        return String.format("%s: $%.2f", name(), price);
    }
}