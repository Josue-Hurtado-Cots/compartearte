package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelos.modeloTema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("nuevoTema")
public class controladorNuevoTema extends HttpServlet{

	modeloTema modeloTema;
	
	/**
	 * Crea un nuevo tema
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloTema = new modeloTema((Connection)req.getSession().getAttribute("conexion"));
			int idUsuario = Integer.parseInt(req.getParameter("idAutor"));
			int idSubforo = Integer.parseInt(req.getParameter("idSubforo"));
			String titulo = req.getParameter("titulo");
			String mensaje = req.getParameter("mensaje");
			boolean estadoInsercion = modeloTema.crearTema(idUsuario, idSubforo, titulo, mensaje);
			if(estadoInsercion){
				req.getSession().setAttribute("exitoNuevoTema", "Se ha creado el tema correctamente.");
			}else{
				req.getSession().setAttribute("errorNuevoTema", "No se ha podido crear el tema, por favor, inténtelo de nuevo mas tarde.");
			}
			resp.sendRedirect("subforo.jsp?id="+idSubforo+"&pag=1");
		}catch (IOException e) {
			System.out.println("Error al redireccionar - controladorNuevoTema@doPost");
		}
	}
	
}
