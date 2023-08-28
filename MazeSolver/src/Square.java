public class Square
{
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int START = 2;
    public static final int EXIT = 3;

    public static final char WORKING = 'o';
    public static final char EXPLORED = '.';
    public static final char ON_FINAL_PATH = 'x';
    public static final char UNKNOWN = '_';

    private final int row;
    private final int col;
    private final int type;
    private char status;

    public Square(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
        if(type == EMPTY)
        {
            status = UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch(type)
        {
            case EMPTY:
                return status + "";
            case WALL:
                return "#";
            case START:
                return "S";
            case EXIT:
                return "E";
            default:
                return "!";
        }
    }

    public boolean equals (Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (!(other instanceof Square))
        {
            return false;
        }
        Square s = (Square) other;
        return s.getRow() == this.row && s.getCol() == this.col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getType() {
        return type;
    }

    public char getStatus() {
        return status;
    }

    public void reset()
    {
        if (type == 0)
        {
            status = UNKNOWN;
        }
    }

    public void setStatus(char status) {
        this.status = status;
    }
}
