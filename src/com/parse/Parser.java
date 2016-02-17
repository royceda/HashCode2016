package com.parse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

import com.localisation.Order;
import com.localisation.Warehouse;



public class Parser {

	private int r;
	private int c;
	private int D;
	private int T;
	private int P; // payload
	
	private int n;
	private int[] weight;
	
	private int wh;//how many warehouses
	//private List<Warehouse> warehouses;
	
	private HashMap<Integer, Warehouse> warehouses;
	
	private int no; //how many orders
	private List<Order> orders;
	
	public HashMap<Integer, Order> mapOrders;
	
	public int nb;
	
	
	
	public int getWh(){
		return wh;
	}
	
	public int nbCommande(){
		int n = 0;
		for(Iterator<Order> ite = orders.iterator(); ite.hasNext();){
			n += ite.next().getN();
		}
		nb = n;
		return n;	
	}
	
	public int getNo(){
		return no;
	}
	
	public int[] getWeight(){
		return weight;
	}
	
	
	public List<Order> getOrders(){
		return orders;
	}
	
	public HashMap<Integer, Warehouse> getWarehouses(){
		return warehouses;
	}
	
	
	public Parser(String file) throws FileNotFoundException, IOException{
		String line;
		
		InputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		
		warehouses = new HashMap<Integer, Warehouse>();
		orders = new ArrayList<Order>();
		
		
		//first line
		if((line = br.readLine()) != null){
			String[] var = line.split(" ");
			setR(Integer.parseInt(var[0]));
			setC(Integer.parseInt(var[1]));
			setD(Integer.parseInt(var[2]));
			setT(Integer.parseInt(var[3]));
			setP(Integer.parseInt(var[4]));	
		}
		
		
		//Product
		if((line = br.readLine()) != null){
			String[] var = line.split(" ");
			n = Integer.parseInt(var[0]);
		}
		
		//weight product
		weight = new int[n];
		if((line = br.readLine()) != null){
			String[] var = line.split(" ");
			for(int i = 0; i<n; i++){
				weight[i] = Integer.parseInt(var[i]);
			}
		}
			
		//warehouse		
		if((line = br.readLine()) != null){
			String[] var = line.split(" ");
			wh = Integer.parseInt(var[0]);
		}	
		for(int i =0; i< wh; i++){
			Warehouse tmp = new Warehouse(i);	
			//coord
			if((line = br.readLine()) != null){
				String[] var = line.split(" ");
				tmp.setR(Integer.parseInt(var[0]));
				tmp.setC(Integer.parseInt(var[1]));						
			}			
			//availabilities
			int availTmp[] = new int[n];
			if((line = br.readLine()) != null){
				String[] var = line.split(" ");
				for(int j = 0; j < n; j++){
					availTmp[j] = Integer.parseInt(var[j]);
				}
				tmp.setStock(availTmp);
			}
			warehouses.put(tmp.getId(), tmp);
		}
			
			
		//orders
		if((line = br.readLine()) != null){
			String[] var = line.split(" ");
			no = Integer.parseInt(var[0]);
		}
		
		for(int i = 0; i < no; i++){
			Order tmp = new Order(i);
			//coord
			if((line = br.readLine()) != null){
				String[] var = line.split(" ");
				tmp.setR(Integer.parseInt(var[0]));
				tmp.setC(Integer.parseInt(var[1]));						
			}
			
			//number of item
			if((line = br.readLine()) != null){
				String[] var = line.split(" ");
				tmp.setN(Integer.parseInt(var[0]));
			}
			
			//demand
			int demandTmp[] = new int[n];
			if((line = br.readLine()) != null){
				String[] var = line.split(" ");
				for(int j = 0; j < tmp.getN(); j++){
					int index = Integer.parseInt(var[j]);
					demandTmp[index] += 1; 
				}
				tmp.setDemands(demandTmp);
			}	
		
			orders.add(tmp);	
		}
		
		//sorting of order
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
		
		
		mapOrders = new HashMap<Integer, Order>();
		for(Order tmp: orders)
			mapOrders.put(tmp.getId(), tmp);
			
		
		
		this.nbCommande();
		System.out.println("parse: ok!");
	}


	public int getR() {
		return r;
	}


	public void setR(int r) {
		this.r = r;
	}

	public int getN() {
		return n;
	}


	public void setN(int r) {
		this.n = r;
	}

	public int getC() {
		return c;
	}


	public void setC(int c) {
		this.c = c;
	}


	public int getD() {
		return D;
	}


	public void setD(int d) {
		D = d;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
	}

	public int getP() {
		return P;
	}

	public void setP(int p) {
		P = p;
	}
}
