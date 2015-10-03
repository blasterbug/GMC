package se.umu.cs.dist.ht15.dali_ens15bsf.time;

import java.util.HashMap;

public class VectorClock extends HashMap<String, Integer>  {

	public VectorClock() {
		super();
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

	/**
		* @return 0 == concurrent
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

}
