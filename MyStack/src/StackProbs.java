
import java.util.Stack;

public class StackProbs
{
    private final int index = -1;

    public Stack<Integer> doubleUp(Stack<Integer> nums)
    {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while(!nums.isEmpty())
        {
            stack.push(nums.peek());
            stack.push(nums.pop());
        }
        while(!stack.isEmpty())
        {
            stack2.push(stack.pop());
        }
        return stack2;
    }
    public Stack<Integer> posAndNeg (Stack<Integer> nums)
    {
        Stack<Integer> pos = new Stack<>();
        Stack<Integer> neg = new Stack<>();
        while(!nums.isEmpty())
        {
            if(nums.peek() < 0)
            {
                neg.push(nums.pop());
            }
            else if (nums.peek() >= 0)
            {
                pos.push(nums.pop());
            }
        }
        while (!pos.isEmpty())
        {
            neg.push(pos.pop());
        }
        return neg;
    }
    public Stack<Integer> shiftByN(Stack<Integer> nums, int n)
    {
        for (int i = 0; i < n ; i++) {
            nums.add(0,nums.pop());
        }
        return nums;
    }
    private boolean isVowel(char c)
    {
       return    (c == 'a' || c == 'A' || c == 'e'
               || c == 'E' || c == 'i' || c == 'I'
               || c == 'o' || c == 'O' || c == 'u'
               || c == 'U');
    }
    public String reverseVowels(String str)
    {
        Stack<Character> stack = new Stack<>();
        String output = "";
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if(isVowel(ch[i]))
            {
                stack.push(ch[i]);
            }
        }
        for (int i = 0; i < ch.length; i++) {
            if(isVowel(ch[i]) && !stack.isEmpty())
            {
                ch[i] = stack.pop();
            }
        }
        for (int i = 0; i < ch.length; i++) {
            output += ch[i];
        }

        return output;
    }
    public boolean bracketBalance(String s)
    {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(i);

            if (x == '(' || x == '[' || x == '{') {
                stack.push(x);
                continue;
            }

            if (stack.isEmpty())
                return false;

            switch (x) {
                case ')':
                    stack.pop();
                    if (x == '{' || x == '[')
                        return false;
                    break;

                case '}':
                    stack.pop();
                    if (x == '(' || x == '[')
                        return false;
                    break;

                case ']':
                    stack.pop();
                    if (x == '(' || x == '{')
                        return false;
                    break;
            }
        }
        return stack.isEmpty();
    }
}
