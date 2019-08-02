package com.developer.sunmaungoo.memorydb;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {

	private final String WHITE_SPACE = "\u0020";
	
	private final String COMMA = "\u002c";
	
	private final String SELECTION_KEYWORD = "SELECT";
	  
	private final String FROM_KEYWORD = "FROM";
	
	private Token currentToken;
	
	private String[] tokenBlock;
	
	private int tokenPosition;
	
	private List<String> selectionColumns;
	
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
				
		selectionColumns = createList(currentToken.getValue());
				
		//selection column
		
		//checking if there is a minimum of 1 selection column
		
		if(!eat(TokenType.LIST_VALUE)) {
			return false;
		}
		
		//to handle the case where there is comma
		//after space in selection column

		List<String> tmp = createList(currentToken.getValue());
		
		while(eat(TokenType.LIST_VALUE)) {
			
			selectionColumns.addAll(tmp);
			
		}
		
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
