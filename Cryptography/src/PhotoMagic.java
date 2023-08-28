import java.awt.*;

public class PhotoMagic {

    public static Picture transform(Picture picture, LFSR lfsr)
    {
        int width = picture.width();
        int height = picture.height();

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                Color color = picture.get(i,j);

                int red = color.getRed();
                int newRed = lfsr.generate(8)^red;
                int green = color.getGreen();
                int newGreen = lfsr.generate(8)^green;
                int blue = color.getBlue();
                int newBlue= lfsr.generate(8)^blue;

                Color newColor = new Color(newRed, newGreen, newBlue);
                picture.set(i,j,newColor);
            }
        }
        return picture;
    }

    public static void main(String[] args) {
        Picture picture  = new Picture("pipe.png");
        LFSR lfsr = new LFSR("10100101", 5);

        Picture picture1 = transform(picture, lfsr);
        picture1.show();

        Picture picture2 = transform(picture1, lfsr);
        picture2.show();

    }
}