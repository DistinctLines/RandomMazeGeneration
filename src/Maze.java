import java.util.ArrayList;
import java.util.Random;

public class Maze {

    private Node[][] nodeArray;
    private ArrayList<Node> nodeList = new ArrayList<>();
    private ArrayList<Node> sol = new ArrayList<>();

    private boolean solved = false;

    private int x;
    private int y;

    private int totalNodes;
    private int visitedNodes;

    public Maze(int x, int y){
        this.x = x;
        this.y = y;

        this.totalNodes = x * y;
        this.visitedNodes = 0;

        this.nodeArray = new Node[x][y];

        initNodes();
        setNeighbors();
        generateMaze(nodeArray[0][0]);
        aStarPathfinding(nodeArray[0][0], nodeArray[x - 1][y - 1]);
    }

    private boolean allVisited(){
        for(Node n: nodeList){
            if(n.visited == false) return false;
        }
        return true;
    }

    private void initNodes(){
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                Node node = new Node(i, j);
                nodeArray[i][j] = node;
                nodeList.add(node);
            }
        }
    }

    private void setNeighbors(){
        for(Node n: nodeList){
            n.north = getNodeBasedLoc(n.x, n.y - 1);
            n.south = getNodeBasedLoc(n.x, n.y + 1);
            n.east = getNodeBasedLoc(n.x + 1, n.y);
            n.west = getNodeBasedLoc(n.x - 1, n.y);
        }
    }

    private Node getNodeBasedLoc(int x, int y){
        for(Node n : nodeList){
            if(n.x == x && n.y == y){
                return n;
            }
        }

        return null;
    }

    private void generateMaze(Node n){

        if(n == nodeArray[x - 1][y - 1])
            solved = true;

        if(!solved){
            n.solution = true;
            sol.add(n);
        }

        ArrayList<Node> validNodes = n.validNodes();

        if (allVisited()) {// Base case. If all nodes have been visited it
            // creates the entrance at 0,0 and exit at n,m
            nodeArray[x - 1][y - 1].visitedSouth = true;
            nodeArray[0][0].visitedNorth = true;
            return;// then breaks out of method.
        }

        Random rand = new Random();
        n.visited = true;
        visitedNodes = visitedNodes + 1;

        if(validNodes.size() == 0){
            generateMaze(n.cameHereFrom);

            if(!solved){
                sol.remove(n);
            }

            visitedNodes = visitedNodes-1;
            return;
        }
        else {
            int index = rand.nextInt(validNodes.size());

            Node focusNode = validNodes.get(index);

            if(n.north == focusNode){
                n.visitedNorth = true;
                n.north.cameHereFrom = n;
                n.north.visitedSouth = true;
            }
            if(n.south == focusNode){
                n.visitedSouth = true;
                n.south.cameHereFrom = n;
                n.south.visitedNorth = true;
            }
            if(n.east == focusNode){
                n.visitedEast = true;
                n.east.cameHereFrom = n;
                n.east.visitedWest = true;
            }
            if(n.west == focusNode){
                n.visitedWest = true;
                n.west.cameHereFrom = n;
                n.west.visitedEast = true;
            }

            generateMaze(focusNode);
        }
    }

    public ArrayList<Node> aStarPathfinding(Node start, Node end){

        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();

        openList.add(start);
        start.hScore = start.calculateHScore(end);

        while(!openList.isEmpty()){
            Node current = null;
            for(Node m: openList){
                if(current == null || current.calculateFScore() > m.calculateFScore())
                    current = m;
            }

            openList.remove(current);
            closedList.add(current);

            if(current == end){
                ArrayList<Node> finalPath = new ArrayList<>();
                end.buildPath(finalPath);
                return finalPath;
            }

            ArrayList<Node> adj = current.getAdjacentNodes();

            for(Node m: current.getAdjacentNodes()){
                if(closedList.contains(m))
                    continue;
                if(!openList.contains(m)){
                    m.hScore = m.calculateHScore(end);
                    openList.add(m);
                    m.setParent(current);
                }
                else{
                    float oldG = m.gScore;
                    float newG = 10 + current.gScore;
                    if(oldG > newG)
                        m.setParent(current);
                }
            }
        }
        return null;
    }

    private float getDistance(Node a, Node b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public void display(){
        for(int i = 0; i < x; i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < y; k++) {
                    if (j == 0) System.out.print(nodeArray[k][i].topToString());
                    else if (j == 1) System.out.print(nodeArray[k][i].midToString());
                    else System.out.print(nodeArray[k][i].bottomToString());
                }
                System.out.println();
            }
        }
    }

}
