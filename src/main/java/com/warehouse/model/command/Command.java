package com.warehouse.model.command;

import com.warehouse.model.CommandResponse;

public interface Command {
	CommandResponse process(final String[] input);
}
