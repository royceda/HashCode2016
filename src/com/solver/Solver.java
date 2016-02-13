package com.solver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.parse.Order;
import com.parse.Parser;
import com.parse.Warehouse;

public class Solver {

	private Parser parse;
	private List<Drone> fleet;
	private List<String> instruction;
	private String file;

	
	
	
	public Solver(String file) throws FileNotFoundException, IOException{
		instruction = new ArrayList<String>();
		fleet = new ArrayList<Drone>();
		parse = new Parser(file);
	
		this.file = file;
		for(int i = 0; i<parse.getD(); i++){
			fleet.add(new Drone(i, parse.getP()));
		}
	}
	
	
	public Warehouse getWarehouse(int type){
		//for(Iterator<Warehouse> ite = parse.getWarehouses().iterator(); ite.hasNext(); ){
		//Warehouse wa = ite.next();
		for(Warehouse wa: parse.getWarehouses()){	
			if(wa.hasType(type))
				return wa;
		}
		return null;
	}
	
	public Warehouse getWarehouse(Drone d){
		int dist0 = 10000000;
		Warehouse tmp = new Warehouse();
		for(Warehouse wa: parse.getWarehouses()){
			int dist1 = distanceL(d, wa);
			if(dist0 > dist1){
				dist0 = dist1;
				tmp = new Warehouse(wa);
			}
		}
		return tmp;
	}
	
	public Order getOrder(Warehouse wa){
		int dist0 = 10000000;
		Order tmpO = new Order();
		for(Order tmp: parse.getOrders()){
			int dist1 = distance(wa, tmp);
			if(dist0 > dist1){
				dist0 = dist1;
				tmpO = new Order(tmp);
			}
		}
		return tmpO;
	}
	
	
	public int distance(Warehouse w, Order d){
		double f = 0;	
		f = Math.sqrt(Math.abs(d.getR()*d.getR() - w.getR()) + Math.abs(d.getR()*d.getR() - w.getR()));
		int dist = (int) Math.floor(f);
		if(f > dist)
			return dist+1;
		else
			return dist;	
	}
	
	public int distanceL(Drone d, Warehouse w){
		double f = 0;	
		f = Math.sqrt(Math.abs(d.getR()*d.getR() - w.getR()) + Math.abs(d.getR()*d.getR() - w.getR()));
		int dist = (int) Math.floor(f);
		if(f > dist)
			return dist+1;
		else
			return dist;
	}
	
	public int distanceD(Drone d,  Order w){
		double f = 0;	
		f = Math.sqrt(Math.abs(d.getR()*d.getR() - w.getR()) + Math.abs(d.getR()*d.getR() - w.getR()));
		int dist = (int) Math.floor(f);
		if(f > dist)
			return dist+1;
		else
			return dist;
	}
	
	public void MAJ(){
		for(Iterator<Drone> ite = fleet.iterator(); ite.hasNext();){
			Drone tmp = ite.next();
			int time = tmp.getProcessing();
			if(time > 0)
				tmp.setProcessing(time-1);
		}
	}
	
	/*
	 * Simple heuristics take all and deliver all
	 * a drone and a command, the drone go to the warehouse and deliver
	 */
	public void Simple() throws FileNotFoundException, UnsupportedEncodingException{
		System.out.println("Solving.....");
		//turns
		for(int t = 0; t<parse.getT(); t++){
			//System.out.println("time: "+t);
			MAJ();
			
			//for(Iterator<Order> ite = parse.getOrders().iterator(); ite.hasNext(); ){
			//Order tmpOrder = ite.next();
			for(Order tmpOrder : parse.getOrders()){	
				if(tmpOrder.hasDemand()){	
					
					int type = tmpOrder.getOne();
					//for(Iterator<Drone> ite1 = fleet.iterator(); ite1.hasNext(); ){
					//Drone tmp = ite1.next();
					boolean taken = false;
					
					for(Drone tmp: fleet){	
						if(tmp.getStatus() == "W"){			
							Warehouse wa = getWarehouse(type);
							if(wa != null && !taken){
								//warehouse
								wa.giveOne(type);
								
								tmp.setProcessing(distanceL(tmp, wa));
								
								tmp.setR(wa.getR());
								tmp.setC(wa.getC());
								tmp.setType(type);
								tmp.setStatus("L");
								tmp.setOrder(tmpOrder.getName());	
								tmp.setWarehouse(wa.getName());
								
								String instL = "" + tmp.getName() + " L " + wa.getName() + " "+ tmp.getType() + " 1 ";
								System.out.println(instL);
								instruction.add(instL);
								taken = true;
							}
						}
							
						else if(tmp.getStatus() == "L" && tmp.getProcessing() == 0){			
							//order
							tmp.setR(tmpOrder.getR());
							tmp.setC(tmpOrder.getC());
							tmp.setStatus("D");
							
							tmp.setProcessing(distanceD(tmp, tmpOrder));
							
							String instD = "" + tmp.getName() + " D " + tmp.getOrder() + " "+ tmp.getType() + " 1 ";
							System.out.println(instD);
							instruction.add(instD);
							
						}
						
						else if(tmp.getStatus() == "D" && tmp.getProcessing() == 0){			
							tmp.setR(tmpOrder.getR());
							tmp.setC(tmpOrder.getC());
							tmp.setStatus("W");
							
							tmp.setProcessing(distanceD(tmp, tmpOrder));
							
							String instD = "" + tmp.getName() + " W " + t ;
							System.out.println(instD);
							instruction.add(instD);	
						}
					}
				}
			}
		}
	}
	
	
	public void multipleLoad(){
		//load all drone with all items it can take with order
		//when the drone is loading in warehouse, he takes orders with available items
		//and it goes for delivery
		System.out.println("Solving.....");
		for(int t = 0; t<parse.getT(); t++){
			//System.out.println("time: "+t);
			MAJ();	
			for(Drone tmp: fleet){
				if(tmp.getStatus() == "W"){			
					Warehouse wa = getWarehouse(tmp);
					Order tmpOrder = getOrder(wa);
					//for(Order tmpOrder : parse.getOrders()){
						for(int i = 0; i < tmpOrder.getN(); i++){
							if(tmpOrder.hasDemand() && !tmp.isLoaded()){
								
								int type = tmpOrder.getOne(wa);
								if(type != -1 && tmp.canTake(type, parse.getWeight()[type])){							
									tmpOrder.getOne(type);
	
									//duplicated code
									wa.giveOne(type);
									
									tmp.setProcessing(distanceL(tmp, wa));
									
									tmp.setR(wa.getR());
									tmp.setC(wa.getC());
									tmp.setType(type);
									tmp.setStatus("L");
									tmp.setOrder(tmpOrder.getName());	
									tmp.setWarehouse(wa.getName());
									
									tmp.load(type, parse.getWeight()[type]);
									
									
									String instL = "" + tmp.getName() + " L " + wa.getName() + " "+ tmp.getType() + " 1 ";
									System.out.println(instL);
									instruction.add(instL);					
								}		
							}
					}
				}
			
				else if(tmp.getStatus() == "L" && tmp.getProcessing() == 0){			
					Order tmpOrder = parse.getOrders().get(tmp.getOrder()); 
					//order
					tmp.setR(tmpOrder.getR());
					tmp.setC(tmpOrder.getC());
					tmp.setStatus("D");
					
					tmp.setProcessing(distanceD(tmp, tmpOrder));
					
					for(Iterator<Integer> ite = tmp.getOrders().iterator(); ite.hasNext();){
						int type = ite.next();
						String instD = "" + tmp.getName() + " D " + tmp.getOrder() + " "+ type + " 1 ";
						System.out.println(instD);
						instruction.add(instD);
					}
					
				}
				
				
				else if(tmp.getStatus() == "D" && tmp.getProcessing() == 0){	
					Order tmpOrder = parse.getOrders().get(tmp.getOrder()); 
					tmp.setR(tmpOrder.getR());
					tmp.setC(tmpOrder.getC());
					tmp.setStatus("W");
					
					tmp.setProcessing(distanceD(tmp, tmpOrder));
					
					String instD = "" + tmp.getName() + " W " + t ;
					System.out.println(instD);
					instruction.add(instD);	
				}
			}	
		}
	}


	
	
	public void print(String file1) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(file1, "UTF-8");
		int size = instruction.size();
		writer.println(""+size);
		for(Iterator<String> ite = instruction.iterator(); ite.hasNext();){
			String tmp = ite.next();
			writer.println(tmp);
		}
		writer.close();
		System.out.println("End.");
	}
	
}
