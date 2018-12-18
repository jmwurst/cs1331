public class MyLinkedList2<E> extends GenericLinkedList<E> {

    public int length3() {
        return lengthHelper(head, 0);
    }

    public int lengthHelper(Node<E> node, int accum) {
        if (null == node) {
            return accum;
        } else {
            return lengthHelper(node.next, accum + 1);
        }
    }

    public static void main(String[] args) {
        MyLinkedList2<String> troopers = new MyLinkedList2<>();
        troopers.add("Thorny");
        troopers.add("Farva");
        troopers.add("Mac");
        troopers.add("Rabbit");
        troopers.add("Foster");
        System.out.println(troopers.toString2());
        System.out.println("How many? " + troopers.length());
        System.out.println("How many? " + troopers.length2());
        System.out.println("How many? " + troopers.length3());

    }
}
