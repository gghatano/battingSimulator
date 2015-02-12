// Player.java 
//

class Player {
  double average; 
  double probSingle, probDouble, probTriple, probHomerun;
  double probSwingOut, probOtherOut;
  String batterName;


  // 8 parametars
  Player(double average, 
    double probSingle, double probDouble, double probTriple, double probHomerun,
    double probSwingOut, double probOtherOut, 
    String batterName){

    this.average = average; 
    this.probSingle = probSingle; 
    this.probDouble = probDouble; 
    this.probTriple = probTriple;
    this.probHomerun= probHomerun;
    this.probSwingOut = probSwingOut; 
    this.probOtherOut = probOtherOut; 
    this.batterName = batterName;
  }

  // default params for test
  Player(){
    this(0.35, 
        0.20, 0.05, 0.0, 0.1, 
        0.3, 0.4, "Ichiro");
  }

  // Main Method for test
  public static void main(String[] args) {
    Player player = new Player(0.3, 
                               0.1, 0.1, 0.1, 0.1,
                               0.1, 0.1, "Ichiro");

    
  }
}
