package controladoresUsuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import modelos.modeloUsuario;

import java.io.IOException;
import java.sql.*;


@WebServlet("modificarPerfil")
public class controladorModificarPerfil extends HttpServlet{

	modeloUsuario modeloUsuario;
	
	
	/**
	 * Recibe la contraseña actual, la nueva, y el correo, para actualizar los datos del usuario.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		int idUsuario = Integer.valueOf(req.getParameter("id"));
		String correo = req.getParameter("correo");
		String passActual = DigestUtils.sha512Hex(req.getParameter("passActual"));
		String passNueva = req.getParameter("nuevaPass");
		modeloUsuario = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
		
		/*Comprobamos si quiere cambiar o no la contraseña*/
		if(passNueva.length()>6){
			passNueva = DigestUtils.sha512Hex(passNueva);
		}
		
		try{
			/*Si la contraseña es correcta, actualiza los datos, si no, no*/
			if(modeloUsuario.comprobarPassword(idUsuario, passActual)){
				modeloUsuario.actualizarDatos(idUsuario, correo, passNueva);
				req.getSession().setAttribute("exitoActualizacion", "Sus datos han sido actualizados correctamente");
				resp.sendRedirect("/perfil.jsp");
			}else{
				req.getSession().setAttribute("errorActualizacion", "La contraseña que ha introducido no es correcta");
				resp.sendRedirect("/modificarPerfil.jsp");
			}
		}catch(IOException e){
			System.out.println("Error al redireccionar - controladorModificarPerfil@doPost");
		}
		
	}
	
}
