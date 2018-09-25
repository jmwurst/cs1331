import java.util.Scanner;
import java.io.File;

/**
* Given a list of values and the number of bins,
* generate a histogram of the given data.
*
* @author Justin Wurst
* @version 09-05-18
*/
public class Histogram_OLD2 {

    /**
    * Generates a histogram.
    *
    * @param  args  index 0 contains the name of the file where the data is
    *               index 1 contains the number of bins
    */
    public static void main(String[] args) throws Exception {

        int bins = Integer.parseInt(args[1]);
        int gap = 101 / bins;

        int[] histogram = new int[101];
        String fileName = args[0];
        Scanner in = new Scanner(new File(fileName));
        while (in.hasNextLine()) {
            String raw = in.nextLine().split(",")[1];
            int grade = Integer.parseInt(raw.trim());
            histogram[grade]++;
        }

        int high = 100;
        for (int i = 0; i < bins; i++) {
            int low = high - ((i != bins - 1) ? (gap - 1) : high);
            System.out.printf("%3d -%3d | ", high, low);
            for (int j = low; j <= high; j++) {
                for (int k = 0; k < histogram[j]; k++) {
                    System.out.print("[]");
                }
            }
            System.out.println();
            high = low - 1;
        }
    }
}