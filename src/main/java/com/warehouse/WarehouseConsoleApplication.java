package com.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.warehouse.processor.InputProcessor;

public class WarehouseConsoleApplication implements CommandLineRunner {
	@Autowired
	private InputProcessor inputProcessor;

	@Override
	public void run(String... args) throws Exception {
		inputProcessor.process();
	}
}
