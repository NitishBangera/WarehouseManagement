package com.warehouse.model.command;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.warehouse.model.command.Command;
import com.warehouse.model.command.CommandFactory;
import com.warehouse.model.command.Status;

public class CommandFactoryTest {

	@Test
	public void testCommand() {
		CommandFactory commandFactory = new CommandFactory();
		commandFactory.addCommand("test", new Status(null));
		Command status = commandFactory.getCommandProcessor("test");
		assertNotNull(status);
		assertTrue((status instanceof Status));
	}
}
