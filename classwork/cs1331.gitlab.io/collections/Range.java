import java.lang.Iterable;
import java.util.Iterator;

public class Range implements Iterator<Integer>, Iterable<Integer> {

    private int start;
    private int end;
    private int step;

    public Range(int end) {
        this(0, end);
    }

    public Range(int start, int end) {
        this(start, end, 1);
    }

    public Range(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public boolean hasNext() {
        return start < end;
    }

    @Override
    public Integer next() {
        int ret = start;
        start += step;
        return ret;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }

    public static void main(String[] args) {
        for (Integer i: new Range(10)) {
            System.out.println(i);
        }
        System.out.println();
        for (Integer i: new Range(1, 10)) {
            System.out.println(i);
        }
        System.out.println();
        for (Integer i: new Range(0, 10, 2)) {
            System.out.println(i);
        }
    }
}
