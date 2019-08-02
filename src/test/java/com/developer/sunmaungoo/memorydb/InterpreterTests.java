package com.developer.sunmaungoo.memorydb;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class InterpreterTests {

	
	@Test
	void interpretTest1() {
		
		Interpreter interpreter=new Interpreter("");
		
		assertFalse(interpreter.interpret());
	}
	
	
	@Test
	void interpretTest2() {
		
		Interpreter interpreter=new Interpreter("SELECT C1,C2 FROM T1");
		
		assertTrue(interpreter.interpret());
		
		List<String> selectionColumns = interpreter.getSelectionColumns();
				
		assertTrue(selectionColumns.size()==2);
		assertTrue(selectionColumns.get(0).contentEquals("C1"));
		assertTrue(selectionColumns.get(1).contentEquals("C2"));

		String tableName = interpreter.getTableName();
		
		assertNotNull(tableName);
		
		assertTrue(tableName.contentEquals("T1"));
		
	}
	
	@Test
	void interpretTest3() {
		
		Interpreter interpreter=new Interpreter("SELECT C1, C2 FROM T1");
		
		assertTrue(interpreter.interpret());
		
		List<String> selectionColumns = interpreter.getSelectionColumns();
				
		assertTrue(selectionColumns.size()==2);
		assertTrue(selectionColumns.get(0).contentEquals("C1"));
		assertTrue(selectionColumns.get(1).contentEquals("C2"));

		String tableName = interpreter.getTableName();
		
		assertNotNull(tableName);
		
		assertTrue(tableName.contentEquals("T1"));
		
	}
	
	@Test
	void interpretTest4() {
		
		Interpreter interpreter=new Interpreter("SELECT C1 FROM T1");
		
		assertTrue(interpreter.interpret());
		
		List<String> selectionColumns = interpreter.getSelectionColumns();
				
		assertTrue(selectionColumns.size()==1);
		assertTrue(selectionColumns.get(0).contentEquals("C1"));

		String tableName = interpreter.getTableName();
		
		assertNotNull(tableName);
		
		assertTrue(tableName.contentEquals("T1"));
		
	}
	
	@Test
	void interpretTest5() {
		
		Interpreter interpreter=new Interpreter("C1 FROM T1");
		
		assertFalse(interpreter.interpret());
		
	}
	
	@Test
	void interpretTest6() {
		
		Interpreter interpreter=new Interpreter("SELECT C1 FROM");
		
		assertFalse(interpreter.interpret());
			
	}
	
	@Test
	void interpretTest7() {
		
		Interpreter interpreter=new Interpreter("SELECT C1 FROM T1,T2");
		
		assertFalse(interpreter.interpret());
			
	}
	
	@Test
	void interpretTest8() {
		
		Interpreter interpreter=new Interpreter("SELECT C1 FROM T1 EXTRA_VALUE");
		
		assertFalse(interpreter.interpret());
			
	}
}
