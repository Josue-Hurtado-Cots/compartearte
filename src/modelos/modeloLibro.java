package modelos;

import java.security.KeyStore.ProtectionParameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class modeloLibro {

	Connection conexion;
	
	public modeloLibro(Connection con){
		conexion = con;
	}

	
	/**
	 * Almacena en la tabla compartearte.libro, un nuevo libro subido por un usuario.
	 * @param idUsuario
	 * @param titulo
	 * @param descripcion
	 * @param provincia
	 * @param categoriaLibro
	 * @param urlImg1
	 * @param urlImg2
	 * @param urlImg3
	 * @param fecha
	 * @return
	 */
	public boolean almacenarLibro(int idUsuario, String titulo, String descripcion, String provincia,
			String categoriaLibro, String urlImg1, String urlImg2, String urlImg3, String fecha) {
		int resultadoInsercion = 0;
		String sentencia = "insert into compartearte.libro(\"idPropietario\", titulo, descripcion, provincia, categoria, \"urlImagen1\", \"urlImagen2\", \"urlImagen3\", fecha) "+
							"values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idUsuario);
			statement.setString(2, titulo);
			statement.setString(3, descripcion);
			statement.setString(4, provincia);
			statement.setString(5, categoriaLibro);
			statement.setString(6, urlImg1);
			statement.setString(7, urlImg2);
			statement.setString(8, urlImg3);
			statement.setDate(9, new java.sql.Date(format.parse(fecha).getTime()));
			resultadoInsercion = statement.executeUpdate();
			/*Comprobamos el exito o error de la consulta y en funcion de esto devolvemos true o false*/
			if(resultadoInsercion == 1){
				return true;
			}else{
				return false;
			}
		}catch (SQLException e){
			System.out.println("Error al insertar un nuevo libro - modeloLibro@almacenarLibro");
			return false;
		} catch (ParseException e) {
			System.out.println("Error al convertir de String a Date - modeloLibro@almacenarLibro");
			return false;
		}
	}


	/**
	 * Carga la lista de libros del usuario cuyo id coincida con el pasado por parametro.
	 * @param idUsuario
	 * @return
	 */
	public Object[][] librosUsuario(int idUsuario) {
		Object[][] datos = null;
		String sentencia = "select * from compartearte.libro where \"idPropietario\" = ? ";
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
			datos = new Object[numElementos][8];
			for (int i=0;i<numElementos;i++) {
				datos[i][0] = rs.getString("titulo");
				datos[i][1] = rs.getString("descripcion");
				datos[i][2] = rs.getString("provincia");
				datos[i][3] = rs.getString("categoria");
				datos[i][4] = rs.getString("urlImagen1");
				datos[i][5] = rs.getString("urlImagen2");
				datos[i][6] = rs.getString("urlImagen3");
				datos[i][7] = rs.getInt("id");
				rs.next();
			}
		}catch (SQLException e){
			System.out.println("Error al obtener los libros del usuario - modeloLibro@librosUsuario");
		}
		return datos;
	}


	/**
	 * Borra el libro de la bbdd cuyo id coincida con el pasado por parametro.
	 * @param idLibro
	 * @return
	 */
	public boolean borrarLibro(int idLibro) {
		String sentencia = "delete from compartearte.libro where id = ?";
		try {
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idLibro);
			/*Si al hacer la consulta devuelve 0, no se ha borrado, asi que devolver� false. Caso contrario, true*/
			if(statement.executeUpdate()!=0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error borrar el libro - modeloLibro@borrarLibro");
			return false;
		}
	}


	/**
	 * Devuelve la informacion de un libro.
	 * @param id
	 * @return
	 */
	public String[] cargarLibro(int id) {
		String sentencia = "select titulo, nombreusuario, descripcion, provincia, categoria, \"urlImagen1\", "
				+"\"urlImagen2\", \"urlImagen3\", fecha, telefono from compartearte.libro L join compartearte.usuario U "
				+"on L.\"idPropietario\" = U.id where L.id = ?";
		ResultSet rs;
		String[] datos = new String[11];
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			rs.next();
			datos[0] = "1";
			datos[1] = rs.getString("titulo");
			datos[2] = rs.getString("nombreusuario");
			datos[3] = rs.getString("descripcion");
			datos[4] = rs.getString("provincia");
			datos[5] = rs.getString("categoria");
			datos[6] = rs.getString("urlImagen1");
			datos[7] = rs.getString("urlImagen2");
			datos[8] = rs.getString("urlImagen3");
			datos[9] = String.valueOf(rs.getDate("fecha"));
			datos[10] = rs.getString("telefono");
			return datos;
		}catch(SQLException e){
			System.out.println("Error al intentar obtener los datos de un libro - modeloLibro@cargarLibro");
			return datos;
		}
	}


	/**
	 * Recibe una serie de parametros, y en funcion de estos devuelve una serie de libros.
	 * @param provincia
	 * @param categoriaLibro
	 * @param nombre
	 * @param tipoOrden
	 * @return
	 */
	public String[][] filtrarResultados(String provincia, String categoriaLibro, String nombre, String tipoOrden) {
		String sentencia = "select * from compartearte.libro where provincia like ? and categoria like ? and titulo like ?"+tipoOrden;
		ResultSet rs;
		String[][] datos = null;
		int numElementos = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, "%"+provincia+"%");
			statement.setString(2, "%"+categoriaLibro+"%");
			statement.setString(3, "%"+nombre+"%");
			rs = statement.executeQuery();
			/*Comprobamos el numero de elementos que trae la consulta*/
			rs.last();
			numElementos = rs.getRow();
			rs.first();
			/*Recorremos el resultset y vamos metiendo los datos en el array*/
			datos = new String[numElementos][9];
			for (int i=0;i<numElementos;i++) {
				datos[i][0] = "Libro";
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
			System.out.println("Error al intentar obtener los datos de los libros filtrados - modeloLibro@filtrarResultados");
			e.printStackTrace();
			return null;
		}
	}
	

	
	/**
	 * Devuelve la cantidad de libros nuevos subidos ese mes.
	 * @param fecha
	 * @return
	 */
	public int getCantidadLibros(String fecha) {
		String sentencia = "select count(titulo) from compartearte.libro where fecha::text like '%"+fecha+"%'";
		ResultSet rs;
		int cantidad = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			rs.next();
			cantidad = Integer.parseInt(rs.getString("count"));
		}catch (SQLException e){
			System.out.println("Error al obtener la cantidad de libros - modeloLibro@getCantidadLibros");
		}
		return cantidad;
	}
	
	
}
