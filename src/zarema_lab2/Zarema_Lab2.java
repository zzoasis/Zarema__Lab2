
package zarema_lab2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

public class Zarema_Lab2 {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
        public static void main(String[] args) throws SQLException {
        Connection myconn = null;
        Statement mystmt = null;
        ResultSet myrs = null;
        
         

        try {
            // Подключение к базе данных
            myconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/people", "root", "GH2zmk245mySQL");
            mystmt = myconn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            myrs = mystmt.executeQuery("SELECT * FROM people.people");

            // Вывод начальных данных
            System.out.println("Люди:");
            while (myrs.next()) {
                System.out.println(myrs.getString("name") + " - " + myrs.getString("age"));
            }

             System.out.println("\nИзменение возраста\n");
            myrs.beforeFirst();
            while (myrs.next()) {
                String oldAge = myrs.getString("age");
                String newAge = changeAge(oldAge);
               myrs.updateString("age", newAge);
                myrs.updateRow();
            }

            System.out.println("Конечное состояние таблицы:");
            myrs.beforeFirst();
            while (myrs.next()) {
                System.out.println(myrs.getString("name") + ": " + myrs.getString("age"));
            }
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            if (myrs != null) {
                myrs.close();
            }
            if (mystmt != null) {
                mystmt.close();
            }
            if (myconn != null) {
                myconn.close();
            }
        }
    }

    private static String changeAge(String age) {
        return Long.toString(Long.parseLong(age) + ThreadLocalRandom.current().nextInt(-5, 5));
    }
    }
    

