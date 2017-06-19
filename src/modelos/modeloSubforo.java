package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class modeloSubforo {

	Connection conexion;
	
	public modeloSubforo(Connection con){
		conexion = con;
	}

	/**
	 * Devuelve la lista de subforos.
	 * @return
	 */
	public String[][] listadoSubforos() {
		String sentencia = "select *, (select count(id) from compartearte.tema where compartearte.subforo.id = compartearte.tema.\"idSubforo\") as \"temas\", "+
				"(select count(respuesta.id) from compartearte.respuesta, compartearte.tema where compartearte.respuesta.\"idTema\" = compartearte.tema.id"+
				" and compartearte.tema.\"idSubforo\" = compartearte.subforo.id) as \"respuestas\" from compartearte.subforo order by id";
		ResultSet rs;
		int numSubforos = 0;
		String[][] datos = null;
		try{
			PreparedStatement statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery();
			rs.last();
			numSubforos = rs.getRow();
			datos = new String[numSubforos][7];
			rs.first();
			for(int i=0;i<datos.length;i++){
				datos[i][0] = String.valueOf(rs.getInt("id"));
				datos[i][1] = rs.getString("titulo");
				datos[i][2] = rs.getString("urlIcono");
				datos[i][3] = String.valueOf(rs.getBoolean("borradoLogico"));
				datos[i][4] = String.valueOf(rs.getInt("tipo"));
				datos[i][5] = String.valueOf(rs.getInt("temas"));
				datos[i][6] = String.valueOf(rs.getInt("respuestas"));
				rs.next();
			}
		}catch (SQLException e){
			System.out.println("Error al obtener la lista de subforos - modeloSubforo@listadoSubforos");
		}
		return datos;
	}

	
}
