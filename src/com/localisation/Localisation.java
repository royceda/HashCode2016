package com.localisation;

import java.util.HashMap;
import java.util.Map;

public class Localisation {
	protected int Id;
	protected int r;
	protected int c;
	
	public int[] demand; //items needed
	
	
	
	public int getOne(int type){
		demand[type]--;
		return type;
	}
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
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
	
	
	public int distance(Localisation lo){
		double f = 0;	
		f = Math.sqrt(Math.abs(this.getR() - lo.getR())^2 + Math.abs(this.getC()-lo.getC())^2);
		int dist = (int) Math.floor(f);
		if(f > dist)
			return dist+1;
		else
			return dist;
	}
	
	
	public int bestPoint(HashMap<Integer, Localisation> map){
		int dist0 = 10000000;
		int tmpO = 0;
		for(Map.Entry<Integer, Localisation> tmp : map.entrySet()){
			int dist1 = this.distance(tmp.getValue());
			if(dist0 > dist1){
				dist0 = dist1;
				tmpO = tmp.getKey();
			}
		}
		return tmpO;
	}
	
	
}
