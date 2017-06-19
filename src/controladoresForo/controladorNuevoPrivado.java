package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelos.modeloMensajePrivado;
import modelos.modeloTema;

import java.io.IOException;
import java.sql.*;



@WebServlet("nuevoMensaje")
public class controladorNuevoPrivado extends HttpServlet{

	modeloMensajePrivado modeloMensajePrivado;
	
	/**
	 * Crea un nuevo mensaje privado
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloMensajePrivado = new modeloMensajePrivado((Connection)req.getSession().getAttribute("conexion"));
			int idAutor = Integer.parseInt(req.getParameter("idAutor"));
			int idReceptor = Integer.parseInt(req.getParameter("idReceptor"));
			String asunto = req.getParameter("asunto");
			String mensaje = req.getParameter("mensaje");
			boolean estadoInsercion = modeloMensajePrivado.crearPrivado(idAutor, idReceptor, asunto, mensaje);
			if(estadoInsercion){
				req.getSession().setAttribute("exitoPrivado", "Se ha mandado su mensaje.");
			}else{
				req.getSession().setAttribute("errorPrivado", "No se ha podido mandar el mensaje, por favor, inténtelo de nuevo mas tarde.");
			}
			resp.sendRedirect("perfil.jsp");
		}catch (IOException e) {
			System.out.println("Error al redireccionar - controladorNuevoTema@doPost");
		}
	}
	
}
