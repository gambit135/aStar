//A node for the A* algorithm
public class Node {

    //Parent node.
    Node parent;

    //Cell in the board
    Cell cell;

    //Movement cost (can be due to terrain cost and movement points)
    //In this example, it is the cost of moving from a given location to another (navigation cost)
    float g = 1;

    //Heuristic utility value
    //(can be linear distance to destination or other "convenience" value)
    double h = 0;

    //Eval function
    double f = 0;

    public float calculateG() {
        if (parent != null) {
            return this.g + parent.calculateG();//kinda recursive/backtracking call
        }
        return this.g;
    }

    //Calculates h as the linear distance to the destination
    public void calculateH(Cell destination) {
        this.h = Math.sqrt(
                Math.pow(destination.x - this.cell.x, 2) +
                        Math.pow(destination.y - this.cell.y, 2)
        );
    }

    //f = g + h
    //Lazy
    public double calculateF() {
        this.f = g + h;
        return f;
    }


    Node(int x, int y) {
        cell = Main.map[x][y];
    }

    Node(Cell cell) {
        this.cell = cell;
    }


    /**
     * Used to verify if it already exists on a list. Just uses the coordinate values of the cell.
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Node otherNode = (Node) other;
        return cell.x == otherNode.cell.x &&
                cell.y == otherNode.cell.y;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + cell.x +
                ", y=" + cell.y +
                ", f=" + f +
                ", g=" + g +

                '}';
    }
}
