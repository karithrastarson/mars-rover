# Implementation and how to run

The construction of the classes is inspired by [the problem description](https://github.com/karithrastarson/mars-rover/blob/master/PROBLEM_DESCRIPTION.md). 
The nouns were considered as classes while verbs were considered as methods.
As the project progressed, the more complex logic was extracted to helper functions to make the code more accessible. 

The program is executed by running the main method in file [/marsrover/src/marsrover/Main.java](https://github.com/karithrastarson/mars-rover/blob/master/marsrover/src/marsrover/Main.java)

# Classes and structure
## Rover

The Rover class represents the Mars Rover, the vehicle. The only thing that the vehicle should be concerned with in this model, 
is its location and orientation. The class has the following variables:

* int X: The x coordinates in the grid system
* int Y: The y coordinates in the grid system
* Cardinal Direction: North, South, West, East. Represents where the vehicle is headed.

Furthermore, the class has methods to manipulate its variables. Those methods are.

* Turn left: Changes the cardinal direction. North becomes West. West becomes South, etc.
* Turn right: Changes the cardinal direction. North becomes East. East becomes South, etc.
* Move forward: Changes the coordinates of the vehicle. 

## Plateau

The plateau is the platform that the mars rovers will be landing and navigating on. 
The plateau is represented with a two dimensional array of boolean values. Each cell has value _true_ or _false_.
If the value is _true_ it means that this cell in the grid is occupied by a vehicle. That is the only information
that the plateau keeps track of, in addititon to its dimensions. 

The plateau is established by the user. The user feeds the controller two integers, which represents the coordinates of the top
right cell of the grid. That input is used to establish the data structure.

## Controller

The controller is the brain of the system. The controller has all the information required to drive the project forward.
The controller has the following variables:

* Rovers: A list of rovers present in the simulation
* Plateau: A plateau established in the simulation.
* lastEstablishedRover: That is the last rover that the user established, and is currently operating on. 
In this version of the program, the user is only able to feed control strings to the last established rover.

### Input processing by the controller

The controller is fed an input string in each cycle of the simulation. 
The controller performs the following actions on the string to determine what to do:

1. Cast the input string to uppercase, to make sure the input is not case sensitive. 

2. Check whether the string contains any spaces. If the input is just a long string without spaces,
then this is handled as a control input, and is sanitized as such.

3. If it reaches this stage, that means the input string contains spaces. Then the string is split by the spaces.
If the result is three substrings, then the input is handled as establishment of a new rover, and the input is sanitized accordingly.

4. At last, if the result of the string split is two substring then this is handled as the establishment of a new plateau.

5. If none of the criteria above was met, then this input is ignored and the user informed that his input was invalid.

### Key methods of the controller

The controller is made up of some key methods. One is the _processInput_ which was described shortly above. Others include:

* moveRover(String s, Rover r): This takes the control string (s) and applies on the rover r. Each character of the string
is read, where 'L' makes the rover turn left, 'R' makes the rover turn right and 'M' makes the rover move forward. 
This function calls a helper function called _nextMoveOffGrid_ which returns a boolean value _true_ if the rover is placed
at the boundary of the grid, and is attempting to move accross it. When this is attempted the controller ignores the command
from the user.

* createRover(int x, int y, CardinalDirection dir): This creates a new instances of a rover, at a location (x,y) in the grid,
but conditional statmement makes sure that the rover is not created outside the established grid. The rover is then added to a 
list of rovers, but that list is a class variable and allows the controller to keep track of all rovers in the simulation.
Furthermore it updates the established plateau, and informs it that one of its cell is now occupied.

* displayGrid(): The current command line version of this program prints the output on the terminal window. This function
was created to give a clear image of the status of the system. It prints out all cells of the two dimensional plateau. An 'o' 
indicates that the cell is empty, but an arrow, pointing to one of the cardinal direction, if the cell is occupied by a rover.
This makes navigating the rovers easier for the user. Furthermore, at the bottom of the output at list of the vehicles is printed,
as well as their coordinates and cardinal direction.

## Main

The main function is the function that runs the simulation. It runs an endless loop of the following:

1. Prompt the user for input

2. Make the controller process the input

3. Output the status of the system, provided by the controller.

# Next steps, ideas.

The next steps of the project could be:

* Unit tests and proper QA structure.

* Establish a front-end that would make the simulation more visual

* Operate multiple rovers per simulation, not just the latest one launched.

* Handle interaction between rovers, to avoid crashes in the grid system.

* Establish further boundaries on the simulation. For example, what should be the maximum number of rovers in a simulation?
