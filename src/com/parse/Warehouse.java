package com.parse;

public class Warehouse {

	private int name;
	private int r;
	private int c;
	private int[] avail;
	
	
	public Warehouse(){
		
	}
	
	public Warehouse(int i){
		name = i;
		r = 0;
		c = 0;
		//avail = new int[0];
	}
	
	public Warehouse(int r, int c, int[] avail){
		this.setR(r);
		this.setC(c);
		this.setAvail(avail);
	}

	public Warehouse(Warehouse wa){
		this.setName(wa.getName());
		this.setR(wa.getR());
		this.setC(wa.getC());
		this.setAvail(wa.getAvail());
	}
	
	
	public void giveOne(int i){
		avail[i] -= 1;
	}
	
	public boolean hasType(int type){
		if(avail[type] > 0)
			return true;
		else
			return false;
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


	public int[] getAvail() {
		return avail;
	}

	

	public void setAvail(int[] avail) {
		this.avail = new int[avail.length];
		for(int i = 0; i<avail.length; i++)
			this.avail[i] = avail[i];
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}
}
