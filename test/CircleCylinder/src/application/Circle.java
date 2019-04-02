package application;

public class Circle {
	int r;
	
	public Circle (int r) {
		this.r = r;
	}
	
	public double circum(int r) {
		return 2*3.14*r;
	}
	
	public double area(int r) {
		return 3.14*r*r;
	}
	
	public int getR() {
		return this.r;
	}
}
