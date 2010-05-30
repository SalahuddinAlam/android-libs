package net.kazed.android.collection;

import org.junit.Assert;
import org.junit.Test;


public class AveragerTest {

	@Test
	public void testAverageMaximum() {
		Averager averager = new Averager(4);
		averager.add(1.0F);
		Assert.assertEquals(1.0F, averager.getAverage(), 0.00001);
		Assert.assertEquals(1.0F, averager.getMaximum(), 0.00001);
		
		averager.add(0.0F);
		averager.add(2.0F);
		Assert.assertEquals(1.0F, averager.getAverage(), 0.00001);
		Assert.assertEquals(2.0F, averager.getMaximum(), 0.00001);
		
		averager.add(-2.0F);
		averager.add(4.0F);
		Assert.assertEquals(1.0F, averager.getAverage(), 0.00001);
		Assert.assertEquals(4.0F, averager.getMaximum(), 0.00001);

		averager.add(0.0F);
		averager.add(0.0F);
		averager.add(0.0F);
		averager.add(0.0F);
		Assert.assertEquals(0.0F, averager.getAverage(), 0.00001);
		Assert.assertEquals(0.0F, averager.getMaximum(), 0.00001);
	}
}
