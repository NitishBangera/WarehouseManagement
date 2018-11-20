package com.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.warehouse.model.command.CommandFactory;
import com.warehouse.model.command.ProductSearch;
import com.warehouse.model.command.Sell;
import com.warehouse.model.command.Status;
import com.warehouse.model.command.Store;
import com.warehouse.model.command.Warehouse;
import com.warehouse.rest.WarehouseFilter;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { WarehouseApplication.class, WarehouseConsoleApplication.class,
				WarehouseSessionConfig.class }, args);
	}

	@Bean
	public FilterRegistrationBean<WarehouseFilter> filterRegistrationBean(WarehouseFilter warehouseFilter) {
		FilterRegistrationBean<WarehouseFilter> registrationBean = new FilterRegistrationBean<WarehouseFilter>();
		registrationBean.setFilter(warehouseFilter);
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(2);
		return registrationBean;
	}

	@Bean
	public CommandFactory warehouseCommandFactory(Warehouse warehouse, Store store, Sell sell, Status status,
			ProductSearch productSearch) {
		CommandFactory commandFactory = new CommandFactory();
		commandFactory.addCommand("warehouse", warehouse);
		commandFactory.addCommand("store", store);
		commandFactory.addCommand("sell", sell);
		commandFactory.addCommand("status", status);
		for (String searchCommand : productSearch.getSearchCommands()) {
			commandFactory.addCommand(searchCommand, productSearch);
		}
		return commandFactory;
	}
}
