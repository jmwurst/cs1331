/**
 * Serves as the basis for any AI, and is therefore capable of changing
 * Dr. Chipotle's cannon's target.
 *
 * @author Justin Wurst
 * @version 10-16-18
 */
public abstract class AI {
    private boolean destructed;
    private Coordinates cannonTarget, secretHQ;

    /**
     * Used in super calls by subclasses; since AI is abstract, it cannot
     * be instantiated.
     *
     * @param cannonTarget Represents the location of the cannon target.
     * @param secretHQ Represents the location of Dr. Chipotle's secret base.
     */
    public AI(Coordinates cannonTarget, Coordinates secretHQ) {
        destructed = false;
        this.cannonTarget = cannonTarget;
        this.secretHQ = secretHQ;
    }

    /**
     * Changes the guacamole cannon target to a new target based on a) if
     * the AI has self-destructed and b) the results of the abstract
     * shouldSwapCannonTarget() method.
     *
     * @param newCoords A Coordinates object representing the new target.
     * @return a boolean indicating if the swap was successful.
     */
    public boolean swapCannonTarget(Coordinates newCoords) {
        if (!destructed && !newCoords.equals(cannonTarget)) {
            if (shouldSwapCannonTarget()) {
                cannonTarget = newCoords;
                return true;
            } else if (shouldSelfDestruct()) {
                selfDestruct();
                return false;
            }
        }
        return false;
    }

    /**
     * Determines if the cannon target should be swapped in favor of a new
     * target.
     *
     * @return a boolean indicating whether or not the cannon target should be
     *         swapped.
     */
    public abstract boolean shouldSwapCannonTarget();

    /**
     * "Destroys" the AI by setting the destructed variable to true.
     */
    public void selfDestruct() {
        destructed = true;
    }

    /**
     * Determines if the AI should self-destruct or not.
     *
     * @return a boolean indicating whether or not the AI should self-destruct.
     */
    public abstract boolean shouldSelfDestruct();

    /**
     * @return the status of the AI's destruction (i.e. true if destroyed,
     *         false if not).
     */
    public boolean getDestructed() {
        return destructed;
    }

    /**
     * @return the Coordinates representation of the cannon target.
     */
    public Coordinates getCannonTarget() {
        return cannonTarget;
    }

    /**
     * @return the Coordinates representation of Dr. Chipotle's secret lair.
     */
    public Coordinates getSecretHQ() {
        return secretHQ;
    }

    /**
     * Generates a string representation of this instance of AI.
     *
     * @return a String indicating the current target of Dr. Chipotle's
     *         guacamole cannon.
     */
    public String toString() {
        return "Dr. Chipotle's guacamole cannon is currently pointed at "
               + cannonTarget + ".";
    }
}