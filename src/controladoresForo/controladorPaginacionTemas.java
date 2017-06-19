package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelos.modeloTema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("paginacionTemas")
public class controladorPaginacionTemas extends HttpServlet{

	modeloTema modeloTema;
	PrintWriter pw;
	
	/**
	 * Devuelve el numero de temas del subforo en cuestion para montar la paginacion en cliente
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloTema = new modeloTema((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			int idSubforo = Integer.parseInt(req.getParameter("idSubforo"));
			int numTemas = modeloTema.getNumeroTemas(idSubforo);
			pw.print(numTemas);
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto resp - controladorPaginacionTemas@doPost");
		}
	}
}
