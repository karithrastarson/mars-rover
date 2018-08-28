package com.mars.objects;

/*
 * Created by Kari Thrastarson on 27-08-2018
 * 
 * This class represents the platform that the rover will be landing on and navigating on.
 * The platform is represented with a grid.
 * The class takes two integers as parameters that are the dimensions of the grid.
 * The only information that the plateau maintains is whether a cell is occupied or not.
 * The controller (com.mars.controllers.Controller) will make sure to update that information, so the grid is up to date.
 * 
 * */

public class Plateau {
	
	private boolean[][] grid;
	private int rows;
	private int cols;
	
	//Constructor - Takes two parameters, to indicate the dimension of the grid to establish
	public Plateau(int row, int col) {
		rows = row + 1;
		cols = col + 1;
		
		grid = new boolean[rows][cols];
		
		//Initialize the grid with false values, meaning that the grid does not contain any rovers
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j] = false;
			}
		}
	}

	//GETTERS
	public boolean[][] getGrid() {
		return grid;
	}
	
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	
	//Is a rover parked in this particular cell?
	public boolean isCellOccupied(int x, int y) {
		return grid[x][y];
	}
	
	//Indicate that a rover is parked in this cell
	public void occupyCell(int x, int y) {
		grid[x][y] = true;
	}
	
	//Indicate that a rover has moved from this cell
	public void clearCell(int x, int y) {
		grid[x][y] = false;
	}
}
