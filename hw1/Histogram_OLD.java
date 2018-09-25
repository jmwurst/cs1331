import java.util.Scanner;
import java.io.File;

public class Histogram_OLD {
    
    public static boolean inRange(int n, String s)
    {
        s = s.replaceAll(" ", "");
        int high = Integer.parseInt(s.substring(0, s.indexOf('-')));
        int low = Integer.parseInt(s.substring(s.indexOf('-') + 1, s.indexOf('|')));
        return (n >= low && n <= high);
    }

    public static void main(String[] args) throws Exception {
        
        int bins = Integer.parseInt(args[1]);
        int gap = 101 / bins;

        String[] histogram = new String[bins];

        int temp = 100;
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = String.format("%3d -", temp);
            temp -= (i != histogram.length - 1) ? (gap - 1) : temp;
            histogram[i] += String.format("%3d | ", temp);
            temp--;
        }

        String fileName = args[0];
        Scanner in = new Scanner(new File(fileName));
        while (in.hasNextLine()) {
            String raw = in.nextLine().split(",")[1];
            int grade = Integer.parseInt(raw.replaceAll(" ", ""));
            for (int i = 0; i < histogram.length; i++)
                if (inRange(grade, histogram[i]))
                    histogram[i] += "[]";
        }

        for (int i = 0; i < histogram.length; i++) {
            System.out.print(histogram[i]);
            if (i != histogram.length - 1)
                System.out.println("");
        }
        
    }
}