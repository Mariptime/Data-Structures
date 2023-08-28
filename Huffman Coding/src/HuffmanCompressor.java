import java.io.*;
import java.util.*;

public class HuffmanCompressor {
    public static final int CHAR_MAX = 255;
    private static String rootFileName;

    public static void compress(String fileName) {
        try
        {
            rootFileName = fileName.split(".txt")[0];
            generateHuffmanTree(rootFileName);
            String[] codes = new String[CHAR_MAX+1];
            Scanner File_Code = new Scanner(new File(rootFileName + ".code"));
            while (File_Code.hasNextLine()) {
                int n = Integer.parseInt(File_Code.nextLine());
                codes[n] = File_Code.nextLine();
            }

            File File_Short = new File(rootFileName + ".short");
            FileInputStream input = new FileInputStream(rootFileName + ".txt");
            BitOutputStream output = new BitOutputStream(File_Short.getName());

            int read = input.read();
            while (read != -1) {
                generateFrequencyTable(codes[read], output);
                read = input.read();
            }
            input.close();
            output.close();
        }
        catch (IOException e)
        {
            System.out.println("An IOException Occurred");
        }
    }

    public void expand(String codeFile, String fileName)
    {
        HuffmanTree tree = new HuffmanTree(codeFile);
        BitInputStream input = new BitInputStream(rootFileName + ".short");
        File outputTxt = new File(fileName);
        String output = outputTxt.getName();
        tree.decode(input, output, CHAR_MAX);
    }

    private static void generateHuffmanTree(String filename)  {
        try
        {
            FileInputStream input = new FileInputStream(filename + ".txt");
            int[] count = new int[CHAR_MAX];
            int read = input.read();
            while (read != -1) {
                count[read]++;
                read = input.read();
            }
            HuffmanTree tree = new HuffmanTree(count);
            File output = new File(filename + ".code");
            tree.write(output.getName());
        }
        catch (IOException e)
        {
            System.out.println("An IOException Occurred");

        }
    }

    private static void generateFrequencyTable(String s, BitOutputStream output) {
        for (int i = 0; i < s.length(); i++)
            output.writeBit(s.charAt(i) - '0');
    }
}