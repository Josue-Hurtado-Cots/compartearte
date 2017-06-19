package controladoresProducto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import modelos.modeloLibro;
import modelos.modeloPelicula;
import modelos.modeloVideojuego;

@WebServlet("borrarProducto")
public class controladorEliminarProducto extends HttpServlet{

	
	modeloLibro modeloLibro;
	modeloPelicula modeloPelicula;
	modeloVideojuego modeloVideojuego;


	/**
	 * Metodo doGet del controladorEliminarProducto. Como dice el nombre, es llamado cuando se va a borrar un producto.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		/*Obtenemos el tipo de producto*/
		String tipo = req.getParameter("tipo");
		boolean exitoConsulta = false;
		
		try{
			/*En funcion del tipo usamos un modelo u otro*/
			switch (tipo) {
				case "libro": modeloLibro = new modeloLibro((Connection)req.getSession().getAttribute("conexion"));
					exitoConsulta = modeloLibro.borrarLibro(Integer.parseInt(req.getParameter("id")));
					break;
					
				case "pelicula": modeloPelicula = new modeloPelicula((Connection)req.getSession().getAttribute("conexion"));
					exitoConsulta = modeloPelicula.borrarPelicula(Integer.parseInt(req.getParameter("id")));
					break;
					
				case "videojuego": modeloVideojuego = new modeloVideojuego((Connection)req.getSession().getAttribute("conexion"));
					exitoConsulta = modeloVideojuego.borrarVideojuego(Integer.parseInt(req.getParameter("id")));
					break;
			}
			
			if(exitoConsulta){
				req.getSession().setAttribute("exitoBorrado", "El producto ha sido borrado correctamente");
			}else{
				req.getSession().setAttribute("errorBorrado", "No se ha podido borrar el producto, por favor, int�ntelo de nuevo.");
			}
			if(req.getParameter("origen").equals("perfil")){
				resp.sendRedirect("perfil.jsp");
			}else if(req.getParameter("origen").equals("administracion")){
				resp.sendRedirect("administracionArticulos.jsp");
			}
		} catch (IOException e){
			System.out.println("Error al redireccionar tras borrar producto - controladorEliminarProducto@doGet");
		}
		
	}
	
	
}
