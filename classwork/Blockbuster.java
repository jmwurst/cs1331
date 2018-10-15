public class Blockbuster {
    public static void main(String[] args) {
        Movie[] cart = {
            new Movie("ACADEMY DINOSAUR", "A Epic Drama", new Drama()),
            new Movie("Probably not Academy Dinosaur", "car chases n stuff", new Action()),
            new Movie("Terminator", "Governating", new Action())
        };

        double total = 0.0;
        for (Movie m : cart) {
            MovieType t = m.getType();
            total += t.getPrice();
        }
        System.out.println("total = " + total);
    }
}