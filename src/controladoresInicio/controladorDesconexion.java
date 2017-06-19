package controladoresInicio;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("desconexion")
public class controladorDesconexion extends HttpServlet{

	
	
	/**
	 * Metodo doGet para cerrar la sesion del usuario.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		try {
			RequestDispatcher redireccion = req.getRequestDispatcher("/perfil.jsp");
			/*Cerramos la session del usuario*/
			req.getSession().invalidate();
			redireccion.forward(req, resp);
		}catch(IOException e){
			System.out.println("Error al redireccionar tras realizar desconexion - controladorDesconexion@doPost");
		} catch (ServletException e) {
			System.out.println("Error al obtener requestDispatcher - controladorDesconexion@doPost");
		}
	}
	
}