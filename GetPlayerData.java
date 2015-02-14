import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

class GetPlayerData{
  Player player;
  String teamName;

  GetPlayerData(String teamName){
    this.teamName = teamName;
  }
  
  // default
  GetPlayerData(){
    this("giants");
  }

  void readDataFile(){
    try{
      File file = new File(teamName+".dat");
      BufferedReader bf = new BufferedReader(new FileReader(file));
      while(bf.ready()){
        String line = bf.readLine();
        String[] playerRawData = line.split(",");
        System.out.println(playerRawData[1]);
        System.out.println(playerRawData[2]);
      }
    } catch(FileNotFoundException ex){
      System.out.println(ex);
    } catch(IOException ex){
      System.out.println(ex);
    }
  }

  public static void main(String[] args) {
    System.out.println("hoge");
    GetPlayerData gpd = new GetPlayerData();
    gpd.readDataFile();
  }
}



