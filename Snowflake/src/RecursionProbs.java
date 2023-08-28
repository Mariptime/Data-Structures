import java.util.*;

public class RecursionProbs
{
    public double sumReciprocals(int n)
    {
        if(n == 1)
        {
            return 1;
        }
        else return (1.0/n) + sumReciprocals(n-1);
    }

    public int productIfEvens(int n)
    {
        if(n == 1)
        {
            return 2;
        }
        else return productIfEvens(n-1) * (n*2);
    }

    public void doubleUp(Stack<Integer> nums) {
        Stack<Integer> str = new Stack<>();
        while (!nums.isEmpty()) {
            str.push(nums.pop());
        }

        while (!str.isEmpty()) {
            nums.push(str.peek());
            nums.push(str.pop());
        }
    }

    public void countToBy(int n, int m)
    {
        if (n ==0)
        {
            System.out.println(m);
        }
        else if (n == 1)
        {
            System.out.println("1");
        }

        else countToBy(n-m, m);
    }

    public int matchingDigits(int a, int b)
    {
        int rema = a%10;
        int remb = b%10;
        int diva = a/10;
        int divb = b/10;

        if (rema != remb)
        {
            return 0;
        }
        else
            return 1 + matchingDigits(diva, divb);
    }

    public void printThis(int n)
    {
        if (n == 1)
        {
            System.out.print("*");
        }
        else if(n == 2)
        {
            System.out.print("**");
        }
        else {
            System.out.println("<");
            printThis(n-2);
            System.out.println(">");
        }
    }

    public void printThis2(int n)
    {
        if(n==1)
        {
            System.out.print("1");
        }
        else if (n==2)
        {
            System.out.print("11");
        }
        else {
            int round = (int) ((n/2.0) + 0.5);
            System.out.print(round);
            printThis2(n-2);
            System.out.println(round);
        }
    }
}
