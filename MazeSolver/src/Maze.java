import java.io.*;
import java.util.*;

public class Maze
{
    private int row;
    private int col;
    private Square exit;
    private Square start;
    private Square[][] grid;


    public Maze() {

    }
    public boolean loadMaze(String fileName)
    {
        try {
            Scanner scan = new Scanner(new File(fileName));
            row = scan.nextInt();
            col = scan.nextInt();
            grid = new Square[row][col];
            int slot;
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    slot = scan.nextInt();
                    grid[r][c] = new Square(r,c,slot);
                    if (slot == 2)
                    {
                        start = new Square(r,c,slot);
                    }
                    if (slot == 3)
                    {
                        exit = new Square(r,c,slot);
                    }
                }
            }
        return true;
        }
        catch (Exception e)
        {
            System.out.println("Failed To Read File Import");
            return false;
        }
    }

    List<Square> getNeighbors(Square s)
    {
        List<Square> list = new ArrayList<>();
        if(!(s.getRow()+1 >= row))
            list.add(grid[s.getRow()+1][s.getCol()]);
        if(!(s.getCol()+1 >= col))
            list.add(grid[s.getRow()][s.getCol()+1]);
        if(!(s.getRow()-1 < 0))
            list.add(grid[s.getRow()-1][s.getCol()]);
        if(!(s.getCol()-1 < 0))
            list.add(grid[s.getRow()][s.getCol()-1]);
        return list;
    }

    public Square getExit() {
        return exit;
    }

    public Square getStart() {
        return start;
    }

    void reset()
    {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                grid[r][c].reset();
            }
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
               output += grid[r][c].toString();
            }
            output += "\n";
        }
        return output;
    }
}
