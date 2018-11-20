package com.warehouse.model.rest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.warehouse.model.rest.SearchCommand;

public class SearchCommandTest {

	@Test
	public void test() {
		SearchCommand command = new SearchCommand();
		command.setCommand("test");
		command.setSearchValue("A");
		assertEquals("test", command.getCommand());
		assertEquals("A", command.getSearchValue());
	}

}
