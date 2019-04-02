package application;

public class Cylinder extends Circle{
	
	int h;
	
	public Cylinder (int r, int h) {
		super(r);
		this.h=h;
	}
	
	public double volume() {
		return area(r)*h; // area function is inherited
	}
	
	public double surfArea() {
		return circum(r)*h+2*area(h); // circum & area is inherited
	}
}
