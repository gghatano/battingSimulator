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
      File file = new File("./teamData/"+teamName+".txt");
      BufferedReader bf = new BufferedReader(new FileReader(file));
      System.out.println("Name,ATBAT,Single,Double,Triple,Homerun,BB,SO,OBP");
      while(bf.ready()){
        String line = bf.readLine();
        String[] playerRawData = line.split(",");
        for(String elem:playerRawData){
          System.out.print(elem + ",");
        }
        System.out.println("");
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



