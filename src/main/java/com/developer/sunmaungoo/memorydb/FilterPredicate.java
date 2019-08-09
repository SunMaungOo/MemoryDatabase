package com.developer.sunmaungoo.memorydb;

import java.util.function.BiPredicate;

public class FilterPredicate {

	/**
	 * What is the modifer for next predicate
	 */
	private Modifer modifer;
	
	private BiPredicate<String, String> predicate;
	
	private String columnName;
	
	public FilterPredicate(Modifer modifer,
			BiPredicate<String, String> predicate,
			String columnName) {
		
		this.modifer = modifer;
		
		this.predicate = predicate;
		
		this.columnName = columnName;
	}
	public Modifer getModifer() {
		
		return modifer;
		
	}
	
	public BiPredicate<String, String> getPredicate(){
		
		return predicate;
	}
	
	/**
	 * 
	 * @return column name the predicate test upon
	 */
	public String getColumnName() {
		return columnName;
	}
}
