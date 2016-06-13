import java.util.ArrayList;


public class Node {

    Node north;
    Node south;
    Node east;
    Node west;

    Node cameHereFrom;
    Node parent = null;

    int x;
    int y;

    int gScore;
    int hScore;

    boolean visited;
    boolean solution = false;

    boolean visitedNorth;
    boolean visitedSouth;
    boolean visitedEast;
    boolean visitedWest;

    public Node(int x, int y){
        this.x = x;
        this.y = y;

        this.visited = false;

        visitedNorth = false;
        visitedSouth = false;
        visitedEast = false;
        visitedWest = false;
    }

    public int calculateHScore(Node n){
        return Math.abs(this.x - n.x) + Math.abs(this.y - n.y);
    }

    public int calculateFScore(){
        return gScore + hScore;
    }

    public String topToString(){

        StringBuilder sb = new StringBuilder();

        sb.append("X ");

        if(this.visitedNorth){
            sb.append("  ");
        }else {
            sb.append("X ");
        }

        sb.append("X ");

        return sb.toString();
    }

    public String midToString(){

        StringBuilder sb = new StringBuilder();

        if(visitedWest){
            sb.append("  ");
        }
        else sb.append("X ");

        sb.append("  ");

        if(visitedEast) {
            sb.append("  ");
        }
        else sb.append("X ");
        return sb.toString();
    }

    public String bottomToString(){

        StringBuilder sb = new StringBuilder();

        sb.append("X ");

        if(visitedSouth) sb.append("  ");
        else sb.append("X ");

        sb.append("X ");

        return sb.toString();
    }

    public ArrayList<Node> validNodes(){
        ArrayList<Node> list = new ArrayList<>();

        if(this.north != null && this.north.visited == false){
            list.add(this.north);
        }
        if(this.south != null && this.south.visited == false){
            list.add(south);
        }
        if(this.east != null && this.east.visited == false){
            list.add(this.east);
        }
        if(this.west != null && this.west.visited == false){
            list.add(this.west);
        }

        return list;
    }

    public void setParent(Node parent){
        this.parent = parent;
        this.gScore = parent.gScore + 10;
    }

    public void buildPath(ArrayList<Node> finalPath){
        finalPath.add(0, this);
        if(parent == null)
            return;

        parent.buildPath(finalPath);
    }

    public ArrayList<Node> getAdjacentNodes(){

        ArrayList<Node> valid = new ArrayList<>();

        if(north != null && visitedNorth){
            valid.add(north);
        }
        if(south != null && visitedSouth){
            valid.add(south);
        }
        if(east != null && visitedEast){
            valid.add(east);
        }
        if(west != null && visitedWest){
            valid.add(west);
        }

        return valid;
    }
}
