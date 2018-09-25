import java.util.Scanner;
import java.io.File;

/**
* Given a list of values and the number of bins,
* generate a histogram of the given data.
*
* @author Justin Wurst
* @version 09-05-18
*/
public class Histogram {

    /**
    * Generates a histogram.
    *
    * @param  args  index 0 contains the name of the file where the data is
    *               index 1 contains the number of bins
    */
    public static void main(String[] args) throws Exception {

        int bins = Integer.parseInt(args[1]);
        int gap = 101 / bins;
        int[] histogram = new int[bins];
        String[] labels = new String[bins];

        String fileName = args[0];
        Scanner in = new Scanner(new File(fileName));
        while (in.hasNextLine()) {
            String rawGrade = in.nextLine().split(",")[1].trim();
            int grade = Integer.parseInt(rawGrade);

            int high = 100;
            for (int i = 0; i < bins; i++) {
                int low = high - ((i != bins - 1) ? (gap - 1) : high);
                if (grade >= low && grade <= high) {
                    histogram[i]++;
                }
                labels[i] = String.format("%3d -%3d | ", high, low);
                high = low - 1;
            }
        }

        for (int i = 0; i < bins; i++) {
            System.out.print(labels[i]);
            for (int j = 0; j < histogram[i]; j++) {
                System.out.print("[]");
            }
            System.out.println();
        }
    }
}