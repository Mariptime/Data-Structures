import java.util.*;

public class Permutations {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a string to permute: ");
        String source = scan.next();
        System.out.println("\nPermutations of " + source + ": \n" + printPerm(source));
    }

    public static Set<String> printPerm(String str) {
        Set<String> permutations = new HashSet<>();
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            permutations.add("");
            return permutations;
        }
        char first = str.charAt(0);
        String sub = str.substring(1);
        Set<String> words = printPerm(sub);
        for (String strNew : words) {
            for (int i = 0;i<=strNew.length();i++){
                permutations.add(strNew.substring(0, i) + first + strNew.substring(i));
            }
        }
        return permutations;
    }
}
