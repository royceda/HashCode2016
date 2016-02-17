package com.localisation;

import java.util.HashMap;
import java.util.List;

public class Order extends Localisation {
	
	private int n; //number of order
	//private int[] demands; //demands
	
	
	
	
	/**
	 * get the best warehouse
	 * @param war
	 * @return
	 */
	public Warehouse getNearest(HashMap<Integer, Warehouse> war){
		int dist = 1000000;
		Warehouse wa = null;
		for(Integer key: war.keySet()){
			Warehouse tmp = war.get(key);
			int dist2 = this.distance(tmp);
			if(dist2 < dist){
				dist = dist2;
				wa = tmp;
			}
		}
		return wa;
	}
	
	
	public boolean isEmpty(){
		if(n > 0)
			return false;
		else
			return false;		
	}
	
	public int getOne(){
		for(int i = 0; i < demand.length; i++){
			if(demand[i] > 0){
				demand[i]--;
				return i;
			}
		}
		return -1;
	}
	
	public int getOne(Warehouse wa){
		for(int i = 0; i < demand.length; i++){
			if(demand[i] > 0 && wa.hasType(i)){
				//qty[i]--;
				return i;
			}
		}
		return -1;
	}
	
	
	
	
	public boolean hasDemand(){
		for(int i = 0; i < demand.length; i++){
			if(demand[i] != 0)
				return true;
		}
		return false;
	}
	
	
	public Order(int id){
		setId(id);
		setR(0);
		setC(0);
		setN(0);
		//setQty(new int[0]);
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(Order tmp) {
		setId(tmp.getId());
		setR(tmp.getR());
		setC(tmp.getC());
		setN(tmp.getN());
		setDemands(tmp.getDemands());
	}

	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[] getDemands() {
		return demand;
	}

	
	public void setDemands(int[] demand) {
		this.demand = new int[demand.length];
		for(int i = 0; i<demand.length; i++)
			this.demand[i] = demand[i];		
	}
}
