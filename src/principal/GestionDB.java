/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Mati
 */
public class GestionDB
{

    Connection conexion = null;
    Statement sentencia = null;
    ResultSet resultado = null;

    String nombreDB = "bodega";
    String servidor = "localhost:3306";
    String usuario = "Admin";
    String password = "123";

    String SQL;

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://" + servidor + "/" + nombreDB;

    public void insert(String tabla, String nombre, int precio)
    {

        try
        {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);

            sentencia = conexion.createStatement();

            if ("productos".equals(tabla))
            {
                SQL = "INSERT INTO " + tabla + "(nombre, precio) values(" + "\"" + nombre + "\"," + precio + ");";
            }
            else if ("tiendas".equals(tabla))
            {
                SQL = "INSERT INTO " + tabla + "(nombre, numero) values(" + "\"" + nombre + "\"," + precio + ");";
            }
            sentencia.executeUpdate(SQL);

            System.out.println("OK!");

            sentencia.close();
            conexion.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {

            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void delete(String tabla, int id)
    {

        try
        {
            int ai = 0;
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);

            sentencia = conexion.createStatement();

            if ("productos".equals(tabla))
            {
                resultado = sentencia.executeQuery("SELECT AUTO_INCREMENT\n"
                        + "FROM information_schema.TABLES\n"
                        + "WHERE TABLE_SCHEMA = \"bodega\"\n"
                        + "AND TABLE_NAME = \"productos\"");
                while (resultado.next())
                {
                    ai = resultado.getInt("AUTO_INCREMENT");
                }
            }
            else if ("tiendas".equals(tabla))
            {

                resultado = sentencia.executeQuery("SELECT AUTO_INCREMENT\n"
                        + "FROM information_schema.TABLES\n"
                        + "WHERE TABLE_SCHEMA = \"bodega\"\n"
                        + "AND TABLE_NAME = \"tiendas\"");
                while (resultado.next())
                {
                    ai = resultado.getInt("AUTO_INCREMENT");
                }
            }

            SQL = "DELETE FROM " + tabla + " where id = " + id + "; ";

            sentencia.executeUpdate(SQL);

            SQL = "ALTER TABLE " + tabla + " AUTO_INCREMENT = " + (ai - 1) + ";";
            sentencia.executeUpdate(SQL);
            System.out.println("OK!");

            sentencia.close();
            conexion.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {

            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void update(String tabla, int id, String nombre, int precio)
    {

        try
        {

            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);

            sentencia = conexion.createStatement();

            if ("productos".equals(tabla))
            {
                SQL = "UPDATE " + tabla + " SET nombre = '" + nombre + "', precio = '" + precio + "' where id = " + id;
            }
            else if ("tiendas".equals(tabla))
            {
                SQL = "UPDATE " + tabla + " SET nombre = '" + nombre + "', numero = '" + precio + "' where id = " + id;
            }

            sentencia.executeUpdate(SQL);
            System.out.println("OK!");

            sentencia.close();
            conexion.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {

            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void getData(JTable table, String tabla)
    {
        try
        {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);
            
            sentencia = conexion.createStatement();

            SQL = "SELECT * FROM " + tabla + " ORDER BY " + tabla + ".id";

            resultado = sentencia.executeQuery(SQL);

            int fila = 0;
            while (resultado.next())
            {
                table.setValueAt(resultado.getInt("id"), fila, 0);
                table.setValueAt(resultado.getString("nombre"), fila, 1);

                if ("productos".equals(tabla))
                {
                    table.setValueAt(resultado.getInt("precio"), fila, 2);
                }
                else if ("tiendas".equals(tabla))
                {
                    table.setValueAt(resultado.getInt("numero"), fila, 2);
                }

                fila++;
            }
            

            System.out.println("OK!");

            sentencia.close();
            conexion.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {

            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

}
