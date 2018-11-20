package com.warehouse.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.model.rest.SearchCommand;
import com.warehouse.processor.InputProcessor;

@RestController
public class WarehouseController {
	private InputProcessor inputProcessor;

	@Autowired
	public WarehouseController(InputProcessor inputProcessor) {
		this.inputProcessor = inputProcessor;
	}

	@PostMapping("/warehouse/{slots}")
	public ResponseEntity<String> warehouse(@PathVariable("slots") String slots) {
		String[] command = new String[2];
		command[0] = "warehouse";
		command[1] = slots;
		return ResponseEntity.ok(inputProcessor.processCommand(command));
	}

	@PostMapping("/warehouse/use/{warehouseId}")
	public ResponseEntity<String> useWarehouse(@PathVariable("warehouseId") String warehouseId) {
		String[] command = new String[3];
		command[0] = "warehouse";
		command[1] = "use";
		command[2] = warehouseId;
		return ResponseEntity.ok(inputProcessor.processCommand(command));
	}

	@PostMapping("/store/{productCode}/{colour}")
	public ResponseEntity<String> store(@PathVariable("productCode") String productCode,
			@PathVariable("colour") String colour) {
		String[] command = new String[3];
		command[0] = "store";
		command[1] = productCode;
		command[2] = colour;
		return ResponseEntity.ok(inputProcessor.processCommand(command));
	}
	
	@PostMapping("/sell/{slotNumber}")
	public ResponseEntity<String> sell(@PathVariable("slotNumber") String slotNumber) {
		String[] command = new String[2];
		command[0] = "sell";
		command[1] = slotNumber;
		return ResponseEntity.ok(inputProcessor.processCommand(command));
	}
	
	@GetMapping("/status")
	public ResponseEntity<String> status() {
		String[] command = new String[1];
		command[0] = "status";
		return ResponseEntity.ok(inputProcessor.processCommand(command));
	}
	
	@PostMapping(value = "search", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> command(@RequestBody SearchCommand input) {
		String[] command = new String[2];
		command[0] = input.getCommand();
		command[1] = input.getSearchValue();
		return ResponseEntity.ok(inputProcessor.processCommand(command));
	}
}
