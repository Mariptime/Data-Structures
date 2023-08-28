
public class GuitarString 
{

	private final RingBuffer buffer;
	private int stringLength;
	private int time;
	private static final int SAMPLING_RATE = 44100;
	private static final double DECAY_FACTOR = .996;
	
	public GuitarString(double frequency)
	{
		time = 0;
		int length = (int) Math.ceil(SAMPLING_RATE / frequency);
		stringLength = length;
		buffer = new RingBuffer(length);
		while(!buffer.isFull())
			buffer.enqueue(0);
	}

	public GuitarString(double[] init)
	{
		time = 0;
		buffer = new RingBuffer(init.length);
		for(int i = 0; i < init.length; i++)
		{
			buffer.enqueue(init[i]);
		}
	
	}

	public void pluck()
	{
		for(int i = 0; i < stringLength; i++)
		{
			double rnd = Math.random() - 0.5;
			buffer.enqueue(rnd);
		}
		
	}

	public void tic()
	{
		double first = buffer.dequeue();
		double second = sample();
		buffer.enqueue(DECAY_FACTOR * (first + second) / 2 );
		time++;
	}
	public double sample()
	{
		return buffer.peek();
	}

	public int time()
	{
		return time;
	}

	public static void main(String[] args) {
		int N = 25;
		double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };
		GuitarString testString = new GuitarString(samples);
		for (int i = 0; i < N; i++) {
			int t = testString.time();
			double sample = testString.sample();
			System.out.printf("%6d %8.4f\n", t, sample);
			testString.tic();
		}
	}
}
