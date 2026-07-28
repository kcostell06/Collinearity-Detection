package csu22011_a1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

//-------------------------------------------------------------------------
/**
 *  Test class for Collinear.java
 *
 *  @author  
 *  @version 03/10/22 22:33:19
 */
@RunWith(JUnit4.class)
public class CollinearTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new Collinear();
      
    }
    
    
    public static void main(String[] args)
	{
		//System.out.println("checkpoint 1");
		int[] testData = {1000,2000,4000,5000};
		for(int i=0;i<testData.length;i++)
		{
			//System.out.println("checkpoint 2");
			int n = testData[i];
			String data = "";
			if(n==1000)
				 data="r01000-";
			else if(n==2000)
				data="r02000-";
			else if(n==4000)
				data="r04000-";
			else 
				data="r05000-";
			
			int a1[] = (new In(data + "1.txt")).readAllInts();
			int a2[] = (new In(data + "2.txt")).readAllInts();
			int a3[] = (new In(data + "3.txt")).readAllInts();
			
			System.out.println("Array size: " + n);
			Stopwatch stopwatch = new Stopwatch();
			Collinear.countCollinear(a1, a2, a3);
			double time = stopwatch.elapsedTime();
			System.out.println("Brute force: " + time + " seconds");
			
			stopwatch = new Stopwatch();
			Collinear.countCollinear(a1, a2, a3);
			time = stopwatch.elapsedTime();
			System.out.println("Efficent: " + time + " seconds\n\n");
		}
		
	}

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check that the two methods work for empty arrays
     */

    @Test
    public void testEmpty()
    {
        int expectedResult = 0;

        assertEquals("countCollinear with 3 empty arrays should return zero",     expectedResult, Collinear.countCollinear(new int[0], new int[0], new int[0]));
        assertEquals("countCollinearFast with 3 empty arrays should return zero", expectedResult, Collinear.countCollinearFast(new int[0], new int[0], new int[0]));
    }
   
    @Test
    public void testBinarySearchNotFound()
    {
        int[] a = new int[] {10, 20, 30, 40};
        assertFalse("binarySearch should not find absent element between", Collinear.binarySearch(a, 25));
        assertFalse("binarySearch should not find element smaller than min", Collinear.binarySearch(a, 5));
        assertFalse("binarySearch should not find element greater than max", Collinear.binarySearch(a, 50));
    }

    @Test
    public void testBinarySearchMiddle()
    {
        int[] a = new int[] {1, 3, 5, 7, 9};
        assertTrue("binarySearch should find existing middle element", Collinear.binarySearch(a, 5));
    }

    @Test
    public void testBinarySearchEdges()
    {
        int[] a = new int[] {2, 4, 6, 8};
        assertTrue("binarySearch should find first element", Collinear.binarySearch(a, 2));
        assertTrue("binarySearch should find last element", Collinear.binarySearch(a, 8));
    }

    @Test
    public void testSortWithDuplicates()
    {
        int[] withDup = new int[] {3, 1, 2, 3, 2, 1};
        Collinear.sort(withDup);
        assertArrayEquals(new int[] {1,1,2,2,3,3}, withDup);
    }
   
    @Test
    public void testSortAlreadySortedAndReversed()
    {
        int[] sorted = new int[] {1, 2, 3, 4, 5};
        Collinear.sort(sorted);
        assertArrayEquals(new int[] {1,2,3,4,5}, sorted);

        int[] rev = new int[] {5,4,3,2,1};
        Collinear.sort(rev);
        assertArrayEquals(new int[] {1,2,3,4,5}, rev);
    }


    @Test
    public void testCountCollinearNoneAndSome()
    {
    int[] a1 = new int[] {0, 2};
    int[] a2 = new int[] {1, 3};
    int[] a3 = new int[] {10, 11};
    assertEquals(0, Collinear.countCollinear(a1, a2, a3));

        // make one collinear triple: choose a1=1, a2=3 => x3 = 2*3 - 1 = 5
        int[] b1 = new int[] {1};
        int[] b2 = new int[] {3};
        int[] b3 = new int[] {5};
        assertEquals(1, Collinear.countCollinear(b1, b2, b3));
    }

    @Test
    public void testCountCollinear()
    {
        int[] a1 = new int[] {0, 1, 2};
        int[] a2 = new int[] {1, 2, 3};
        int[] a3 = new int[] {2, 3, 4};
        // Valid triples: (0,1,2) via x3=2*1-0=2; (1,2,3); (2,3,4)
        assertEquals(5, Collinear.countCollinear(a1, a2, a3));
    }

    @Test
    public void testCountCollinearFast()
    {
        int[] a1 = new int[] { -2, 0, 1, 4 };
        int[] a2 = new int[] { -1, 2, 3 };
        int[] a3 = new int[] { -4, -2, 1, 5, 6 };
        assertEquals(Collinear.countCollinear(a1, a2, a3), Collinear.countCollinearFast(a1, a2, a3));
    }
   
    
    private static long runCountCollinearTrial(int n, int bound, long seed)
    {
        int[] a1 = generateUniqueArray(n, bound, seed ^ 0x9E3779B97F4A7C15L);
        int[] a2 = generateUniqueArray(n, bound, seed ^ 0xC2B2AE3D27D4EB4FL);
        int[] a3 = generateUniqueArray(n, bound, seed ^ 0x165667B19E3779F9L);
        long t0 = System.nanoTime();
        Collinear.countCollinear(a1, a2, a3);
        long t1 = System.nanoTime();
        return (t1 - t0) / 1_000_000; // ms
    }


    private static int[] generateUniqueArray(int n, int bound, long seed)
    {
        java.util.Random rnd = new java.util.Random(seed);
        java.util.HashSet<Integer> seen = new java.util.HashSet<>(n * 2);
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
        {
            int x;
            do { x = rnd.nextInt(bound); } while (!seen.add(x));
            a[i] = x;
        }
        return a;
    }

}