package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static Connection getConnection(final String nomeBanco, final String usuario, final String senha) {
		return getConnection("localhost", "5432", nomeBanco, usuario, senha);
	}
	
	public static Connection getConnection(final String ipBanco, final String portaBanco, final String nomeBanco, final String usuario, final String senha) {
		Connection Conn = null;
		
		try {
			Conn = DriverManager.getConnection("jdbc:postgresql://"+ipBanco+":"+portaBanco+"/"+nomeBanco, usuario, senha);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Conn;
	}

}
