package controladoresForo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import modelos.modeloRespuesta;
import modelos.modeloTema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("cargarRespuestas")
public class controladorCargaRespuestas extends HttpServlet{

	modeloRespuesta modeloRespuesta;
	PrintWriter pw;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			modeloRespuesta = new modeloRespuesta((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			int pagina = Integer.valueOf(req.getParameter("pag"));
			int idTema = Integer.valueOf(req.getParameter("idTema"));
			String[][] respuestasTema = modeloRespuesta.respuestasTema(pagina, idTema);
			String json = mapper.writeValueAsString(respuestasTema);
			resp.setContentType("text/html; charset=UTF-8");
			pw.print(json);
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto resp - controladorCargaTema@doPost");
		}
	}
	
}
