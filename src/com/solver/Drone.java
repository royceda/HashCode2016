package com.solver;

import java.util.List;

import com.parse.Order;
import com.parse.Warehouse;

public class Drone {

	private int name;
	private int r;
	private int c;
	private String status; //loaded, unloaded, waited
	private int type;
	private int warehouse;
	private int processing;
	private int order;
	
	private boolean loaded;
	
	public Drone(int name){
		setProcessing(0);
		this.setName(name);
		setR(0);
		setC(0);
		setStatus("W");
		setLoaded(false);
	}
	
	public int nextWarehouse(List<Warehouse> warehouses){
		
		return 0;
	}
	
	
	
	public int nextOrder(List<Order> orders){
		
		return 0;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getProcessing() {
		return processing;
	}

	public void setProcessing(int processing) {
		this.processing = processing;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(int warehouse) {
		this.warehouse = warehouse;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	
	
	
}
