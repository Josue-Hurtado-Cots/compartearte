package controladoresInicio;
import java.sql.Connection;
import lib.ConexionBBDD;

public class controladorConexionBBDD {
	
	
	ConexionBBDD conexion;

	/**
	 * Crea la conexion con la base de datos.
	 */
	public controladorConexionBBDD(){
		conexion = new ConexionBBDD("ns3034756.ip-91-121-81.eu", "ajhurtado", "ajhurtado", "ajhurtado");
	}
	
	/**
	 * Devuelve la conexion con la bbdd creada.
	 * @return
	 */
	public Connection getConexion(){
		return conexion.getConexion();
	}
	
}
