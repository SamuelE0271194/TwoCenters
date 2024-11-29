import java.util.ArrayList;

//implementing the generic is a pain
public class Pair{ //was a pair, now is a quadruplet       
  public final Integer t;
  public final ArrayList<Node> u;
  public final Integer s; 
  public final Integer v;

  public Pair(Integer t, ArrayList<Node> u, Integer s, Integer v){         
    this.t = t;
    this.u = u;
    this.s = s;
    this.v = v;
  }

  public Integer getFirst(){
    return this.t;
  }

  public ArrayList<Node> getSecond(){
    return this.u;
  }

  public Integer getThird(){
    return this.s;
  }

  public Integer getFourth(){
    return this.v;
  }

}
