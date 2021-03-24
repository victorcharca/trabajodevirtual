/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuPrincipal;

import Control_BD.*;
import about.SimpleFrame;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */
public class Ventas extends javax.swing.JDialog {

    int c = 0;
    int n = 0;
    int totals = 0;
    DefaultTableModel modelo;
    // llamada clase de conexion
    ConexionConBaseDatos conectar = new ConexionConBaseDatos();
    String ClienteExistente = "no";

    /**
     * Creates new form Ventas
     */
    public Ventas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        modelo = new DefaultTableModel();
        // Agrega fila
        modelo.addColumn("No.");
        modelo.addColumn("idVentas");
        modelo.addColumn("Cod");
        modelo.addColumn("Productos");
        modelo.addColumn("Cant.");
        modelo.addColumn("Precios Units");
        modelo.addColumn("Importe");
        this.JTableProduct.setModel(modelo);
        setLocationRelativeTo(null);

        // CONFIGURAR el taño de ancho de la tabla
        int[] anchos = {35, 70, 37, 180, 40, 90, 120};
        for (int i = 0; i < JTableProduct.getColumnCount(); i++) {
            JTableProduct.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        // Leer la fecha de computador y colocarlo el cuadro de fecha
        //-------------------------------------
       fecha_actual();
        //-------------------------------------------
        //Aumentar id cliente automaticamente
        this.jTextFieldCodigoCliente.setText(String.valueOf(id_cliente_auto()));
        this.jTextFieldCodVentas.setText(String.valueOf(id_venta_auto()));
        this.jTextFieldCodFacturas.setText(String.valueOf(id_factura_auto()));
    }
    
    public void fecha_actual(){
         Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formateador.format(fechaActual);
        jTextFieldFecha.setText(fecha);
        jTextFieldFecha.setEditable(false);
    }
    
    public int id_cliente_auto(){
        
        Generador ge = new Generador();
        int id_max2 = 1;
        try{
            id_max2 = ge.auto_increm("SELECT MAX(idCliente) FROM table_cliente;");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return id_max2;
    }
    
    public int id_venta_auto(){
        Generador ge = new Generador();
        int id_max2 = 1;
        try{
            id_max2 = ge.auto_increm("SELECT MAX(idVentas) FROM table_ventas;");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return id_max2;
    }
    
    public int id_factura_auto(){
        Generador ge = new Generador();
        int id_max2 = 1;
        try{
            id_max2 = ge.auto_increm("SELECT MAX(No_Facturas) FROM table_facturas;");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return id_max2;
}
    
    public void limpiarTabla(JTable tabla){
        try {
            DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
            int filas=tabla.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    //ventana de productos
    private void llamarProductos() {

        // llamada de datos
        Control_Productos load = new Control_Productos();
        load.CargarProductos();
        //Centramos nuestro jDialog
        jDialogProductos.setLocation(200, 100);
        //La hacemos modal
        jDialogProductos.setModal(true);
        //Establecemos dimensiones.
        jDialogProductos.setMinimumSize(new Dimension(747, 385));

        //Establecemos un título para el jDialog
        jDialogProductos.setTitle("Lista de Productos.");
        //La hacemos visible.
        jDialogProductos.setVisible(true);
    }

    //ventana de productos
    private void llamarVendedor() {

        // llamada de datos
        Control_Vendedor loads = new Control_Vendedor();
        loads.CargarVendedor();
        //Centramos nuestro jDialog
        jDialogVendedor.setLocation(250, 150);
        //La hacemos modal
        jDialogVendedor.setModal(true);
        //Establecemos dimensiones.
        jDialogVendedor.setMinimumSize(new Dimension(500, 358));

        //Establecemos un título para el jDialog
        jDialogVendedor.setTitle("Lista de Productos.");
        //La hacemos visible.
        jDialogVendedor.setVisible(true);
    }

    //ventana de productos
    private void llamarCliente() {

        // llamada de datos
        Control_Cliente loadss = new Control_Cliente();
        loadss.CargarClientes();
        //Centramos nuestro jDialog
        jDialogCliente.setLocation(250, 150);
        //La hacemos modal
        jDialogCliente.setModal(true);
        //Establecemos dimensiones.
        jDialogCliente.setMinimumSize(new Dimension(530, 358));
        //Establecemos un título para el jDialog
        jDialogCliente.setTitle("Lista de Clientes.");
        //La hacemos visible.
        jDialogCliente.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogVendedor = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        SeleccionarVendedor = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jDialogProductos = new javax.swing.JDialog();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SeleccionarProductos = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldParametroBusqueda = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jDialogCliente = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        SeleccionarCliente = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JTableProduct = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCodigoCliente = new javax.swing.JTextField();
        jTextFieldNombreCliente = new javax.swing.JTextField();
        jTextFieldApellido = new javax.swing.JTextField();
        jTextFieldCedula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCodFacturas = new javax.swing.JTextField();
        jTextFieldFecha = new javax.swing.JTextField();
        jTextFieldCodVendedor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldCodProducto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldValor = new javax.swing.JTextField();
        xcant = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldProductos = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldCodVentas = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        LabelCant = new javax.swing.JLabel();
        jTextFieldNo = new javax.swing.JTextField();
        jTextFieldCant = new javax.swing.JTextField();
        jTextFieldImporte = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTextFieldTotals = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();

        jDialogVendedor.setResizable(false);
        jDialogVendedor.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SeleccionarVendedor.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        SeleccionarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(SeleccionarVendedor);

        jDialogVendedor.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 330, 120));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 102));
        jButton1.setText("Seleccionar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jDialogVendedor.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/FotoliaComp_65788270_jz4eEB4qgaZ0kMopuwpgOffUvlmIpQbP.jpg"))); // NOI18N
        jDialogVendedor.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jDialogProductos.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 204, 0));
        jLabel16.setText("Productos y Articulos de Ferreteria");
        jDialogProductos.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jScrollPane2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SeleccionarProductos.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        SeleccionarProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(SeleccionarProductos);

        jDialogProductos.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 610, 230));

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 102, 102));
        jButton7.setText("Seleccionar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jDialogProductos.getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("Buscar ");

        jTextFieldParametroBusqueda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldParametroBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldParametroBusquedaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel21)
                    .addGap(12, 12, 12)
                    .addComponent(jTextFieldParametroBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21)
                        .addComponent(jTextFieldParametroBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jDialogProductos.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 250, 40));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/ferreteria-la-llave-portada-001.jpg"))); // NOI18N
        jDialogProductos.getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        SeleccionarCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SeleccionarCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(SeleccionarCliente);

        jLabel20.setFont(new java.awt.Font("Sylfaen", 0, 36)); // NOI18N
        jLabel20.setText("Lista de Clientes");

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton10.setForeground(new java.awt.Color(0, 51, 51));
        jButton10.setText("Seleccionar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialogClienteLayout = new javax.swing.GroupLayout(jDialogCliente.getContentPane());
        jDialogCliente.getContentPane().setLayout(jDialogClienteLayout);
        jDialogClienteLayout.setHorizontalGroup(
            jDialogClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogClienteLayout.createSequentialGroup()
                .addGroup(jDialogClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogClienteLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jButton10))
                    .addGroup(jDialogClienteLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel20)))
                .addContainerGap(159, Short.MAX_VALUE))
            .addGroup(jDialogClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDialogClienteLayout.createSequentialGroup()
                    .addGap(0, 30, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 30, Short.MAX_VALUE)))
        );
        jDialogClienteLayout.setVerticalGroup(
            jDialogClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogClienteLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addContainerGap())
            .addGroup(jDialogClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDialogClienteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Ventas");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JTableProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        JTableProduct.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        JTableProduct.setSelectionBackground(new java.awt.Color(153, 153, 153));
        jScrollPane3.setViewportView(JTableProduct);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 590, 200));

        jLabel1.setFont(new java.awt.Font("Constantia", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registros de Ventas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 480, 40));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos personales de Cliente:", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel3.setText("Nombre");

        jTextFieldCodigoCliente.setEditable(false);
        jTextFieldCodigoCliente.setBackground(new java.awt.Color(255, 204, 204));
        jTextFieldCodigoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldNombreCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldApellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldCedula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel4.setText("Cedula");

        jLabel5.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel5.setText("Apellido");

        jLabel6.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel6.setText("Cod_Cliente");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("Elegir un Cliente ?");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton3)
                        .addGap(0, 140, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldApellido)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 540, 140));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 790, 10));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Facturas y Ventas:", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Source Sans Pro", 0, 18)); // NOI18N
        jLabel7.setText("Id-Ventas");

        jTextFieldCodFacturas.setEditable(false);
        jTextFieldCodFacturas.setBackground(new java.awt.Color(255, 204, 204));
        jTextFieldCodFacturas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldFecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldCodVendedor.setEditable(false);
        jTextFieldCodVendedor.setBackground(new java.awt.Color(255, 204, 204));
        jTextFieldCodVendedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel9.setText("Atendido por");

        jLabel10.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel10.setText("N°.");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Valor $");

        jTextFieldCodProducto.setEditable(false);
        jTextFieldCodProducto.setBackground(new java.awt.Color(255, 204, 204));
        jTextFieldCodProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldCodProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodProductoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Cant");

        jTextFieldValor.setEditable(false);
        jTextFieldValor.setBackground(new java.awt.Color(255, 204, 204));
        jTextFieldValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        xcant.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        xcant.setModel(new javax.swing.SpinnerNumberModel(1, 0, 30, 1));
        xcant.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Cod");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Productos");

        jTextFieldProductos.setEditable(false);
        jTextFieldProductos.setBackground(new java.awt.Color(255, 204, 204));
        jTextFieldProductos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel14.setText("Fecha");

        jTextFieldCodVentas.setEditable(false);
        jTextFieldCodVentas.setBackground(new java.awt.Color(255, 204, 204));
        jTextFieldCodVentas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldCodVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodVentasActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Elegir un Producto?");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton6.setText("Agregar este Producto");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/trabajador.png"))); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        LabelCant.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelCant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelCant.setText("Cantidad");

        jTextFieldNo.setEditable(false);
        jTextFieldNo.setBackground(new java.awt.Color(204, 255, 204));

        jTextFieldCant.setEditable(false);
        jTextFieldCant.setBackground(new java.awt.Color(204, 255, 204));

        jTextFieldImporte.setEditable(false);
        jTextFieldImporte.setBackground(new java.awt.Color(204, 255, 204));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("N°.");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Importe");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldCodFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel9)
                                        .addComponent(jTextFieldCodVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(41, 41, 41)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jTextFieldCodVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(jButton2)
                                    .addGap(29, 29, 29)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addComponent(jLabel8)
                            .addGap(78, 78, 78)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(68, 68, 68)
                            .addComponent(jLabel12))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jTextFieldNo)
                                    .addGap(18, 18, 18))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldCodProducto))
                                    .addGap(36, 36, 36)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldCant)
                                .addComponent(LabelCant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldProductos, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(20, 20, 20)
                                            .addComponent(jTextFieldValor)
                                            .addGap(34, 34, 34))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(jTextFieldImporte)))
                                    .addGap(20, 20, 20)
                                    .addComponent(xcant, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(104, 104, 104))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldCodFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldCodVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCodVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton6)))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCodProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jTextFieldProductos)
                    .addComponent(jTextFieldValor)
                    .addComponent(xcant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCant)
                    .addComponent(jTextFieldImporte)
                    .addComponent(jTextFieldNo))
                .addGap(23, 23, 23))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 540, 250));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/FotoliaComp_65790455_A9698WOdTOIEhPxl3ywQ0nZPi07EeRmf.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 300, 380));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 0, 0));
        jButton4.setText("Eliminar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 450, 170, -1));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(204, 0, 0));
        jButton5.setText("Cerrar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 600, 120, -1));

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 51, 51));
        jButton8.setText("Registrar Facturas");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 170, -1));

        jTextFieldTotals.setEditable(false);
        jTextFieldTotals.setBackground(new java.awt.Color(204, 255, 204));
        jTextFieldTotals.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldTotals.setForeground(new java.awt.Color(0, 102, 0));
        jTextFieldTotals.setText("00.0");
        jPanel1.add(jTextFieldTotals, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 450, 170, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Total a Pagar $");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, -1, 30));

        jToggleButton1.setText("Guardar Facturas");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 550, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCodProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodProductoActionPerformed

    private void jTextFieldCodVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodVentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodVentasActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        llamarVendedor();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        llamarProductos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // boton de selecciona productos
        int i = SeleccionarProductos.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Favor... seleccione una fila");
        } else//de lo contrario si se selecciono la fila 
        {
            // obtener valor de tabla por columna y enviar datos a texfield
            jTextFieldCodProducto.setText(String.valueOf(SeleccionarProductos.getValueAt(i, 0)));
            jTextFieldProductos.setText(String.valueOf(SeleccionarProductos.getValueAt(i, 1)));
            jTextFieldValor.setText(String.valueOf(SeleccionarProductos.getValueAt(i, 2)));

        }
        jDialogProductos.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int i = SeleccionarVendedor.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Favor... seleccione una fila");
        } else//de lo contrario si se selecciono la fila 
        {
            // obtener valor de tabla por columna y enviar datos a texfield
            jTextFieldCodVendedor.setText(String.valueOf(SeleccionarVendedor.getValueAt(i, 0)));
            //vendedors.setText(String.valueOf(SeleccionarVendedor.getValueAt(i, 1)));
            jLabel9.setText(String.valueOf(SeleccionarVendedor.getValueAt(i, 1)));
        }
        jDialogVendedor.dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        //sumar contador

        if (jTextFieldCodVentas.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Caja vacia");
        } else {

            n = 1 + n;
            jTextFieldNo.setText(String.valueOf(n));
            // leer la cantidad pedidas
            int cant = (Integer) xcant.getValue();
            //problema de obrtener valor de spinner toca dar vuelta
            jTextFieldCant.setText(String.valueOf(cant));

            int a = Integer.parseInt(jTextFieldValor.getText());
            int b = Integer.parseInt(jTextFieldCant.getText());
            // Calcular la cantidad por valor
            int importe = a * b;
            jTextFieldImporte.setText(String.valueOf(importe));

            int can = Integer.parseInt(this.jTextFieldCodVentas.getText());
            can = can + c;
            //Agregar datos a la tabla        
            String datos[] = new String[7];
            datos[0] = jTextFieldNo.getText();
            datos[1] = String.valueOf(can);
            datos[2] = jTextFieldCodProducto.getText();
            datos[3] = jTextFieldProductos.getText();
            datos[4] = jTextFieldCant.getText();
            datos[5] = jTextFieldValor.getText();
            datos[6] = jTextFieldImporte.getText();
            modelo.addRow(datos);

            totals = totals + importe;
            jTextFieldTotals.setText(String.valueOf(totals));

           c++;
           System.out.println(can);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        // seleccion la fila
        int i = JTableProduct.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Favor... seleccione una fila");
        } else//de lo contrario si se selecciono la fila 
        {
            // coje variable de importe que elimino
            String nums = (String) JTableProduct.getValueAt(i, 6);
            int entero = Integer.parseInt(nums);

            // cambiarlo de subtotalas que va eliminar 
            // sumar total
            totals = totals - entero;
            jTextFieldTotals.setText(String.valueOf(totals));

            // eliminar fila
            this.modelo.removeRow(i);
            // restar un aticulos
            n = n - 1;
            // poner nuevo ciclo
            int num = 1;
            for (int w = 0; w < n; w = w + 1) {
                //agregado de nuevo 
                JTableProduct.setValueAt(num, w, 0);
                num = num + 1;
            }

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        // conectar
         System.out.println(jTextFieldCodFacturas.getText()+" - "+jTextFieldCodigoCliente.getText()+" - "+jTextFieldFecha.getText()+" - "+jTextFieldCodVendedor.getText()+" - "+jTextFieldTotals.getText());
        Connection reg = conectar.getConexion();

        //PARA DETERMINAR SI ES CLIENTE EXISTENTE O NO
        if (ClienteExistente == "si") {
            JOptionPane.showMessageDialog(null, " Cliente existe ok");
        } //sino agregado cliente existente entonces lo guarda nuevo cliente
        else {
        // regristar bd a la tabla de cliente
            // obtener datos         
            String cod = jTextFieldCodigoCliente.getText();
            String nombre = jTextFieldNombreCliente.getText();
            String apellido = jTextFieldApellido.getText();
            String cedula = jTextFieldCedula.getText();
            Control_Cliente add = new Control_Cliente();
            // enviar datos a regristar en el control_cliente
            add.agregarCliente(cod, nombre, apellido, cedula);
        }

        // regristo de BD a la tabla de Facturas
        String sql_Facturas = "INSERT INTO table_Facturas (No_Facturas,cliente,fecha,vendedor,totals)VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = reg.prepareStatement(sql_Facturas);
            pst.setString(1, jTextFieldCodFacturas.getText());
            pst.setString(2, jTextFieldCodigoCliente.getText());
            pst.setString(3, jTextFieldFecha.getText());
            pst.setString(4, jTextFieldCodVendedor.getText());
            pst.setInt(5, Integer.parseInt(jTextFieldTotals.getText()));
            
            int ns = pst.executeUpdate();
            if (ns > 0) {
                JOptionPane.showMessageDialog(null, "Regristado Exitosamente de Facturas ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error - FACTURAS: " + ex);
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
             System.out.println(ex.getMessage());
        }


        // regristar bd a la tabla de ventas
        // guardar base de datos a la tabla de ventas
        // se regrista por fila con el ciclo hasta No. de ultimo.
        for (int w = 0; w < n; w = w + 1) {
            //obteniendo valor fila por columna a la ves y w es fila y n es total de articulos comprados
            String col1 = (String) JTableProduct.getValueAt(w, 1);// valor de codigo ventas
            String col2 = (String) JTableProduct.getValueAt(w, 2);// valor de codigo productos
            String col3 = (String) JTableProduct.getValueAt(w, 4);// valor de cantidad productos
            String col4 = (String) JTableProduct.getValueAt(w, 6);// valor de importe

            String sql_Ventas = "INSERT INTO table_Ventas (idVentas,No_Facturas,productos,cantidad,importe)VALUES (?,?,?,?,?)";
            try {
                PreparedStatement pst = reg.prepareStatement(sql_Ventas);
                pst.setString(1, col1);
                pst.setString(2, jTextFieldCodFacturas.getText());
                pst.setString(3, col2);
                pst.setString(4, col3);
                pst.setString(5, col4);
                int ns = pst.executeUpdate();
                if (ns > 0) {
                    JOptionPane.showMessageDialog(null, "Regristado Exitosamente de Ventas " + col1);
                }
           

            } catch (SQLException ex) {
                 System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "Error - VENTAS: " + ex);
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }catch(Exception ex){
                 System.out.println(ex.getMessage());
            }
        }
        // cierra de ventas

        // y ahora genera facturas guardada en la carpeta Facturas
        Image portada;

        // CREA DOCUMENTOS CON TAMAÑO CARTAS Y MARGENE DE TODO LADO DE 50   
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("facturas/" + jTextFieldCodFacturas.getText() + ".pdf"));
            document.open();

            portada = Image.getInstance("portada.png");
            portada.setAlignment(Element.ALIGN_CENTER);
            portada.scalePercent(45f);// tamaño de imagen

            document.add(portada);
            document.add(new Paragraph("---------------------------------------------------------"));
            document.add(new Paragraph("|    FACTURAS     |"));
            document.add(new Paragraph("---------------------------------------------------------"));
            document.add(new Paragraph("Numero Fact. : " + jTextFieldCodFacturas.getText()));
            document.add(new Paragraph("Cliente : " + jTextFieldNombreCliente.getText() + " " + jTextFieldApellido.getText() + " - ID : " + jTextFieldCedula.getText()));
            document.add(new Paragraph("Atendio por : " + jLabel9.getText()));
            document.add(new Paragraph("Fecha   : [ " + jTextFieldFecha.getText() + " ] - Total a Pagar : $ " + totals + "  pesos"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("| No. |  Id-Ventas   |  COD.  |          PRODUCTOS             |CANT.| VALOR UNIT "
                    + "|  IMPORTE  |"));
            document.add(new Paragraph("----------------------------------------------------------------------"
                    + "--------------------------------------------------------"));

            // parte de dibujo la tabla
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(500, 500);
            Graphics2D g2;
            g2 = tp.createGraphicsShapes(500, 500);
            // g2 = tp.createGraphics(500, 500);
            JTableProduct.print(g2);
            g2.dispose();
            //posicion de la tabla de lista de compras
            cb.addTemplate(tp, 50, -85);
            //cierra el documento
            document.close();
            JOptionPane.showMessageDialog(null, "Generado PDF Exitosamente.");
            SimpleFrame sf = new SimpleFrame(jTextFieldCodFacturas.getText());
            sf.setVisible(true);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Generando PDF"+e);
        }finally{
            try{
                Limpiar l = new Limpiar();
        l.limpiar_texto(jPanel2);
         l.limpiar_texto(jPanel3);
         
         fecha_actual();
        
        this.jTextFieldCodigoCliente.setText(String.valueOf(id_cliente_auto()));
        this.jTextFieldCodVentas.setText(String.valueOf(id_venta_auto()));
        this.jTextFieldCodFacturas.setText(String.valueOf(id_factura_auto()));
        this.jTextFieldTotals.setText("");
        
        limpiarTabla(JTableProduct);
        
            }catch(Exception ex){
            }
        }
        
        this.totals = 0;
        n = 0;
        ClienteExistente = "no";
        c = 0;
        
       
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        llamarCliente();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        // boton de seleccionado
        int i = SeleccionarCliente.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Favor... seleccione una fila");
        } else//de lo contrario si se selecciono la fila 
        {
            ClienteExistente = "si";
            // obtener valor de tabla por columna y enviar datos a texfield
            jTextFieldCodigoCliente.setText(String.valueOf(SeleccionarCliente.getValueAt(i, 0)));
            jTextFieldNombreCliente.setText(String.valueOf(SeleccionarCliente.getValueAt(i, 1)));
            jTextFieldApellido.setText(String.valueOf(SeleccionarCliente.getValueAt(i, 2)));
            jTextFieldCedula.setText(String.valueOf(SeleccionarCliente.getValueAt(i, 3)));

        }
        jDialogCliente.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextFieldParametroBusquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldParametroBusquedaKeyPressed
        // TODO add your handling code here:
        Control_Productos cc = new Control_Productos();
        String parametroBusqueda = jTextFieldParametroBusqueda.getText();
        cc.buscarProductosparaVentas(parametroBusqueda);


    }//GEN-LAST:event_jTextFieldParametroBusquedaKeyPressed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        // y genera facturas guardada en la carpeta Facturas
        Image portada;

        // CREA DOCUMENTOS CON TAMAÑO CARTAS Y MARGENE DE TODO LADO DE 50
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("facturas/" + jTextFieldCodFacturas.getText() + ".pdf"));
            document.open();

            portada = Image.getInstance("portada.png");
            portada.setAlignment(Element.ALIGN_CENTER);
            portada.scalePercent(45f);// tamaño de imagen

            document.add(portada);
            document.add(new Paragraph("---------------------------------------------------------"));
            document.add(new Paragraph("|    FACTURAS     |"));
            document.add(new Paragraph("---------------------------------------------------------"));
            document.add(new Paragraph("Numero Fact. : " + jTextFieldCodFacturas.getText()));
            document.add(new Paragraph("Cliente : " + jTextFieldNombreCliente.getText() + " " + jTextFieldApellido.getText() + " - ID : " + jTextFieldCedula.getText()));
            document.add(new Paragraph("Atendio por : " + jLabel9.getText()));
            document.add(new Paragraph("Fecha   : [ " + jTextFieldFecha.getText() + " ] - Total a Pagar : $ " + totals + "  pesos"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("| No. |  Id-Ventas   |  COD.  |          PRODUCTOS             |CANT.| VALOR UNIT "
                    + "|  IMPORTE  |"));
            document.add(new Paragraph("----------------------------------------------------------------------"
                    + "--------------------------------------------------------"));

            // parte de dibujo la tabla
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(500, 500);
            Graphics2D g2;
            g2 = tp.createGraphicsShapes(500, 500);
            // g2 = tp.createGraphics(500, 500);
            JTableProduct.print(g2);
            g2.dispose();
            //posicion de la tabla de lista de compras
            cb.addTemplate(tp, 50, -85);
            //cierra el documento
            document.close();
            JOptionPane.showMessageDialog(null, "Generado PDF Exitosamente.");
            SimpleFrame sf = new SimpleFrame(jTextFieldCodFacturas.getText());
            sf.setVisible(true);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Ventas dialog = new Ventas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable JTableProduct;
    private javax.swing.JLabel LabelCant;
    public static javax.swing.JTable SeleccionarCliente;
    public static javax.swing.JTable SeleccionarProductos;
    public static javax.swing.JTable SeleccionarVendedor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JDialog jDialogCliente;
    private javax.swing.JDialog jDialogProductos;
    private javax.swing.JDialog jDialogVendedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextFieldApellido;
    private javax.swing.JTextField jTextFieldCant;
    private javax.swing.JTextField jTextFieldCedula;
    private javax.swing.JTextField jTextFieldCodFacturas;
    private javax.swing.JTextField jTextFieldCodProducto;
    private javax.swing.JTextField jTextFieldCodVendedor;
    private javax.swing.JTextField jTextFieldCodVentas;
    private javax.swing.JTextField jTextFieldCodigoCliente;
    private javax.swing.JTextField jTextFieldFecha;
    private javax.swing.JTextField jTextFieldImporte;
    private javax.swing.JTextField jTextFieldNo;
    private javax.swing.JTextField jTextFieldNombreCliente;
    private javax.swing.JTextField jTextFieldParametroBusqueda;
    private javax.swing.JTextField jTextFieldProductos;
    private javax.swing.JTextField jTextFieldTotals;
    private javax.swing.JTextField jTextFieldValor;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JSpinner xcant;
    // End of variables declaration//GEN-END:variables
}
