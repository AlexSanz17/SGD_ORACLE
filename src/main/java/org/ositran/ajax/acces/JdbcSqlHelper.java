/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.acces;

import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.log4j.Logger;

public class JdbcSqlHelper {

    private static Logger log = Logger.getLogger(JdbcSqlHelper.class);

    public static Connection EjecutarConeccion() {
        return EjecutarConeccion2("Mysql", "siged", "siged", "siged");
    }

    public static Connection EjecutarConeccion2(String NombreGestorBD, String NombreBD, String NombreUsuario, String Contrasenia) {
        Connection cn = null;

        try {
            if (NombreGestorBD.equals("ODBC")) {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
                cn = DriverManager.getConnection("jdbc:odbc:" + NombreBD, NombreUsuario, Contrasenia);
            }
            if (NombreGestorBD.equals("Oracle")) {
                Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                cn = DriverManager.getConnection("jdbc:oracle:thin:@" + NombreBD, NombreUsuario, Contrasenia);
            }
            if (NombreGestorBD.equals("Postgres")) {
                Class.forName("org.postgresql.Driver").newInstance();
                cn = DriverManager.getConnection("jdbc:postgresql:" + NombreBD, NombreUsuario, Contrasenia);
            }
            if (NombreGestorBD.equals("Mysql")) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String Cadena = "jdbc:mysql://192.168.1.13:3306/" + NombreBD + "?user=" + NombreUsuario + "&password=" + Contrasenia;
                cn = DriverManager.getConnection(Cadena);

            }
        } catch (Exception e) {
            log.error("No se encuentra la libreria JDBC o no esta referenciado");
        }

        return cn;
    }
}
