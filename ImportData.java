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
      sql = "select * from baseballData";
      System.out.println(myStatement.execute(sql));
      ResultSet rs = myStatement.executeQuery("select * from baseballData");
      while(rs.next()){
        System.out.print(rs.getString("name"));
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
    System.out.println("hoge");
    new ImportData();
  }
}
