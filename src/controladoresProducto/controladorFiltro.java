package controladoresProducto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import modelos.modeloLibro;
import modelos.modeloPelicula;
import modelos.modeloVideojuego;

@WebServlet("filtradoBusqueda")
public class controladorFiltro extends HttpServlet{
	
	modeloLibro modeloLibro;
	modeloPelicula modeloPelicula;
	modeloVideojuego modeloVideojuego;
	PrintWriter pw;
	
	
	/**
	 * Metodo doPost, llamado cada vez que hay un cambio en el formulario de busqueda.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		String tipo = req.getParameter("tipo");
		String provincia = req.getParameter("provincia");
		String orden = req.getParameter("orden");
		String categoriaLibro = req.getParameter("categoriaLibro");
		String categoriaPelicula = req.getParameter("categoriaPelicula");
		String categoriaVideojuego = req.getParameter("categoriaVideojuego");
		String consola = req.getParameter("consola");
		String formato = req.getParameter("formato");
		String nombre = req.getParameter("nombre");
		ObjectMapper mapper = new ObjectMapper();
		String[][] datos = null;
		String json;

		
		try{
			switch(tipo){
				case "libro": modeloLibro = new modeloLibro((Connection)req.getSession().getAttribute("conexion"));
					datos = modeloLibro.filtrarResultados(provincia, categoriaLibro, nombre, tipoOrden(orden));
					json = mapper.writeValueAsString(datos);
					pw = resp.getWriter();
					pw.print(json);
					break;
					
				case "pelicula": modeloPelicula = new modeloPelicula((Connection)req.getSession().getAttribute("conexion"));
					datos = modeloPelicula.filtrarResultados(provincia, categoriaPelicula, nombre, tipoOrden(orden));
					json = mapper.writeValueAsString(datos);
					pw = resp.getWriter();
					pw.print(json);
					break;
					
				case "videojuego": modeloVideojuego = new modeloVideojuego((Connection)req.getSession().getAttribute("conexion"));
					datos = modeloVideojuego.filtrarResultados(provincia, categoriaVideojuego, nombre, tipoOrden(orden));
					json = mapper.writeValueAsString(datos);
					pw = resp.getWriter();
					pw.print(json);
					break;
			}
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto HttpServletResponse - controladorFiltro@doPost");
		}
	}
	
	
	/**
	 * Devuelve el orden de busqueda
	 * @param orden
	 * @return
	 */
	private String tipoOrden(String orden){
		
		switch(orden){
			case "nombreascendente": orden = " order by titulo asc ";
				break;
			case "nombredescendente": orden = " order by titulo desc ";
				break;
			case "masreciente": orden = " order by fecha desc ";
				break;
			case "menosreciente": orden = " order by fecha asc ";
				break;
		}
		
		return orden;
	}
	
	
}
