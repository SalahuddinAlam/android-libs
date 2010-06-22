package net.kazed.android.collection;

import java.io.Serializable;


public class Averager implements Serializable {

	private static final long serialVersionUID = 1L;
	
   private float[] speeds;
	private int index = 0;
	private int size = 0;
	private float total = 0.0F;
	private float average = 0.0F;
	private int maximumIndex = -1;
	
	public Averager() {
		this(10);
	}
	
	public Averager(int size) {
		speeds = new float[size];
	}
	
	public void clear() {
		index = 0;
		size = 0;
		total = 0.0F;
		average = 0.0F;
		maximumIndex = -1;
	}
	
	public void add(float speed) {
		if (size == speeds.length) {
			total -= speeds[index];
		}
		speeds[index] = speed;
		if (size < speeds.length) {
			size++;
		}
		if (maximumIndex == -1) {
			maximumIndex = index;
		} else if (index == maximumIndex) {
			float maximum = speeds[0];
			for (int i=1; i<size; i++) {
				if (maximum < speeds[i]) {
					maximum = speeds[i];
					maximumIndex = i;
				}
			}
		} else {
			if (speeds[maximumIndex] < speed) {
				maximumIndex = index;
			}
		}
		index++;
		if (index >= speeds.length) {
			index = 0;
		}
		total += speed;
		average = total / size;
	}
	
	public float getAverage() {
		return average;
	}
	
	public float getMaximum() {
		return speeds[maximumIndex];
		
//		float maximum = 0.0F;
//		for (float speed : speeds) {
//			if (speed > maximum) {
//				maximum = speed;
//			}
//		}
//		return maximum;
	}
	
}
