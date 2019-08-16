import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		boolean finished = false;
		
		while (!finished) {
			board.printBoard();
			board.setStartNode();
			board.setFinishNode();
			board.findPath();
			Scanner s = new Scanner(System.in);
			int n;
			System.out.println("Enter 1 if you would like to chose a new start and end node or any other number if you'd like to quit.");
			n = s.nextInt();
			System.out.println();
			if (n==1){
				finished = false;
			} else {
				finished = true;
				System.out.println("Program Terminated.");
			}
			board.getStart().clearStart();
			board.getFinish().clearFinish();
			board.resetPathBoard();
		}
	}
}
