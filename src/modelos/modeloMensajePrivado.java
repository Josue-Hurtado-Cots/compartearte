package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class modeloMensajePrivado {

	
	Connection conexion;
	
	public modeloMensajePrivado(Connection con){
		conexion = con;
	}

	
	/**
	 * Crea el mensaje privado.
	 * @param idAutor
	 * @param idReceptor
	 * @param asunto
	 * @param mensaje
	 * @return
	 */
	public boolean crearPrivado(int idAutor, int idReceptor, String asunto, String mensaje) {
		String sentencia = "INSERT INTO compartearte.\"mensajePrivado\"(\"idEmisor\", \"idReceptor\", asunto, mensaje, leido) "+
				"VALUES (?, ?, ?, ?, false)";
		int estadoInsercion = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idAutor);
			statement.setInt(2, idReceptor);
			statement.setString(3, asunto);
			statement.setString(4, mensaje);
			estadoInsercion = statement.executeUpdate();
			if(estadoInsercion==1){
				return true;
			}else{
				return false;
			}
		}catch (SQLException e){
			System.out.println("Error al crear un nuevo mensaje privado - modeloMensajePrivado@crearPrivado");
			return false;
		}
	}


	/**
	 * Carga el numero de mensajes privados sin leer del usuario cuyo id coincida con el pasado por parametro.
	 * @param idUsuario
	 * @return
	 */
	public int notificaciones(int idUsuario) {
		String sentencia = "select count(id) from compartearte.\"mensajePrivado\" where leido = false and \"idReceptor\" = ?";
		ResultSet rs;
		int numeroPrivados = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idUsuario);
			rs = statement.executeQuery();
			rs.next();
			numeroPrivados = rs.getInt("count");
		}catch(SQLException e){
			System.out.println("Error al obtener el numero de mensajes privados del usuario - modeloMensajePrivado@notificaciones");
		}
		return numeroPrivados;
	}
	
	
}
