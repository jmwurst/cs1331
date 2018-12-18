package edu.gatech.cs1331.exam3review;

import java.util.Iterator;

public class MyStuff implements Iterable<String> {

    private class Msi implements Iterator<String> {

        private int i;

        @Override
        public boolean hasNext() {
            return i < stuff.length;
        }

        @Override
        public String next() {
            return stuff[i++];
        }
    }

    private String[] stuff = {"foo", "bar", "baz"};

    interface I {
        int a(String s);
    }

    public static void main(String[] args) {
        I i = s -> s.length();
        I iAlt = (String s) -> s.length();

        I i2 = new I() {
            public int a(String s) {
                return s.length();
            }
        };

        for (String thing : new MyStuff()) {
            System.out.println("Thing: " + thing);
        }

        Iterator<String> iter = new MyStuff().iterator();
        while(iter.hasNext()) {
            String thing = iter.next();
            System.out.println("Thing: " + thing);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new Msi();
    }
}
