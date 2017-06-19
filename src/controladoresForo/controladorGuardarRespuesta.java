package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelos.modeloRespuesta;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("guardarRespuesta")
public class controladorGuardarRespuesta extends HttpServlet{

	modeloRespuesta modeloRespuesta;
	PrintWriter pw;
	
	/**
	 * Guarda la respuesta que escribe el usuario en un tema.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloRespuesta = new modeloRespuesta((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
			int idTema = Integer.parseInt(req.getParameter("idTema"));
			String respuesta = req.getParameter("respuesta");
			boolean estadoInsercion = modeloRespuesta.guardarRespuesta(idUsuario, idTema, respuesta);
			pw.print(estadoInsercion);
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto resp - controladorGuardarRespuesta@doPost");
		}
	}
}
