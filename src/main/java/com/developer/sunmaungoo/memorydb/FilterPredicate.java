package com.developer.sunmaungoo.memorydb;

public class FilterPredicate {

	/**
	 * What is the modifer for next predicate
	 */
	private Modifer modifer;
	
	private BiPredicate<String, String> predicate;
	
	public FilterPredicate(Modifer modifer,
			BiPredicate<String, String> predicate) {
		
		this.modifer = modifer;
		
		this.predicate = predicate;
	}
	public Modifer getModifer() {
		
		return modifer;
		
	}
	
	public BiPredicate<String, String> getPredicate(){
		
		return predicate;
	}
}
