package com.developer.sunmaungoo.memorydb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DatabaseEngineTests {

	@Test
	void queryTest1() {
		
		DatabaseEngine engine = new DatabaseEngine();
    	
    	Table table1 = new Table("T1");
    	table1.addColumn("C1");
    	table1.addColumn("C2");
    	
    	List<String> row1 = new ArrayList<String>();
       	row1.add("T1R1C1");
    	row1.add("T1R1C2");
    	
    	List<String> row2 = new ArrayList<String>();
    	row2.add("T1R2C1");
    	row2.add("T1R2C2");

    	table1.addRow(row1);
    	table1.addRow(row2);

      	Table table2 = new Table("T2");
    	table2.addColumn("C1");
    	table2.addColumn("C2");

    	List<String> row3 = new ArrayList<String>();
    	row3.add("T2R1C1");
    	row3.add("T2R1C2");
    	
    	List<String> row4 = new ArrayList<String>();
    	row4.add("T2R2C1");
    	row4.add("T2R2C2");
    	
    	table2.addRow(row3);
    	table2.addRow(row4);

    	engine.register(table1);
    	engine.register(table2);
    	
    	List<List<String>> rows = engine.query("SELECT C1,C2 FROM T1");
    	
    	assertTrue(rows.size()==2);
    	assertTrue(rows.get(0).get(0).contentEquals("T1R1C1"));
    	assertTrue(rows.get(0).get(1).contentEquals("T1R1C2"));
    	assertTrue(rows.get(1).get(0).contentEquals("T1R2C1"));
    	assertTrue(rows.get(1).get(1).contentEquals("T1R2C2"));
	}
	
	@Test
	void queryTest2() {
			
		DatabaseEngine engine = new DatabaseEngine();
    	
    	Table table1 = new Table("T1");
    	table1.addColumn("C1");
    	table1.addColumn("C2");
    	
    	List<String> row1 = new ArrayList<String>();
       	row1.add("T1R1C1");
    	row1.add("T1R1C2");
    	
    	List<String> row2 = new ArrayList<String>();
    	row2.add("T1R2C1");
    	row2.add("T1R2C2");

    	table1.addRow(row1);
    	table1.addRow(row2);

      	Table table2 = new Table("T2");
    	table2.addColumn("C1");
    	table2.addColumn("C2");

    	List<String> row3 = new ArrayList<String>();
    	row3.add("T2R1C1");
    	row3.add("T2R1C2");
    	
    	List<String> row4 = new ArrayList<String>();
    	row4.add("T2R2C1");
    	row4.add("T2R2C2");
    	
    	table2.addRow(row3);
    	table2.addRow(row4);

    	engine.register(table1);
    	engine.register(table2);
    	
    	List<List<String>> rows = engine.query("SELECT C1 FROM T1");
    	
    	assertTrue(rows.size()==2);
    	assertTrue(rows.get(0).get(0).contentEquals("T1R1C1"));
    	assertTrue(rows.get(1).get(0).contentEquals("T1R2C1"));
    	
	}
	
	@Test
	void queryTest3() {
		
		DatabaseEngine engine = new DatabaseEngine();
    	
    	Table table1 = new Table("T1");
    	table1.addColumn("C1");
    	table1.addColumn("C2");
    	
    	List<String> row1 = new ArrayList<String>();
       	row1.add("T1R1C1");
    	row1.add("T1R1C2");
    	
    	List<String> row2 = new ArrayList<String>();
    	row2.add("T1R2C1");
    	row2.add("T1R2C2");

    	table1.addRow(row1);
    	table1.addRow(row2);

      	Table table2 = new Table("T2");
    	table2.addColumn("C1");
    	table2.addColumn("C2");

    	List<String> row3 = new ArrayList<String>();
    	row3.add("T2R1C1");
    	row3.add("T2R1C2");
    	
    	List<String> row4 = new ArrayList<String>();
    	row4.add("T2R2C1");
    	row4.add("T2R2C2");
    	
    	table2.addRow(row3);
    	table2.addRow(row4);

    	engine.register(table1);
    	engine.register(table2);
    	
    	List<List<String>> rows = engine.query("SELECT C1 FROM T2");
    	
    	assertTrue(rows.size()==2);
    	assertTrue(rows.get(0).get(0).contentEquals("T2R1C1"));
    	assertTrue(rows.get(1).get(0).contentEquals("T2R2C1"));
    	
	}
	
	
	@Test
	void queryTest4() {
		
		DatabaseEngine engine = new DatabaseEngine();
    	
    	Table table1 = new Table("T1");
    	table1.addColumn("C1");
    	table1.addColumn("C2");
    	
    	List<String> row1 = new ArrayList<String>();
       	row1.add("T1R1C1");
    	row1.add("T1R1C2");
    	
    	List<String> row2 = new ArrayList<String>();
    	row2.add("T1R2C1");
    	row2.add("T1R2C2");

    	table1.addRow(row1);
    	table1.addRow(row2);

      	Table table2 = new Table("T2");
    	table2.addColumn("C1");
    	table2.addColumn("C2");

    	List<String> row3 = new ArrayList<String>();
    	row3.add("T2R1C1");
    	row3.add("T2R1C2");
    	
    	List<String> row4 = new ArrayList<String>();
    	row4.add("T2R2C1");
    	row4.add("T2R2C2");
    	
    	table2.addRow(row3);
    	table2.addRow(row4);

    	engine.register(table1);
    	engine.register(table2);
    	
    	List<List<String>> rows = engine.query("SELECT * FROM T1");
    	
    	assertTrue(rows.size()==2);
    	assertTrue(rows.get(0).get(0).contentEquals("T1R1C1"));
    	assertTrue(rows.get(0).get(1).contentEquals("T1R1C2"));
    	assertTrue(rows.get(1).get(0).contentEquals("T1R2C1"));
    	assertTrue(rows.get(1).get(1).contentEquals("T1R2C2"));
	}
}
