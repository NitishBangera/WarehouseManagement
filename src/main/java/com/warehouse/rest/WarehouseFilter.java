package com.warehouse.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warehouse.WarehouseContextHolder;
import com.warehouse.model.WarehouseInfo;
import com.warehouse.service.WarehouseService;

@Component
public class WarehouseFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(WarehouseFilter.class);

	@Autowired
	private WarehouseService warehouseService;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		final String[] tokens = httpRequest.getRequestURI().split("/");
		final String command = tokens[1];

		if (command.equals("warehouse")) {
			HttpSession session = httpRequest.getSession();
			filterChain.doFilter(request, response);
			Integer warehouseId = WarehouseContextHolder.getContext();
			if (warehouseId != null) {
				session.setAttribute("warehouse_id", warehouseId);
			} else {
				session.invalidate();
			}
		} else {
			HttpSession session = null;
			try {
				session = httpRequest.getSession(false);
				if (session != null) {
					final Integer warehouseId = (Integer) session.getAttribute("warehouse_id");
					WarehouseInfo warehouseInfo = warehouseService.getWarehouse(warehouseId);
					if (warehouseInfo == null) {
						log.error("Invalid warehouse id : {}", warehouseId);
						httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
								"Invalid session. Try again creating or using an existing warehouse.");
						session.invalidate();
					} else {
						WarehouseContextHolder.setContext(warehouseInfo.getId());
						filterChain.doFilter(request, response);
					}
				} else {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid session. Try again creating or using an existing warehouse.");
				}
			} catch (Exception ex) {
				log.error("Error", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Invalid session. Try again creating or using an existing warehouse.");
				if (session != null) {
					session.invalidate();
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("Initiating WarehouseFilter");
	}
}
