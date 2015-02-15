
class Simulation{
  public static void main(String[] args) {

    String[] startingMember = {"坂本勇人", "阿部慎之助", "村田修一", "長野久義", 
                               "片岡治大", "アンダーソン", "セペダ", "亀井善行", "高橋由伸"};

    /* definition */
    PlayerData[] startingMemberData = new PlayerData[startingMember.length];
    GameSituation[] gameSituation = new GameSituation[startingMember.length];

    for(int i = 0; i < startingMemberData.length; i++){
      startingMemberData[i] = new PlayerData(startingMember[i]);
      gameSituation[i] = new GameSituation(startingMemberData[i]);
    }


    /* simulation until one game ends */
    int batterNumber = 0;
    while (GameSituation.inningGetter()<10) {
      gameSituation[batterNumber].attack();
      batterNumber++;
      batterNumber = batterNumber % 9;
    }

    /* output the result of game simulation */
    System.out.println("Simulation Result: score is " + GameSituation.scoreGetter());
  }
}
