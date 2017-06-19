package controladoresAdministracion;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import modelos.modeloUsuario;

@WebServlet("banearUsuario")
public class controladorBan extends HttpServlet{

	
	modeloUsuario modeloUsuario;
	
	
	/**
	 * Llama al modeloUsuario pasandole la solicitud de baneo/desbaneo
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		int id = Integer.parseInt(req.getParameter("id"));
		modeloUsuario = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
		try{
			/*Comprobamos si el usuario esta baneado o desbaneado*/
			if(modeloUsuario.comprobarBan(id)){
				modeloUsuario.cambiarBaneo(false, id);
			}else{
				modeloUsuario.cambiarBaneo(true, id);
			}
			resp.sendRedirect("administracionUsuarios.jsp");
		}catch(IOException e){
			System.out.println("Error al redireccionar tras banear/desbanear al usuario - controladorBan@doGet");
		}
	}
	
	
}
