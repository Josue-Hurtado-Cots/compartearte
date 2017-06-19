package controladoresUsuario;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

import modelos.modeloUsuario;

@WebServlet("iniciarSesion")
public class controladorLogin extends HttpServlet{
	
	modeloUsuario modelo;

	
	/**
	 * Metodo doPost para el login del usuario. Llama al metodo de comprobar login del modelo, y si es correcto, a�adir� los datos en la session, si no, redirigir� a la ventana de inicio.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		Object [] datosUsuario;
		/*Obtenemos los datos del formulario de login*/
		String correo = req.getParameter("correo");
		String password = DigestUtils.sha512Hex(req.getParameter("pass"));
		/*Instanciamos el modelo pasandole la conexion, la cual se encuentra en la session, le pasamos los datos, y recogemos lo que nos devuelve*/
		modelo = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
		datosUsuario = modelo.comprobarLogin(correo, password);
		/*Comprobamos si el login es correcto o no, para redirigir a un sitio u otro, pasando informacion por la session*/
		try{
			if((boolean)datosUsuario[0]){
				/*En el caso de que el login sea correcto, comprobamos si el usuario esta baneado*/
				if(!(boolean)datosUsuario[4]){
					req.getSession().setAttribute("datosUsuario", datosUsuario);
					req.getSession().setAttribute("estado", "Conectado");
				}else{
					req.getSession().setAttribute("datosUsuario", datosUsuario);
					req.getSession().setAttribute("estado", "Baneado");
				}
			}else{
				req.getSession().setAttribute("estado", "LoginErroneo");
			}
			resp.sendRedirect("/compartearte/perfil.jsp");
			return;
		}catch(IOException e){
			System.out.println("Error al redireccionar tras comprobar el login - controladorLogin@doPost");
		}
	}
	
}
