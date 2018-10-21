import java.util.*;

public class Tester {
    public static void main(String[] args) {
        ArrayList<RandomAI> randomAI = new ArrayList<RandomAI>();
        ArrayList<RogueAI> rogueAI = new ArrayList<RogueAI>();
        ArrayList<DoctorCS> doctorCS = new ArrayList<doctorCS>();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("RandomAI (1)%nRogueAI (2)%nDoctorCS (3)%nExit (4)%nEnter selection here: ");
            int sel = in.nextInt();
            if (sel == 1) {
                System.out.print("cannonTarget (separate with comma):");
                String input = in.nextLine();
                int lat = Integer.parseInt(input.substring(0, input.indexOf(',')));
                int lon = Integer.parseInt(input.substring(input.indexOf(',')+1));

            } else if (sel == 2) {

            } else if (sel == 3) {

            } else if (sel == 4) {
                System.exit(0);
            }
        }
    }
}