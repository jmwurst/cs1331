public class MyLinkedList<E> extends GenericLinkedList<E> {

    public int length3() {
        // Your code here. Replace the stub with a call to a recursive
        // helper method that computes the length of the list (whcih
        // you must also write). Note that Node<E> and head are
        // protected in GenericLinkedList<E>. Look at toString2 in
        // GenericLinkedList<E> for inspiration.
        return -1;
    }

    // For testing. All three length methods should return the same value.
    public static void main(String[] args) {
        MyLinkedList<String> troopers = new MyLinkedList<>();
        troopers.addFront("Thorny");
        troopers.addFront("Farva");
        troopers.addFront("Mac");
        troopers.addFront("Rabbit");
        troopers.addFront("Foster");
        System.out.println(troopers.toString2());
        System.out.println("How many? " + troopers.length());
        System.out.println("How many? " + troopers.length2());
        System.out.println("How many? " + troopers.length3());

    }
}
