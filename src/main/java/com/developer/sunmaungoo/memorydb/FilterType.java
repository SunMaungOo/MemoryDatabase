package com.developer.sunmaungoo.memorydb;

public enum FilterType {
	START,
	EOF,
	/**
	 * Example :
	 * Column name or predicate string value
	 */
	VALUE,
	/**
	 * ==
	 * !=
	 */
	OPERATION,
	AND,
	OR,
}
