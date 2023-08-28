import java.awt.*;

enum GemType {
    GREEN, BLUE, ORANGE
}

public class Gem {
    private final GemType type;
    private final int points;

    public Gem() {
        this(GemType.values()[(int) (Math.random() * GemType.values().length)], 4);
    }

    public Gem(GemType type, int points) {
        this.type = type;
        this.points = points;
    }

    public String toString() {
        return getType() + " " + getPoints();
    }

    public GemType getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

    public void draw(double x, double y) {
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 14));
        StdDraw.setPenColor(Color.WHITE);
        if (getType() == GemType.GREEN) {
			StdDraw.picture(x, y, "gem_green.png");
			StdDraw.text(x,y,getPoints() + "");
        } else if (getType() == GemType.BLUE) {
			StdDraw.picture(x, y, "gem_blue.png");
            StdDraw.text(x,y,getPoints() + "");
        } else if (getType() == GemType.ORANGE) {
			StdDraw.picture(x, y, "gem_orange.png");
            StdDraw.text(x,y,getPoints() + "");
        }
    }

    public static void main(String[] args) {
		final int maxGems = 16;
		Gem green  = new Gem(GemType.GREEN, 10);
		Gem blue   = new Gem(GemType.BLUE, 20);
		Gem orange = new Gem(GemType.ORANGE, 30);
		System.out.println(green  + ", " + green.getType()  + ", " + green.getPoints());
		System.out.println(blue   + ", " + blue.getType()   + ", " + blue.getPoints());
		System.out.println(orange + ", " + orange.getType() + ", " + orange.getPoints());
		green.draw(0.3, 0.7);
		blue.draw(0.5, 0.7);
		orange.draw(0.7, 0.7);
		for (int i = 0; i < maxGems; i++)
		{
			Gem g = new Gem();
			g.draw(1.0 / maxGems * (i + 0.5), 0.5);
		}
    }
}
