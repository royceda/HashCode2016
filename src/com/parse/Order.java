package com.parse;



public class Order {

	private int name;
	private int r;
	private int c;
	private int n;
	private int[] qty;
	
	
	
	
	public int getOne(){
		for(int i = 0; i < qty.length; i++){
			if(qty[i] > 0){
				qty[i]--;
				return i;
			}
		}
		return -1;
	}
	
	public boolean hasDemand(){
		for(int i = 0; i < qty.length; i++){
			if(qty[i] != 0)
				return true;
		}
		return false;
	}
	
	
	public Order(int i){
		setName(i);
		setR(0);
		setC(0);
		setN(0);
		//setQty(new int[0]);
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[] getQty() {
		return qty;
	}

	public void setQty(int[] qty) {
		this.qty = qty;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public void setqty(int[] demand) {
		qty = new int[demand.length];
		for(int i = 0; i<demand.length; i++)
			this.qty[i] = demand[i];
	
		
	}
	
	
}
