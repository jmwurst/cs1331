import java.util.Iterator;

public class Range implements Iterator<Integer> {
    int start, stop, step;

    public Range(int start, int stop, int step) {
        this.start = start;
        this.stop = stop;
        this.step = step;
    }

    public Range(int end) {
        this(0, end);
    }

    public Range(int start, int stop) {
        this(start, stop, 1);
    }

    public boolean hasNext() {
        return start < stop;
    }
    public Integer next() {
        if (!this.hasNext()) {
            throw new RuntimeException();
        }
        int temp = start;
        start += step;
        return new Integer(temp);
    }

    public static void main(String[] args) {
        Range a = new Range(4, 9, 2);
        while (a.hasNext()) {
            System.out.println(a.next());
        }
    }
}