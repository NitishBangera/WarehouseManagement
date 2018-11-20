package com.warehouse.model.command;

import java.util.Set;

public interface Search extends Command {
	Set<String> getSearchCommands();
}
