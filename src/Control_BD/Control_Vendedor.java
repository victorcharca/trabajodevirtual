/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control_BD;
import BD_Vendedor.ListarVendedor;
import static BD_Vendedor.ListarVendedor.jTableListarVendedor;

import consultas.*;
import MenuPrincipal.Ventas;
import static MenuPrincipal.Ventas.SeleccionarVendedor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */


    

public class Control_Vendedor {
    
    //modelo para la tabla
    DefaultTableModel modelo;
    //vector con los titulos de cada columna
    String[] titulosColumnas = {"CÓDIGO","NOMBRE Y APELLIDOS"};
    //matriz donde se almacena los datos de cada celda de la tabla
    String info[][] = {};
    
   
    /**
     * Metodo para listar todos los registros de la tabla
     * de clientes, los muestra en un jtable.
     */
    public void listarTodosVendedor() {

        modelo = new DefaultTableModel(info, titulosColumnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

       

        //le asigna el modelo al jtable
        ListarVendedor.jTableListarVendedor.setModel(modelo);
        
         int[] anchos = {80, 200};
        for(int i = 0; i < jTableListarVendedor.getColumnCount(); i++) {
        jTableListarVendedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //ejecuta una consulta a la BD
        ejecutarConsultaTodaTabla();

    }//cierra metodo listarTodosClientes
    
        public void CargarVendedor() {

        modelo = new DefaultTableModel(info, titulosColumnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

       

        //le asigna el modelo al jtable
        Ventas.SeleccionarVendedor.setModel(modelo);
        
         int[] anchos = {80, 200};
        for(int i = 0; i < SeleccionarVendedor.getColumnCount(); i++) {
        SeleccionarVendedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
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
            String consultaSQL = "SELECT * FROM table_vendedor ORDER BY idVendedor ASC";
            resultado = sentencia.executeQuery(consultaSQL);
            //mientras haya datos en la BD ejecutar eso...
            while (resultado.next()) {
                int codigo = resultado.getInt("idVendedor");
                String nombre = resultado.getString("nombreVendedor");
                //crea un vector donde los está la informacion (se crea una fila)
                Object[] info = {codigo, nombre};
                //al modelo de la tabla le agrega una fila
                //con los datos que están en info
                modelo.addRow(info);
            }//cierra while (porque no hay mas datos en la BD)

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error SQL:\n" + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error:\n" + e);
            conexion = null;
        } finally {
            CerrarConexiones.metodoCerrarConexiones(conexion, sentencia, resultado, ps);
        }

    }//cierra metodo ejecutarConsulta
    
    // eliminar  
    
    public void EliminarVendedor(String code){
        
        try {            
            Connection conexion = ConexionConBaseDatos.getConexion();
            Statement comando = conexion.createStatement();
            int cantidad = comando.executeUpdate("delete from table_vendedor where idVendedor=" + code);
            if (cantidad == 1) {
                JOptionPane.showMessageDialog(null,"Eliminado");
            } else {
                JOptionPane.showMessageDialog(null,"No existe Vendedor de Codigo "+code);
            }
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"error "+ex);
        }
    }
    
    public void ModificarVendedor (String cod, String code, String nombre)
    {
        try {
            Connection conexion = ConexionConBaseDatos.getConexion();
            Statement comando = conexion.createStatement();

            // linea de codigo de mysql que actualiza regristos que va modificar
            int cantidad = comando.executeUpdate("update table_vendedor set nombreVendedor ='" + nombre + "', "
                + "idVendedor = " + code + " where idVendedor=" + cod);
            if (cantidad == 1) {
                JOptionPane.showMessageDialog(null," Modifico con Exito");
            } else {
                JOptionPane.showMessageDialog(null,"No existe Vendedor de un codigo "+cod);
            }
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null," Error -->"+ex);
        }

    }
            
            
}

