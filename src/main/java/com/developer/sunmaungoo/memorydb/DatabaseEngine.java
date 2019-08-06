package com.developer.sunmaungoo.memorydb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseEngine {
	
	private final String WILD_CARD="*";
	
	private List<Table> tables;
	
	public DatabaseEngine() {
		
		this.tables = new ArrayList<Table>();
		
	}
	
	public boolean register(Table table) {
		
		if(table==null) {
			return false;
		}
		
		return tables.add(table);
	}
	
	public List<List<String>> query(String expression){
		
		Interpreter interpreter = new Interpreter(expression);
		
		if(!interpreter.interpret()) {
			return new ArrayList<List<String>>();
		}
		
		String tableName = interpreter.getTableName();
		
		Optional<Table> foundTable = getTableName(tableName);
		
		//table name not found
		
		if(!foundTable.isPresent()) {
			return new ArrayList<List<String>>();
		}
		
		List<FilterPredicate> predicates = interpreter.getFilters();
		
		List<String> columnNames = interpreter.getSelectionColumns();

		if(columnNames.size()==1 && 
				columnNames.get(0).contentEquals(WILD_CARD)) {
			
			return foundTable.get().getRows(predicates);
			
		}
		
		return foundTable.get().getRows(columnNames,predicates);
	}
	
	private Optional<Table> getTableName(String tableName) {
		
		if(tableName==null) {
			return Optional.empty();
		}
		
		for(Table currentTable : tables) {
			
			if(currentTable.getTableName().contentEquals(tableName)) {
				return Optional.of(currentTable);
			}
		}
		
		return Optional.empty();
	}
	
	
}
