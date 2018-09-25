import java.util.Arrays;

public class Calc {
    
    public static double apply(String operator, int[] operands) {
        double result = operands[0];
        for (int i = 1; i < operands.length; i++) {
            
        }
        return result;
    }

    public static void main(String[] args) {
        String operator = args[0];
        int[] operands = new int[args.length - 1];
        boolean isFirst = true;
        int index = 0;
        for (String arg : args) {
            if (isFirst) {
                isFirst = false;
            }
            else {
                operands[index] = Integer.parseInt(arg);
                index++;
            }
        }
        double answer = apply(operator, operands);
        System.out.printf("(%s) = ", Arrays.toString(args)
                                      .replace("[", "")
                                      .replace("]", "")
                                      .replace(",", ""));
        System.out.println(answer);
    }
}