import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ImportData {
  double probSingle, probDouble, probTriple, probHomerun;
  double probBB, probSwingOut, probOtherOut;
  String batterName;

  public ImportData(String batterName){
    this.batterName= batterName;
    Connection conn;
    Statement myStatement;
    String sql;

    try{
      Class.forName("org.sqlite.JDBC");
      String path="jdbc:sqlite:./baseballData.sqlite";
      conn = DriverManager.getConnection(path);
      myStatement = conn.createStatement();

      // execute sql
      sql = "select * from baseballData where name='" + batterName + "'";
      ResultSet rs = myStatement.executeQuery(sql);

      // print
      while(rs.next()){

        // calculate the probabilities
        
        int singleHit = rs.getInt("single");
        int doubleHit = rs.getInt("double");
        int tripleHit = rs.getInt("triple");
        int homerun = rs.getInt("homerun");

        int swingOut = rs.getInt("swingout");
        int bb = rs.getInt("bb");

        int atbat = rs.getInt("atbat");

        probSingle = (double)singleHit / (double) atbat;
        probDouble = (double)doubleHit / (double) atbat;
        probTriple = (double)tripleHit / (double) atbat;
        probHomerun = (double)homerun / (double) atbat;

        probSwingOut = (double)swingOut / (double) atbat;
        probBB = (double) swingOut / (double) atbat; 
        probOtherOut = 1 - probSingle - probDouble - probTriple - probHomerun - probSwingOut - probBB;

      }

    } catch(ClassNotFoundException e){
      System.out.println("ClassNotFoundException");
      System.err.println(e.getMessage());
    } catch(SQLException e){
      System.out.println("SQLException");
      System.err.println(e.getMessage());
    }
  }

  // default
  public ImportData(){
    this("阿部慎之助");
  }

  public static void main(String[] args) {
    new ImportData("坂本勇人");
  }
}
