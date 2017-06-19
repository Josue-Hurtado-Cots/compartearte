package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class modeloVideojuego {

	Connection conexion;
	
	public modeloVideojuego(Connection con){
		conexion = con;
	}

	
	/**
	 * Almacena un nuevo videojuego en la base de datos.
	 * @param idUsuario
	 * @param titulo
	 * @param descripcion
	 * @param provincia
	 * @param categoriaVideojuego
	 * @param urlImg1
	 * @param urlImg2
	 * @param urlImg3
	 * @param consola
	 * @param fecha
	 * @return
	 */
	public boolean almacenarVideojuego(int idUsuario, String titulo, String descripcion, String provincia,
			String categoriaVideojuego, String urlImg1, String urlImg2, String urlImg3, String consola, String fecha) {
		int resultadoInsercion = 0;
		String sentencia = "insert into compartearte.videojuego(\"idPropietario\", titulo, descripcion, provincia, categoria, \"urlImagen1\", \"urlImagen2\","+
							" \"urlImagen3\", consola, fecha) "+
							"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idUsuario);
			statement.setString(2, titulo);
			statement.setString(3, descripcion);
			statement.setString(4, provincia);
			statement.setString(5, categoriaVideojuego);
			statement.setString(6, urlImg1);
			statement.setString(7, urlImg2);
			statement.setString(8, urlImg3);
			statement.setString(9, consola);
			statement.setDate(10, new java.sql.Date(format.parse(fecha).getTime()));
			resultadoInsercion = statement.executeUpdate();
			/*Comprobamos el exito o error de la consulta y en funcion de esto devolvemos true o false*/
			if(resultadoInsercion == 1){
				return true;
			}else{
				return false;
			}
		}catch (SQLException e){
			System.out.println("Error al insertar un nuevo videojuego - modeloLibro@almacenarLibro");
			return false;
		} catch (ParseException e) {
			System.out.println("Error al convertir de String a Date - modeloVideojuego@almacenarVideojuego");
			return false;
		}
	}

	
	/**
	 * Carga la lista de videojuegos del usuario cuyo id coincida con el pasado por parametro.
	 * @param idUsuario
	 * @return
	 */
	public Object[][] videojuegosUsuario(int idUsuario) {
		Object[][] datos = null;
		String sentencia = "select * from compartearte.videojuego where \"idPropietario\" = ? ";
		ResultSet rs;
		int numElementos;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, idUsuario);
			rs = statement.executeQuery();
			/*Comprobamos el numero de elementos que trae la consulta*/
			rs.last();
			numElementos = rs.getRow();
			rs.first();
			/*Recorremos el resultset y vamos metiendo los datos en el array*/
			datos = new Object[numElementos][9];
			for (int i=0;i<numElementos;i++) {
				datos[i][0] = rs.getString("titulo");
				datos[i][1] = rs.getString("descripcion");
				datos[i][2] = rs.getString("provincia");
				datos[i][3] = rs.getString("categoria");
				datos[i][4] = rs.getString("urlImagen1");
				datos[i][5] = rs.getString("urlImagen2");
				datos[i][6] = rs.getString("urlImagen3");
				datos[i][7] = rs.getString("id");
				datos[i][8] = rs.getString("consola");
				rs.next();
			}
		}catch (SQLException e){
			System.out.println("Error al obtener los videojuegos del usuario - modeloVideojuego@videojuegosUsuario");
		}
		return datos;
	}

	/**
	 * Borra el videojuego de la bbdd cuyo id coincida con el que reciba por parametro.
	 * @param idVideojuego
	 * @return
	 */
	public boolean borrarVideojuego(int idVideojuego) {
		String sentencia = "delete from compartearte.videojuego where id = ?";
		try {
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idVideojuego);
			/*Si al hacer la consulta devuelve 0, no se ha borrado, asi que devolver� false. Caso contrario, true*/
			if(statement.executeUpdate()!=0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error borrar el videojuego - modeloVideojuego@borrarVideojuego");
			return false;
		}
	}


	/**
	 * Devuelve la informacion de un videojuego.
	 * @param id
	 * @return
	 */
	public String[] cargarVideojuego(int id) {
		String sentencia = "select titulo, nombreusuario, descripcion, provincia, categoria, \"urlImagen1\", "
				+"\"urlImagen2\", \"urlImagen3\", fecha, consola, telefono from compartearte.videojuego V join compartearte.usuario U "
				+"on V.\"idPropietario\" = U.id where V.id = ?";
		ResultSet rs;
		String[] datos = new String[12];
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			rs.next();
			datos[0] = "3";
			datos[1] = rs.getString("titulo");
			datos[2] = rs.getString("nombreusuario");
			datos[3] = rs.getString("descripcion");
			datos[4] = rs.getString("provincia");
			datos[5] = rs.getString("categoria");
			datos[6] = rs.getString("urlImagen1");
			datos[7] = rs.getString("urlImagen2");
			datos[8] = rs.getString("urlImagen3");
			datos[9] = String.valueOf(rs.getDate("fecha"));
			datos[10] = rs.getString("consola");
			datos[11] = rs.getString("telefono");
			return datos;
		}catch(SQLException e){
			System.out.println("Error al intentar obtener los datos de un videojuego - modeloVideojuego@cargarVideojuego");
			e.printStackTrace();
			return datos;
		}
	}


	/**
	 * Recibe una serie de parametros, y en funcion de estos devuelve una serie de videojuegos.
	 * @param provincia
	 * @param categoriaVideojuego
	 * @param nombre
	 * @param tipoOrden
	 * @return
	 */
	public String[][] filtrarResultados(String provincia, String categoriaVideojuego, String nombre, String tipoOrden) {
		String sentencia = "select * from compartearte.videojuego where provincia like ? and categoria like ? and titulo like ?"+tipoOrden;
		ResultSet rs;
		String[][] datos = null;
		int numElementos = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, "%"+provincia+"%");
			statement.setString(2, "%"+categoriaVideojuego+"%");
			statement.setString(3, "%"+nombre+"%");
			rs = statement.executeQuery();
			/*Comprobamos el numero de elementos que trae la consulta*/
			rs.last();
			numElementos = rs.getRow();
			rs.first();
			/*Recorremos el resultset y vamos metiendo los datos en el array*/
			datos = new String[numElementos][9];
			for (int i=0;i<numElementos;i++) {
				datos[i][0] = "Videojuego";
				datos[i][1] = String.valueOf(rs.getInt("id"));
				datos[i][2] = rs.getString("idPropietario");
				datos[i][3] = rs.getString("titulo");
				datos[i][4] = rs.getString("descripcion");
				datos[i][5] = rs.getString("provincia");
				datos[i][6] = rs.getString("categoria");
				datos[i][7] = rs.getString("urlImagen1");
				datos[i][8] = String.valueOf(rs.getDate("fecha"));
				rs.next();
			}
			/*Devolvemos los libros*/
			return datos;
		}catch(SQLException e){
			System.out.println("Error al intentar obtener los datos de los videojuegos filtrados - modeloVideojuego@filtrarResultados");
			return null;
		}
	}



		
	/**
	 * Devuelve la cantidad de videojuegos nuevos subidos ese mes.
	 * @param fecha
	 * @return
	 */
	public int getCantidadVideojuegos(String fecha) {
		String sentencia = "select count(titulo) from compartearte.videojuego where fecha::text like '%"+fecha+"%'";
		ResultSet rs;
		int cantidad = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			rs.next();
			cantidad = Integer.parseInt(rs.getString("count"));
		}catch (SQLException e){
			System.out.println("Error al obtener la cantidad de videojuegos - modeloVideojuego@getCantidadVideojuegos");
		}
		return cantidad;
	}
}

