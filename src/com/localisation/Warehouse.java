package com.localisation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Warehouse extends Localisation {
	
	private int[] stock;// availability
	
	public int[] plus;// items can be given
	private int[] inWait; //
	
	private List<Integer> waitList;
	private List<Order> orders;
	
	
	public int nb;
	
	public Warehouse(){
		
	}
	
	public Warehouse(int i){
		setId(i);
		setR(0);
		setC(0);
		orders = new ArrayList<Order>();
		waitList = new ArrayList<Integer>();
	}
	
	public Warehouse(int r, int c, int[] avail){
		this.setR(r);
		this.setC(c);
		this.setStock(avail);
		orders = new ArrayList<Order>();
		waitList = new ArrayList<Integer>();
	}

	public Warehouse(Warehouse wa){
		this.setId(wa.getId());
		this.setR(wa.getR());
		this.setC(wa.getC());
		this.setStock(wa.getStock());
		orders = new ArrayList<Order>();
		waitList = new ArrayList<Integer>();		
	}
	
	/**
	 * give an object that type is i
	 * @param i
	 */
	public void giveOne(int i){
		stock[i] -= 1;
	}
	
	/**
	 * a new order
	 * @return
	 */
	public Order giveOrder(){
		for(Order tmp: orders){
			if(tmp.hasDemand()){
				return tmp;
			}
		}
		return null;
	}
	
	
	/**
	 * check availability of type
	 * @param type
	 * @return
	 */
	public boolean hasType(int type){
		if(stock[type] > 0)
			return true;
		else
			return false;
	}
	
	
	public int[] getStock() {
		return stock;
	}

	

	public void setStock(int[] avail) {
		this.stock = new int[avail.length];
		for(int i = 0; i<avail.length; i++)
			this.stock[i] = avail[i];
	}
	
	
	public List<Order> getOrders(){
		return orders;
	}
	
	
	public void addOne(int type){
		stock[type]++;
	}
	
	
	public boolean hasNeeds(){
		int n = demand.length;
		for(int i = 0; i<n; i++){
			if(demand[i]>0){
				return true;
			}
		}
		return false;
	}
	
	public void addInWait(int type){
		this.inWait[type]++;
	}
	
	/**
	 * define need & plus
	 */
	public void setting(){
		int n = stock.length;
		int[] tmp = new int[n];
		demand = new int[n];
		plus = new int[n];
		inWait = new int[n];
		
		for(Iterator<Order> ite = orders.iterator(); ite.hasNext();){
			Order tmpO = ite.next();
			for(int i = 0; i< stock.length; i++){
				if(tmpO.getDemands()[i] > 0){
					tmp[i] += tmpO.getDemands()[i];
				}
			}
		}	
		for(int i = 0; i< stock.length; i++){
			if(tmp[i] > 0){
				if(tmp[i] > stock[i]){
					demand[i] = tmp[i] - stock[i];
				}
				else if(tmp[i] < stock[i]){
					plus[i] = stock[i] - tmp[i];
				}
			}
		}		
	}
	
	
	/**
	 * check if all clients are completed
	 * @return
	 */
	public boolean hasDemand(){
		for(Iterator<Order> ite = orders.iterator(); ite.hasNext();){
			Order tmp = ite.next();
			if(tmp.hasDemand())
				return true;
		}
		return false;
	}
	
	/**
	 * get warehouse's fleet
	 * @param list
	 * @return
	 */
	public List<Drone> getFleet(List<Drone> list){
		List<Drone> fleet = new ArrayList<Drone> ();
		for(Drone tmp: list){
			if(tmp.getWarehouse() == this.getId()){
				fleet.add(tmp);
			}
			
		}
		return fleet;
	}
	
	
	public void affect(List<Drone> list){
		List<Drone> fleet = getFleet(list);
		Collections.sort(orders, new Comparator<Order>() {
			@Override
	        public int compare(Order o1, Order o2)
	        {
				 if(o1.getN() == o2.getN())
					 return 0;
				 else if ( o1.getN() < o2.getN())
					 return -1;
				 else if( o1.getN() > o2.getN())
					 return 1;
				 return 0;
	        }
	    });
		
		int c = 0;
		for(Drone tmp: fleet){
			if(tmp.isDelivrer()){
				tmp.setOrder(orders.get(c).getId());
				c++;
			}
		}
		
	}
	
	
	
	/**
	 * How many object ordered
	 * @return
	 */
	public int nbCommande(){
		int n=0;
		for(Iterator<Order> ite = orders.iterator(); ite.hasNext();){
			n += ite.next().getN();
		}
		nb = n;
		return n;
	}
	
	
	public void addOrder(Order o){
		orders.add(o);
	}
	

}
