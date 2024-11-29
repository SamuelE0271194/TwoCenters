import java.util.ArrayList;

public class Node{

  int weight;
  int totalWeight;
  int id;
  ArrayList<Node> neighbours; 

  public Node(int id, int weight){
    this.weight = weight;
    this.totalWeight = weight;
    this.id = id;
    this.neighbours = new ArrayList<>();
  }

  public int getId(){
    return this.id;
  }

  public int getWeight(){
    return this.weight;
  }
  
  public int calTotalWeight(Node source){
    totalWeight = weight;
    for (Node node: neighbours){
      if (node == source){
        continue;
      }
      totalWeight += node.calTotalWeight(this);
    }
    return totalWeight;
  }

  public void addNeighbour(Node node){
    this.neighbours.add(node);
    node.neighbours.add(this);
    //System.out.println("edge add : " + this.id + " + " + node.id);
  }

  public void removeNeighbour(Node node){
    this.neighbours.remove(node);
    node.neighbours.remove(this);
    //System.out.println("edge chop : " + this.id + " - " + node.id);
  }

  public void removeAllNeighbours(){
    while (!this.neighbours.isEmpty()){
      this.removeNeighbour(this.neighbours.get(0));
    }
  }

  public ArrayList<Node> getNeighbours(){
    return this.neighbours;
  }

  public Node oneCenter(){
   return null; 
  }
  /*
  public Pair heaviestBranch(Node source){
    //pre order traverse 
    int total = this.weight;
    Pair heaviest = new Pair(0, new ArrayList<Node>(), 0, 0);
    
    for (Node node: this.neighbours){
      if (node == source){
        continue;
      }
      Pair compare = node.heaviestBranch(this);
      if (this.id == 3){
        System.out.println(node.id + " " + compare.getFirst());
      }
      total += compare.getFirst(); //add the weights anways need it to compare
      if (compare.getFirst() > heaviest.getFirst()){
        heaviest = compare; 
      }
    }
    heaviest.getSecond().add(this);
    return new Pair(total, heaviest.getSecond(), 0, 0);
  }
  */ 
  public Pair heaviestBranch(Node source){
    //pre order traverse 
    int total = this.weight;
    int depth = 1;
    int ecc = 1; 
    Pair heaviest = new Pair(0, new ArrayList<Node>(), 1, 1);
    
    for (Node node: this.neighbours){
      if (node == source){
        continue;
      }
      
      Pair compare = node.heaviestBranch(this);
      /*if (this.id == 3){
        System.out.println(node.id + " " + compare.getFirst() + " " + compare.getFourth());
      }*/
      total += compare.getFirst();
      ecc += 1;

      if (compare.getFirst() * compare.getFourth() > heaviest.getFirst() * heaviest.getFourth()){
        heaviest = compare; 
      }
    }
    heaviest.getSecond().add(this);
    if (ecc < heaviest.getThird()){
      ecc = heaviest.getThird(); 
    }
    depth += heaviest.getFourth();

    return new Pair(total, heaviest.getSecond(), ecc, depth);
  }

  public int pruneNeighbour(){
  //super powerful, remember to cut neighbours before pruning the join if you want to save
    //System.out.println("pruning");
    while (!neighbours.isEmpty()){
      Node node = neighbours.get(0);
      node.removeNeighbour(this);
      this.weight += node.pruneNeighbour();
    }
    return this.weight;
  }

}
