package com.developer.sunmaungoo.memorydb;

import java.util.ArrayList;
import java.util.List;

/**
 * Give predicate based on filter statement
 * @author SunMaungOo
 *
 */
public class FilterInterpreter {

	private final String AND_COMMAND="AND";
	
	private final String OR_COMMAND="OR";
	
	private final String EQUAL = "==";
	
	private final String NOT_EQUAL = "!=";
	
	private FilterToken currentToken;
	
	private int tokenPosition;
	
	private List<String> filters;
	
	private List<FilterPredicate> filterPredicate;
		
	public FilterInterpreter(List<String> filters) {
		
		this.filters = filters;

		this.currentToken = new FilterToken(FilterType.START, null);
		
		this.tokenPosition = -1;
		
		this.filterPredicate = new ArrayList<FilterPredicate>();
		
	}
	
	public boolean interpret() {

		currentToken = getNextToken();
		
		while(currentToken.getType()!=FilterType.EOF) {
		
			String columnName = getValue();
			
			if(!eat(FilterType.VALUE)) {
				return false;
			}
			
			String operation = getValue();
			
			if(!eat(FilterType.OPERATION)) {
				return false;
			}
			
			String predicateString = getValue();
			
			if(!eat(FilterType.VALUE)) {
				return false;
			}
			
			BiPredicate<String, String> predicate = null;
			
			if(operation.contentEquals(EQUAL)) {
				predicate = createPredicate(true, columnName, predicateString);
			}else {
				predicate = createPredicate(false, columnName, predicateString);
			}
			
			if(eat(FilterType.EOF)) {
								
				filterPredicate.add(new FilterPredicate(Modifer.NONE,
						predicate));
				
				return true;
			}
			
			if(currentToken.getType()==FilterType.AND) {
								
				filterPredicate.add(new FilterPredicate(Modifer.AND,
						predicate));

			}else if(currentToken.getType()==FilterType.OR) {
								
				filterPredicate.add(new FilterPredicate(Modifer.OR,
						predicate));

			}
			
			//if the filter does not end it must be combine predicate statement
			
			if(!(eat(FilterType.OR) || eat(FilterType.AND))) {
				
				return false;
			}
			
		}
		
		return true;
		
	}
	
	public List<FilterPredicate> getPredicate(){
		return filterPredicate;
	}
	
	private BiPredicate<String, String> createPredicate(final boolean isEquals,
			final String columnName,final String columnValue){
		
		
		return new BiPredicate<String, String>() {
			
			@Override
			public boolean test(String value1, String value2) {
				
				if(isEquals) {
					
					return value1.contentEquals(columnName) &&
							value2.contentEquals(columnValue);
					
				}else {
					return value1.contentEquals(columnName) &&
							!value2.contentEquals(columnValue);
				}
				
			}
		};
	}
	
	private FilterToken getNextToken() {
		
		++tokenPosition;
		
		if(tokenPosition>=filters.size()) {		
			return new FilterToken(FilterType.EOF,null);
		}
		
		String currentExpression = filters.get(tokenPosition);

		if(currentExpression.contentEquals(AND_COMMAND)) {
			
			return new FilterToken(FilterType.AND, null);
			
		}else if(currentExpression.contentEquals(OR_COMMAND)) {
			
			return new FilterToken(FilterType.OR, null);
			
		}else if(currentExpression.contentEquals(EQUAL)) {
			
			return new FilterToken(FilterType.OPERATION, EQUAL);
			
		}else if(currentExpression.contentEquals(NOT_EQUAL)) {
			
			return new FilterToken(FilterType.OPERATION,NOT_EQUAL);
			
		}
		
		return new FilterToken(FilterType.VALUE, currentExpression);

	}
	
	private boolean eat(FilterType filterType) {
		
		if(currentToken.getType()==filterType) {
			
			currentToken = getNextToken();

			return true;
		}
		
		return false;
	}
	
	private String getValue() {
		
		return (String)currentToken.getValue();
	}
}
