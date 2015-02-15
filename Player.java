// Player.java 
//

class Player {
  double average; 
  double probSingle, probDouble, probTriple, probHomerun;
  double probBB, probSwingOut, probOtherOut;
  String batterName;


  Player(double average, 
    double probSingle, double probDouble, double probTriple, double probHomerun,
    double probBB, double probSwingOut, double probOtherOut, 
    String batterName){

    this.average = average; 
    this.probSingle = probSingle; 
    this.probDouble = probDouble; 
    this.probTriple = probTriple;
    this.probHomerun= probHomerun;

    this.probBB = probBB;
    this.probSwingOut = probSwingOut; 
    this.probOtherOut = probOtherOut; 
    this.batterName = batterName;
  }

  // default params for test
  Player(){
    this(0.35, 
        0.20, 0.05, 0.0, 0.1, 
        0.1, 0.3, 0.4, "Ichiro");
  }

  // Main Method for test
  public static void main(String[] args) {

    // import data of batter name
    ImportData importData = new ImportData("坂本勇人");
    System.out.println(importData.batterName);

    // construct 
    Player player = new Player(importData.probSingle,
        importData.probSingle, importData.probDouble, importData.probTriple, importData.probHomerun, 
        importData.probBB, importData.probSwingOut, importData.probOtherOut, 
        importData.batterName);
    System.out.println(player.probSingle);


    // simulation 
    GameSituation gameSituation1 = new GameSituation(player);
    while(!(GameSituation.gameEndCheck())){
      gameSituation1.attack();
      System.out.println("game single hit : " +  GameSituation.gameSingleNumGetter());
      System.out.println("game score : " +  GameSituation.scoreGetter());
    }
  }
}
