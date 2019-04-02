package application;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int r;
		int h;
		String input;
		
		System.out.print("Put your radius of cylinder: ");
		input = scan.nextLine();
		r = Integer.parseInt(input);
		System.out.print("Put your height of cylinder: ");
		input = scan.nextLine();
		h = Integer.parseInt(input);
		
		Cylinder a = new Cylinder(r,h);
		
		double surfArea = a.surfArea();
		double volume = a.volume();
		
		System.out.println("surfArea is " +surfArea);
		System.out.println("volume is "+volume);
	}
}
