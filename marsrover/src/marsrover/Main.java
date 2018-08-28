package marsrover;

import java.util.Scanner;

import com.mars.controllers.Controller;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Hello world!");
		Controller roverController = new Controller();
		Scanner in = new Scanner(System.in);  // Reading from System.in
		
		String inputString = "";
		
		do {
			System.out.println("Input a control string: ");
			inputString = in.nextLine();
			roverController.processInput(inputString);
			System.out.println("\n---OUTPUT--- \n");
			System.out.println(roverController.displayGrid());
		} while(true);
			
		
		
	}
}