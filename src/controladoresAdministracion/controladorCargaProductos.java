package controladoresAdministracion;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.sql.*;
import modelos.modeloMultiplesTablas;

@WebServlet("cargarProductosPaginacion")
public class controladorCargaProductos extends HttpServlet{

	modeloMultiplesTablas modeloMultiplesTablas;
	PrintWriter pw;
	
	/**
	 * Recibe la peticion desde la pagina de administracion de productos. Recibe como parametro la pagina a cargar.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			int pagina = Integer.valueOf(req.getParameter("pagina"));
			modeloMultiplesTablas = new modeloMultiplesTablas((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String[][] productos = modeloMultiplesTablas.cargarProductosAdministracion(pagina);
			String json = mapper.writeValueAsString(productos);
			pw.print(json);
		}catch(IOException e){
			System.out.println("Error al intentar obtener el objeto writer - controladorCargaProductos@doPost");
		}
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		doPost(req, resp);
	}
}
