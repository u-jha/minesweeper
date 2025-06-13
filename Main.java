//Import Section
//jha00031
import java.util.Random;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        Scanner snum = new Scanner(System.in);
        System.out.println("Chose a game leve: easy, medium, or hard.");
        String level = s.nextLine();
        int row = 0;
        int col = 0;
        int flags = 0;
        if (level.equals("easy")){
            row = 5;
            col = 5;
            flags = 5;
        }
        else if (level.equals("medium")){
            row = 9;
            col = 9;
            flags = 12;
        }
        else if (level.equals("hard")){
            row = 20;
            col = 20;
            flags = 0;
        }

        Minefield minefield = new Minefield(row,col,flags);
        System.out.println("Here's what the playing field looks like. \n" + minefield);
        System.out.println("Chose starting coord. Enter row:");
        int r1 = snum.nextInt();
        System.out.println("Enter col");
        int c1 = snum.nextInt();
        minefield.createMines(r1,c1,flags);
        minefield.evaluateField();
        minefield.revealStartingArea(r1, c1);



        while(!minefield.gameOver() && minefield.getMines()>0){
            System.out.println("Get ready to make a choice. Debug Mode on? [yes or press any key]");
            String debug = s.nextLine();
            System.out.println("Here's your playing field:");
            if (debug.equals("yes"))
                minefield.debug();
            else
                System.out.println(minefield);

            System.out.println("What's your move? Enter row");
            int r = snum.nextInt();
            System.out.println("Enter col");
            int c = snum.nextInt();
            System.out.println("Placing flag or nah? [f/any key]");
            boolean place = false;
            if (s.nextLine().equals("f"))
                place = true;
            minefield.guess(r, c, place);
        }

        if (minefield.gameOver()){
            System.out.println("so yeah you hit a mine ur done.");
        }
        else if (minefield.correctBoard())
            System.out.println("YOU GOT ALL THE MINES LET'S GO!!!");
        else{
            System.out.println("you used up all ur flags and got one wrong wow. Here's the actual board: " + minefield);

        }

    }
    
    
}
