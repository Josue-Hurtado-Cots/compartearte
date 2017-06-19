package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import modelos.modeloSubforo;


@WebServlet("listadoSubforos")
public class controladorCargaSubforos extends HttpServlet{

	modeloSubforo modeloSubforo;
	PrintWriter pw;
	
	/**
	 * Servlet llamado cuando se va a acceder al foro. Devuelve la lista de subforos que hay.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloSubforo = new modeloSubforo((Connection)req.getSession().getAttribute("conexion"));
			ObjectMapper mapper = new ObjectMapper();
			pw = resp.getWriter();
			String[][] datos = modeloSubforo.listadoSubforos();
			String json = mapper.writeValueAsString(datos);
			pw.print(json);
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto resp - controladorCargaSubforos@doPost");
		}
	}
	
}
