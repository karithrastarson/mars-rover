package com.mars.objects;

import com.mars.tools.CardinalDirection;

/*
 * Created by Kari Thrastarson on 27-08-2018
 * 
 * This class represents a robotic rover that is to be landed by NASA on a plateau on Mars.
 * The class contains variables that indicate the Rover's position and cardinal direction.
 * This class is for example utilized by com.mars.controllers.Controller
 * 
 * */
public class Rover {
	
	private int x; 
	private int y;
	private CardinalDirection cardinalDirection;
	
	//Constructor without parameters establishes the rover in position (0,0) facing North.
	public Rover() {
		x = 0;
		y = 0;
		cardinalDirection = CardinalDirection.NORTH;
	}
	
	//Constructor with parameters establishes the rover in position (x,y) facing one of the cardinal directions..
	public Rover(int x, int y, CardinalDirection dir) {
		this.x = x; 
		this.y = y;
		this.cardinalDirection = dir;
	}
	
	// This function applies changes to the cardinal direction, and moves it 90 degrees to the left.
	// So North turns to West. West to South etc.
	public void turnLeft() {
		switch(cardinalDirection) {
			case NORTH: cardinalDirection = CardinalDirection.WEST;
				break;
			case WEST: cardinalDirection = CardinalDirection.SOUTH;
				break;
			case SOUTH: cardinalDirection = CardinalDirection.EAST;
				break;
			case EAST: cardinalDirection = CardinalDirection.NORTH;
				break;
		}
	}
	
	//This function applies changes to the cardinal direction, and moves it 90 degrees to the right.
	//So North turns to East. East to South etc.
	public void turnRight() {
		switch(cardinalDirection) {
		case NORTH: cardinalDirection = CardinalDirection.EAST;
			break;
		case EAST: cardinalDirection = CardinalDirection.SOUTH;
			break;
		case SOUTH: cardinalDirection = CardinalDirection.WEST;
			break;
		case WEST: cardinalDirection = CardinalDirection.NORTH;
			break;
		}	
	}
	
	/*This function moves the rover one forward according to it's cardinal position
	 * that means: 
	 * Facing north: (x,y) -> (x, y+1)
	 * Facing east: (x,y) -> (x-1, y)
	 * Facing south: (x,y) -> (x, y-1)
	 * Facing west: (x,y) -> (x+1, y)
	 * */
	 
	public void moveForward() {
		switch(cardinalDirection) {
		case NORTH: y++;
			break;
		case EAST: x++;
			break;
		case SOUTH: y--;
			break;
		case WEST: x--;
			break;
		}
	}
	
	//GETTERS
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public CardinalDirection getCardinalDirection() {
		return cardinalDirection;
	}

}
