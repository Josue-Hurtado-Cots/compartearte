package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class modeloMultiplesTablas {

	
	Connection conexion;
	
	public modeloMultiplesTablas(Connection con){
		conexion = con;
	}

	
	/**
	 * Llama al procedimiento almacenado ultimosArticulos, el cual devolver� una lista de productos.
	 * @param numPag
	 */
	public String[][] ultimosProductos(Integer numPag) {
		String sentencia = "select * from ultimosArticulos(?)";
		ResultSet rs;
		int numElementos = 0;
		String[][] datos = null;
		try {
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, numPag);
			rs = statement.executeQuery();
			/*Comprobamos el numero de elementos que trae la consulta*/
			rs.last();
			numElementos = rs.getRow();
			rs.first();
			/*Recorremos el resultset y vamos metiendo los datos en el array*/
			datos = new String[numElementos][10];
			for (int i=0;i<numElementos;i++) {
				datos[i][0] = String.valueOf(rs.getString("id"));
				datos[i][1] = rs.getString("titulo");
				datos[i][2] = rs.getString("descripcion");
				datos[i][3] = rs.getString("categoria");
				datos[i][4] = rs.getString("provincia");
				datos[i][5] = rs.getString("urlImagen1");
				datos[i][6] = rs.getString("urlImagen2");
				datos[i][7] = rs.getString("urlImagen3");
				datos[i][8] = rs.getString("tipo");
				datos[i][9] = String.valueOf(rs.getDate("fecha"));
				rs.next();
			}
			return datos;
		} catch (SQLException e) {
			System.out.println("Error al cargar los ultimos productos - modeloProcedimientosAlmacenados@ultimosProductos");
			e.printStackTrace();
			return datos;
		}
	}


	/**
	 * Devuelve el numero de elementos para hacer la paginacion
	 * @return
	 */
	public int numeroElementos() {
		String sentencia = "select count (producto) from (select pelicula.descripcion as producto from compartearte.pelicula union"
				+ " select libro.descripcion as producto from compartearte.libro union select videojuego.descripcion as producto"
				+" from compartearte.videojuego) as t1";
		int numElementos = 0;
		ResultSet rs;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia);
			rs = statement.executeQuery();
			rs.next();
			numElementos = rs.getInt("count");
		}catch (SQLException e){
			System.out.println("Error al intentar extraer el numero de productos - modeloProcedimientosAlmacenados@numeroElementos");
			e.printStackTrace();
		}
		return numElementos;
	}


	/**
	 * Devuelve los productos paginados, preparados para modificarlos o eliminarlos.
	 * @param pagina
	 * @return
	 */
	public String[][] cargarProductosAdministracion(int pagina) {
		String sentencia = "select * from ultimosProductosAdministracion(?)";
		ResultSet rs;
		int numElementos;
		String[][] productos = null;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, pagina);
			rs = statement.executeQuery();
			/*Comprobamos el numero de elementos que trae la consulta*/
			rs.last();
			numElementos = rs.getRow();
			rs.first();
			productos = new String[numElementos][7];
			for(int i=0;i<productos.length;i++){
				productos[i][0] = String.valueOf(rs.getInt("id"));
				productos[i][1] = String.valueOf(rs.getInt("idPropietario"));
				productos[i][2] = rs.getString("propietario");
				productos[i][3] = rs.getString("titulo");
				productos[i][4] = rs.getString("categoria");
				productos[i][5] = rs.getString("provincia");
				productos[i][6] = String.valueOf(rs.getDate("fecha"));
				rs.next();
			}
		}catch(SQLException e){
			System.out.println("Error al obtener los productos para la pagina de administracion - modeloMultiplesTablas@cargarProductosAdministracion");
			e.printStackTrace();
		}
		return productos;
	}
	
	
}
