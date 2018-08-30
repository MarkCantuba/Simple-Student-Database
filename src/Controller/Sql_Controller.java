package Controller;

import Entities.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Class containing a class that will connect us to mySql server
 */
public class Sql_Controller {

    public static Connection connectToDatabase(String url) {
        return connectToDatabase(url, "root", "potato");
    }
    /**
     * Establishes a connection to  mySql Server with url, url
     * @param url: URL of the mtSql server to be accessed
     * @param user: mySql username
     * @param pass: mySql password
     * @return: Established connection in desired mySql url
     */
    private static Connection connectToDatabase(String url, String user, String pass) {
        Connection conn = null; // Initialize our connection variable
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Loads the driver into our class dynamically
            conn = DriverManager.getConnection(url, user, pass);    // Connect to database
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Gets the result set from given table name
     * @param conn: Connection established into our data base
     * @param tableName: Name of our table
     * @return resultant set
     */
    public static ResultSet getResultSet(Connection conn, String tableName) {
        ResultSet resultSet = null;
        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Adds a new student into our database
     * @param student: Student object, containing name, studentID, and GPA
     * @param connection: Connection to our sql server database
     * @param TableName: Name of the table you wish to update
     */
    public static void AddStudent(Student student, Connection connection, String TableName) {
        AddStudent(connection,TableName, student.getStudentId(), student.getFirstName(), student.getLastName(),
                student.getGpa());
    }

    /**
     * Helper method to implement Add Student method
     * @param conn Connection to our database
     * @param TableName name of the table to be accessed
     * @param StudentID studentID of the student. Has to be unique!
     * @param FirstName name of the student
     * @param LastName student's last name
     * @param GPA gpa of the student
     */
    private static void AddStudent(Connection conn, String TableName, int StudentID, String FirstName, String LastName,
                                     double GPA) {
        try {
            Statement statement = conn.createStatement();
            if (!exists(conn, StudentID)) {
                statement.execute("INSERT INTO " + TableName + " VALUES ('" + StudentID + "','" + FirstName +
                        "','" + LastName + "'," + GPA +")");
            } else {
                System.out.println("A student with the given studentID already exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete student off the university database
     * @param StudentId: student's ID number
     * @param connection: established connection to our database
     * @param TableName: name of the table to delete student from
     */
    public static void RemoveStudent(int StudentId, Connection connection, String TableName) {
        RemoveStudent(connection,TableName, StudentId);
    }

    /**
     * Helper method for the delete method
     * @param conn: established connection to our database to be used
     * @param TableName: name of the table to be modified
     * @param StudentID: student's ID number
     */
    private static void RemoveStudent(Connection conn, String TableName, int StudentID) {
        try {
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM " + TableName + " WHERE STUDENTID=" + StudentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(Connection connection, int studentId) {
        return exists(connection, studentId, "universitydb");
    }

    private static boolean exists(Connection conn, int studentID, String tableName) {
        ResultSet resultSet;
        int result = 0;
        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE studentID = " + studentID);
            resultSet.first();
            result = resultSet.getInt("StudentID");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Student obtainStudent(Connection connection, int studentId) {
        if (exists(connection, studentId)) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM universitydb WHERE studentID=" + studentId);
                resultSet.next();
                return new Student(resultSet.getInt("StudentID"), resultSet.getDouble("GPA"),
                        resultSet.getString("FirstName"), resultSet.getString("LastName"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students";
        Connection conn = connectToDatabase(url);
        Sql_Controller.AddStudent(new Student(11214497, 2.0, "Mark", "Cantuba"), conn, "universitydb");
        Sql_Controller.AddStudent(new Student(11214499, 2.0, "Mark", "Cantuba"), conn, "universitydb");
    }
}
