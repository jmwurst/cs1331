import java.time.LocalDate;

public class Bill extends Employee2 implements Payable {

    public Bill(String name, LocalDate hireDate) {
        super(name, hireDate);
    }

    public static void main(String[] args) {
        Bill b = new Bill("Bill", LocalDate.now());
        System.out.println(b.getMonthlyPay());

    }
}
