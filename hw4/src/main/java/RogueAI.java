/**
 * A more intelligent subclass of AI than RandomAI, RogueAI attempts to disable
 * the firewalls within the guac cannon; however, there is an alert level that
 * this raises, and if is too high it will cause the RogueAI to self-destruct.
 *
 * @author Justin Wurst
 * @version 10-16-18
 */
public class RogueAI extends AI {
    private int firewallProtection, alertLevel;
    private final int maxAlert;

    /**
     * Initializes a RogueAI instance based on the initial firewall strength,
     * initial alert level, and maximum alert level of the guac cannon, the
     * current location where the cannon is aimed, and the location of Dr.
     * Chipotle's secret lair.
     *
     * @param firewallProtection The initial strength of the guac cannon's
     *                           firewall.
     * @param alertLevel The initial alert level of the guac cannon.
     * @param maxAlert The maximum alert level of the guac cannon before the
     *                 AI self-destructs.
     * @param cannonTarget The current location at which the guac cannon is
     *                     aimed.
     * @param secretHQ The location of Dr. Chipotle's secret lair.
     */
    public RogueAI(int firewallProtection, int alertLevel, int maxAlert,
                   Coordinates cannonTarget, Coordinates secretHQ) {
        super(cannonTarget, secretHQ);
        this.firewallProtection = firewallProtection;
        this.alertLevel = alertLevel;
        this.maxAlert = maxAlert;
    }

    /**
     * Delegates to the fully-parameterized constructor if no initial alert
     * level is provided by assigning it a value of 0.
     *
     * @param firewallProtection The initial strength of the guac cannon's
     *                           firewall.
     * @param maxAlert The maximum alert level of the guac cannon before the
     *                 AI self-destructs.
     * @param cannonTarget The current location at which the guac cannon is
     *                     aimed.
     * @param secretHQ The location of Dr. Chipotle's secret lair.
     */
    public RogueAI(int firewallProtection, int maxAlert,
                   Coordinates cannonTarget, Coordinates secretHQ) {
        this(firewallProtection, 0, maxAlert, cannonTarget, secretHQ);
    }

    /**
     * Delegates to the fully-parameterized constructor if no initial alert
     * level or maximum alert is provided by assigning the former a value of
     * 0 and the latter a value of 10.
     *
     * @param firewallProtection The initial strength of the guac cannon's
     *                           firewall.
     * @param cannonTarget The current location at which the guac cannon is
     *                     aimed.
     * @param secretHQ The location of Dr. Chipotle's secret lair.
     */
    public RogueAI(int firewallProtection,
                   Coordinates cannonTarget, Coordinates secretHQ) {
        this(firewallProtection, 0, 10, cannonTarget, secretHQ);
    }

    /**
     * The guac cannon firewall is lowered, but this in turn raises the alert
     * level of its security systems by 1.
     */
    public void lowerFirewall() {
        firewallProtection -= 2;
        alertLevel++;
    }

    /**
     * The cannon target can only be swapped if the firewalls have been fully
     * lowered; if they are not, the swap will fail.
     *
     * @return a boolean indicating the success of the swap based on the guac
     *         cannon's firewall.
     */
    public boolean shouldSwapCannonTarget() {
        return firewallProtection <= 0;
    }

    /**
     * The AI will only self-destruct if the alert level has exceeded its
     * maximum threshold; if it has not, it will not self-destruct.
     *
     * @return a boolean indicating if the AI should self-destruct based on
     *         the alert level.
     */
    public boolean shouldSelfDestruct() {
        return alertLevel >= maxAlert;
    }

    /**
     * @return an integer representing the current strength of the firewall.
     */
    public int getFirewallProtection() {
        return firewallProtection;
    }

    /**
     * @return an integer representing the current guac cannon alert level.
     */
    public int getAlertLevel() {
        return alertLevel;
    }

    /**
     * @return an integer representing the maximum alert level.
     */
    public int getMaxAlert() {
        return maxAlert;
    }

    /**
     * Generates a string representation of the current instance of RogueAI.
     *
     * @return a String containing the AI's target (via a super method call)
     *         and the alert and firewall levels of the guac cannon.
     */
    public String toString() {
        return String.format(
                super.toString().substring(0, super.toString().length() - 1)
                + ", and is at alert level %d with firewall protection %d.",
                alertLevel, firewallProtection);
    }
}