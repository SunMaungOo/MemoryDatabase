package com.developer.sunmaungoo.memorydb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Interpreter {

	private final String WHITE_SPACE = "\u0020";
	
	private final String COMMA = "\u002c";
	
	private final String SELECTION_KEYWORD = "SELECT";
	  
	private final String FROM_KEYWORD = "FROM";
	
	private final String FILTER_KEYWORD = "WHERE";
	
	private Token currentToken;
	
	private String[] tokenBlock;
	
	private int tokenPosition;
	
	private List<String> selectionColumns;
	
	private List<FilterPredicate> filterPredicates;
		
	private String tableName;
	
	/**
	 * 
	 * @param expression non-null expression
	 */
	public Interpreter(String expression) {
		
		this.tokenBlock = expression.trim().split(WHITE_SPACE);
				
		this.currentToken = new Token(TokenType.START, null);
		
		this.tokenPosition = -1;
	
		this.selectionColumns = new ArrayList<String>();
		
		this.filterPredicates = new ArrayList<FilterPredicate>();
	}
	
	/**
	 * Interpret the String
	 * @return
	 */
	public boolean interpret() {
	
		currentToken = getNextToken();
		
		// expression is empty
		
		if(currentToken.getType()==TokenType.EOF) {
			return false;
		}
				
		if(!eat(TokenType.SELECTION)) {
			return false;
		}
				
		//selection column

		Optional<List<String>> output = createListValue();
		
		if(!output.isPresent()) {
			return false;
		}
		
		selectionColumns = output.get();
		
		if(!eat(TokenType.FROM)) {
			return false;
		}
		
		tableName = getTableName(currentToken.getValue());
		
		if(tableName==null) {
			return false;
		}
		
		//table name
		
		if(!eat(TokenType.LIST_VALUE)) {
			return false;
		}
		
		
		//if it is a WHERE statement
		
		if(eat(TokenType.FILTER)) {
			
			//get all the filters we have to perform
			
			output = createListValue();
			
			if(!output.isPresent()) {				
				return false;
			}
						
			FilterInterpreter filterInterprerter = new FilterInterpreter(output.get());
			
			if(!filterInterprerter.interpret()) {
				return false;
			}
			
			filterPredicates = filterInterprerter.getPredicate();
		}
		
		
		if(currentToken.getType()!=TokenType.EOF) {
			return false;
		}
				
		return true;
	}
	
	/**
	 * 
	 * @return nullable table name
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 
	 * @return non-null selection columns
	 */
	public List<String> getSelectionColumns(){
		return selectionColumns;
	}
	
	public List<FilterPredicate> getFilters(){
		return filterPredicates;
	}
	
	public boolean hasFilter() {
		return filterPredicates.size()>0;
	}
	
	public Token getNextToken() {
		
		//move to next position
		
		++tokenPosition;
		
		if(tokenPosition>=tokenBlock.length) {
			return new Token(TokenType.EOF,null);
		}
		
		String currentExpression = tokenBlock[tokenPosition];
		
		if(currentExpression.contentEquals(SELECTION_KEYWORD)) {
			
			return new Token(TokenType.SELECTION, null);
			
		}else if(currentExpression.contentEquals(FROM_KEYWORD)) {
			
			return new Token(TokenType.FROM, null);

		}else if(currentExpression.contentEquals(FILTER_KEYWORD)) {
			
			return new Token(TokenType.FILTER, null);
			
		}
		
		return new Token(TokenType.LIST_VALUE, currentExpression);
	}
	
	private List<String> createList(Object value) {
		
		if(value==null) {
			return new ArrayList<String>();
		}
		
		String tmp = (String)value;
				
		return Utils.toList(tmp.split(COMMA));
	}
	
	/**
	 * 
	 * @param value
	 * @return table name or null on error
	 */
	private String getTableName(Object value) {
		
		List<String> tmp = createList(value);
		
		if(tmp.size()==1) {
			return tmp.get(0);
		}
		
		return null;
	}
	
	private Optional<List<String>> createListValue(){
		
		List<String> output = createList(currentToken.getValue());
		
		//selection column
		
		//checking if there is a minimum of 1 selection column
		
		if(!eat(TokenType.LIST_VALUE)) {
			return Optional.empty();
		}
		
		//to handle the case where there is comma
		//after space in selection column

		List<String> tmp = createList(currentToken.getValue());
		
		while(eat(TokenType.LIST_VALUE)) {
			
			output.addAll(tmp);
			
			tmp = createList(currentToken.getValue());
		}
		
		return Optional.of(output);
	}
		
	/**
	 * Get next token if the current token type are same
	 * @param tokenType
	 * @return
	 */
	public boolean eat(TokenType tokenType) {
		
		if(currentToken.getType()==tokenType) {
			
			currentToken = getNextToken();
			
			return true;
		}
		
		return false;
	}
}
