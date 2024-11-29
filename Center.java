import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Center{
  
  public static void main(String[] args) throws FileNotFoundException{
    File folder = new File("./Test");
    File[] listOfFiles = folder.listFiles(); 
    PrintWriter writer = new PrintWriter("centers.k.out");
    for (int i = 0; i < listOfFiles.length; i++){
      //because 10 comes after 1
      if (i == 1){
        continue;
      }
      writer.println(Center.calcCenter(listOfFiles[i]));
    }
    writer.println(Center.calcCenter(listOfFiles[1]));
    writer.close();
  }

  public static int calcCenter(File file) throws FileNotFoundException{
  
    Scanner sc = new Scanner(file);
      
    int size = sc.nextInt();
    int[][] edges = new int[size][2];
    int[] weights = new int[size];
    Tree tree = new Tree(size);

    for (int i = 0; i < size - 1; i++){
      edges[i][0] = sc.nextInt() - 1;
      edges[i][1] = sc.nextInt() - 1;
    }
    
    for (int i = 0; i < size; i++){
      weights[i] = sc.nextInt();
      tree.addNode(i, weights[i]);
    }

    for (int i = 0; i < size - 1; i++){
      tree.addEdge(edges[i][0], edges[i][1]);
    }

    ArrayList<Node> path = tree.pruneTree();
    /* 
    System.out.println("~~~~~~~~~~~"); 
    for (int i = 0; i < path.size() - 1; i++){
      System.out.println(path.get(i).getId() + " + " + path.get(i).getWeight());
    }
    System.out.println("~~~~~~~~~~~"); 
    */ 
    int[] centers = Center.results(path, false);

    Tree calcTree = new Tree(size);
    for (int i = 0; i < size; i++){
      calcTree.addNode(i, weights[i]);
    }
    for (int i = 0; i < size - 1; i++){
      calcTree.addEdge(edges[i][0], edges[i][1]);
    }
    
    calcTree.cutEdge(centers[2], centers[3]);
    int fw = calcTree.calculateTotal(centers[0], centers[1]);

    //now backwards 
    centers = Center.results(path, true);
    
    calcTree = new Tree(size);
    for (int i = 0; i < size; i++){
      calcTree.addNode(i, weights[i]);
    }
    for (int i = 0; i < size - 1; i++){
      calcTree.addEdge(edges[i][0], edges[i][1]);
    }

    calcTree.cutEdge(centers[2], centers[3]);
    int bw = calcTree.calculateTotal(centers[0], centers[1]);
    
    if (fw < bw){
      System.out.println(fw);
      return fw;
    } else {
      System.out.println(bw);
      return bw;
    }
    
  }

  public static int[] results(ArrayList<Node> path, boolean reverse){
    
    if (reverse){
      Collections.reverse(path); 
    } 

    int c1 = 0;
    int c2 = 1;

    int splitC1 = 0; 
    //the c2 is just + 1, this is to note the furthest from c1

    int c1L = path.get(c1).getWeight();
    int c1R = 0;
    int c2L = path.get(c2).getWeight();
    int c2R = path.get(c2).calTotalWeight(path.get(c1))
                - path.get(c2).getWeight();
    //moving right -> cost = cL - cR
    //if cost is -ve move
    
    boolean cond = true;
    while (cond){
      cond = false; 
      
      if (c2R > c2L){
        cond = true;
        c2 += 1;
        c2L += path.get(c2).getWeight();
        c2R -= path.get(c2).getWeight();
      }

      if (c1R > c1L){
        cond = true;
        c1 += 1;
        c1L += path.get(c1).getWeight();
        c1R -= path.get(c1).getWeight();
      }
      
      //if ths dist btw the pts is odd
      if ((c2 - c1 - 1) % 2 == 1){ 
        if (splitC1 >= (c2 - c1)/2 + c1){
          continue;
        }
        cond = true;
        splitC1 += 1;
        c2L -= path.get(splitC1).getWeight();
        c1R += path.get(splitC1).getWeight();
      }

      //System.out.println(c1L + " " + c1R + " | " + c2L + " " + c2R);
      
    }
    //System.out.println(path.get(c1).getId());
    //System.out.println(path.get(c2).getId());
    int[] out = new int[4]; 
    out[0] = path.get(c1).getId();
    out[1] = path.get(c2).getId();
    out[2] = path.get(splitC1).getId();
    out[3] = path.get(splitC1+1).getId();
    return out;
  }
}
