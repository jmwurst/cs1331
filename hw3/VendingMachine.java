import java.util.Random;

/**
* A model of a vending machine in Java. It is capable of storing VendingItem
* objects with defined types and values. The model can vend items, which are
* randomly given out for free, and keeps a total of the amount of money all
* machines have made.
*
* @author Justin Wurst
* @version 09-28-18
*/
public class VendingMachine {
    private static double totalSales;

    private VendingItem[][][] shelf;
    private int luckyChance;
    private Random rand;

    /**
    * Initializes a VendingMachine object with a 6 row, 3 column machine with
    * a row depth of 5, then adds a random set of items to the machine.
    */
    public VendingMachine() {
        totalSales = 0;
        shelf = new VendingItem[6][3][5];
        luckyChance = 0;
        rand = new Random();

        restock();
    }

    /**
    * Given a position in a machine, vend the item at that position
    * (remove it from the machine) and shift every item behind it forward
    * by one index. Check if the customer has won a free item, and update
    * the total sales by all machines.
    *
    * @param code the position in the machine to vend from; should
    *             have a row letter and column number
    *
    * @return the VendingItem that is being vended
    */
    public VendingItem vend(String code) {
        if (code.length() != 2
            || !(code.charAt(0) >= 'A' && code.charAt(0) <= 'F')
            || !(code.charAt(1) >= '1' && code.charAt(1) <= '3')) {
            System.out.printf("%s is an invalid item location.%n", code);
            return null;
        }

        int row = code.charAt(0) - 65;
        int col = code.charAt(1) - 49;
        if (shelf[row][col][0] == null) {
            System.out.printf("There are no items at %s. Sorry!%n", code);
            return null;
        }

        VendingItem temp = shelf[row][col][0];
        for (int i = 0; i < shelf[row][col].length - 1; i++) {
            shelf[row][col][i] = shelf[row][col][i + 1];
        }
        shelf[row][col][shelf[row][col].length - 1] = null;
        if (free()) {
            System.out.println("Congratulations, your item was free!");
        } else {
            totalSales += temp.getPrice();
        }
        return temp;
    }

    private boolean free() {
        if (rand.nextInt(100) + 1 <= luckyChance) {
            luckyChance = 0;
            return true;
        } else {
            luckyChance++;
            return false;
        }
    }

    /**
    * Replaces all item positions in the machine with new, randomly-generated
    * items.
    */
    public void restock() {
        VendingItem[] items = VendingItem.values();
        for (int i = 0; i < shelf.length; i++) {
            for (int j = 0; j < shelf[i].length; j++) {
                for (int k = 0; k < shelf[i][j].length; k++) {
                    shelf[i][j][k] = items[rand.nextInt(items.length)];
                }
            }
        }
    }

    /**
    * @return the total amount of money that the vending machine has accepted
    */
    public static double getTotalSales() {
        return totalSales;
    }

    /**
    * Determines the number of items currently stored in the vending machine.
    *
    * @return a count of the VendingItems in the machine
    */
    public int getNumberOfItems() {
        int count = 0;
        for (int i = 0; i < shelf.length; i++) {
            for (int j = 0; j < shelf[i].length; j++) {
                for (int k = 0; k < shelf[i][j].length; k++) {
                    if (shelf[i][j][k] != null) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
    * Sums the values of the items currently stored in the vending machine.
    *
    * @return the total monetary amount of VendingItems in the machine
    */
    public double getTotalValue() {
        double value = 0;
        for (int i = 0; i < shelf.length; i++) {
            for (int j = 0; j < shelf[i].length; j++) {
                for (int k = 0; k < shelf[i][j].length; k++) {
                    if (shelf[i][j][k] != null) {
                        value += shelf[i][j][k].getPrice();
                    }
                }
            }
        }
        return value;
    }

    /**
    * @return the percent chance that a customer will receive the item for free
    */
    public int getLuckyChance() {
        return luckyChance;
    }

    /**
    * Generates a visual that represents the machine's grid of items and
    * provides their names, as well as the number of items, the total value
    * of all items, and the total sales so far.
    *
    * @return a String representation of the VendingMachine instance
    */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("----------------------------------------------------------"
                 + "------------\n");
        s.append("                            VendaTron 9000                "
                 + "            \n");
        for (int i = 0; i < shelf.length; i++) {
            s.append("------------------------------------------------------"
                     + "----------------\n");
            for (int j = 0; j < shelf[0].length; j++) {
                VendingItem item = shelf[i][j][0];
                String str = String.format("| %-20s ",
                             (item == null ? "(empty)" : item.name()));
                s.append(str);
            }
            s.append("|\n");
        }
        s.append("----------------------------------------------------------"
                 + "------------\n");
        s.append(String.format("There are %d items with a total "
                 + "value of $%.2f.%n", getNumberOfItems(), getTotalValue()));
        s.append(String.format("Total sales across vending machines "
                 + "is now: $%.2f.%n", getTotalSales()));
        return s.toString();
    }
}