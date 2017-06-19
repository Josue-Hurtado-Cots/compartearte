package controladoresUsuario;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import modelos.modeloUsuario;

@WebServlet("registroUsuario")
public class controladorRegistro extends HttpServlet{

	
	modeloUsuario modelo;
	
	
	/**
	 * Metodo doPost para registrar a un nuevo usuario.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		RequestDispatcher redireccion;
		/*Obtenemos los datos del formulario de registro*/
		String usuario = req.getParameter("usuario");
		String correo = req.getParameter("correo");
		String password = DigestUtils.sha512Hex(req.getParameter("pass"));
		String telefono = req.getParameter("telefono");
		/*Instanciamos el modelo pasandole la conexion, la cual se encuentra en la session, le pasamos los datos, y recogemos lo que nos devuelve*/
		modelo = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
		/*Comprobamos si el login se ha realizado correctamente o no*/
		try{
			if(modelo.guardarUsuario(usuario, correo, password, telefono)){
				req.getSession().setAttribute("estado", "registroCorrecto");
				/*Redireccionamos*/
				redireccion = req.getRequestDispatcher("/perfil.jsp");
				redireccion.forward(req, resp);
			}else{
				req.getSession().setAttribute("estado", "registroErroneo");
				/*Redireccionamos*/
				redireccion = req.getRequestDispatcher("/registro.jsp");
				redireccion.forward(req, resp);
			}
		}catch(IOException e){
			System.out.println("Error al redireccionar tras realizar registro - controladorRegistro@doPost");
		} catch (ServletException e) {
			System.out.println("Error al obtener requestDispatcher - controladorRegistro@doPost");
		}
	}
	
}
