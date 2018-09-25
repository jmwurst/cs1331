public class test {
    
    public test(String...args){
    }

    public static void main(String[] args)  {
        
        int[] one = {3, 4, 5};
        int[] two = {4, 5, 6};
        int[] three = {5, 6, 7};

        int[] temp = one;
        one = two;
        two = three;
        three = null;


        if (two == three)
            System.out.println("yeeyee");
        for (int i = 0; i < 3; i++)
            System.out.println(temp[i]);

        for (int i = 0; i < 3; i++)
            System.out.println(one[i]);

        for (int i = 0; i < 3; i++)
            System.out.println(two[i]);
    }
}