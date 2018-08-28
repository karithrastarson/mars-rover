package com.mars.controllers;

import java.util.ArrayList;

import com.mars.objects.Plateau;
import com.mars.objects.Rover;
import com.mars.tools.CardinalDirection;

public class Controller {
	
	//Class Variables
	
	//List of rovers present in the simulation
	private ArrayList<Rover> rovers;
	
	//The platform on which the rovers navigate
	private Plateau plateau;
	
	//The last rover that the user created
	private Rover lastEstablishedRover;

	//Constructor
	public Controller() {
		rovers = new ArrayList<Rover>(); 
	}

	//This function takes input from the user to establish the grid, which is the plateau.
	public void initializePlateau(int row, int col) {
		plateau = new Plateau(row, col);
		if(rovers != null) {
			//If the rovers have been established before, 
			//then that record is viped, as the user is starting a new simulation with a new grid.
			rovers = new ArrayList<Rover>(); 
		}
	}

	//Create a rover in a specific location
	public void createRover(int x, int y, CardinalDirection car) {
		//Make sure that the given coordinates are not outside the established plateau.
		if(x < plateau.getCols() && y < plateau.getRows()) {
		Rover rover = new Rover(x, y, car);
		
		//Update the information in the plateau. 
		plateau.occupyCell(x, y);

		lastEstablishedRover = rover;
		rovers.add(rover);
		}
		else {
			System.out.println("The given coordinates for the rover are not within the boundaries of the grid that has been established");
		}
	}

	public void moveRover(String sequence, Rover rover) {
		//The input sequence is a sequence of manouevres, L, R or M.
		//Those manouevres are then applied to the selected rover
		for (char ch: sequence.toCharArray()) {
			switch(ch) {
			case 'L': rover.turnLeft();
			break;
			case 'R': rover.turnRight();
			break;
			case 'M': 
				//Make sure that the rover doesn't go out of bounds
				if(!nextMoveOffGrid(rover)) {
					plateau.clearCell(rover.getX(), rover.getY());
					rover.moveForward();
					plateau.occupyCell(rover.getX(), rover.getY());
				}
				break;
			default: break;
			}
		}
	}
	
	/* The processInput() function should handle three types of input:
	1. format "X Y" where X and Y are positive non-zero integers. This established the plateau. 
		TODO: Boundary case: What happens if the plateau has already been established? re-establish and wipe all rovers. 
		This means a new simulation is starting.
	2. format "X Y Z", where X and Y are positive non-zero integers and Y is a cardinal direction, NORTH, SOUTH, WEST EAST. 
		This establishes a rover in the grid system, and this indicates that the next control string will be applied to this rover. So this rover should be stored in a variable.
	3. format "(L*R*M*)*", a single string, containing a combination of the letters L, R and M and no spaces. This indicates the
	 	manouver that the previously established rover should do.

	 All other inputs should be ignored.

	 */
	public void processInput(String input) {
		input = input.toUpperCase();
		//First check: If the input contains no spaces, then this is a manoeuvre string.
		if(!input.contains(" ")) {
			//This string will be sanitized as a manoueuvre string.
			
			//Scroll through all characters of the string and make sure their either L, R or M.
			boolean inputIsValid = true;
			for (char ch: input.toCharArray()) {
				inputIsValid = inputIsValid && (ch == 'L' || ch == 'R' || ch == 'M');
				//This logic makes sure, that if the test fails once then isInputvalid will never be true again.
			}

			if(inputIsValid) {
				moveRover(input, lastEstablishedRover);
			}

			return;
		}

		//Next split the string by spaces. 
		String[] splitInput = input.split(" ");

		//If that produces 3 substrings, it is establishing a rover
		if(splitInput.length == 3) {
			//Further logic for establish a rover

			try {
				int x = Integer.parseInt(splitInput[0]);
				int y = Integer.parseInt(splitInput[1]);

				switch(splitInput[2]) {
				case "N": createRover(x, y, CardinalDirection.NORTH);
				break;
				case "S": createRover(x, y, CardinalDirection.SOUTH);
				break;
				case "E": createRover(x, y, CardinalDirection.EAST);
				break;
				case "W": createRover(x, y, CardinalDirection.WEST);
				break;
				default: break;
				}
			}
			catch (NumberFormatException e)
			{
				//x or y did not parse to int
				System.out.println("input parameter is not a valid integer number");
			}

			return;
		}

		//If that produces 2 substrings, it is establishing the plateau.
		if(splitInput.length == 2) {
			//Further logic for establishing the plateau.
			try
			{
				// checking valid integer using parseInt() method
				int x = Integer.parseInt(splitInput[0]);
				int y = Integer.parseInt(splitInput[1]);

				if(x > 0 && y > 0) {
					initializePlateau(x, y);
				}
			} 
			catch (NumberFormatException e)
			{
				System.out.println("Input parameter is not a valid integer number");
			}
		}
		System.out.println("Invalid input");
		return;
		//Else the input is invalid.
	}

	private boolean nextMoveOffGrid(Rover rover) {
		//Get dimension of the plateau
		int rows = plateau.getRows();
		int cols = plateau.getCols();
		//Get the cardinal direction of the rover
		CardinalDirection dir = rover.getCardinalDirection();

		//See if the rover is headed outside the plateau
		switch(dir) {
			case NORTH: return (rover.getY() == rows - 1);
			case SOUTH: return (rover.getY() == 0);
			case WEST: return (rover.getX() == 0);
			case EAST: return (rover.getX() == cols -1);
		}
		return false;
	}

	/* displayGrid is a pretty messy string manipulation method
	 * created to display the current status of the system. This methods is plagued with
	 * all sorts of boundary checks, to make sure not to run into any nullpointer exception*/
	public String displayGrid() {
		String gridString = "";
		if(plateau != null) {
			int x = plateau.getRows();
			int y = plateau.getCols();
			for(int i = y - 1; i >= 0; i--) {
				for(int j = 0; j < x ; j++) {
					if(plateau.isCellOccupied(j, i)) {
						Rover r = getRoverAtCell(j, i);
						if(r != null) {
							switch(r.getCardinalDirection()) {
							case NORTH: gridString += " ^ "; break;
							case SOUTH: gridString += " v "; break;
							case WEST: gridString += " < "; break;
							case EAST: gridString += " > "; break;
							}
						}
					}
					else {
						gridString += " o ";
					}
				}
				gridString += "\n";
			}
		}
		else {
			return "No plateau has not yet been established. Please establish it before running other commands. You do so by entering two numbers, indicating the dimension";
		}
		
		//Finally, print the status of all rovers present in the simulation
		if(rovers != null) {
			int i = 1;
			for(Rover r : rovers) {
				gridString += "\n Rover " + Integer.toString(i++)+ ": " + r.getX() + " " + r.getY() + " " + r.getCardinalDirection().toString();
			}
		}
		return gridString;
	}

	//getRoverAtCell is created to be able to fetch the rover of each cell, to be able to display its cardinal direction
	private Rover getRoverAtCell(int x, int y) {
		if(rovers == null) {
			return null;
		}
		else {
			for(Rover r : rovers) {
				if(r.getX() == x && r.getY() == y) {
					return r;
				}
			}
		}
		return null;
	}
}


