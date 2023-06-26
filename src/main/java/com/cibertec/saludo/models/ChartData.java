package com.cibertec.saludo.models;

public class ChartData {
	private String name;
    private int y;
    
    
    
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public ChartData() {
    }

    public ChartData(String name, int y) {
        this.name = name;
        this.y = y;
    }
    
    
}
