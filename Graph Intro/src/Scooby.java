import java.io.*;
import java.util.*;

public class Scooby {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(new File("scooby.dat"));
            int n = scan.nextInt();
            for (int iterations = 0; iterations < n; iterations++) {
                String[] root = null;
                for (int i = 0; i < n; i++) {
                    System.out.println(n);
                    root = scan.nextLine().split(" ");
                    Arrays.sort(root);
                }

                String[] toPath = scan.nextLine().split("");
                String currentSpot = toPath[0];
                String finish = toPath[1];
                String pastSpot = "";

                for (int i = 0; i < 25; i++) {
                    if (root[i % root.length].equals(pastSpot)) {
                        System.out.println("no");
                        break;
                    } else if (currentSpot.equals(root[i % root.length].split("")[0])) {
                        pastSpot = root[i % root.length];
                        currentSpot = pastSpot.charAt(1) + "";
                        if (currentSpot.equals(finish)) {
                            System.out.println("yes");
                            break;
                        }
                    } else if (currentSpot.equals(root[i % root.length].split("")[1])) {
                        pastSpot = root[i % root.length];
                        currentSpot = pastSpot.charAt(0) + "";
                        if (currentSpot.equals(finish)) {
                            System.out.println("yes");
                            break;
                        }
                    } else {
                        System.out.println("no");
                        break;
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}