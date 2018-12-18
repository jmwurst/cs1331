import java.time.LocalDate;

public class GoofyDemo {

    public static void main(String[] args) {
        HourlyEmployee3 alissa = new HourlyEmployee3("Alissa P. Hacker",
                                                     LocalDate.now());
        Payable eva = new HourlyEmployee3("Eva L. Uator", LocalDate.now());

        System.out.printf("%s is-a HourlyEmployee3: %s%n",
                          eva, eva instanceof HourlyEmployee3);
        System.out.printf("%s is-a Employee3: %s%n",
                          eva, eva instanceof Employee3);
    }
}
