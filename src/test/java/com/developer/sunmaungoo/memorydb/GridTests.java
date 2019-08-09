package com.developer.sunmaungoo.memorydb;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import org.junit.jupiter.api.Test;

public class GridTests {

	@Test
	void addColumnTest1() {
		
		Grid grid = new Grid();
		
		assertTrue(grid.addColumn("C1"));
	}
	
	@Test
	void addColumnTest2() {
		
		Grid grid = new Grid();
		
		assertTrue(grid.addColumn("C1"));		
		assertFalse(grid.addColumn("C1"));
	}

	@Test
	void addColumnTest3() {
	
		Grid grid = new Grid();

		assertFalse(grid.addColumn(null));
	}
	
	@Test
	void addRowTest1() {
		
		Grid grid = new Grid();
		
		grid.addColumn("C1");
		grid.addColumn("C2");
		
		List<String> row1=new ArrayList<String>();
		row1.add("R1");
		row1.add("R2");

		assertTrue(grid.addRow(row1));
	}
	
	@Test
	void addRowTest2() {
		
		Grid grid = new Grid();
		grid.addColumn("C1");
		grid.addColumn("C2");
		
		List<String> row1=new ArrayList<String>();
		row1.add("R1");

		assertFalse(grid.addRow(row1));
	}
	
	@Test
	void addRowTest3() {
		
		Grid grid = new Grid();
		grid.addColumn("C1");
		grid.addColumn("C2");

		assertFalse(grid.addRow(null));
	}
	
	@Test
	void getRowsTest1() {
		
		Grid grid = new Grid();
		grid.addColumn("C1");
		grid.addColumn("C2");
		
		List<String> row1 =new ArrayList<String>();
		row1.add("R1C1");
		row1.add("R1C2");

		List<String> row2 =new ArrayList<String>();
		row2.add("R2C1");
		row2.add("R2C2");
		
		grid.addRow(row1);
		grid.addRow(row2);
		
		List<String> columns = new ArrayList<String>();
		columns.add("C1");
		columns.add("C2");

		List<List<String>> rows = grid.getRows(columns,null);

		assertTrue(rows.size()==2);
		assertTrue(rows.get(0).get(0).contentEquals("R1C1"));
		assertTrue(rows.get(0).get(1).contentEquals("R1C2"));
		assertTrue(rows.get(1).get(0).contentEquals("R2C1"));
		assertTrue(rows.get(1).get(1).contentEquals("R2C2"));

	}
	
	@Test
	void getRowsTest2() {
		
		Grid grid = new Grid();
		grid.addColumn("C1");
		grid.addColumn("C2");
		
		List<String> row1 =new ArrayList<String>();
		row1.add("R1C1");
		row1.add("R1C2");

		List<String> row2 =new ArrayList<String>();
		row2.add("R2C1");
		row2.add("R2C2");
		
		grid.addRow(row1);
		grid.addRow(row2);
		
		List<String> columns = new ArrayList<String>();
		columns.add("C2");

		List<List<String>> rows = grid.getRows(columns,null);

		assertTrue(rows.size()==2);
		assertTrue(rows.get(0).get(0).contentEquals("R1C2"));
		assertTrue(rows.get(1).get(0).contentEquals("R2C2"));

	}
	
	@Test
	void getRowsTest3() {
		
		Grid grid = new Grid();
		grid.addColumn("C1");
		grid.addColumn("C2");
		
		List<String> row1 =new ArrayList<String>();
		row1.add("R1C1");
		row1.add("R1C2");

		List<String> row2 =new ArrayList<String>();
		row2.add("R2C1");
		row2.add("R2C2");
		
		grid.addRow(row1);
		grid.addRow(row2);
		
		List<String> columns = new ArrayList<String>();
		columns.add("C2");

		List<List<String>> rows = grid.getRows(null);

		assertTrue(rows.size()==2);
		assertTrue(rows.get(0).get(0).contentEquals("R1C1"));
		assertTrue(rows.get(0).get(1).contentEquals("R1C2"));
		assertTrue(rows.get(1).get(0).contentEquals("R2C1"));
		assertTrue(rows.get(1).get(1).contentEquals("R2C2"));

	}
	
	@Test
	void getRowsTest4() {
		
		//testing filter and select column
		
		Grid grid = new Grid();
		grid.addColumn("C1");
		grid.addColumn("C2");
		
		List<String> row1 =new ArrayList<String>();
		row1.add("R1C1");
		row1.add("R1C2");

		List<String> row2 =new ArrayList<String>();
		row2.add("R2C1");
		row2.add("R2C2");
		
		grid.addRow(row1);
		grid.addRow(row2);
		
		List<String> columns = new ArrayList<String>();
		columns.add("C2");

		List<FilterPredicate> predicates = new ArrayList<FilterPredicate>();
		predicates.add(new FilterPredicate(Modifer.NONE, new BiPredicate<String, String>() {
			
			@Override
			public boolean test(String columnName, String columnValue) {
				return columnName.contentEquals("C1") &&
						columnValue.contentEquals("R2C1");
			}
		}, "C1"));
		
		List<List<String>> rows = grid.getRows(columns,predicates);

		assertTrue(rows.size()==1);
		assertTrue(rows.get(0).get(0).contentEquals("R2C2"));

	}
	
	
	@Test
	void getRowsTest5() {
		
		Grid grid = new Grid();
		grid.addColumn("C1");
		grid.addColumn("C2");
		
		List<String> row1 =new ArrayList<String>();
		row1.add("R1C1");
		row1.add("R1C2");

		List<String> row2 =new ArrayList<String>();
		row2.add("R2C1");
		row2.add("R2C2");
		
		grid.addRow(row1);
		grid.addRow(row2);
		

		List<FilterPredicate> predicates = new ArrayList<FilterPredicate>();
		predicates.add(new FilterPredicate(Modifer.NONE, new BiPredicate<String, String>() {
			
			@Override
			public boolean test(String columnName, String columnValue) {
				return columnName.contentEquals("C1") &&
						columnValue.contentEquals("R2C1");
			}
		}, "C1"));
		
		List<List<String>> rows = grid.getRows(predicates);

		
		assertTrue(rows.size()==1);
		assertTrue(rows.get(0).get(0).contentEquals("R2C1"));
		assertTrue(rows.get(0).get(1).contentEquals("R2C2"));

	}
}
