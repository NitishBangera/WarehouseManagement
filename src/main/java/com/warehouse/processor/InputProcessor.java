package com.warehouse.processor;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.warehouse.WarehouseException;
import com.warehouse.model.CommandResponse;
import com.warehouse.model.command.Command;
import com.warehouse.model.command.CommandFactory;
import com.warehouse.model.command.Search;

@Service
public class InputProcessor {
	private static final Logger log = LoggerFactory.getLogger(InputProcessor.class);
	private CommandFactory warehouseCommandFactory;
	private String inputFilePath;

	@Autowired
	public InputProcessor(CommandFactory warehouseCommandFactory, @Value("${application.input-file-path}") String inputFilePath) {
		this.warehouseCommandFactory = warehouseCommandFactory;
		this.inputFilePath = inputFilePath;
	}

	public void process() {
		boolean commandLineInterface = true;
		if (!(inputFilePath == null || inputFilePath.isEmpty())) {
			File file = new File(inputFilePath);
			try (Scanner in = new Scanner(file)) {
				while (in.hasNextLine()) {
					process(in, true);
				}
				commandLineInterface = false;
			} catch(Exception e) {
				log.error("File processing error", e);
			}
		} else {
			log.info("No input file mentioned. Command line interface enabled");
		}
		
		if (commandLineInterface) {
			try (Scanner in = new Scanner(System.in)) {
				while (true) {
					process(in, false);
				}
			}
		}
	}

	private void process(Scanner in, boolean printInput) {
		System.out.println("Input:");
		String input = in.nextLine();
		System.out.println(printInput ? input+"\n" : "");
		System.out.println("Output:");
		if (!input.isEmpty()) {
			String[] tokens = input.split(" ");
			if (tokens.length >= 1) {
				System.out.println(processCommand(tokens));
			}
		} else {
			System.out.println("Usage: <command> <parameters>");
		}
		System.out.println();
	}
	
	public String processCommand(String[] tokens) {
		String inputCommand = tokens[0];
		Command command = warehouseCommandFactory.getCommandProcessor(inputCommand);
		if (command != null) {
			String[] commandInput = null;
			if (command instanceof Search) {
				commandInput = tokens;
			} else {
				commandInput = Arrays.copyOfRange(tokens, 1, tokens.length);
			}
			CommandResponse response = null;
			String outputMessage = null;
			try {
				response = command.process(commandInput);
				outputMessage = response.getMessage();
			} catch (WarehouseException we) {
				outputMessage = we.getMessage();
			}
			return outputMessage;
		} else {
			return "Invalid command : " + inputCommand;
		}
	}
}
