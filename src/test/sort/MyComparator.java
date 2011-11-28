package test.sort;

import java.util.Comparator;
import java.util.Map;

public class MyComparator implements Comparator<Map<String, Integer>> {

	@Override
	public int compare(Map<String, Integer> o1, Map<String, Integer> o2) {
		// TODO Auto-generated method stub
		Integer v1 = o1.get("Value");
		Integer v2 = o2.get("Value");
		return v1.compareTo(v2);	
	}
}
