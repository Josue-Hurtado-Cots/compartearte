package controladoresAdministracion;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import modelos.modeloUsuario;

@WebServlet("aniadirIncidencia")
public class controladorIncidencias extends HttpServlet{

	
	modeloUsuario modeloUsuario;
	
	
	/**
	 * Añadira una incidencia a un usuario. Si es la tercera, será baneado.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		int id = Integer.parseInt(req.getParameter("id"));
		modeloUsuario = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
		try{
			/*Comprobamos el numero de incidencias del usuario*/
			int incidencias = modeloUsuario.incidencias(id);
			/*Si el usuario tiene 2 incidencias, se le pone una tercera y se le banea.*/
			if(incidencias==2){
				modeloUsuario.nuevaIncidencia(id, incidencias+1);
				modeloUsuario.cambiarBaneo(true, id);
			}
			/*Si tiene menos, simplemente se le añade una incidencia mas*/
			else{
				modeloUsuario.nuevaIncidencia(id, incidencias+1);
			}
			resp.sendRedirect("administracionUsuarios.jsp");
		}catch(IOException e){
			System.out.println("Error al redireccionar tras añadir una incidencia a un usuario - controladorIncidencias@doGet");
		}
	}
}
