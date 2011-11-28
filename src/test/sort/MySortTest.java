package test.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MySortTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, Integer>> aList = new ArrayList<Map<String, Integer>>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Integer> map1 = new HashMap<String, Integer>();
			map1.put("Value", new Integer(random.nextInt(100)));
			aList.add(map1);
			
		}
		
//		HashMap<String, String> map2 = new HashMap<String, String>();
//		map2.put("Value", "40");
//		aList.add(map2);
//		
//		HashMap<String, String> map3 = new HashMap<String, String>();
//		map3.put("Value", "30");
//		aList.add(map3);
		

		Collections.sort((List)aList,new MyComparator());
		
		for (int i = 0; i < aList.size(); i++) {
			System.out.println(aList.get(i));	
		}
	}

}
