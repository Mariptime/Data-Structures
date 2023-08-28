
public class GuitarHero {

	public static void main(String[] args)
	{
		String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
		{
			GuitarString[] guitarString = new GuitarString[keyboard.length()];
		for(int i = 0; i < keyboard.length(); i++)
		{
			double f = 440.0 * Math.pow(2.0, (i-24.0)/12.0);
			guitarString[i] = new GuitarString(f);
		}

		while (true)
		{
            if (StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				if (keyboard.contains(String.valueOf(key)))
					guitarString[keyboard.indexOf(key)].pluck();
			}

            double sample = 0;
            for(int i = 0; i < keyboard.length(); i++)
            {
            	sample += guitarString[i].sample();
            	guitarString[i].tic();
            }
            StdAudio.play(sample);
        }

		}
	}
}
