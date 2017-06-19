package controladoresProducto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import modelos.modeloMultiplesTablas;

@WebServlet("ultimosArticulos")
public class controladorUltimosProductos extends HttpServlet{

	
	modeloMultiplesTablas modeloProcedimientosAlmacenados;
	PrintWriter pw;
	
	
	/**
	 * Metodo doGet de controladorUltimosProductos. Devuelve la lista de los ultimos productos.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		try {
			pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			/*Instanciamos el modelo y llamamos al metodo que hara llamada al procedimiento almacenado de la bbdd*/
			modeloProcedimientosAlmacenados = new modeloMultiplesTablas((Connection)req.getSession().getAttribute("conexion"));
			String[][] productos = modeloProcedimientosAlmacenados.ultimosProductos(Integer.valueOf(req.getParameter("numPag")));
			/*Parseamos los resultados a json*/
			String json = mapper.writeValueAsString(productos);
			/*Lo mandamos a la vista*/
			pw.print(json);
		} catch (IOException e) {
			System.out.println("Error al obtener el writer del objeto response - controladorUltimosProductos@doGet");
		}
	}
	
	
	
	/**
	 * Metodo doPost de controladorUltimosProductos. Devuelve la lista de los ultimos productos.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req, resp);
	}
	
}
