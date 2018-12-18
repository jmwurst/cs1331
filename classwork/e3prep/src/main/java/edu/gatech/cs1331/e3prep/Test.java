package edu.gatech.cs1331.e3prep;

class Parent {
    //void show(String in) {
    //    System.out.println("parent " + in);
    //}
    void hide() {
        System.out.println("hello b");
    }
}

class Child extends Parent {
    void show(String in) {
        System.out.println("child " + in);
    }
}

public class Test {
    static void bar() throws Throwable {
        throw new Throwable("Wee!");
    }
    static void foo() throws Throwable {
        bar();
        System.out.println("Foo!");
    }
    public static void main(String[] args) {
        Parent beter = new Child();
        ((Child) beter).show("beeeeeter");
        beter.hide();
    }
}