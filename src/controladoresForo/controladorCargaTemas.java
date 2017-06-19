package controladoresForo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import modelos.modeloTema;

@WebServlet("cargarTemas")
public class controladorCargaTemas extends HttpServlet{

	
	modeloTema modeloTema;
	PrintWriter pw;
	
	/**
	 * Carga la lista de temas cuyo idSubforo sea igual al que recibe por parametro.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try {
			modeloTema = new modeloTema((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			int idSubforo = Integer.parseInt(req.getParameter("id"));
			int pagina = Integer.parseInt(req.getParameter("pag"));
			String[][] temas = modeloTema.cargarTemas(pagina, idSubforo);
			String json = mapper.writeValueAsString(temas);
			pw.print(json);
		} catch (IOException e) {
			System.out.println("Error al redireccionar - controladorCargaTemas@doPost");
		}
	}
	
}
