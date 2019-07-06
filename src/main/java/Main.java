import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Main {
    static final int X_MAP_SIZE = 5;
    static final int Y_MAP_SIZE = 5;
    static LinkedList<Node> openList;
    static LinkedList<Node> closedList;

    static Cell[][] map = new Cell[X_MAP_SIZE][Y_MAP_SIZE];

    static Cell startLocation = new Cell(4, 3);
    static Cell endLocation = new Cell(1, 1);

    static {
        //Initialize map
        for (int i = 0; i < X_MAP_SIZE; i++)
            for (int j = 0; j < Y_MAP_SIZE; j++)
                map[i][j] = new Cell(i, j);
    }

    public static void main(String[] args) {
        aStar(startLocation, endLocation);
    }

    public static void aStar(Cell startLocation, Cell endLocation) {
        openList = new LinkedList<>();
        closedList = new LinkedList<>();

        //0. Current node is the starting location
        Node currentNode = new Node(startLocation);
        Node destinationNode = new Node(endLocation);

        do {
            //1. Add current location to closed list (visited)
            //currentNode.calculateF();
            closedList.add(currentNode);

            //2. Check neighbors that aren't in the closed list, and add them to the open list if available
            checkNeighbors(currentNode);

            for (Node n : openList) {
                //3. Calculate the movement cost values (g) for the nodes on the open list
                n.calculateG();

                //4. Calculate the heuristic values (h) for the nodes on the open list
                n.calculateH(endLocation);

                //5. Calculate the f values for the nodes on the open list
                n.calculateF();

            } //Note: ^This can all be done for each node right before it is added to the open list instead

            //6. Sort the open list by its f values, to get the node with the lowest value
            Collections.sort(openList, Comparator.comparing(Node::calculateF));

            //7. This becomes the current node
            currentNode = openList.getFirst();


        } while (!closedList.contains(destinationNode));

        System.out.println("Closed list");
        System.out.println(closedList);

    }

    //Checks neighbors and adds them to the open list if available
    public static void checkNeighbors(Node currentLocation) {

        //Check neighbor up (y-1)
        checkNeighbor(currentLocation.cell.x, currentLocation.cell.y - 1, currentLocation);

        //Check neighbor down (y+1)
        checkNeighbor(currentLocation.cell.x, currentLocation.cell.y + 1, currentLocation);

        //Check neighbor left (x-1)
        checkNeighbor(currentLocation.cell.x - 1, currentLocation.cell.y, currentLocation);

        //Check neighbor right (x+1)
        checkNeighbor(currentLocation.cell.x + 1, currentLocation.cell.y, currentLocation);
    }

    private static void checkNeighbor(int x, int y, Node parent) {
        //Check if not outside the borders of the map, or if it is not blocked
        if (
                x >= 0 &&
                        x < X_MAP_SIZE &&
                        y >= 0 &&
                        y < Y_MAP_SIZE
        ) {
            Node newNodeMaybe = new Node(map[x][y]);
            newNodeMaybe.parent = parent;
            //Add valid neighbor to open list, if it is not on the closed list
            addNeighbor(newNodeMaybe);

        }
    }

    static void addNeighbor(Node newNodeMaybe) {
        if (!closedList.contains(newNodeMaybe)) {
            if (openList.contains(newNodeMaybe)) {    //If we already have it in the open list
                //Recalculate G
                // (or the cost of moving to that neighbor from our NEW current position)
                float newG = newNodeMaybe.calculateG();
                Node previousNode = openList.get(openList.indexOf(newNodeMaybe));
                float previousG = previousNode.calculateG();

                //If the new G value is better (smaller) than the one of our previously stored node,
                // replace the already stored node with the new one on the open list
                if (newG < previousG) {
                    openList.remove(previousNode);
                } else {
                    return;
                }
            }
            openList.add(newNodeMaybe);
        }
    }
}
