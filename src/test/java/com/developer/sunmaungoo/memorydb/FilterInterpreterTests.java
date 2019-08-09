package com.developer.sunmaungoo.memorydb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import org.junit.jupiter.api.Test;

class FilterInterpreterTests {

	@Test
	void interprerterTest1() {
		
		List<String> filters = new ArrayList<String>();
		filters.add("C1");
		filters.add("==");
		filters.add("10");

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate> predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==1);
		
		assertTrue(predicate.get(0).getPredicate().test("C1", "10"));
		assertTrue(predicate.get(0).getColumnName().contentEquals("C1"));

	}
	
	@Test
	void interprerterTest2() {
		
		List<String> filters = new ArrayList<String>();
		filters.add("C1");
		filters.add("==");
		filters.add("10");

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate> predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==1);
		
		assertFalse(predicate.get(0).getPredicate().test("C1", "20"));
		
		assertTrue(predicate.get(0).getColumnName().contentEquals("C1"));

	}

	@Test
	void interprerterTest3() {
		
		List<String> filters = new ArrayList<String>();
		filters.add("C1");
		filters.add("!=");
		filters.add("10");

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate> predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==1);
		
		assertTrue(predicate.get(0).getPredicate().test("C1", "2"));
		
		assertTrue(predicate.get(0).getColumnName().contentEquals("C1"));

	}
	
	@Test
	void interprerterTest4() {
		
		List<String> filters = new ArrayList<String>();
		filters.add("C1");
		filters.add("!=");
		filters.add("10");

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate> predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==1);
		
		assertFalse(predicate.get(0).getPredicate().test("C1", "10"));
		
		assertTrue(predicate.get(0).getColumnName().contentEquals("C1"));

	}
	
	@Test
	void interprerterTest5() {
		
		List<String> filters = new ArrayList<String>();
		filters.add("C1");
		filters.add("==");
		filters.add("10");
		filters.add("AND");
		filters.add("C2");
		filters.add("==");
		filters.add("20");

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate>predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==2);
		
		assertTrue(predicate.get(0).getPredicate().test("C1", "10"));
		assertTrue(predicate.get(0).getColumnName().contentEquals("C1"));
		assertTrue(predicate.get(1).getPredicate().test("C2", "20"));
		assertTrue(predicate.get(1).getColumnName().contentEquals("C2"));

	}
	
	@Test
	void interprerterTest6() {
		
		List<String> filters = new ArrayList<String>();
		filters.add("C1");
		filters.add("==");
		filters.add("10");
		filters.add("OR");
		filters.add("C2");
		filters.add("==");
		filters.add("20");

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate> predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==2);
		
		assertTrue(predicate.get(0).getPredicate().test("C1", "10"));
		assertTrue(predicate.get(0).getColumnName().contentEquals("C1"));
		assertTrue(predicate.get(1).getPredicate().test("C2", "20"));
		assertTrue(predicate.get(1).getColumnName().contentEquals("C2"));


	}
	
	
	@Test
	void interprerterTest7() {
		
	
		String[] columnName=new String[] {"C1","C2"};

		String[] columnValue=new String[] {"10","20"};
		
		String[] testColumnName = new String[] {"C1","C2"};
		
		String[] testColumnValue=new String[] {"10","20"};

		List<String> filters = new ArrayList<String>();
		filters.add(columnName[0]);
		filters.add("==");
		filters.add(columnValue[0]);
		filters.add("AND");
		filters.add(columnName[1]);
		filters.add("==");
		filters.add(columnValue[1]);

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate> predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==2);
		
		boolean result = true;
		
		for(int i=0;i<predicate.size();i++) {
			
			BiPredicate<String, String> currentPredicate = predicate.get(i).getPredicate();
			
			result = currentPredicate.test(testColumnName[i], testColumnValue[i]);
			
			assertTrue(predicate.get(i).getColumnName().contentEquals(testColumnName[i]));

			Modifer currentModifer = predicate.get(i).getModifer();
			
			if(currentModifer!=Modifer.NONE) {
				
				i++;
				
				BiPredicate<String, String> nextPredicate = predicate.get(i).getPredicate();
				
				boolean nextResult = nextPredicate.test(testColumnName[i],testColumnValue[i]);
				
				if(currentModifer==Modifer.AND) {
					
					result = result && nextResult;
					
				}else if(currentModifer==Modifer.OR) {
					
					result = result || nextResult;

				}
			}
		}
		
		assertTrue(result);

	}
	
	@Test
	void interprerterTest8() {
		
	
		String[] columnName=new String[] {"C1","C1"};

		String[] columnValue=new String[] {"10","20"};
		
		String[] testColumnName = new String[] {"C1","C1"};
		
		String[] testColumnValue=new String[] {"5","20"};
		
		List<String> filters = new ArrayList<String>();
		filters.add(columnName[0]);
		filters.add("==");
		filters.add(columnValue[0]);
		filters.add("OR");
		filters.add(columnName[1]);
		filters.add("==");
		filters.add(columnValue[1]);

		FilterInterpreter interprerter = new FilterInterpreter(filters);
		
		assertTrue(interprerter.interpret());
		
		List<FilterPredicate> predicate = interprerter.getPredicate();
		
		assertNotNull(predicate);
		
		assertTrue(predicate.size()==2);
		
		boolean result = true;
		
		for(int i=0;i<predicate.size();i++) {
			
			BiPredicate<String, String> currentPredicate = predicate.get(i).getPredicate();
				
			result = currentPredicate.test(testColumnName[i], testColumnValue[i]);
			
			assertTrue(predicate.get(i).getColumnName().contentEquals(testColumnName[i]));

			Modifer currentModifer = predicate.get(i).getModifer();
			
			if(currentModifer!=Modifer.NONE) {
				
				i++;
				
				BiPredicate<String, String> nextPredicate = predicate.get(i).getPredicate();
				
				boolean nextResult = nextPredicate.test(testColumnName[i],testColumnValue[i]);
				
				if(currentModifer==Modifer.AND) {
					
					result = result && nextResult;
					
				}else if(currentModifer==Modifer.OR) {
					
					result = result || nextResult;

				}
			}
		}
		
		assertTrue(result);

	}
}
