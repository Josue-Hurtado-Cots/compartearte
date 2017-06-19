package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import modelos.modeloTema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("cargaUltimosTemas")
public class controladorUltimosTemasIndex extends HttpServlet{

	
	modeloTema modeloTema;
	PrintWriter pw;
	
	/**
	 * Metodo doPost que devuelve los 10 ultimos temas para cargarlos en el index.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloTema = new modeloTema((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String[][] temas = modeloTema.cargarUltimosTemas();
			String json = mapper.writeValueAsString(temas);
			pw.print(json);
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto resp - controladorUltimosTemasIndex@doPost");
		}
	}
	
	
}
