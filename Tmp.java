import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

class Tmp{
  public static void main(String[] args) {
    try{
      File file = new File("hoge.txt");
      BufferedReader bf = new BufferedReader(new FileReader(file));
      while(bf.ready()){
        String tmp = bf.readLine();
        System.out.println(tmp);
        String[] rows = tmp.split(",");
        System.out.println(rows[0]);
        System.out.println(rows[1]);

      }
    } catch(FileNotFoundException ex){
      System.out.println(ex);
    } catch(IOException ex){
      System.out.println(ex); 
    }

  }

}
