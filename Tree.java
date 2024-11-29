import java.util.ArrayList;

public class Tree{
  
  int size;
  Node[] vertices;

  public Tree(int size){
    this.size = size; 
    this.vertices = new Node[size];
  }

  public void addNode(int id, int weight){
    vertices[id] = new Node(id, weight); 
  }

  public void addEdge(int id1, int id2){
    vertices[id1].addNeighbour(vertices[id2]);
  }

  public void cutEdge(int id1, int id2){
    vertices[id1].removeNeighbour(vertices[id2]);
  }

  public ArrayList<Node> longestPath(){
    //first source doesn't matter
    //out.get(1) is the furthest from the id node
    Pair out = vertices[1].heaviestBranch(vertices[1]);
    Node start = out.getSecond().get(0);
    out = start.heaviestBranch(start);
    out.getSecond().add(start);
    return out.getSecond();
  }

  public ArrayList<Node> pruneTree(){
    ArrayList<Node> longest = this.longestPath();
    for (int i = 1; i < longest.size() - 2; i++){
      Node prune = longest.get(i); 
      prune.removeNeighbour(longest.get(i-1));
      prune.removeNeighbour(longest.get(i+1));
      
      prune.pruneNeighbour();

      prune.addNeighbour(longest.get(i-1));
      prune.addNeighbour(longest.get(i+1));
    }

    return longest; //returns the path with modified weights
  }
  
  //cause i can use contains, i have to destroy the tree for speed
  public int calculateTotal(int c1, int c2){
    int checked = 0; 
    ArrayList<Node> buffer = new ArrayList<>();
    buffer.add(vertices[c1]);
    buffer.add(vertices[c2]);
    int depth = 0;
    int total = 0; 
    
    //I cant use contains here cause its O(n)
    while (checked < size){
      ArrayList<Node> hold = new ArrayList<>();
      while (!buffer.isEmpty()){
        Node node = buffer.remove(0);
        hold.addAll(node.getNeighbours());
        node.removeAllNeighbours();
        total += depth * (node.getWeight());
        checked += 1;
      }
      depth += 1;
      buffer.addAll(hold);
    }
    return total;
  }

}
