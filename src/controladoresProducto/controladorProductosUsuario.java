package controladoresProducto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import modelos.modeloLibro;
import modelos.modeloPelicula;
import modelos.modeloVideojuego;

@WebServlet("cargarProductos")
public class controladorProductosUsuario extends HttpServlet{

	PrintWriter pw;
	modeloLibro modeloLibro;
	modeloPelicula modeloPelicula;
	modeloVideojuego modeloVideojuego;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try {
			/*Declaramos las variables que vamos a necesitar*/
			pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			int idUsuario = Integer.parseInt(req.getParameter("id"));
			Object[] datos = new Object[3];
			String json;
			
			/*Obtenemos los datos de los productos*/
			modeloLibro = new modeloLibro((Connection)req.getSession().getAttribute("conexion"));
			datos[0] = modeloLibro.librosUsuario(idUsuario);
			modeloPelicula = new modeloPelicula((Connection)req.getSession().getAttribute("conexion"));
			datos[1] = modeloPelicula.peliculasUsuario(idUsuario);
			modeloVideojuego = new modeloVideojuego((Connection)req.getSession().getAttribute("conexion"));
			datos[2] = modeloVideojuego.videojuegosUsuario(idUsuario);
			
			/*Formateamos a json y lo mandamos*/
			json = mapper.writeValueAsString(datos);
			pw.print(json);
		} catch (IOException e) {
			System.out.println("Error al intentar obtener el PrintWriter del objeto resp - controladorProductosUsuario@doPost");
		}
	}
}
