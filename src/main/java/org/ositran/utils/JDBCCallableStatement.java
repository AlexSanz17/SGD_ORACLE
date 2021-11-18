package org.ositran.utils;


import gob.ositran.siged.config.SigedProperties;


import java.sql.DriverManager;
import java.sql.Connection;



public class JDBCCallableStatement {

	public   Connection getDBConnection() throws Exception  {

		Connection dbConnection = null;

		try {
			Class.forName(SigedProperties.getPropertyByKey("bd.driver"));
		} catch (Exception e) {
			throw e;
		}

		try {
			dbConnection = DriverManager.getConnection(
					SigedProperties.getPropertyByKey("bd.url"),
					SigedProperties.getPropertyByKey("bd.usuario"),
					SigedProperties.getPropertyByKey("bd.password"));
			return dbConnection;

		} catch (Exception e) {
			throw e;
		}

	}

}
