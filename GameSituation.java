// GameSituation
//
// INPUT :: batter data
// OUTPUT :: 

class GameSituation{
  Player player;
  private static int firstBase=0, secondBase=0, thirdBase = 0;
  private static int score=0, outCount=0;
  private static int inning = 1;
  private static int singleNum=0, doubleNum=0, tripleNum=0, homerunNum=0;

  GameSituation(Player player){
    this.player = player;
  }


  // void attack()
  // 
  void attack(){
    double battingResultRandomNumber = Math.random();

    // single hit
    if(battingResultRandomNumber<=player.probSingle){
      // whether second runner come back to home.
      if(secondBase == 1){
        double secondRunnerScoringRandomNumber = Math.random();
        if(secondRunnerScoringRandomNumber < 0.6){
          score += thirdBase + secondBase; 
          thirdBase = 0;
          secondBase = firstBase;
          firstBase = 1;
        }else{
          score += thirdBase;
          thirdBase = secondBase;
        score += thirdBase;
        thirdBase = secondBase;
        secondBase = firstBase;
        firstBase = 1; 
      }
    // Double
    }else if(battingResultRandomNumber <= player.probSingle + player.probDouble){
    // Triple
    }else if(battingResultRandomNumber <= player.probSingle + player.probDouble + player.probTriple){
    // Homerun
    }else if(battingResultRandomNumber <= player.probSingle + player.probDouble + player.probTriple + player.probHomerun){
      score += 1 + firstBase + secondBase + thirdBase;
      firstBase = 0;
      secondBase = 0;
      thirdBase = 0;
    // other hitting out
    } else {
      outCount += 1;
      outCountCheck();
    }
  }

  // if outCount == 3 then inning += 1 and cleanup the runners.
  void outCountCheck(){
    if(outCount == 3){
      firstBase = 0; secondBase = 0; thirdBase = 0;
      inning += 1;
      System.out.println("Three Out. Next Inning :: " + inning + ".");
      outCount = 0;
      gameEndCheck();
    }
  }

  public static boolean gameEndCheck(){
    if(inning == 10){
      return true;
    } else {
      return false; 
    }
  }

  // Main Method for test
  public static void main(String[] args) {
    System.out.println("Compile OK");

    // default player
    Player player1 = new Player();

    GameSituation gameSituation1 = new GameSituation(player1);
    System.out.println(GameSituation.gameEndCheck());

    // Simulation by using default player data
    while(!(GameSituation.gameEndCheck())){
      gameSituation1.attack();
      System.out.println("Inning:" + GameSituation.inning);
      System.out.println("OutCount:" + GameSituation.outCount);
      System.out.println("Score:" + GameSituation.score);
    }
  }
}
