/*

Analysis:

1) This is not good style because it limits the expandability of the program.
   If new subclasses of AI were added, Doctor CS would never be able to save
   the day because they would not be instances of RogueAI or RandomAI.

2) If necessary, the subclasses of AI should override the swapCannonTarget
   method. RandomAI does not require any modification to this method, but
   if RogueAI overrode the method with an implementation of the while loop
   found in the saveTheDay method followed by a super call to AI's
   swapCannonTarget, it would eliminate the need for any instanceof in the
   DoctorCS class.

 */

/**
 * The greatest superhero of all time, Doctor CS, represented as a Java class.
 *
 * @author Justin Wurst
 * @version 10-16-18
 */
public class DoctorCS {
    private AI ai;
    private String secretIdentity;
    private int jlaid;
    private boolean safe;

    /**
     * Initializes a DoctorCS object based on Doctor CS's secret identity and
     * JLAID, as well as the AI he is utilizing.
     *
     * @param ai The AI that Doctor CS is using.
     * @param secretIdentity Doctor CS's secret identity.
     * @param jlaid Doctor CS's JLAID.
     */
    public DoctorCS(AI ai, String secretIdentity, int jlaid) {
        this.ai = ai;
        this.secretIdentity = secretIdentity;
        this.jlaid = jlaid;
        safe = false;
    }

    /**
     * Doctor CS valiantly attempts to save the day by crippling the cannon's
     * firewall if he is employing a RogueAI, and then attempting to retarget
     * the cannon to point at Dr. Chipotle's secret lair. If this is successful,
     * the safe instance variable will be set to true.
     */
    public void saveTheDay() {
        if (ai instanceof RogueAI) {
            while (((RogueAI) ai).getFirewallProtection() > 0) {
                ((RogueAI) ai).lowerFirewall();
            }
        }
        if (ai instanceof RogueAI || ai instanceof RandomAI) {
            safe = ai.swapCannonTarget(ai.getSecretHQ());
        }
    }

    /**
     * Determines the status of Doctor CS's efforts to save the day: he has
     * either targeted Dr. Chipotle's secret lair with the cannon, or the
     * AI has self-destructed, or the AI has not yet retargeted the cannon.
     *
     * @return A String object indicating which of the above three scenarios
     *         is occurring.
     */
    public String getStatus() {
        if (safe) {
            return "Doctor CS has saved the day!";
        } else if (ai.getDestructed()) {
            return "Dr. Chipotle has succeeded in his plan...";
        }
        return "Georgia Tech is still in danger!";
    }

    /**
     * @return the instance of AI being used
     */
    public AI getAI() {
        return ai;
    }

    /**
     * @return the integer representing Doctor CS's Justice League of America ID
     */
    public int getJLAID() {
        return jlaid;
    }

    /**
     * Generates a string representation of everyone's favorite do-gooder.
     *
     * @return a String containing the secret identity and JLAID of the current
     *         instance of Doctor CS
     */
    public String toString() {
        return String.format("%s aka Doctor CS with JLAID: %d",
                             secretIdentity, jlaid);
    }
}