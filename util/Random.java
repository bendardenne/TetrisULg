package util;

public class Random {

	private int seed;

	public Random()
	{
		// nanoTime ensures with good probability that
		// two consecutives instances will not have the same seed
		seed = (int) System.nanoTime();
	}

	public Random(int seed)
	{
		super();
		this.seed = seed;
	}

	// Returns a random integer in [0, max[
	public int nextInt(int max)
	{
		seed = Math.abs(22695477 * seed + 1) ;

		return (int) seed % max;
	}

}
