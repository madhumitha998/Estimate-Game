// import java.util.Scanner;
// import gamelogic.*;


// public class GameMenu {
//     private GameLogic gameLogic;

//     public GameMenu() {
//         this.gameLogic = new GameLogic();
//     }

//     public void display() {
//         System.out.println("1. Start New Game");
//         System.out.println("2. Start New Game Anyway");
//         System.out.println("3. Quit Application");
//         System.out.print("Please enter your choice:");
//     }

//     public void readOption() {
//         Scanner sc = new Scanner(System.in);
//         int choice;
//         do {
//             display();
//             choice = sc.nextInt();
//             switch (choice) {
//                 case 1:
//                     startNewGame();
//                     break;
//                 case 2:
//                     startNewGame();
//                     break;
//                 case 3:
//                     QuitGame();
//                     break;
//                 default:
//                     System.out.println("Enter a choice between 1 to 3");
//             }
//         } while (choice != 2);
//     }

//     public void startNewGame() { 
//         // Create a score variable that stores the overall current score in the game
//         // Returns the table hand so the front end can display it to the user 
//         ArrayOfPlayers newTableHand = gameLogic.startNewGame();
//         Winner winner;

//         //FE Code to display the table with the data from newTableHand


//         // While round != 11
//             // gameLogic.getRound()
//             // Use the round to calculate how many tricks there are
//             // While tricks are not played finished
//                 // Wait for input from user from the FE
//                 // TableHand trickResults = gameLogic.getTrickResults()
//                 // FE Displayes the trickResults
//                 // Card getWinner = gameLogic.getWinner(TableHand)
//                 // FE displays the winner

//                 // Score currentScore = gameLogic.currentScore()
//                 // FE Updates the currentScore

//             // winner = gameLogic.getWinner()
//             // if not null, break out of while loop

//             // gameLogic.addRound()
        
//         // Winner winner = gameLogic.getWinner() (Winner contains score, player)
//         // FE Display COngratulatory Screen for Winner
//         // FE Prompts to ask for starting a new game




//     }
// }