package com.solver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.localisation.Drone;
import com.localisation.Order;
import com.localisation.Warehouse;
import com.parse.Parser;
import com.localisation.*;


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
		for(Integer key: parse.getWarehouses().keySet()){
			Warehouse wa = parse.getWarehouses().get(key);
			if(wa.hasType(type))
				return wa;
		}
		return null;
	}


	public void MAJ(){
		for(Iterator<Drone> ite = fleet.iterator(); ite.hasNext();){
			Drone tmp = ite.next();
			int time = tmp.getProcessing();
			if(time > 0)
				tmp.setProcessing(time-1);
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

	/*
	 * Simple heuristics take all and deliver all
	 * a drone and a command, the drone go to the warehouse and deliver
	 */
	public void Simple() throws FileNotFoundException, UnsupportedEncodingException{
		System.out.println("Solving.....");
		//turns
		int T = 0;
		int current = 0;
		Order tmpOrder = parse.getOrders().get(current); //sort orders in parser		
		for(int t = 0; t<parse.getT(); t++){
			MAJ();
			for(Drone tmp: fleet){	
				if(!tmpOrder.hasDemand()){	
					//System.out.println("Order: "+current+" / "+parse.getOrders().size()+" completed to delivered");
					current++;
					if(current < parse.getOrders().size())
						tmpOrder = parse.getOrders().get(current);
					else{
						T = t;
						break;
					}
				}
				int type = tmpOrder.getOne();
				//for(Iterator<Drone> ite1 = fleet.iterator(); ite1.hasNext(); ){
				//Drone tmp = ite1.next();
				boolean taken = false;
				if(tmp.getStatus() == "W"){			
					Warehouse wa = getWarehouse(type);
					if(wa != null && !taken){
						//warehouse
						wa.giveOne(type);
						tmp.setProcessing(tmp.distance(wa));
						tmp.setR(wa.getR());
						tmp.setC(wa.getC());
						tmp.setType(type);
						tmp.setStatus("L");
						tmp.setOrder(tmpOrder.getId());	
						tmp.setWarehouse(wa.getId());
						String instL = "" + tmp.getId() + " L " + wa.getId() + " "+ tmp.getType() + " 1 ";
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
					tmp.setProcessing(tmp.distance(tmpOrder));
					String instD = "" + tmp.getId() + " D " + tmp.getOrder() + " "+ tmp.getType() + " 1 ";
					System.out.println(instD);
					instruction.add(instD);
				}
				else if(tmp.getStatus() == "D" && tmp.getProcessing() == 0){			
					tmp.setR(tmpOrder.getR());
					tmp.setC(tmpOrder.getC());
					tmp.setStatus("W");
					tmp.setProcessing(tmp.distance(tmpOrder));
					String instD = "" + tmp.getId() + " W " + t ;
					System.out.println(instD);
					instruction.add(instD);	
				}
			}
		}
		System.out.println("time: "+T +"\n time: "+parse.getT());
	}



	public void repartition(List<Drone> list){
		for(Integer key: parse.getWarehouses().keySet()){
			Warehouse wa = parse.getWarehouses().get(key);

			int total = parse.nbCommande();
			int n = wa.nbCommande();//nb commandes de wa
			int nd = (int) Math.floor(total/n)+1;

			for(int i = 0; i<nd; i++){	
				if(nd <= list.size()){
					Drone drone = list.get(i);
					if(drone.isDelivrer()){
						if(drone.getWarehouse() == -1){
							drone.setWarehouse(wa.getId());
						}
						else
							nd++;
					}
				}
			}
		}
	}
	


	public void giveLoad(Drone tmp, int type, Warehouse wa, Localisation ord){	
		wa.giveOne(type);
		ord.getOne(type);
		tmp.load(type, parse.getWeight()[type]);
		tmp.setProcessing(tmp.distance(wa));
		tmp.setR(wa.getR());
		tmp.setC(wa.getC());		
		tmp.setStatus("L");		
		String instL = "" + tmp.getId() + " L " + wa.getId() + " "+ type + " 1 ";
		System.out.println(instL);
		instruction.add(instL);	
	}


	public void giveDelivery(Drone tmp, Localisation tmpOrder, String status){
		tmp.setR(tmpOrder.getR());
		tmp.setC(tmpOrder.getC());
		tmp.setStatus(status);
		tmp.setProcessing(tmp.distance(tmpOrder));

		for(int i = 0; i<parse.getN(); i++){
			for(Iterator<Integer> ite = tmp.getItems().iterator(); ite.hasNext();){
				int type = ite.next();
				String instD = "" + tmp.getId() +" "+ status+" " + tmp.getOrder() + " "+ type + " 1 ";
				System.out.println(instD);
				instruction.add(instD);
			}
			tmp.getItems().clear();
		}
	}


	public void giveWait(Drone tmp, Localisation tmpOrder, int turn){
		tmp.setR(tmpOrder.getR());
		tmp.setC(tmpOrder.getC());
		tmp.setStatus("W");
		tmp.setProcessing(tmp.distance(tmpOrder));
		String instD = "" + tmp.getId() + " W " + turn ;
		System.out.println(instD);
		instruction.add(instD);	
	}


	public void multiple(){
		System.out.println("Preprocessing...");
		//pour tout client: definir sa warehouse la plus proche
		for(Order ord: parse.getOrders()){
			Warehouse wa = ord.getNearest(parse.getWarehouses());
			wa.addOrder(ord);
		}


		//pour tout warehouse: definir plus et besoin
		for(Integer key: parse.getWarehouses().keySet()){
			Warehouse wa = parse.getWarehouses().get(key);
			wa.setting();
		}

		//Affectation des drones
		int index = 0;
		for(Integer key: parse.getWarehouses().keySet()){
			Warehouse wa = parse.getWarehouses().get(key);
			Drone tmpDrone = fleet.get(index);			
			tmpDrone.setType(1);
			tmpDrone.setWarehouse(wa.getId());
			index++;
		}
		
		//livreur
		repartition(fleet);
		//TSP des warehouses ou reseaux
		//definir les voisins
		
		
		
		
		System.out.println("Solving....");
		for(int t = 0; t<parse.getT(); t++){
			MAJ();
			//System.out.println(t);
			//placer les drones à leur warehouse et leur affecter les premier client(trié en spt)	
			for(Drone tmp: fleet){	
				if(tmp.getStatus() == "W"){	
					if(tmp.isDelivrer()){
						Warehouse wa = parse.getWarehouses().get(tmp.getWarehouse());		
						//verifier que la warehouse n'a pas finis
						if(!wa.hasDemand()){
							List<Drone> tmpFleet = wa.getFleet(fleet);
							repartition(tmpFleet);
						}else{
							Order ord = wa.getOrders().get(tmp.getOrder());

							if(ord.hasDemand()){
								//si livreur
								for(int i = 0; i<parse.getN(); i++){
									if(ord.getDemands()[i]>0){
										if(wa.getStock()[i]>0){
											if(tmp.canTake(i, parse.getWeight()[i])){			
												giveLoad(tmp, i, wa, ord);	
											}
										}else{
											wa.addInWait(i);
										}
									}
								}					
							}
						}
					}else{//facteur
						Warehouse wa = parse.getWarehouses().get(tmp.getWarehouse());		
						Warehouse tmpWa = parse.getWarehouses().get(tmp.getOrder());
						for(int i = 0; i<tmpWa.getStock().length; i++){
							if(tmpWa.demand[i] > 0 && wa.plus[i] > 0){
								if(tmp.canTake(i, parse.getWeight()[i])){	
									giveLoad(tmp, i, wa, tmpWa);	
								}				
							}
						}		
					}		



				}else if(tmp.getStatus() == "L" && tmp.getProcessing() == 0){			
					//livre au client
					Warehouse wa = parse.getWarehouses().get(tmp.getOrder());
					
					if(tmp.isDelivrer()){
						Order tmpOrder = wa.getOrders().get(tmp.getOrder());
						giveDelivery(tmp, tmpOrder, "D");
					}else{
						Warehouse tmpOrder = parse.getWarehouses().get(tmp.getOrder());
						giveDelivery(tmp, tmpOrder, "U");
					}
				}else if(tmp.getStatus() == "D" && tmp.getProcessing() == 0){			
					Order tmpOrder = parse.getOrders().get(tmp.getOrder());
					giveWait(tmp, tmpOrder, t); //TURN!!!

				}else if(tmp.getStatus() == "U" && tmp.getProcessing() == 0){			
					//les facteurs livrent au voisin 				
					Warehouse tmpOrder = parse.getWarehouses().get(tmp.getOrder());
					giveWait(tmp, tmpOrder, t); //TURN!!!		
				}	
			}
		}

		//les livreur chargent au max pour leur orders: s'il manquent les derniers objets les clients sont en waitlist

		//si une warehouse est complete le facteur devient livreur et on recreer un tsp

		//quand une warehouse sont repartis dans les autres warehouses


	}
}
