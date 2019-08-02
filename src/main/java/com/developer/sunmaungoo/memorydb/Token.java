package com.developer.sunmaungoo.memorydb;

public class Token {

	private TokenType tokenType;
	
	private Object value;
	
	public Token(TokenType tokenType,Object value) {
		
		this.tokenType = tokenType;
		
		this.value = value;
	}
	
	public TokenType getType() {
		
		return tokenType;
	}
	
	public Object getValue() {
		
		return value;
	}
}
