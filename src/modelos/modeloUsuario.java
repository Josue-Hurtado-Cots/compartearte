package modelos;

import java.sql.*;
import java.util.HashMap;

import javax.naming.spi.DirStateFactory.Result;


public class modeloUsuario {

	Connection conexion;
	
	public modeloUsuario(Connection con){
		conexion = con;
	}
	
	/**
	 * Metodo que recibe como parametros el correo del usuario y la contrase�a encriptada, y comprueba con la bbdd si existe algun registro con los mismos datos.
	 * De ser asi, devolvera true. Si no, devolvera false.
	 * @param correo
	 * @param password
	 * @return HashMap<String, Object>
	 */
	public Object[] comprobarLogin(String correo, String password){
		String sentencia = "select usuario.id, usuario.correo, usuario.nombreUsuario, usuario.baneado, usuario.\"nivelPermisos\", "+
				"(select count(respuesta.id) as respuestas from compartearte.respuesta where respuesta.\"idAutor\" = (select usuario.id"+
				" from compartearte.usuario where correo like ?))"+
				" from compartearte.usuario where correo like ? and password like ?";
		Object datosUsuario[] = new Object[7];
		ResultSet rs;
		try {
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, correo);
			statement.setString(2, correo);
			statement.setString(3, password);
			rs = statement.executeQuery();
			/*Si el login ha sido correcto, el resultset deberia tener 1 registro, si no, no ha sido correcto*/
			if(rs.absolute(1)){
				/*Metemos en un hashmap los datos del usuario*/
				datosUsuario[0] = true;
				datosUsuario[1] = rs.getInt("id");
				datosUsuario[2] = rs.getString("correo");
				datosUsuario[3] = rs.getString("nombreUsuario");
				datosUsuario[4] = rs.getBoolean("baneado");
				datosUsuario[5] = rs.getInt("nivelPermisos");
				datosUsuario[6] = rs.getInt("respuestas");
			}else{
				/*Si el login es incorrecto, metemos el valor false para el que el controlador se encargue de hacer lo que deba.*/
				datosUsuario[0] = false;
			}
		} catch (SQLException e) {
			System.out.println("Error al comprobar el login del usuario - modeloUsuario@comprobarLogin");
		}
		return datosUsuario;
	}

	
	
	/**
	 * Registra a un nuevo usuario con los parametros pasados a la funcion y devuelve si se ha insertado correctamente o no.
	 * @param usuario
	 * @param correo
	 * @param password
	 * @param telefono
	 * @return
	 */
	public boolean guardarUsuario(String usuario, String correo, String password, String telefono) {
		String sentencia = "insert into compartearte.usuario(correo, nombreUsuario, password, telefono) values (?, ?, ?, ?)";
		int resultadoRegistro = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setString(1, correo);
			statement.setString(2, usuario);
			statement.setString(3, password);
			statement.setString(4, telefono);
			resultadoRegistro = statement.executeUpdate();
			if(resultadoRegistro == 1){
				return true;
			}else{
				return false;
			}
		}catch (SQLException E){
			System.out.println("Error al registrar usuario - modeloUsuario@guardarUsuario");
			return false;
		}
	}
	


		
	/**
	 * Devuelve el numero de usuarios totales
	 * @return
	 */
	public int getUsuarios() {
		String sentencia = "select count(id) from compartearte.usuario";
		ResultSet rs;
		int numUsuarios = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			rs.next();
			numUsuarios = rs.getInt("count");
		}catch (SQLException e){
			System.out.println("Error al intentar obtener la cantidad de usuarios - controladorPDF@getUsuarios");
		}
		return numUsuarios;
	}

	
	/**
	 * Devuelve el numero de usuarios baneados
	 * @return
	 */
	public int getUsuariosBaneados() {
		String sentencia = "select count(id) from compartearte.usuario where baneado = true";
		ResultSet rs;
		int numUsuarios = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			rs.next();
			numUsuarios = rs.getInt("count");
		}catch (SQLException e){
			System.out.println("Error al intentar obtener la cantidad de usuarios baneados - controladorPDF@getUsuariosBaneados");
		}
		return numUsuarios;
	}

	
	/**
	 * Devuelve los usuarios
	 * @return
	 */
	public String[][] cargarUsuarios() {
		String sentencia = "select id, correo, nombreusuario, telefono, \"nivelPermisos\", baneado, incidencias from compartearte.usuario order by id";
		ResultSet rs;
		int numUsuarios = 0;
		String[][] datos = null;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery();
			rs.last();
			numUsuarios = rs.getRow();
			datos = new String[numUsuarios][7];
			rs.first();
			for(int i=0;i<datos.length;i++){
				datos[i][0] = String.valueOf(rs.getInt("id"));
				datos[i][1] = rs.getString("correo");
				datos[i][2] = rs.getString("nombreusuario");
				datos[i][3] = rs.getString("telefono");
				datos[i][4] = String.valueOf(rs.getInt("nivelPermisos"));
				datos[i][5] = String.valueOf(rs.getBoolean("baneado"));
				datos[i][6] = String.valueOf(rs.getInt("incidencias"));
				rs.next();
			}
		}catch (SQLException e){
			System.out.println("Error al obtener la lista de usuarios - modeloUsuario@cargarUsuarios");
		}
		return datos;
	}

	
	/**
	 * Comprueba si un usuario esta baneado o no.
	 * @param id
	 * @return
	 */
	public boolean comprobarBan(int id) {
		String sentencia = "select baneado from compartearte.usuario where id = ?";
		ResultSet rs;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			rs.next();
			if(rs.getBoolean("baneado")==false){
				return false;
			}else{
				return true;
			}
		}catch(SQLException e){
			System.out.println("Error al comprobar si el usuario está baneado o no - modeloUsuario@comprobarBan");
			return false;
		}
	}

	
	/**
	 * Si el usuario esta baneado, lo desbanea. Si no esta baneado, lo banea.
	 * @param bool
	 * @param id
	 */
	public void cambiarBaneo(boolean bool, int id) {
		String sentencia = "update compartearte.usuario set baneado = ? where id = ?";
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setBoolean(1, bool);
			statement.setInt(2, id);
			statement.executeUpdate();
		}catch(SQLException e){
			System.out.println("Error al banear/desbanear usuario - modeloUsuario@cambiarBaneo");
		}
	}

	
	/**
	 * Devuelve el numero de incidencias del usuario cuyo id coincide con el pasado por parametro.
	 * @param id
	 * @return
	 */
	public int incidencias(int id) {
		String sentencia = "select incidencias from compartearte.usuario where id = ?";
		int incidencias = 0;
		ResultSet rs;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			rs.next();
			incidencias = rs.getInt("incidencias");
			return incidencias;
		}catch(SQLException e){
			System.out.println("Error al obtener el numero de incidencias del usuario - modeloUsuario@incidencias");
			return 0;
		}
	}

	
	/**
	 * Aumenta en 1 el numero de incidencias del usuario.
	 * @param id
	 * @param incidencias
	 */
	public void nuevaIncidencia(int id, int incidencias) {
		String sentencia = "update compartearte.usuario set incidencias = ? where id = ?";
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, incidencias);
			statement.setInt(2, id);
			statement.executeUpdate();
		}catch(SQLException e){
			System.out.println("Error al banear/desbanear usuario - modeloUsuario@cambiarBaneo");
		}		
	}
	
	
	/**
	 * Comprueba si la contraseña pasada en el formulario de modificacion es correcta o no
	 * @param idUsuario
	 * @param pass
	 * @return
	 */
	public boolean comprobarPassword(int idUsuario, String pass){
		String sentencia = "select id from compartearte.usuario where id = ? and password = ?";
		ResultSet rs;
		try {
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, idUsuario);
			statement.setString(2, pass);
			rs = statement.executeQuery();
			/*Si la contraseña es correcta, el resultset deberia tener 1 registro, si no, no es correcta*/
			if(rs.absolute(1)){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error al comprobar la contraseña del usuario - modeloUsuario@comprobarPassword");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Actualiza el correo, contraseña, o ambos, del usuario
	 * @param idUsuario
	 * @param correo
	 * @param passNueva
	 */
	public void actualizarDatos(int idUsuario, String correo, String passNueva) {
		String sentencia;
		PreparedStatement statement;
		try{
			if(passNueva.length()>6){
				sentencia = "update compartearte.usuario set correo = ?, password = ? where id = ?";
				statement = conexion.prepareStatement(sentencia);
				statement.setString(1, correo);
				statement.setString(2, passNueva);
				statement.setInt(3, idUsuario);
			}else{
				sentencia = "update compartearte.usuario set correo = ? where id = ?";
				statement = conexion.prepareStatement(sentencia);
				statement.setString(1, correo);
				statement.setInt(2, idUsuario);
			}
			statement.executeUpdate();
		}catch(SQLException e){
			System.out.println("Error al modificar usuario - modeloUsuario@actualizarDatos");
		}
	}

	
	/**
	 * Obtiene los datos de un usuario y los devuelve a la vista de modificacion.
	 * @param id
	 * @return
	 */
	public String[] obtenerDatosModificacion(int id) {
		String sentencia = "select correo, nombreusuario, telefono, baneado, incidencias, \"nivelPermisos\" from compartearte.usuario where id = ?";
		String[] datos = new String[6];
		ResultSet rs;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			rs.next();
			datos[0] = rs.getString("correo");
			datos[1] = rs.getString("nombreusuario");
			datos[2] = rs.getString("telefono");
			datos[3] = String.valueOf(rs.getBoolean("baneado"));
			datos[4] = String.valueOf(rs.getInt("incidencias"));
			datos[5] = String.valueOf(rs.getInt("nivelPermisos"));
		}catch(SQLException e){
			System.out.println("Error al obtener los datos del usuario - modeloUsuario@obtenerDatosModificacion");
		}
		return datos;
	}

	
	/**
	 * Modifica el usuario con los parametros que recibe
	 * @param id
	 * @param correo
	 * @param nombreUsuario
	 * @param telefono
	 * @param baneado
	 * @param incidencias
	 * @param nivelPermisos
	 * @return
	 */
	public boolean modificarUsuario(int id, String correo, String nombreUsuario, String telefono, boolean baneado, int incidencias, int nivelPermisos) {
		String sentencia = "update compartearte.usuario set correo = ?, nombreusuario = ?, telefono = ?, incidencias = ?,"
				+ " \"nivelPermisos\" = ?, baneado = ? where id = ?";
		int estadoActualizacion = 0;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setString(1, correo);
			statement.setString(2, nombreUsuario);
			statement.setString(3, telefono);
			statement.setInt(4, incidencias);
			statement.setInt(5, nivelPermisos);
			statement.setBoolean(6, baneado);
			statement.setInt(7, id);
			estadoActualizacion = statement.executeUpdate();
			
			if(estadoActualizacion == 1){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			System.out.println("Error al modificar el usuario - modeloUsuario@modificarUsuario");
			return false;
		}
		
	}
	
	
	/**
	 * Devuelve el nombre del usuario cuyo id coincida con el pasado por parametro.
	 * @param idUsuario
	 * @return
	 */
	public String getNombreUsuario(int idUsuario){
		String sentencia = "select nombreusuario from compartearte.usuario where id = ?";
		ResultSet rs;
		String nombre = "";
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			statement.setInt(1, idUsuario);
			rs = statement.executeQuery();
			rs.next();
			nombre = rs.getString("nombreusuario");
		}catch(SQLException e){
			System.out.println("Error al obtener el nombre del usuario - modeloUsuario@getNombreUsuario");
		}
		return nombre;
	}
	
	
}
