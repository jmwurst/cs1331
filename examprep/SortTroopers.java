import java.util.*;
import java.util.stream.Collectors;

public class SortTroopers {

    static class MustacheComparator implements Comparator<Trooper> {

        public int compare(Trooper a, Trooper b) {
            if (a.hasMustache() && !b.hasMustache()) {
                return 1;
            } else if (b.hasMustache() && !a.hasMustache()) {
                return -1;
            } else {
                return a.getName().compareTo(b.getName());
            }
        }
    }

    public static void main(String[] args) {
        List<Trooper> troopers = new ArrayList<>();
        troopers.add(new Trooper("Thorny", true));
        troopers.add(new Trooper("Farva", true));
        troopers.add(new Trooper("Farva", true));
        troopers.add(new Trooper("Ursula", false));
        troopers.add(new Trooper("Rabbit", false));
        troopers.add(new Trooper("Mac", true));
        troopers.add(new Trooper("Foster", true));


        // Using an instance of a named (inner) class
        Collections.sort(troopers, new MustacheComparator());

        // Using an instance of an anonymous inner class
        Collections.sort(troopers, new Comparator<Trooper>() {
            public int compare(Trooper a, Trooper b) {
                if (a.hasMustache() && !b.hasMustache()) {
                    return 1;
                } else if (b.hasMustache() && !a.hasMustache()) {
                    return -1;
                } else {
                    return a.getName().compareTo(b.getName());
                }
            }
        });

        // Using a lambda expression
        Collections.sort(troopers, (Trooper a, Trooper b) -> {
            if (a.hasMustache() && !b.hasMustache()) {
                return 1;
            } else if (b.hasMustache() && !a.hasMustache()) {
                return -1;
            } else {
                return a.getName().compareTo(b.getName());
            }
        });
        System.out.println("\nAfter mustache-based sorting:\n" + troopers);

        Collections.sort(troopers, (Trooper a, Trooper b) -> a.getName().compareTo(b.getName()));
        System.out.println("\nAfter name-based sorting (lambda 2):\n" + troopers);

        // Using a composed comparator
        Comparator<Trooper> byMustacheThenName =
            Comparator.comparing(Trooper::hasMustache)
            .thenComparing(Trooper::getName);
        Collections.sort(troopers, byMustacheThenName);
        System.out.println("\nBy mustache, then name:\n" + troopers);
    }
}
