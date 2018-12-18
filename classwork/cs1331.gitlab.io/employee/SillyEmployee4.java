import java.time.LocalDate;

public  class SillyEmployee4 extends Employee4 {

    public SillyEmployee4(String name, LocalDate hireDate) {
        super(name, hireDate);
    }

    public double monthlyPay() {
        return 1000000.0;
    }

    public static void main(String[] args) {
        Employee4 emp1 = new SillyEmployee4("Robert Paulson", LocalDate.now());
        Employee4 emp2 = new Employee4("Robert Paulson", LocalDate.now());
    }
}
