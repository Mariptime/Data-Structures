import java.io.FileNotFoundException;

// basically a testing method
public class WorstFit {

    public static void main(String[] args) {
        //load disk from input file
        try {
            Disk disk = new Disk("input20.txt");
            //create groups of bytes and prints them out in a similar fashion
            //to how it was required in the doc
            disk.figureOut();

            //approximately prints the required disks
            System.out.println("Number of disks rq'd = " + disk.count);
        } catch (FileNotFoundException e) {
            //print error if constructing causes this error
            System.out.println("File Not Found error occurred");
        }
    }
}
