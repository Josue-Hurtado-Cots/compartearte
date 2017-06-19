package controladoresAdministracion;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import modelos.modeloUsuario;


@WebServlet("cargarUsuarios")
public class controladorCargaUsuarios extends HttpServlet{

	
	modeloUsuario modeloUsuario;
	PrintWriter pw;
	
	/**
	 * Llama al modeloUsuario para que le devuelva los usuarios y lo devuelve a la vista.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try {
			modeloUsuario = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
			ObjectMapper mapper = new ObjectMapper();
			String[][] usuarios = modeloUsuario.cargarUsuarios();
			String json = mapper.writeValueAsString(usuarios);
			pw = resp.getWriter();
			pw.write(json);
		} catch (IOException e) {
			System.out.println("Error al convertir el array a Json - controladorCargaUsuarios@doPost");
		}
	}
	
}
