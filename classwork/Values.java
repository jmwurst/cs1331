public class Values {

    public static String toString(String[] a) {
        String result = "[";
        for (int i = 0; i < a.length; i++)
            result += a[i] + ((i == a.length - 1) ? "" : ", ");
        return result + "]";
    }

    public static void main(String[] args) {
        String argsAsString = toString(args);
        System.out.println(argsAsString);
    }
}