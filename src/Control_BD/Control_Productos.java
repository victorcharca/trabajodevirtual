package Control_BD;

import BD_Productos.ConsultarProductos;
import MenuPrincipal.Ventas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * En esta clase se exponen todos los métodos para ejercer control sobre los
 * datos que van desde y hacia la tabla clientes. En esta clase se hace la
 * validación y organizacion de los datos.
 *
 * @author David
 */


public class Control_Productos {

    //modelo para la tabla
    DefaultTableModel modelo;
    //vector con los titulos de cada columna
    String[] titulosColumnas = {"CÓDIGO", "NOMBRE", " PRECIOS"};
    //matriz donde se almacena los datos de cada celda de la tabla
    String info[][] = {};
    
    
    
    

     public void agregarProductos(String cedula, String nombre, String apellido, String direccion, String telefono) {


         
    }//cierra metodo agregarCliente
     
     
     public void CargarProductos(){
         
         modelo = new DefaultTableModel(info, titulosColumnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
      //le asigna el modelo al jtable
        Ventas.SeleccionarProductos.setModel(modelo);

        //ejecuta una consulta a la BD
        ejecutarConsultaTodaTabla();
         
     }
    
    
    
    
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    /**
     * Metodo para listar todos los registros de la tabla
     * de clientes, los muestra en un jtable.
     */
    public void listarTodosProductos() {

        modelo = new DefaultTableModel(info, titulosColumnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
      //le asigna el modelo al jtable
        ConsultarProductos.jTableListarCliente.setModel(modelo);

        //ejecuta una consulta a la BD
        ejecutarConsultaTodaTabla();

    }//cierra metodo listarTodosClientes
    
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
            String consultaSQL = "SELECT * FROM table_productos ORDER BY idProductos ASC";
            resultado = sentencia.executeQuery(consultaSQL);


            //mientras haya datos en la BD ejecutar eso...
            while (resultado.next()) {


                String codigo = resultado.getString("idProductos");
                String nombre = resultado.getString("nombreProductos");
                String precio = resultado.getString("preciosProductos");



                //crea un vector donde los está la informacion (se crea una fila)
                Object[] info = {codigo, nombre, precio};

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
    
  
    public void buscarProductos(String parametroBusqueda) {

        

            modelo = new DefaultTableModel(info, titulosColumnas) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            ;
            //le asigna el modelo al jtable
            ConsultarProductos.jTableListarCliente.setModel(modelo);
            //ejecuta una consulta a la BD
            buscarRegistroProductos(parametroBusqueda);

    }
    
    
    public void buscarRegistroProductos(String parametroBusqueda) {

        try {

            conexion = ConexionConBaseDatos.getConexion();
            String selectSQL;
            resultado = null;
                            
                selectSQL = "SELECT * FROM table_Productos WHERE nombreProductos LIKE ? ORDER BY idProductos ASC";
                ps = conexion.prepareStatement(selectSQL);
                ps.setString(1, "%" + parametroBusqueda + "%");
    
            resultado = ps.executeQuery();

            while (resultado.next()) {
                String codigo = resultado.getString("idProductos");
                String nombre = resultado.getString("nombreProductos");
                String precio = resultado.getString("preciosProductos");

                

                //crea un vector donde los está la informacion (se crea una fila)
                Object[] info = {codigo,nombre, precio};
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
    public void EliminarProductos(String code) {

        try {            
            Connection conexion = ConexionConBaseDatos.getConexion();
            Statement comando = conexion.createStatement();
            int cantidad = comando.executeUpdate("delete from table_Productos where idProductos=" + code);
            if (cantidad == 1) {
   
                JOptionPane.showMessageDialog(null,"Eliminado");
            } else {
                JOptionPane.showMessageDialog(null,"No existe Producto de Codigo "+code);
            }
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"error "+ex);
        }

    }//cierra metodo eliminarCliente

    
    /**
     * Método para validar y modificar la 
     * información de un cliente.
     */
    public void ModificarProductos(String code,String cod,String nombre,String precios) {

    
        try {
            Connection conexion = ConexionConBaseDatos.getConexion();
            Statement comando = conexion.createStatement();

            // linea de codigo de mysql que actualiza regristos que va modificar
            int cantidad = comando.executeUpdate("update table_Productos set nombreProductos ='" + nombre + "', "
                + " preciosProductos ='" + precios + "', idProductos ='" + cod + "' where idProductos=" + code);
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
    
    
    
    
    //es para buscar productos de compras en ventas- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
   
    public void buscarProductosparaVentas(String parametroBusqueda) {

        

            modelo = new DefaultTableModel(info, titulosColumnas) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            ;
            //le asigna el modelo al jtable
            Ventas.SeleccionarProductos.setModel(modelo);
            //ejecuta una consulta a la BD
            buscarRegistroProductoss(parametroBusqueda);

    }
    
    
    public void buscarRegistroProductoss(String parametroBusqueda) {

        try {

            conexion = ConexionConBaseDatos.getConexion();
            String selectSQL;
            resultado = null;
                            
                selectSQL = "SELECT * FROM table_Productos WHERE nombreProductos LIKE ? ORDER BY idProductos ASC";
                ps = conexion.prepareStatement(selectSQL);
                ps.setString(1, "%" + parametroBusqueda + "%");
    
            resultado = ps.executeQuery();

            while (resultado.next()) {
                String codigo = resultado.getString("idProductos");
                String nombre = resultado.getString("nombreProductos");
                String precio = resultado.getString("preciosProductos");

                

                //crea un vector donde los está la informacion (se crea una fila)
                Object[] info = {codigo,nombre, precio};
                //al modelo de la tabla le agrega una fila
                //con los datos que están en info
                modelo.addRow(info);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error\n Por la Causa" + e);
        } finally {
            CerrarConexiones.metodoCerrarConexiones(conexion, sentencia, resultado, ps);
        }


    }


}//cierra class
