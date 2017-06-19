package controladoresProducto;

import modelos.modeloLibro;
import modelos.modeloPelicula;
import modelos.modeloVideojuego;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

@WebServlet("producto")
public class controladorInformacionProducto extends HttpServlet{

	modeloLibro modeloLibro;
	modeloPelicula modeloPelicula;
	modeloVideojuego modeloVideojuego;
	
	
	/**
	 * Carga la informacion de un producto.
	 * @param tipo
	 * @param id
	 * @param conexion
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		/*Instanciamos las variables que se van a necesitar*/
		Connection conexion = (Connection)req.getSession().getAttribute("conexion");
		String tipo = req.getParameter("tipo");
		int id = Integer.valueOf(req.getParameter("id"));
		String[] datos = null;
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		PrintWriter pw = null;
		/*En funcion del tipo de articulo y su id, devolvera un producto u otro*/
		try{
			switch(tipo){
			
				case "Libro": modeloLibro = new modeloLibro(conexion);
					datos = modeloLibro.cargarLibro(id);
					break;
					
				case "Pelicula": modeloPelicula = new modeloPelicula(conexion);
					datos = modeloPelicula.cargarPelicula(id);
					break;
					
				case "Videojuego": modeloVideojuego = new modeloVideojuego(conexion);
					datos = modeloVideojuego.cargarVideojuego(id);
					break;	
			}
			json = mapper.writeValueAsString(datos);
			pw = resp.getWriter();
			/*Lo devolvemos a la vista*/
			pw.write(json);
		}catch(IOException e){
			System.out.println("Error al parsear datos a json - controladorInformacionProducto@doGet");
		}
	}
	
	
}
