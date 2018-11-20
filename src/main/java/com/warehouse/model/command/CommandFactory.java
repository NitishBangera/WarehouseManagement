package com.warehouse.model.command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
	private Map<String, Command> commands = new HashMap<>();

	public void addCommand(final String commandName, final Command commandProcessor) {
		commands.put(commandName, commandProcessor);
	}

	public Command getCommandProcessor(final String commandName) {
		return commands.get(commandName);
	}
}
