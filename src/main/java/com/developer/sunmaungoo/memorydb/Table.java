package com.developer.sunmaungoo.memorydb;

import java.util.List;

public class Table {

	private String tableName;
	
	private Grid tableInfo;
	
	public Table(String tableName) {
	
		this.tableName = tableName;
		
		this.tableInfo = new Grid();
		
	}
	
	public String getTableName() {
		
		return tableName;
	}
	
	public boolean addColumn(String columnName) {
		
		return tableInfo.addColumn(columnName);		
	}
	
	public boolean addRow(List<String> row) {
		
		return tableInfo.addRow(row);	
	}
	
	public List<List<String>> getRows(List<String> columnNames,
			List<FilterPredicate> predicates){
		
		return tableInfo.getRows(columnNames,predicates);	
	}
	
	public List<List<String>> getRows(List<FilterPredicate> predicates){
		
		return tableInfo.getRows(predicates);
		
	}


	@Override
	public int hashCode() {
		
		return tableName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		if(this==obj) {
			return true;
		}
			
		if(!(obj instanceof Table)) {
			return false;
		}
		
		Table tmp = (Table)obj;
		
		return tableName.equals(tmp.getTableName());
	}
	
	
}
