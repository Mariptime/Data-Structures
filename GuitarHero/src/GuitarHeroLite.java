
public class GuitarHeroLite { 

    public static void main(String[] args) {
        double CONCERT_A = 440.0;
        double CONCERT_C = CONCERT_A * Math.pow(2, 3.0/12.0);
        GuitarString stringA = new GuitarString(CONCERT_A);
        GuitarString stringC = new GuitarString(CONCERT_C);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'a') { stringA.pluck(); }
                if (key == 'c') { stringC.pluck(); }
            }
            double sample = stringA.sample() + stringC.sample();
            StdAudio.play(sample);
            stringA.tic();
            stringC.tic();
        }
    }

}