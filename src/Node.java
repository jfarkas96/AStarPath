public class Node implements Comparable<Node>{

	private int f, g, h, type, x, y;
	private Node parent;
	private boolean isStart, isEnd, hasGSet;

	public Node(int t){
		type = t;
		parent = null;
		isStart = false;
		isEnd = false;
		hasGSet = false;
		//type 0 is traverseable, 1 is not
	}

	//mutator methods to set values
	public void setF(){
		f = g + h;
	}
	public void setG(int value){
		g = value;
		hasGSet=true;
	}
	public void setH(int value){
		h = value;
	}
	public void setX(int value){
		x = value;
	}
	public void setY(int value){
		y = value;
	}
	public void setParent(Node n){
		parent = n;
	}
	public boolean setAsStart(){
		if (type==0){
			isStart = true;
			System.out.println("Starting node set.");
			System.out.println();
			return true;
		} else {
			System.out.println("Unable to set node as start. Please choose a non-blocked off node");
			return false;
		}
	}
	public boolean setAsEnd(){
		if (type==0){
			isEnd = true;
			System.out.println("Finish node set.");
			System.out.println();
			return true;
		} else {
			System.out.println("Unable to set node as finish. Please choose a non-blocked off node that is not the starting node");
			return false;
		}
	}

		//accessor methods to get values
		public int getF(){
			return f;
		}
		public int getG(){
			return g;
		}
		public int getH(){
			return h;
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		public int getType(){
			return type;
		}
		public Node getParent(){
			return parent;
		}
		public boolean isStart(){
			return isStart;
		}
		public boolean isEnd(){
			return isEnd;
		}
		public boolean isGSet(){
			return hasGSet;
		}
		public void clearStart(){
			isStart = false;
		}
		public void clearFinish(){
			isEnd = false;
		}
		
		public boolean isOpen(){
			if (type==0){
				return true;
			} else {
				return false;
			}
		}

		public String toString(){
			if (isStart()){
				return "S";
			} else if (isEnd()){
				return "F";
			} else if (type==0){
				return "O";
			} else if (type==1){
				return "X";
			} else {
				return "";
			}
		}
		
		@Override
	    public int compareTo(Node otherNode) {
			if (this.getF() < otherNode.getF()){
				return -1;
			} else if (this.getF() == otherNode.getF()) {
				return 0;
			} else {
				return 1;
			}
		}
		
		
	}
