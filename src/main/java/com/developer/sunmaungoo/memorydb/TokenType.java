package com.developer.sunmaungoo.memorydb;

public enum TokenType {
	START,
	SELECTION,
	FROM,
	EOF,
	/**
	 * Example
	 * C1
	 * C1,C2
	 */
	LIST_VALUE,
	FILTER,
}
