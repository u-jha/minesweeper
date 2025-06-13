// Import Section
//jha00031
import javax.sound.midi.Soundbank;
import java.util.Random;

public class Minefield {
    /**
    Global Section
    */
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /* 
     * Class Variable Section
     * 
    */
    private Cell [][] minefield;
    private boolean gameOver = false;
    private int mines;

    /*Things to Note:
     * Please review ALL files given before attempting to write these functions.
     * Understand the Cell.java class to know what object our array contains and what methods you can utilize
     * Understand the StackGen.java interface to know what type of stack you will be working with and methods you can utilize
     * Understand the QGen.java interface to know what type of queue you will be working with and methods you can utilize
     */
    
    /**
     * Minefield
     * 
     * Build a 2-d Cell array representing your minefield.
     * Constructor
     * @param rows       Number of rows.
     * @param columns    Number of columns.
     * @param flags      Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        minefield = new Cell[rows][columns];
        mines  =  flags;

        for (int i =0; i<minefield.length; i++){
            for (int j=0; j<minefield[0].length; j++){
                minefield[i][j] = new Cell(false, "0"); //initializing all
            }
        }
    }

    /**
     * evaluateField
     * 
     *
     * @function:
     * Evaluate entire array.
     * When a mine is found check the surrounding adjacent tiles. If another mine is found during this check, increment adjacent cells status by 1.
     * 
     */
    public void evaluateField() {
        for (int row = 0; row < minefield.length; row++) {
            for (int col = 0; col < minefield[0].length; col++) {
                if (minefield[row][col].getStatus().equals("M")) {
                    //adds to bottom three

                    if (row < minefield.length - 1) {
                        if (!minefield[row + 1][col].getStatus().equals("M")) {
                            int val = Integer.parseInt(minefield[row + 1][col].getStatus());
                            val++;
                            minefield[row + 1][col].setStatus(val + "");
                        }

                        if (col < minefield[0].length - 1) { //adds to bottom right
                            if (!minefield[row + 1][col + 1].getStatus().equals("M")) {
                                int val = Integer.parseInt(minefield[row + 1][col + 1].getStatus());
                                val++;
                                minefield[row + 1][col + 1].setStatus(val + "");
                            }
                        }

                        if (0 < col) { //adds to bottom left
                            if (!minefield[row + 1][col - 1].getStatus().equals("M")) {
                                int val = Integer.parseInt(minefield[row + 1][col - 1].getStatus());
                                val++;
                                minefield[row + 1][col - 1].setStatus(val + "");
                            }
                        }
                    }


                    //adds to top
                    if (0<row) {
                        if (!minefield[row - 1][col].getStatus().equals("M")) {
                            int val = Integer.parseInt(minefield[row - 1][col].getStatus());
                            val++;
                            minefield[row - 1][col].setStatus(val + "");
                        }

                        if (col < minefield[0].length - 1) { //adds to top right
                            if (!minefield[row - 1][col + 1].getStatus().equals("M")) {
                                int val = Integer.parseInt(minefield[row - 1][col + 1].getStatus());
                                val++;
                                minefield[row - 1][col + 1].setStatus(val + "");
                            }
                        }

                        if (0 < col) { //adds to top left
                            if (!minefield[row - 1][col - 1].getStatus().equals("M")) {
                                int val = Integer.parseInt(minefield[row - 1][col - 1].getStatus());
                                val++;
                                minefield[row - 1][col - 1].setStatus(val + "");
                            }
                        }
                    }

                    if (col<minefield[0].length-1) { //adds to right
                        if (!minefield[row][col + 1].getStatus().equals("M")) {
                            int val = Integer.parseInt(minefield[row][col + 1].getStatus());
                            val++;
                            minefield[row][col + 1].setStatus(val + "");
                        }
                    }

                    if (0<col){ //adds to left
                        if (!minefield[row][col-1].getStatus().equals("M")) {
                            int val = Integer.parseInt(minefield[row][col-1].getStatus());
                            val++;
                            minefield[row][col-1].setStatus(val+"");
                        }
                    }

                }
            }
        }
    }

    /**
     * createMines
     * 
     * Randomly generate coordinates for possible mine locations.
     * If the coordinate has not already been generated and is not equal to the starting cell set the cell to be a mine.
     * utilize rand.nextInt()
     * 
     * @param x       Start x, avoid placing on this square.
     * @param y        Start y, avoid placing on this square.
     * @param mines      Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        while (mines>0){
            Random rand = new Random(); //random!
            int row = rand.nextInt(0,minefield.length);
            int col = rand.nextInt(0,minefield[0].length);
            if (row!=x && col !=y && !minefield[row][col].getStatus().equals("M") && minefield[row][col].getRevealed()==false){
                minefield[row][col].setStatus("M");
                mines--; //and we're incrementing now slay
            }
        }
    }

    /**
     * guess
     * 
     * Check if the guessed cell is inbounds (if not done in the Main class). 
     * Either place a flag on the designated cell if the flag boolean is true or clear it.
     * If the cell has a 0 call the revealZeroes() method or if the cell has a mine end the game.
     * At the end reveal the cell to the user.
     * 
     * 
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     * @param flag    A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        if (x<0 || x>=minefield.length || y<0 || y>=minefield[0].length)
            return false; //not in bounds booo
        if (flag){ //setting the mine to a flag
            minefield[x][y].setStatus("F");
            minefield[x][y].setRevealed(true);
            mines--;
            return false;
        }
        if (minefield[x][y].getStatus().equals("M")){ //mine that you did NOT flag bc the user sucks at minefield
            gameOver=true;
            return true;
        }
        if (minefield[x][y].getStatus().equals("0")){
            this.revealZeroes(x,y);
            return false;
        }
        minefield[x][y].setRevealed(true);
        return false;
    }

    /**
     * gameOver
     * 
     * Ways a game of Minesweeper ends:
     * 1. player guesses a cell with a mine: game over -> player loses
     * 2. player has revealed the last cell without revealing any mines -> player wins
     * 
     * @return boolean Return false if game is not over and squares have yet to be revealed, otherwise return true.
     */
    public boolean gameOver() {
        return gameOver;

    }

    /**
     * Reveal the cells that contain zeroes that surround the inputted cell.
     * Continue revealing 0-cells in every direction until no more 0-cells are found in any direction.
     * Utilize a STACK to accomplish this.
     *
     * This method should follow the psuedo-code given in the lab writeup.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x      The x value the user entered.
     * @param y      The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
    Stack1Gen<int[]> stack = new Stack1Gen<>();
    minefield[x][y].setRevealed(true);
    stack.push(new int [] {x,y});
    while (!stack.isEmpty()){
        int[] top = stack.pop();
        int topX = top[0];
        int topY = top[1];

        if (topX<minefield.length-1) { //checks bottom
            if (minefield[topX+1][topY].getStatus().equals("0") && !minefield[topX+1][topY].getRevealed()){
                minefield[topX+1][topY].setRevealed(true);
                stack.push(new int[]{topX+1, topY});
            }
            if (topY<minefield[0].length-1){ //checks bottom right
                if (minefield[topX+1][topY+1].getStatus().equals("0") && !minefield[topX+1][topY+1].getRevealed()){
                    minefield[topX+1][topY+1].setRevealed(true);
                    stack.push(new int[]{topX+1, topY+1});
                }
            }

            if (0<topY){ //checks bottom left
                if (minefield[topX+1][topY-1].getStatus().equals("0") && !minefield[topX+1][topY-1].getRevealed()){
                    minefield[topX+1][topY-1].setRevealed(true);
                    stack.push(new int[]{topX+1, topY-1});
                }
            }
        }

        if (0<topX) { //checks top
            if (minefield[topX-1][topY].getStatus().equals("0")  && !minefield[topX-1][topY].getRevealed()){
                minefield[topX-1][topY].setRevealed(true);
                stack.push(new int[]{topX-1, topY});
            }

            if (topY<minefield[0].length-1){ //checks top right
                if (minefield[topX-1][topY+1].getStatus().equals("0") && !minefield[topX-1][topY+1].getRevealed()){
                    minefield[topX-1][topY+1].setRevealed(true);
                    stack.push(new int[]{topX-1, topY+1});
                }
            }

            if (0<topY){ //checks top right
                if (minefield[topX-1][topY-1].getStatus().equals("0") && !minefield[topX-1][topY-1].getRevealed()){
                    minefield[topX-1][topY-1].setRevealed(true);
                    stack.push(new int[]{topX-1, topY-1});
                }
            }
        }

        if (topY<minefield[0].length-1){ //checks the sides
            if (minefield[topX][topY+1].getStatus().equals("0") && !minefield[topX][topY+1].getRevealed() ){
                minefield[topX][topY+1].setRevealed(true);
                stack.push(new int[]{topX, topY+1});
            }
        }
        if (0<topY){
            if (minefield[topX][topY-1].getStatus().equals("0") && !minefield[topX][topY-1].getRevealed()){
                minefield[topX][topY-1].setRevealed(true);
                stack.push(new int[]{topX, topY-1});
            }
        }
    }
    }

    /**
     * revealStartingArea
     *
     * On the starting move only reveal the neighboring cells of the initial cell and continue revealing the surrounding concealed cells until a mine is found.
     * Utilize a QUEUE to accomplish this.
     * 
     * This method should follow the psuedo-code given in the lab writeup.
     * Why might a queue be useful for this function?
     *
     * @param x     The x value the user entered.
     * @param y     The y value the user entered.
     */
    public void revealStartingArea(int x, int y) {
        Q1Gen<int[]> queue = new Q1Gen<>();
        minefield[x][y].setRevealed(true);
        queue.add(new int [] {x,y});
        while (queue.length()>0){
            int [] curr = queue.remove();
            int currX = curr[0];
            int currY = curr[1];
            if (minefield[currX][currY].getStatus().equals("M")){
                break;
            }
            minefield[currX][currY].setRevealed(true);


            if (currX<minefield.length-1) { //adds bottom
                if (!minefield[currX+1][currY].getRevealed()){
                    queue.add(new int[]{currX+1, currY});
                }
                if (currY<minefield[0].length-1){ //adds bottom right
                    if (!minefield[currX+1][currY+1].getRevealed()){
                        queue.add(new int[]{currX+1, currY+1});
                    }
                }

                if (0<currY){ //checks bottom left
                    if (!minefield[currX+1][currY-1].getRevealed()){
                        queue.add(new int[]{currX+1, currY-1});
                    }
                }
            }

            if (0<currX) { //checks top
                if (!minefield[currX-1][currY].getRevealed()){
                    queue.add(new int[]{currX-1, currY});
                }

                if (currY<minefield[0].length-1){ //checks top right
                    if (!minefield[currX-1][currY+1].getRevealed()){
                        queue.add(new int[]{currX-1, currY+1});
                    }
                }

                if (0<currY){ //checks top right
                    if (!minefield[currX-1][currY-1].getRevealed()){
                        queue.add(new int[]{currX-1, currY-1});
                    }
                }
            }

            if (currY<minefield[0].length-1){
                if (!minefield[currX][currY+1].getRevealed()){
                    queue.add(new int[]{currX, currY+1});
                }
            }
            if (0<currY){
                if (!minefield[currX][currY-1].getRevealed()){
                    queue.add(new int[]{currX, currY-1});
                }
            }


        }
    }

    /**
     * For both printing methods utilize the ANSI color codes provided! 
     * 
     * 
     * 
     * 
     * 
     * debug
     *
     * @function This method should print the entire minefield, regardless if the user has guessed a square.
     * This method should print out when debug mode has been selected. It is very similar to the toString method below.
     */
    public void debug() {
        System.out.print(" ");
        for (int col = 0; col<minefield[0].length; col++)  //prints grid numbers
            System.out.print(" " + col);
        System.out.println();
        for (int row=0; row<minefield.length; row++){
            System.out.print(row + " "); //prints grid numbers
            for (int col=0; col<minefield[0].length;col++){
                    String color = "";
                    if (minefield[row][col].getStatus().equals("1")) {color = ANSI_CYAN;}
                    else if (minefield[row][col].getStatus().equals("2")) {color = ANSI_PURPLE;}
                    else if (minefield[row][col].getStatus().equals("M")) {color = ANSI_RED_BRIGHT;}
                    else if (minefield[row][col].getStatus().equals("3")) {color = ANSI_BLUE;}
                    else if (minefield[row][col].getStatus().equals("4")) {color = ANSI_GREEN;}
                    else if (minefield[row][col].getStatus().equals("5")) {color = ANSI_BLUE_BRIGHT;}
                    else if (minefield[row][col].getStatus().equals("F")) {color = ANSI_YELLOW;}
                    else if (minefield[row][col].getStatus().equals("6")) {color = ANSI_RED;}
                    else if (minefield[row][col].getStatus().equals("7")) {color = ANSI_CYAN;}
                    else if (minefield[row][col].getStatus().equals("8")) {color = ANSI_PURPLE;}
                    System.out.print(color + minefield[row][col].getStatus() + ANSI_GREY_BACKGROUND + " ");
            }
            System.out.println();
        }
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    @Override
    public String toString() {
        String str = " ";
        for (int col=0; col<minefield[0].length;col++){
            str+=" "+ col;
        }
        str+="\n";
        for (int row=0; row<minefield.length; row++){
            str+= row + " ";
            for (int col=0; col<minefield[0].length;col++){
                if (minefield[row][col].getRevealed()==true){
                    String color = "";
                    if (minefield[row][col].getStatus().equals("1")) {color = ANSI_CYAN;}
                    else if (minefield[row][col].getStatus().equals("2")) {color = ANSI_PURPLE;}
                    else if (minefield[row][col].getStatus().equals("M")) {color = ANSI_RED_BRIGHT;}
                    else if (minefield[row][col].getStatus().equals("3")) {color = ANSI_BLUE;}
                    else if (minefield[row][col].getStatus().equals("4")) {color = ANSI_GREEN;}
                    else if (minefield[row][col].getStatus().equals("5")) {color = ANSI_BLUE_BRIGHT;}
                    else if (minefield[row][col].getStatus().equals("F")) {color = ANSI_YELLOW;}
                    else if (minefield[row][col].getStatus().equals("6")) {color = ANSI_RED;}
                    else if (minefield[row][col].getStatus().equals("7")) {color = ANSI_CYAN;}
                    else if (minefield[row][col].getStatus().equals("8")) {color = ANSI_PURPLE;}
                    str+= color + minefield[row][col].getStatus() + ANSI_GREY_BACKGROUND + " ";
                }
                else
                    str+="- ";
            }
            str+="\n";
        }
        return str;
    }
    public int getMines(){
        return mines;
    }

    public boolean correctBoard(){ //wanting to check if flags are in correct positions at end of game
        for (int row=0; row<minefield.length; row++){
            for (int col=0; col<minefield[0].length; col++){
                if (minefield[row][col].getStatus().equals("M"))
                    return false;
            }
        }
        return true;
    }
}
