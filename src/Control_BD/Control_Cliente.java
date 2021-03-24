package Control_BD;

import BD_Cliente.ListarCliente;
import BD_Cliente.addCliente;
import MenuPrincipal.Ventas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * En esta clase se exponen todos los métodos para ejercer control sobre los
 * datos que van desde y hacia la tabla clientes. En esta clase se hace la
 * validación y organizacion de los datos.
 *
 * @author David
 */


public class Control_Cliente {

    //modelo para la tabla
    DefaultTableModel modelo;
    //vector con los titulos de cada columna
    String[] titulosColumnas = {"CÓDIGO", "NOMBRE", "APELLIDOS", "CEDULA"};
    //matriz donde se almacena los datos de cada celda de la tabla
    String info[][] = {};
    // Conectar Base de Datos
    ConexionConBaseDatos conectar = new ConexionConBaseDatos();
    
    
    

     public void agregarCliente(String cod, String nombre, String apellido, String cedula) {

         Connection reg = ConexionConBaseDatos.getConexion();
        
         String sql = "INSERT INTO table_Cliente ( idCliente, Nombre_Cliente, Apellido_Cliente, Cedula_Cliente)VALUES (?,?,?,?)";
            try {
            
            PreparedStatement pst= reg.prepareStatement(sql);
            pst.setString(1,cod);
            pst.setString(2,nombre);
            pst.setString(3,apellido);
            pst.setString(4,cedula);
            int n = pst.executeUpdate();
            if (n>0){
                JOptionPane.showMessageDialog(null,"Regristado Exitosamente de Cliente");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error - "+ex);
            Logger.getLogger(addCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//cierra metodo agregarCliente

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    /**
     * Metodo para listar todos los registros de la tabla
     * de clientes, los muestra en un jtable.
     */
    public void listarTodosClientes() {

        modelo = new DefaultTableModel(info, titulosColumnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
      //le asigna el modelo al jtable
        ListarCliente.jTableListarCliente.setModel(modelo);

        //ejecuta una consulta a la BD
        ejecutarConsultaTodaTabla();

    }//cierra metodo listarTodosClientes
    
        public void CargarClientes() {

        modelo = new DefaultTableModel(info, titulosColumnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
      //le asigna el modelo al jtable
        Ventas.SeleccionarCliente.setModel(modelo);

        //ejecuta una consulta a la BD
        ejecutarConsultaTodaTabla();

    }//cierra metodo cargarTodosClientes

     /**
     * Metodo para consultar todos los regsitros de la base de datos de clientes
     * y luego ser mostrados en una tabla.
     */
    Connection conexion = null;
    Statement sentencia = null;
    ResultSet resultado = null;
    PreparedStatement ps = null;

    public void ejecutarConsultaTodaTabla() {

        try {
            conexion = ConexionConBaseDatos.getConexion();

            sentencia = conexion.createStatement();
            String consultaSQL = "SELECT * FROM table_Cliente ORDER BY Nombre_Cliente ASC";
            resultado = sentencia.executeQuery(consultaSQL);


            //mientras haya datos en la BD ejecutar eso...
            while (resultado.next()) {


                int codigo = resultado.getInt("idCliente");
                String nombre = resultado.getString("Nombre_Cliente");
                String apellido = resultado.getString("Apellido_Cliente");
                String cedula = resultado.getString("Cedula_Cliente");



                //crea un vector donde los está la informacion (se crea una fila)
                Object[] info = {codigo, nombre, apellido, cedula};

                //al modelo de la tabla le agrega una fila
                //con los datos que están en info
                modelo.addRow(info);

            }//cierra while (porque no hay mas datos en la BD)


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error SQL:\n" + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            conexion = null;
        } finally {
            CerrarConexiones.metodoCerrarConexiones(conexion, sentencia, resultado, ps);
        }

    }//cierra metodo ejecutarConsulta
    
  
    public void buscarCliente(String parametroBusqueda, boolean buscarPorCedula, boolean buscarPorNombre, boolean buscarPorApellido) {

        

            modelo = new DefaultTableModel(info, titulosColumnas) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            ;

            //le asigna el modelo al jtable
            ListarCliente.jTableListarCliente.setModel(modelo);
            //ejecuta una consulta a la BD
            buscarRegistroCedulaONombreOapellido(parametroBusqueda, buscarPorCedula, buscarPorNombre, buscarPorApellido);

        

    }//cierra metodo buscarCliente
    
    /**
     * Método para buscar un registro en la base de datos dentro de la tabla
     * clientes, se puede buscar por la cedula o por el nombre.
     */
    public void buscarRegistroCedulaONombreOapellido(String parametroBusqueda, boolean buscarPorCedula, boolean buscarPorNombre, boolean buscarPorApellido) {

        try {

            conexion = ConexionConBaseDatos.getConexion();
            String selectSQL;
            resultado = null;
            if (buscarPorCedula == true) {               
                selectSQL = "SELECT * FROM table_Cliente WHERE Cedula_Cliente LIKE ? ORDER BY idCliente ASC";
                ps = conexion.prepareStatement(selectSQL);
                ps.setString(1, "%" + parametroBusqueda + "%");
            } 
            else if(buscarPorNombre== true){
                selectSQL = "SELECT * FROM table_Cliente WHERE Nombre_Cliente LIKE ? ORDER BY idCliente ASC";
                ps = conexion.prepareStatement(selectSQL);
                ps.setString(1, "%" + parametroBusqueda + "%");
            }
            else if(buscarPorApellido== true){

                selectSQL = "SELECT * FROM table_Cliente WHERE Apellido_Cliente LIKE ? ORDER BY idCliente ASC";
                ps = conexion.prepareStatement(selectSQL);
                ps.setString(1, "%" + parametroBusqueda + "%");
            }
            resultado = ps.executeQuery();

            while (resultado.next()) {
                int codigo = resultado.getInt("idCliente");
                String nombre = resultado.getString("Nombre_Cliente");
                String apellido = resultado.getString("Apellido_Cliente");
                String cedula = resultado.getString("Cedula_Cliente");
                

                //crea un vector donde los está la informacion (se crea una fila)
                Object[] info = {codigo,nombre, apellido, cedula};
                //al modelo de la tabla le agrega una fila
                //con los datos que están en info
                modelo.addRow(info);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error\n Por la Causa" + e);
        } finally {
            CerrarConexiones.metodoCerrarConexiones(conexion, sentencia, resultado, ps);
        }


    }//cierra metodo buscarRegistro
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    
    /**
     * Método para validar la entrada del usuario
     * que ingresa para eliminar un cliente
     */
    public void EliminarCliente(String code) {

        try {            
            Connection conexion = ConexionConBaseDatos.getConexion();
            Statement comando = conexion.createStatement();
            int cantidad = comando.executeUpdate("delete from table_Cliente where idCliente=" + code);
            if (cantidad == 1) {
   
                JOptionPane.showMessageDialog(null,"Eliminado");
            } else {
                JOptionPane.showMessageDialog(null,"No existe Cliente de Codigo "+code);
            }
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"error "+ex);
        }

    }//cierra metodo eliminarCliente

    
    
    public void ModificarCliente(String code,String cod,String nombre,String apellido,String cedula) {

    
        try {
            Connection conexion = ConexionConBaseDatos.getConexion();
            Statement comando = conexion.createStatement();

            // linea de codigo de mysql que actualiza regristos que va modificar
            int cantidad = comando.executeUpdate("update table_Cliente set Nombre_Cliente ='" + nombre + "', "
                + " Apellido_Cliente ='" + apellido + "', Cedula_Cliente ='" + cedula + "', idCliente ='" + cod + "' where idCliente=" + code);
            if (cantidad == 1) {
                JOptionPane.showMessageDialog(null," Modifico con Exito");
            } else {
                JOptionPane.showMessageDialog(null,"No existe Vendedor de un codigo "+code);
            }
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null," Error -->"+ex);
        }
    }//cierra metodo modificarCliente
    
    
    
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 



}//cierra class
