import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

class ReadTxt{
  public static void main(String[] args) {
    try{
      File file = new File("hoge.txt");
      BufferedReader br = new BufferedReader(new FileReader(file));
      while(br.ready()){
        System.out.println(br.readLine());
      }
      br.close();
    } catch(FileNotFoundException e){
      System.out.println(e);
    } catch(IOException e){
      System.out.println(e); 
    }
  }
}
