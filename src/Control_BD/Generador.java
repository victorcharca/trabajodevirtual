/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control_BD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author David
 */
public class Generador {
    
    public int auto_increm(String sql){
        int id = 1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConexionConBaseDatos db = new ConexionConBaseDatos();
        try{    
                ps = ConexionConBaseDatos.getConexion().prepareStatement(sql);
                rs = ps.executeQuery();
                while(rs.next()){
                    id = rs.getInt(1)+1;
                }
        }catch(Exception ex){
            System.out.println("idmaximo"+ex.getMessage());
            id = 1;
        }
        finally{
            try{
                ps.close();
                rs.close();
               
            }catch(Exception ex){}
        }
        return id;
    }
    
}
