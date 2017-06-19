package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import modelos.modeloTema;

import java.sql.*;


@WebServlet("cargarTema")
public class controladorCargaTema extends HttpServlet{

	modeloTema modeloTema;
	PrintWriter pw;
	
	/**
	 * Carga el tema cuyo id coincida con el pasado por parametro.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloTema = new modeloTema((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			int pagina = Integer.valueOf(req.getParameter("pag"));
			int idTema = Integer.valueOf(req.getParameter("idTema"));
			String[] informacionTema = modeloTema.informacionTema(idTema);
			String json = mapper.writeValueAsString(informacionTema);
			resp.setContentType("text/html; charset=UTF-8");
			pw.print(json);
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto resp - controladorCargaTema@doPost");
		}
	}
}
