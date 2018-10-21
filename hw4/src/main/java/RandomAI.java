import java.util.Random;

/**
 * An subclass of the AI class which has one unique feature: that any attempt
 * to swap the cannon target is successful 50 percent of the time, and a
 * failure results in a 50 percent chance of the RandomAI's self-destruction.
 *
 * @author Justin Wurst
 * @version 10-16-18
 */
public class RandomAI extends AI {
    private static final Random randomizer = new Random();

    /**
     * Initializes a RandomAI instance based on two given Coordinates objects,
     * one representing the cannon target and the other representing the
     * location of Dr. Chipotle's secret lair.
     *
     * @param cannonTarget Represents the location of the cannon target.
     * @param secretHQ Represents the location of Dr. Chipotle's secret lair.
     */
    public RandomAI(Coordinates cannonTarget, Coordinates secretHQ) {
        super(cannonTarget, secretHQ);
    }

    /**
     * Randomly determines the success of swapping the cannonTarget value. There
     * is a 50 percent chance of success.
     *
     * @return a boolean that indicates whether or not the swap was successful.
     */
    public boolean shouldSwapCannonTarget() {
        return randomizer.nextInt(2) == 1;
    }

    /**
     * Randomly determines whether the AI will self-destruct. There is a 50
     * percent chance that it will do so.
     *
     * @return a boolean that indicates whether or not the AI self-destructs.
     */
    public boolean shouldSelfDestruct() {
        return randomizer.nextInt(2) == 1;
    }
}