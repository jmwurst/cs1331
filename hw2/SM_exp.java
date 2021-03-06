import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
* Given a set of training corpora and a string, construct first-order
* Markov chains of the transition probabilities between letters found
* sequentially in the corpora and, for each model, compute the probability
* that the string was generated by it.
*
* @author Justin Wurst
* @version 09-09-18
*/
public class SM_exp {

    private double[][] matrix;
    private String modelName, corpusFile;
    private final static String[] CORPUS_LIST = {"english.corpus",
                                                 "french.corpus",
                                                 "hiphop.corpus",
                                                 "lisp.corpus",
                                                 "spanish.corpus"};

    /**
    * Initializes a SM_exp object and generates a matrix that represents
    * a probabilistic model based on a given corpus file.
    *
    * @param  modelName    name of the model to generate
    * @param  corpusFile   name of the file containing the target corpus
    */
    public SM_exp(String modelName, String corpusFile) throws IOException {
        this.modelName = modelName;
        this.corpusFile = corpusFile;
        System.out.printf("Training %s model ... ", this.modelName);

        FileReader in = new FileReader(this.corpusFile);
        int[][] counts = new int[26][26];
        int prev = 0, cur = 0;
        while ((cur = in.read()) != -1) {
            if (Character.isAlphabetic(cur)) {
                cur = Character.toLowerCase(cur);
                if (Character.isAlphabetic(prev)) {
                    counts[prev - 'a'][cur - 'a']++;
                }
                prev = cur;
            }
        }
        in.close();

        matrix = new double[26][26];
        for (int p = 0; p < 26; p++) {
            double rowSum = 0.0;
            for (int c = 0; c < 26; c++) {
                rowSum += counts[p][c];
            }
            for (int c = 0; c < 26; c++) {
                matrix[p][c] = (rowSum == 0 || counts[p][c] == 0)
                               ? 0.01
                               : counts[p][c] / rowSum;
            }
        }

        System.out.println("done.");

    }

    /**
    * @return the name of the generated model
    */
    public String getName() {
        return modelName;
    }

    /**
    * @return the model's name and a tabular representation
    *         of the probabilistic matrix
    */
    public String toString() {
        String out = "Model: " + modelName + "\n ";
        for (int c = 97; c <= 122; c++) {
            out += String.format("    %s", (char) c + "");
        }
        out += "\n";
        for (int r = 0; r < 26; r++) {
            out += (char) (r + 97) + "";
            for (int c = 0; c < 26; c++) {
                out += String.format("%5.2f", matrix[r][c]);
            }
            out += "\n";
        }
        return out;
    }

    /**
    * Determines the probability that a given string was generated
    * by a source model.
    *
    * @param  test  the string to evaluate for probability
    *               of generation
    *
    * @return a double which represents the probability
    */
    public double probability(String test) {
        double prob = 1.0;
        int prev = 0, cur = 0;
        for (int i = 0; i < test.length(); i++) {
            cur = test.charAt(i);
            if (Character.isAlphabetic(cur)) {
                cur = Character.toLowerCase(cur);
                if (Character.isAlphabetic(prev)) {
                    prob *= matrix[prev - 'a'][cur - 'a'];
                }
                prev = cur;
            }
        }
        return prob;
    }

    /**
    * Creates SM_exp objects for each of a set of given corpora
    * and prints the probabilites that a given string was generated by
    * each model.
    *
    * @param  args  index 0 - (args.length - 2) are the file names of corpora to use
    *               index args.length - 1 is the string to evaluate
    */
    public static void main(String[] args) {
        boolean all = args[0].equals("*.corpus");
        String testStr = args[args.length - 1];
        for (String s : args)
            System.out.println(s);
        ArrayList<SM_exp> models = new ArrayList<SM_exp>();
        for (int i = 0; i < args.length - 1; i++) {
            try {
                models.add(new SM_exp(args[i].substring(0, args[i].indexOf('.')),
                                      args[i]));
            } catch (Exception e) {
                System.out.print("Invalid corpus name");
            }
        }

        System.out.printf("Analyzing: %s%n", testStr);
        double[] allProbs = new double[models.size()];
        int highestProbIndex = 0;
        double totalProb = 0.0, highestProbVal = -1;
        for (int i = 0; i < allProbs.length; i++) {
            allProbs[i] = models.get(i).probability(testStr);
            totalProb += allProbs[i];
            if (allProbs[i] > highestProbVal) {
                highestProbIndex = i;
                highestProbVal = allProbs[i];
            }
        }
        for (int i = 0; i < allProbs.length; i++) {
            allProbs[i] /= totalProb;
        }
        for (int i = 0; i < models.size(); i++) {
            System.out.printf("Probability that the test string is %8s: %4.2f%n",
                              models.get(i).getName(),
                              allProbs[i]);
        }
        System.out.printf("Test string is most likely %s.%n",
                          models.get(highestProbIndex).getName());
    }
}