import java.util.*;
//Contributions by Dillon M
public class knucklebones {
    //declaring the variables, 2D matrix and Scanner
    static int[][] boardStatus = new int[6][3]; 
    static int selectedX,selectedY,lastRoll,playerOneScore,playerTwoScore;
    static boolean gameOver,playerOneTurn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        //while the game isnt over, do this
        setupVar();
        while (!gameOver){
            knucklebones.RenderBoard();
            knucklebones.doGame();
            playerOneTurn = !playerOneTurn;
        }
        autoScorer();
    
    }
    public static void setupVar(){
        gameOver = false;
        playerOneTurn = true;
    }
    //renders the board by using the 2D matrix
    public static void RenderBoard(){

        lastRoll = (int)(Math.random() * 6 + 1);

        for (int i = 1; i <=12; i++) { System.out.println(); }

        for (int y = 1; y <= 3; y++){
            
            for (int x = 1; x <= 6; x++) {
                
                if (selectedX == x - 1 && selectedY == y) System.out.print(">");
                else System.out.print(" ");

                if (boardStatus[x-1][y-1] == 0) System.out.print("▇"); else 
                System.out.print(boardStatus[x-1][y-1]);
                
                if (selectedX == x -1  && selectedY == y) System.out.print("<");
                else System.out.print(" ");

            }
            System.out.println();
        } 
        System.out.println();
        System.out.println();
        System.out.println("      " + lastRoll);
        System.out.println();
        
    }
    //checks if the row is empty
    public static int checkRowSpace(int r){

        if(playerOneTurn){
            for(int i = 0; i < 3; i++){
                if(boardStatus[i][r] == 0){
                    System.out.println(i);
                    return i;
                }
            }
        }
        else{
            for(int i = 0; i < 3; i++){
                if(boardStatus[i+3][r] == 0){
                    System.out.println(i);
                    return i+3;
                }
            } 
        }
        return -1;
    }
    //does the game processes and rules
    public static void doGame(){

        boolean complete = false;
        // Takes an input Y coord from 1-3
        while(!complete){

            while (!complete){
                    if(playerOneTurn) System.out.print("Player one, ");
                    else System.out.print("Player two, ");
                    System.out.println("please input the y coordinate");
                    String inputY = sc.nextLine();
                if(inputY.equals("1")){
                    selectedY = 1;
                    break;
                }
                else if(inputY.equals("2")){
                    selectedY = 2;
                    break;
                }
                else if(inputY.equals("3")){
                    selectedY = 3;
                    break;
                }
            }
            int temp = checkRowSpace(selectedY - 1);
            if(temp != -1){
                selectedX = temp;
                System.out.println(selectedX);
                break;
            }
        }

        boardStatus[selectedX][selectedY - 1] = lastRoll;
        // Clear opponent's numbers
        if(playerOneTurn) {
            for (int i = 0; i <3; i++){
                if (lastRoll == boardStatus[i + 3][selectedY - 1]){
                    boardStatus[i + 3][selectedY - 1] = 0;
                }
            }
        } else {
        for (int i = 0; i <3; i++){
            if (lastRoll == boardStatus[i][selectedY -1 ]){
                boardStatus[i][selectedY -1 ] = 0;

            }
        }
    }
    // Game end condition
    if (checkRowSpace(0) + checkRowSpace(1) + checkRowSpace(2) == -3) gameOver = true;
    }
    //Calculates the points per row
    public static int rowScorer(int r){
        int one,two,three,total = 0;

        if (playerOneTurn){
            one = boardStatus[0][r - 1];
            two = boardStatus[1][r - 1];
            three = boardStatus[2][r - 1];
        } else {
            one = boardStatus[3][r - 1];
            two = boardStatus[4][r - 1];
            three = boardStatus[5][r - 1];
        }
        
        if (one == two && two == three){
            total = one * 9;
        } else if (one == two){
            total = one * 4 + three;
        } else if (one == three) {
            total = one * 4 + two;
        } else if (two == three) {
            total = one + two * 4;
        } else {
            total = one + two + three;
        }
        return total;
    }
    //the greatest scoring algorithm to ever exist
    public static void autoScorer(){
        int total = 0;
        
        playerOneTurn = true;
        for (int i = 1; i <= 3; i++) {
            total += rowScorer(i);
        }
        playerOneScore = total;

        playerOneTurn = false;
        total = 0;
        
        for (int i = 1; i <= 3; i++) {
            total += rowScorer(i);
        }
        playerTwoScore = total;

        System.out.println("Player One: " + playerOneScore);
        System.out.println("Player Two: " + playerTwoScore);
    }
}
