package com.warehouse;

public class WarehouseContextHolder {
	private static ThreadLocal<Integer> holder =  new InheritableThreadLocal<Integer>();
	
	public static void setContext(Integer warehouseId) {
		holder.set(warehouseId);
	}
	
	public static Integer getContext() {
		return holder.get();
	}
}
