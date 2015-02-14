import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ImportData {
  public ImportData(){
    Connection conn;
    Statement myStatement;
    String sql;

    try{
      Class.forName("org.sqlite.JDBC");
      String path="jdbc:sqlite:./baseballData.sqlite";
      conn = DriverManager.getConnection(path);
      myStatement = conn.createStatement();

      // execute sql
      sql = "select * from baseballData where name='坂本勇人'";
      System.out.println(myStatement.execute(sql));
      ResultSet rs = myStatement.executeQuery(sql);

      // print
      while(rs.next()){
        System.out.print(rs.getString("name") + ",");
        
        int single = rs.getInt("single");
        int atbat = rs.getInt("atbat");
        double singleHit = (double)single / (double)atbat;
        System.out.println(singleHit);
      }

    } catch(ClassNotFoundException e){
      System.out.println("ClassNotFoundException");
      System.err.println(e.getMessage());
    } catch(SQLException e){
      System.out.println("SQLException");
      System.err.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    new ImportData();
  }
}
