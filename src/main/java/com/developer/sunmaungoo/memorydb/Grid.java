package com.developer.sunmaungoo.memorydb;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Grid {

	private List<String> columns;
	
	private List<List<String>> rows;
	
	public Grid() {
		
		columns = new ArrayList<String>();
		
		rows = new ArrayList<List<String>>();
		
	}
	
	/**
	 * 
	 * @param columnName non-duplicate , non-null
	 * @return
	 */
	public boolean addColumn(String columnName) {
		
		if(columnName==null) {
			return false;
		}
		
		columnName = columnName.trim();
		
		if(columns.contains(columnName)) {
			return false;
		}
		
		return columns.add(columnName);
	}
	
	/**
	 * 
	 * @param row non-null, same size as columns
	 * @return
	 */
	public boolean addRow(List<String> row) {
		
		if(row==null) {
			return false;
		}
		
		if(columns.size()!=row.size()) {
			return false;
		}
		
		return rows.add(row);

	}
	
	public List<List<String>> getRows(List<String> columnNames,
			List<FilterPredicate> predicates){
		
		List<Integer> indexes = getIndexes(columnNames);
		
		if(indexes.size()==0) {
			throw new IllegalArgumentException("Please provide valid column name");
		}
		
		if(indexes.size()!=columnNames.size()) {
			throw new IllegalArgumentException("Not all column name provided are found");
		}
		
		List<List<String>> displayRow = new ArrayList<List<String>>();
		
		for(int rowIndex=0;rowIndex<rows.size();rowIndex++) {
			
			List<String> row = new ArrayList<String>();

			List<String> currentRow = rows.get(rowIndex);

			if(!isAddRow(currentRow, predicates)) {
				continue;
			}
			
			for(int columnIndex=0;columnIndex<currentRow.size();columnIndex++) { 		
				
				if(isSelectRow(columnIndex,indexes)) {
					
					row.add(currentRow.get(columnIndex));
					
				}
			}
			
			displayRow.add(row);
		}
		
		return displayRow;
	}
	
	/**
	 * Get all rows which satisfy the predicate
	 * @return
	 */
	public List<List<String>> getRows(List<FilterPredicate> predicates){
		
		if(!isValidFilter(predicates)) {
			return rows;
		}
		
		List<List<String>> filterRows = new ArrayList<List<String>>();
		
		for(List<String> currentRow : rows) {
		
			if(isAddRow(currentRow, predicates)) {
				
				filterRows.add(currentRow);
				
			}
		}
		
		
		return filterRows;
	}
	
	private boolean isValidFilter(List<FilterPredicate> predicates) {
		return predicates!=null && predicates.size()>0;
	}
	
	/**
	 * 
	 * @param row
	 * @param predicates
	 * @return whether to add the row based on predicate
	 */
	private boolean isAddRow(List<String> row,
			List<FilterPredicate> predicates) {
		
		if(!isValidFilter(predicates)) {
			return true;
		}
		
		boolean result = true;
		
		for(int i=0;i<predicates.size();i++) {
			
			FilterPredicate currentPredicate = predicates.get(i);
				
			result = test(row, currentPredicate);
			
			Modifer currentModifer = currentPredicate.getModifer();
					
			if(currentModifer==Modifer.NONE) {
				continue;
			}
	
			//if the modifier is not none,there is always next predicate

			FilterPredicate nextPredicate = predicates.get(i+1);

			boolean nextResult = test(row,nextPredicate);
			
			if(currentModifer==Modifer.OR) {
				
				result = result || nextResult;

			}else if(currentModifer==Modifer.AND) {

				result = result && nextResult;

			}

		}
				
		return result;
	}
	
	/**
	 * Test whether for the current row
	 * @param row
	 * @param predicate
	 * @return
	 */
	private boolean test(List<String> row,FilterPredicate predicate) {
		
		int columnIndex = getColumnIndex(predicate.getColumnName());

		if(columnIndex==-1) {
			throw new IllegalArgumentException("Invalid column name found in predicate");
		}
		
		return predicate.getPredicate().test(columns.get(columnIndex),
				row.get(columnIndex));
	}
	
	/**
	 * 
	 * @param columnName
	 * @return column index or -1 if the column name is not found
	 */
	private int getColumnIndex(String columnName) {
		
		for(int i=0;i<columns.size();i++) {
			
			if(columns.get(i).contentEquals(columnName)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private boolean isSelectRow(int columnIndex,
			List<Integer> selectIndexes) {	
		
		return selectIndexes.contains(columnIndex);
	}
	
	/**
	 * 
	 * @param columnNames
	 * @return non-null indexes of column name
	 */
	private List<Integer> getIndexes(List<String> columnNames){
		
		final List<Integer> indexes = new ArrayList<Integer>();
		
		if(columnNames==null) {
			return indexes;
		}
		
		columnNames.forEach(new Consumer<String>() {

			public void accept(String columnName) {
				
				for(int i=0;i<columns.size();i++) {
					
					String currentColumn = columns.get(i);
					
					if(currentColumn.equals(columnName)) {
						
						indexes.add(i);
						
					}
				}
			}
		});
		
		return indexes;
	}
}
