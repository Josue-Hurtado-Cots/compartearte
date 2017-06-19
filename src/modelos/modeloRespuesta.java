package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class modeloRespuesta {

	Connection conexion;
	
	public modeloRespuesta(Connection con){
		conexion = con;
	}

	
	/**
	 * Devuelve las respuestas del tema cuyo id coincida con el pasado por parametro, y de la pagina recibida por parametro.
	 * @param pagina
	 * @param idTema
	 * @return
	 */
	public String[][] respuestasTema(int pagina, int idTema) {
		String sentencia = "select * from ultimasRespuestas(?, ?)";
		ResultSet rs;
		int numRespuestas = 0;
		String[][] respuestas = null;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, pagina);
			statement.setInt(2, idTema);
			rs = statement.executeQuery();
			/*Comprobamos el numero de elementos que trae la consulta*/
			rs.last();
			numRespuestas = rs.getRow();
			rs.first();
			respuestas = new String[numRespuestas][6];
			/*Recorremos el resultset y vamos metiendo los datos en el array*/
			for(int i=0;i<numRespuestas;i++){
				respuestas[i][0] = String.valueOf(rs.getInt("idRespuesta"));
				respuestas[i][1] = String.valueOf(rs.getTimestamp("fechaCreacion"));
				respuestas[i][2] = rs.getString("mensaje");
				respuestas[i][3] = String.valueOf(rs.getInt("idAutor"));
				respuestas[i][4] = rs.getString("autor");
				respuestas[i][5] = String.valueOf(rs.getInt("nivelPermisos"));
				rs.next();
			}
		}catch(SQLException e){
			System.out.println("Error al cargar la lista de respuestas - modeloRespues@respuestasTema");
		}
		return respuestas;
	}


	/**
	 * Guarda la respuesta del usuario en el tema pasado por parametro.
	 * @param idUsuario
	 * @param idTema
	 * @param respuesta
	 * @return
	 */
	public boolean guardarRespuesta(int idUsuario, int idTema, String respuesta) {
		String sentencia = "insert into compartearte.respuesta(\"idTema\", \"idAutor\", mensaje, fecha) values(?, ?, ?, now())";
		int estado = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idTema);
			statement.setInt(2, idUsuario);
			statement.setString(3, respuesta);
			estado = statement.executeUpdate();
			if(estado==1){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			System.out.println("Error al guardar la respuesta del usuario - modeloRespuesta@guardarRespuesta");
			return false;
		}
	}


	/**
	 * Devuelve el numero de respuestas escritas ese mes.
	 * @param fecha
	 * @return
	 */
	public int getRespuestasMes(String fecha) {
		String sentencia = "select count(id) from compartearte.respuesta where fecha::text like '%"+fecha+"%'";
		ResultSet rs;
		int cantidad = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			rs.next();
			cantidad = Integer.parseInt(rs.getString("count"));
		}catch (SQLException e){
			System.out.println("Error al obtener la cantidad de respuestas - modeloRespuesta@getRespuestasMes");
		}
		return cantidad;
	}
	
	
	
	
}
