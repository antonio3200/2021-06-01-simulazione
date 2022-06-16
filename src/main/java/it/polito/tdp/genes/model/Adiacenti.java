package it.polito.tdp.genes.model;

public class Adiacenti  implements Comparable<Adiacenti>{
	String id;
	private Double peso;
	public Adiacenti(String id, double peso) {
		super();
		this.id = id;
		this.peso = peso;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return this.id+" - "+this.peso;
	}
	@Override
	public int compareTo(Adiacenti o) {
		return -(this.peso.compareTo(o.peso));
	}
	
	

}
