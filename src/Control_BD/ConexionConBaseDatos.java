
package Control_BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionConBaseDatos {
     
    
    
    public static Connection conexion = null;
    
    //Este metodo realiza la conexión con la base de datos H2
    public static Connection getConexion() {
        
        try {
           conexion = null;
           //cargar nuestro driver
           Class.forName("org.h2.Driver");
           conexion =DriverManager.getConnection("jdbc:h2:./BD/ventas","sa","");
           System.out.println("conexion establecida");
       } catch (ClassNotFoundException | SQLException e) {
           System.out.println("error de conexion");
           JOptionPane.showMessageDialog(null, "error de conexion "+e);
       }
        
        //Este metodo hace la conexión con la bd mysql
        /*
        try {
           conexion = null;
           //cargar nuestro driver
           Class.forName("com.mysql.jdbc.Driver");
           conexion =DriverManager.getConnection("jdbc:mysql://localhost/ventas","root","");
           System.out.println("conexion establecida");
       } catch (ClassNotFoundException | SQLException e) {
           System.out.println("error de conexion");
           JOptionPane.showMessageDialog(null, "error de conexion "+e);
       }
        */

        return conexion;
    }//cierra metodo obtenerConexion
    
}//fin class
