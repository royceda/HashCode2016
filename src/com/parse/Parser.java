package com.parse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;



public class Parser {

	private int r;
	private int c;
	private int D;
	private int T;
	private int P;
	
	private int n;
	private int[] weight;
	
	private int wh;
	private List<Warehouse> warehouses;
	
	private int no;
	private List<Order> orders;
	
	
	public List<Order> getOrders(){
		return orders;
	}
	
	public List<Warehouse> getWarehouses(){
		return warehouses;
	}
	
	
	public Parser(String file) throws FileNotFoundException, IOException{
		String line;
		
		InputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		
		warehouses = new ArrayList<Warehouse>();
		orders = new ArrayList<Order>();
		
		
		//first line
		if((line = br.readLine()) != null){
			String[] var = line.split(" ");
			setR(Integer.parseInt(var[0]));
			setC(Integer.parseInt(var[1]));
			setD(Integer.parseInt(var[2]));
			setT(Integer.parseInt(var[3]));
			P = Integer.parseInt(var[4]);	
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
				tmp.setAvail(availTmp);
			}
			warehouses.add(tmp);
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
					demandTmp[j] = Integer.parseInt(var[j]);
				}
				tmp.setqty(demandTmp);
			}	
		
			orders.add(tmp);	
		}
		System.out.println("parse: ok!");
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
}
