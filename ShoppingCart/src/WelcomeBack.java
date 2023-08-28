import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WelcomeBack {

    public String getMiddle(String str) {
        int midileIndex = str.length() / 2;
        return str.length() % 2 == 1 ? str.substring(midileIndex, midileIndex + 1) : str.substring(midileIndex - 1, midileIndex + 1);
    }

    private int sumNum(int i) {
        return i >= 0 ? i + (sumNum(i - 1)) : 0;
    }

    public int[] sumNumbers(int i) {
        int[] arr = new int[i + 1];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = sumNum(j);
        }
        return arr;
    }

    public int sumDigits(int i) {
        int sum = 0;
        for (int j = 0; j < 10; j++) {
            sum += (i % 10);
            i /= 10;
        }
        return sum;
    }

    public int keepSummingDigits(int i) {
        i = sumDigits(i);

        if (i / 10 == 0) {
        } else {
            i = keepSummingDigits(i);
        }

        return i;
    }

    public String getIntersection(int[] a, int[] b) {
        String out = "";
        for (int r = 0; r < a.length; r++) {
            for (int c = 0; c < b.length; c++) {
                if (a[r] == b[c]) {
                    out += a[r];
                    break;
                }
            }
        }
        return out;
    }

    public HashMap<Integer, Integer> mapLengths(String[] words) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        int wlength = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > wlength) {
                wlength = words[i].length();
                count = 1;
                map.put(wlength, count);
            } else if (words[i].length() < wlength) {
                if (map.get(words[i].length()) == null) {
                    wlength = words[i].length();
                    count = 1;

                } else {
                    wlength = words[i].length();
                    count = map.get(words[i].length()) + 1;
                }
                map.put(wlength, count);
            } else {
                count++;
                map.put(wlength, count);
            }

        }
        return map;
    }

    public int sumWithoutCarry(int a, int b) {
        int result = 0;
        int multiplier = 1;
        int bit_sum;

        while (true) {
            if (a == 0 && b == 0) {
                break;
            }

            bit_sum = (a % 10) + (b % 10);

            bit_sum %= 10;

            result = (bit_sum * multiplier) + result;
            a /= 10;
            b /= 10;

            multiplier *= 10;
        }
        return result;
    }

    public int buySell1(int[] stock) {
        if (stock == null || stock.length <= 1)
            return 0;

        int min = stock[0];
        int result = 0;

        for (int i = 1; i < stock.length; i++) {
            result = Math.max(result, stock[i] - min);
            min = Math.min(min, stock[i]);
        }

        return result;
    }

    private boolean noSame(ArrayList<Integer> list, int pos) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == pos || (pos - 1) == list.get(i) || (pos + 1 == list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Integer> fib(int a) {
        ArrayList<Integer> fibonacci = new ArrayList<>();
        fibonacci.add(1);
        fibonacci.add(1);
        int count = 1;
        int x = 1;
        while (count < a) {
            fibonacci.add(fibonacci.get(x - 1) + fibonacci.get(x));
            count = fibonacci.get(fibonacci.size() - 1);
            x++;
        }
        return fibonacci;
    }

    public void zeck(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        try {
            Scanner kb = new Scanner(f);
            int[] arr = new int[kb.nextInt()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = kb.nextInt();
            }
            int max = arr[0];
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            ArrayList<Integer> list = fib(max);
            for (int i = 0; i < arr.length; i++) {
                int sum = 0;
                ArrayList<Integer> dup = new ArrayList<>();
                ArrayList<Integer> pos = new ArrayList<>();
                while (sum < arr[i])
                {
                    for(int j = list.size()-1; j >= 0; j--)
                    {
                        if((sum + list.get(j)) <= arr[i] && noSame(pos, j))
                        {
                            sum+=list.get(j);
                            dup.add(list.get(j));
                            pos.add(j);
                        }
                        if (sum == arr[i])
                        {
                            break;
                        }
                    }
                }
                System.out.print(arr[i] + " = ");
                for (int m = 0; m < dup.size() - 1; m++)
                {
                    System.out.print(dup.get(m) + " + ");
                }
                System.out.println(dup.get(dup.size()-1));
            }
            kb.close();

        } catch (Exception e) {
            throw e;
        }
    }

}

