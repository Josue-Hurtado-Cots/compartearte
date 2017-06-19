package controladoresAdministracion;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import modelos.modeloUsuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("modificarUsuario")
public class controladorModificarUsuario extends HttpServlet{
	
	
	modeloUsuario modeloUsuario;
	PrintWriter pw;
	
	
	/**
	 * Recibe los datos del usuario y los actualiza.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		/*Obtenemos los datos*/
		int id = Integer.parseInt(req.getParameter("idUsuario"));
		String correo = req.getParameter("correo");
		String nombreUsuario = req.getParameter("usuario");
		String telefono = req.getParameter("telefono");
		boolean baneado = Boolean.valueOf(req.getParameter("baneado"));
		int incidencias = Integer.parseInt(req.getParameter("incidencias"));
		int nivelPermisos = Integer.parseInt(req.getParameter("nivelPermisos"));
		modeloUsuario = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
		
		try{
			/*Si se actualiza bien, manda un mensaje de exito, si no, uno de error*/
			if(modeloUsuario.modificarUsuario(id, correo, nombreUsuario, telefono, baneado, incidencias, nivelPermisos)){
				req.getSession().setAttribute("exitoModificacion", "El usuario ha sido actualizado correctamente");
				resp.sendRedirect("administracionUsuarios.jsp");
			}else{
				req.getSession().setAttribute("errorModificacion", "Ha ocurrido un fallo, por favor, inténtelo de nuevo");
				resp.sendRedirect("modificarUsuario.jsp?id="+id);
			}
		}catch(IOException e){
			System.out.println("Error al redireccionar - controladorModificarUsuario@doPost");
		}
	}
	
	
	/**
	 * Recibe el id de un usuario, y devuelve sus datos para cargarlos en la pagina de modificacion
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		try{
			/*Obtenemos los datos del usuario y los mandamos a la vista*/
			int id = Integer.parseInt(req.getParameter("idUsuario"));
			modeloUsuario = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
			String[] datosUsuario = modeloUsuario.obtenerDatosModificacion(id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(datosUsuario);
			pw = resp.getWriter();
			pw.write(json);
		}catch (IOException e) {
			System.out.println("Error al parsea a json - controladorModificarUsuario@doGet");
		}
	}
}
