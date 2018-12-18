public class test {
  
    static class Human {
        protected String s;

        public Human(String s) {
          this.s = s;
        }

        public Human() {
          this("");
        }

        public void walk() {
            System.out.println("Human walks");
        }
    }

    static class Boy extends Human {
        public Boy(String s) {
          super(s);
        }

        public Boy() {
          this("");
        }

        public void walk(){
            System.out.println("Boy walks");
        }
        public void run(){
            System.out.println("Boy runs");
        }

        public boolean equals(Boy o) {
          if (null == o) {
            return false;
          }
          if (!(o instanceof Boy)) {
            return false;
          }
          Boy ob = (Boy) o;
          return this.s.equals(ob.s);
        }
    }

    static class Adult extends Human {
        public void walk(){
            System.out.println("Adult walks");
        }
    }

    public static void main(String[] args) {
        Boy b = new Boy();
        Human obj = new Boy();
        Human obj2 = new Human();
        Human obj3 = new Adult();

        b.walk();
        b.run();
        obj.walk();
        ((Boy)obj).run();
        obj2.walk();
        //obj2.run();
        obj3.walk();
        //((Boy)obj3).run();
        System.out.println(new Boy("pee").equals(new Boy("pee")));
    }
}