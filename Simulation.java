
class Simulation{
  public static void main(String[] args) {
    PlayerData playerData = new PlayerData("坂本勇人");
    System.out.println(playerData.batterName);

    GameSituation gameSituation = new GameSituation(playerData);
    gameSituation.attack();
    System.out.println(GameSituation.outCountGetter());
  }
}
