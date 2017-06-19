package controladoresInicio;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.*;

public class controladorSession implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		controladorConexionBBDD controladorConexion = new controladorConexionBBDD();
		event.getSession().setAttribute("estado", "Invitado");
		event.getSession().setAttribute("conexion", controladorConexion.getConexion());
		event.getSession().setMaxInactiveInterval(3600);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		try {
			Connection conexion = (Connection)event.getSession().getAttribute("conexion");
			conexion.close();
			event.getSession().invalidate();
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion con la base de datos - controladorSession@sessionDestroyed");
		}
	}

}
