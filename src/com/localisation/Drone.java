package com.localisation;

import java.util.ArrayList;
import java.util.List;

public class Drone extends Localisation{
	
	private String status; //loaded, unloaded, waited
	private int type; //0: livreur, 1: facteur
	
	private int warehouse; //warehouse's id assigned
	private int order; //order's id assigned can be a warehouse
	private int processing; //time before a new use
		
	private int payload; 
	private boolean loaded;
	private List<Integer> items; //list of item for delivery
	
	
	
	public boolean isDelivrer(){
		if(type == 1){
			return false;
		}else if(type == 0){
			return true;
		}
		return true;
	}
	
	
	public Drone(int name){
		setWarehouse(-1);
		setProcessing(0);
		this.setId(name);
		setR(0);
		setC(0);
		setStatus("W");
		setLoaded(false);
		items = new ArrayList<Integer>();
	}
	
	
	public Drone(int name, int payload){
		setWarehouse(-1);
		setProcessing(0);
		this.setId(name);
		setR(0);
		setC(0);
		setStatus("W");
		setLoaded(false);
		setPayload(payload);
		items = new ArrayList<Integer>();
	}
	
	
	/**
	 * can it take the item typed type
	 * @param type
	 * @param weight
	 * @return
	 */
	public boolean canTake(int type, int weight){
		if(payload - weight < 0)
			return false;
		else 
			return true;
	}

	/**
	 * load an item
	 * @param type2
	 * @param weight
	 */
	public void load(int type2, int weight) {
		items.add(type2);
		payload -= weight;
		if(payload == 0)
			loaded = true;
		else
			loaded = false;	
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

	public int getId() {
		return Id;
	}

	public void setId(int name) {
		this.Id = name;
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


	public int getPayload() {
		return payload;
	}


	public void setPayload(int payload) {
		this.payload = payload;
	}


	public List<Integer> getItems() {
		return items;
	}


	public void setItems(List<Integer> orders) {
		this.items = orders;
	}

	

	
	
	
}
