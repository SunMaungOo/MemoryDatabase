package com.developer.sunmaungoo.memorydb;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	private Utils() {
		
	}
	
	public static <T> List<T> toList(T[] arry){
		
		List<T> lst = new ArrayList<T>();
		
		for(T current : arry) {
			
			lst.add(current);
			
		}
		
		return lst;
	}
	
}
