package se.umu.cs.dist.ht15.dali_ens15bsf.com.time;

import java.io.Serializable;
import java.util.HashMap;

public class VectorClock extends HashMap<String, Integer> implements Serializable
{

	private static final long serialVersionUID = -1976421449766444371L;

	public VectorClock() {
		super();
	}

	public VectorClock(VectorClock c) {
		for (String key : c.keySet() ) {
			super.put(key, c.get(key));
		}
	}

	public void updateTime(String id, Integer time) {
		super.put(id, time);
	}

	public void increment(String id) {
		Integer time = super.get(id);
		if(time == null) {
			super.put(id,1);
		} else {
			super.put(id, time.intValue()+1);
		}

	}

	@Override
	public Integer get(Object key) {
		Integer i = super.get(key);
		if( i == null) {
			return 0;
		}

		return i;
	}

	public static int compareId(VectorClock c1, VectorClock c2, String id) {
		int v1 = c1.get(id).intValue();
		int v2 = c2.get(id).intValue();
		if (v1 == v2)
			return 0;
		if (v1 < v2)
			return -1;
		return 1;
	}

	/**
		* @return 0 -> c1 == c2, -1 -> c1 < c2, 1-> c2 < c1 , 2 -> concurrent
	  */

	public static int compare(VectorClock c1, VectorClock c2) {
		boolean isLargerRight = true;
		boolean isLargerLeft = true;
		boolean isEqual = true;

		for (String key : c1.keySet()) {
			if (c2.containsKey(key)) {
				if(c1.get(key) < c2.get(key) ) {
					isLargerLeft = false;
					isEqual = false;
				} else if (c1.get(key) > c2.get(key)){
					isLargerRight = false;
					isEqual = false;
				}
			} else if (c1.get(key) > 0) {
				isLargerRight = false;
				isEqual = false;
			}
		}
		for (String key2 : c2.keySet()) {
			if (!c1.containsKey(key2)) {
				if (c2.get(key2) > 0) {
					isLargerLeft = false;
					isEqual = false;
				}
			}
		}
		if (isEqual)
			return 0;
		if (isLargerRight && !isLargerLeft)
			return -1;
		if(!isLargerRight && isLargerLeft)
			return 1;
		
		return 2;
	}


	public void updateClock(VectorClock clock) {
		for (String key : clock.keySet() ) 
			if ( !super.containsKey(key) || super.get(key) < clock.get(key))
				this.updateTime(key, clock.get(key));
	}



}
