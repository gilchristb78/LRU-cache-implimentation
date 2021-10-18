import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import java.util.HashMap;


/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTester {

	Random rand = new Random();

	class myDataProvider<T,U> implements DataProvider<T,U>{
		private HashMap<Integer,Integer> provide = new HashMap<Integer,Integer>();
		private int numCalls = 0;

		public U get (T key){
			numCalls++;
			return (U)key; //provide.get(key);
		}

		public myDataProvider(){ }
		public int getNumCalls() {
			return numCalls;
		}
	}

	/**
	 * Test if cache.get performs in constant time
	 * relative to the size of the cache (capacity parameter)
	 */
	@Test
	public void constantTimeTest () {
			long[] times = new long[500];
			for (int f = 1; f < times.length; f++) {
				myDataProvider<Integer, Integer> provider = new myDataProvider<Integer, Integer>();
				Cache<Integer, Integer> cache = new LRUCache<Integer, Integer>(provider, 1000 * f);
				for (int i = 0; i < 1000 * f; i++) {
					cache.get(i);
				}

				final long start = System.currentTimeMillis();
				for (int i = 0; i < 100; i++) {
					cache.get(rand.nextInt((1000 * f)));
				}
				final long end = System.currentTimeMillis();
				times[f] = (end - start);
				assertEquals(provider.getNumCalls(),cache.getNumMisses());
			}

			double eqTotal = 0;
			double total = 0;
			double count = 0;
			for (int i = 1; i < times.length; i++) {
				for (int k = i + 1; k < times.length; k++) {
					if (times[k] > times[i])
						total++;
					if (times[k] == times[i])
						eqTotal++;
					count++;
				}
			}
			double frac = ((.5 * eqTotal) + total) / count;
			System.out.println(frac);
			assertTrue(frac < .6 & frac > .4);
	}


	/**
	 * Tests weather the cache prioritizes keys that are called more often
	 * I.E. implements an LRU cache correctly
	 */
	@Test
	public void leastRecentlyUsedIsCorrect () {
		myDataProvider<Integer, Integer> provider = new myDataProvider<Integer, Integer>();
		Cache<Integer, Integer> cache = new LRUCache<Integer, Integer>(provider, 3);

		assertEquals(cache.getNumMisses(),0);
		cache.get(3);
		cache.get(7);
		cache.get(8);
		//cache = 3,7,8
		assertEquals(cache.getNumMisses(),3);
		cache.get(3);
		//cache = 7, 8, 3
		assertEquals(cache.getNumMisses(),3);
		cache.get(777);
		//cache = 8, 3, 777
		// Test for evicts 7 not 3
		assertEquals(cache.getNumMisses(),4);
		cache.get(3);
		//cache = 8, 777, 3
		assertEquals(cache.getNumMisses(),4);
		cache.get(2);
		cache.get(4);
		//cache = 3,2,4
		assertEquals(cache.getNumMisses(),6);
		cache.get(3);
		// Tests 3 is still in cache (orders LRU correctly)
		assertEquals(cache.getNumMisses(),6);
		assertEquals(provider.getNumCalls(), cache.getNumMisses());
		// Only and Always calls provider when it misses


	}

	/**
	 * Tests whether the cache provides a correct response to a .get call with given provider
	 */
	@Test
	public void responseCorrect () {
		myDataProvider<Integer, Integer> provider = new myDataProvider<Integer, Integer>();
		Cache<Integer, Integer> cache = new LRUCache<Integer, Integer>(provider, 5);
		for(int i = 0; i < 1000; i++){
			assertEquals(cache.get(i),new Integer(i));
		}
	}

	/**
	 * Validates that calls to values in the cache do not call the provider multiple times
	 */
	@Test
	public void cacheCalled (){
		myDataProvider<Integer, Integer> provider = new myDataProvider<Integer, Integer>();
		Cache<Integer, Integer> cache = new LRUCache<Integer, Integer>(provider, 5);
		for(int i = 0; i < 10000; i++){
			cache.get(rand.nextInt(5));
		}
		assertEquals(cache.getNumMisses(),5);
		assertEquals(provider.getNumCalls(),5);
	}



}
