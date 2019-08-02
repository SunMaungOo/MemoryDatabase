package com.developer.sunmaungoo.memorydb;

public class FilterToken {

	private FilterType filterType;
	
	private Object value;
	
	public FilterToken(FilterType filterType,
			Object value) {
	
		this.filterType = filterType;
		
		this.value = value;
		
	}
	
	public FilterType getType() {
		return filterType;
	}
	
	public Object getValue() {
		return value;
	}
}
