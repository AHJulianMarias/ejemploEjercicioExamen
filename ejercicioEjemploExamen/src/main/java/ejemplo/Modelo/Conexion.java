package ejemplo.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static Conexion instancia;
	private static Connection conexionMySQL;
	private static boolean conectado;

	private Conexion() {
		super();
		this.conectado = conectarse();

	}

	private boolean conectarse() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexionMySQL = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "1234");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static Conexion getInstance() {
		if (instancia == null) {
			instancia = new Conexion();

		}
		return instancia;

	}

	public void cerrarConexionSQL() {
		try {
			conexionMySQL.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean isConectado() {
		return conectado;
	}

	public Connection getConexionMySQL() {
		return conexionMySQL;
	}

}
