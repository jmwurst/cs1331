package edu.gatech.cs1331.iterators;

import java.util.Iterator;

public class Range implements Iterator<Integer>, Iterable<Integer> {

    private int begin, end;

    public Range(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public boolean hasNext() {
        return begin < end;
    }

    @Override
    public Integer next() {
        return begin++;
    }

    public static void main(String[] args) {
        Range zeroTen = new Range(0, 11);
        while (zeroTen.hasNext()) {
            int next = zeroTen.next();
            System.out.println("next = " + next);
        }

        for (Integer i: new Range(5, 8)) {
            System.out.println(i);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
}
