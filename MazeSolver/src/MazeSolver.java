import java.util.*;

public abstract class MazeSolver
{
    protected Maze maze;

    protected boolean solved = false;
    protected boolean solvable = true;

    public MazeSolver(Maze maze)
    {
        this.maze = maze;
    }

    public abstract void makeEmpty();

    public abstract boolean isEmpty();

    public abstract void add(Square s);

    public abstract Square next();

    public boolean isSolved(){
        return solved || !solvable;
    }

    public void step(){
        if(isEmpty()){
            solvable = false;
            return;
        }
        Square next = next();
        List<Square> list = maze.getNeighbors(next);
        for(Square s : list) {
            if(s.getStatus() == Square.UNKNOWN){
                add(s);
                s.setStatus(Square.WORKING);
            }
            if(s.getType() == Square.EXIT)
            {
                solved = true;
            }
        }
        next.setStatus(Square.EXPLORED);
    }

    public String getPath(){
        return (solved) ? "Maze is Solved" : "Maze is Not Solved";
    }

    public void solve(){
        while(!isSolved() && solvable){
            step();
        }
    }
}
