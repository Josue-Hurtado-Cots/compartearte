package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;


public class modeloTema {

	
	Connection conexion;
	
	public modeloTema(Connection con){
		conexion = con;
	}

	
	/**
	 * Devuelve la lista de temas del subforo cuyo id coincida con el pasado por parametro.
	 * @param pagina
	 * @param idSubforo
	 * @return
	 */
	public String[][] cargarTemas(int pagina, int idSubforo) {
		String sentencia = "select * from ultimosTemas(?, ?)";
		ResultSet rs;
		int numTemas = 0;
		String[][] temas = null;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, pagina);
			statement.setInt(2, idSubforo);
			rs = statement.executeQuery();
			/*Comprobamos el numero de elementos que trae la consulta*/
			rs.last();
			numTemas = rs.getRow();
			rs.first();
			temas = new String[numTemas][5];
			/*Recorremos el resultset y vamos metiendo los datos en el array*/
			for(int i=0;i<numTemas;i++){
				temas[i][0] = String.valueOf(rs.getInt("idTema"));
				temas[i][1] = rs.getString("titulo");
				temas[i][2] = String.valueOf(rs.getTimestamp("fechaCreacion"));
				temas[i][3] = rs.getString("autor");
				temas[i][4] = rs.getString("tituloSubforo");
				rs.next();
			}
		}catch(SQLException e){
			System.out.println("Error al cargar la lista de temas - modeloTema@cargarTemas");
		}
		return temas;
	}


	/**
	 * Devuelve el numero de temas del subforo cuyo id coincida con el que recibe por parametro.
	 * @param idSubforo
	 * @return
	 */
	public int getNumeroTemas(int idSubforo) {
		String sentencia = "select count(id) from compartearte.tema where \"idSubforo\" = ?";
		ResultSet rs;
		int cantidad = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idSubforo);
			rs = statement.executeQuery();
			rs.next();
			cantidad = Integer.parseInt(rs.getString("count"));
		}catch (SQLException e){
			System.out.println("Error al obtener la cantidad de temas - modeloTemas@getNumeroTemas");
			e.printStackTrace();
		}
		return cantidad;
	}


	/**
	 * Devuelve la información del tema cuyo id coincida con el pasado por parametro.
	 * @param idTema
	 * @return
	 */
	public String[] informacionTema(int idTema) {
		String sentencia = "select tema.id, \"fechaCreacion\", titulo, mensaje, cerrado, \"idAutor\", "+
			"(select nombreusuario from compartearte.usuario where usuario.id = tema.\"idAutor\") as \"nombreAutor\", "+
			"(select count(id) from compartearte.respuesta where respuesta.\"idAutor\" = tema.\"idAutor\") as \"numComentarios\", "+
			"(select \"nivelPermisos\" from compartearte.usuario where \"idAutor\" = usuario.id) "+
			"from compartearte.tema where tema.id = ?";
		ResultSet rs;
		String[] informacionTema = new String[8];
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idTema);
			rs = statement.executeQuery();
			rs.next();
			informacionTema[0] = String.valueOf(rs.getInt("id"));
			informacionTema[1] = String.valueOf(rs.getTimestamp("fechaCreacion"));
			informacionTema[2] = rs.getString("titulo");
			informacionTema[3] = rs.getString("mensaje");
			informacionTema[4] = String.valueOf(rs.getBoolean("cerrado"));
			informacionTema[5] = String.valueOf(rs.getInt("idAutor"));
			informacionTema[6] = rs.getString("nombreAutor");
			informacionTema[7] = rs.getString("nivelPermisos");
		}catch(SQLException e){
			System.out.println("Error al obtener la informacion del tema - modeloTema@informacionTema");
		}
		return informacionTema;
	}


	/**
	 * Devuelve el numero de temas creado ese mes.
	 * @param fecha
	 * @return
	 */
	public int getTemasMes(String fecha) {
		String sentencia = "select count(id) from compartearte.tema where \"fechaCreacion\"::text like '%"+fecha+"%'";
		ResultSet rs;
		int cantidad = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			rs.next();
			cantidad = Integer.parseInt(rs.getString("count"));
		}catch (SQLException e){
			System.out.println("Error al obtener la cantidad de temas - modeloTema@getTemasMes");
		}
		return cantidad;
	}


	/**
	 * Crea un nuevo tema.
	 * @param idUsuario
	 * @param idSubforo
	 * @param titulo
	 * @param mensaje
	 * @return
	 */
	public boolean crearTema(int idUsuario, int idSubforo, String titulo, String mensaje) {
		String sentencia = "INSERT INTO compartearte.tema(\"idSubforo\", \"fechaCreacion\", titulo, mensaje, \"borradoLogico\", cerrado, \"idAutor\") "+
				"VALUES (?, now(), ?, ?, false, false, ?)";
		int estadoInsercion = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idSubforo);
			statement.setString(2, titulo);
			statement.setString(3, mensaje);
			statement.setInt(4, idUsuario);
			estadoInsercion = statement.executeUpdate();
			if(estadoInsercion==1){
				return true;
			}else{
				return false;
			}
		}catch (SQLException e){
			System.out.println("Error al crear un nuevo tema - modeloTema@crearTema");
			return false;
		}
	}


	/**
	 * Carga los 10 ultimos temas creados
	 * @return
	 */
	public String[][] cargarUltimosTemas() {
		String sentencia = "select tema.id, (select nombreusuario from compartearte.usuario where usuario.id = tema.\"idAutor\"), titulo from"
				+ " compartearte.tema order by \"fechaCreacion\" desc limit 10 offset 0";
		ResultSet rs;
		String[][] temas = new String[10][3];
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			for(int i=0;i<10;i++){
				rs.next();
				temas[i][0] = String.valueOf(rs.getInt("id"));
				temas[i][1] = rs.getString("nombreusuario");
				temas[i][2] = rs.getString("titulo");
			}
		}catch(SQLException e){
			System.out.println("Error al cargar la lista de temas - modeloTema@cargarTemas");
			e.printStackTrace();
		}
		return temas;
	}

}
