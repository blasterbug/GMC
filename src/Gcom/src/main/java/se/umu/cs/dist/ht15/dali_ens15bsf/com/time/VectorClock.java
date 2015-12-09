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

	public int compare(VectorClock c2) {
		boolean isLessLeft = true;
		boolean isGreaterLeft = true;
		boolean isEqual = true;

		for (String key : this.keySet()) {
			if (c2.containsKey(key)) {
				if(this.get(key) < c2.get(key) ) {
					isGreaterLeft = false;
					isEqual = false;
				} else if (this.get(key) > c2.get(key)){
					isLessLeft = false;
					isEqual = false;
				}
			} else if (this.get(key) != 0) {
				isEqual = false;
				isLessLeft = false;
			}
		}

		for ( String key : c2.keySet()) {
			if (!this.containsKey(key) && (c2.get(key) != 0)) {
				isEqual = false;
				isGreaterLeft = false;
			}
		}


		if (isEqual)
			return 0;

		if (isLessLeft && !isGreaterLeft)
			return -1;
		if(!isLessLeft && isGreaterLeft)
			return 1;
		
		return 2;
	}


	public void updateClock(VectorClock clock) {
		for (String key : clock.keySet() ) 
			if ( !super.containsKey(key) || super.get(key) < clock.get(key))
				this.updateTime(key, clock.get(key));
	}


	@Override
	public String toString() {
		String res ="{";
		for (String key : super.keySet() )
			res += key +":"+super.get(key)+",";
		res += "}";
		return res;
	}

}
