import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Board {
	private Node[][] board = new Node[15][15];
	private String[][] pathBoard = new String[15][15];
	private Node start = null;
	private Node current = null;
	private Node finish = null;
	private PriorityQueue<Node> openList = new PriorityQueue<Node>();
	private ArrayList<Node> closedList = new ArrayList<Node>();
	private Stack<Node> finalPath = new Stack<Node>();

	public Board() {
		for (int x=0; x<15; x++){
			for (int y=0; y<15; y++){

				int type;
				if (Math.random() < 0.1){
					type = 1;
				} else {
					type = 0;
				}

				board[x][y] = new Node(type);
				if (type == 1) {
					pathBoard[x][y] = "X";
				} else {
					pathBoard[x][y] = "O";
				}
				board[x][y].setX(x);
				board[x][y].setY(y);
			}
		}
	}

	public void printBoard() {
		for (int y=0; y<15; y++){
			for (int x=0; x<15; x++){
				System.out.print(board[x][y]);
				if (x<15) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printPathBoard() {
		for (int y=0; y<15; y++){
			for (int x=0; x<15; x++){
				System.out.print(pathBoard[x][y]);
				if (x<15) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public void setStartNode() {
		Scanner s = new Scanner(System.in);
		boolean xSet = false;
		boolean ySet = false;
		int y=0;
		int x=0;
		while (!xSet) {
			System.out.println("Please enter an x value for the Starting Node: ");
			x = s.nextInt();
			if (x<=14 && x>=0) {
				xSet = true;
			} else {
				System.out.println("Invalid input");
			}
		}
		while (!ySet) {
			System.out.println("Please enter an y value for the Starting Node: ");
			y = s.nextInt();
			if (y<=14 && y>=0) {
				ySet = true;
			} else {
				System.out.println("Invalid input");
			}
		}

		if (board[x][y].setAsStart()==false){
			this.setStartNode();
		}
		start = board[x][y];
		current = board[x][y];
	}

	public void setFinishNode() {
		Scanner s = new Scanner(System.in);
		boolean xSet = false;
		boolean ySet = false;
		int y=0;
		int x=0;
		while (!xSet) {
			System.out.println("Please enter an x value for the Finish Node: ");
			x = s.nextInt();
			if (x<=14 && x>=0) {
				xSet = true;
			} else {
				System.out.println("Invalid input");
			}
		}
		while (!ySet) {
			System.out.println("Please enter an y value for the Finish Node: ");
			y = s.nextInt();
			if (y<=14 && y>=0) {
				ySet = true;
			} else {
				System.out.println("Invalid input");
			}
		}

		if (x==start.getX() && y==this.start.getY()){
			System.out.println("Cannot set finish node at node that is starting node");
			this.setFinishNode();
		}

		if (board[x][y].setAsEnd()==false){
			this.setFinishNode();
		}
		finish = board[x][y];
	}

	public Node getStart() {
		return start;
	}

	public Node getCurrent() {
		return current;
	}

	public Node getFinish() {
		return finish;
	}

	public void findPath() {
		openList.add(start);
		start.setH(this.findH(board[start.getX()][start.getY()]));
		start.setF();
		start.setParent(null);
		current = openList.poll();
		boolean pathFound = this.reachedFinish();
		boolean noPath = false;
		while (!pathFound) {
			closedList.add(current);
			this.generateNeighbors();
			if (!openList.isEmpty()) {
				current = openList.poll();
				pathFound = this.reachedFinish();
			} else {
				System.out.println("There is no possible path between the nodes.");
				System.out.println();
				pathFound = true;
				noPath = true;
			}
		}
		if (!noPath) {
			System.out.println("Path Found!");
			this.printPath();
			System.out.println();
		}
	}

	public void printPath(){
		finalPath.push(current);
		while (current.getParent()!=null){
			finalPath.push(current.getParent());
			current = current.getParent();
		}
		while (!finalPath.isEmpty()){
			Node n = finalPath.pop();
			System.out.println("(" + n.getX() + "," + n.getY() + ")");
		}
		System.out.println();
		this.setPathBoard();
		this.printPathBoard();
	}
	
	public void setPathBoard() {
		Stack<Node> path = new Stack<Node>();
		current = finish;
		path.push(current);
		while (current.getParent()!=null){
			path.push(current.getParent());
			current = current.getParent();
		}
		while (!path.isEmpty()){
			Node n = path.pop();
			if (n == start) {
				pathBoard[n.getX()][n.getY()] = "S";
			} else if (n == finish) {
				pathBoard[n.getX()][n.getY()] = "F";
			} else {
				pathBoard[n.getX()][n.getY()] = "P";
			}
			
		}
	}
	
	public void resetPathBoard() {
		for (int x=0; x<15; x++){
			for (int y=0; y<15; y++) {
				if (board[x][y].getType() == 1) {
					pathBoard[x][y] = "X";
				} else {
					pathBoard[x][y] = "O";
				}
			}
		}
	}

	public int findH(Node n){
		return (Math.abs((n.getX()-finish.getX()))+Math.abs((n.getY()-finish.getY())))*10;
	}

	public int findG(Node n){
		int gFromCurrent;
		if (Math.abs((n.getX()-current.getX()))+Math.abs((n.getY()-current.getY()))>1){
			gFromCurrent = 14;
		} else if (Math.abs((n.getX()-current.getX()))+Math.abs((n.getY()-current.getY()))==1){
			gFromCurrent = 10;
		} else {
			gFromCurrent = 0;
		}
		if (n.isGSet() == true && n.getG()<=gFromCurrent+current.getG()){
			return n.getG();
		} else {
			if (n != start) {
				n.setParent(current);	
			}
			return gFromCurrent+current.getG();
		}
	}

	public void generateNeighbors(){
		for (int y=current.getY()-1; y<current.getY()+2; y++){
			for (int x=current.getX()-1; x<current.getX()+2; x++){
				if (x>=0 && x<15 && y>=0 && y<15){
					if (x!= current.getX() || y!= current.getY()){
						if (board[x][y].isOpen()){
							board[x][y].setG(this.findG(board[x][y]));
							board[x][y].setH(this.findH(board[x][y]));
							board[x][y].setF();
							if (openList.contains(board[x][y])) {
							} else {
								if (!closedList.contains(board[x][y])){
									openList.add(board[x][y]);
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean reachedFinish() {
		if (current.getX() == finish.getX() && current.getY() == finish.getY()) {
			return true;
		} else {
			return false;
		}
	}
}
