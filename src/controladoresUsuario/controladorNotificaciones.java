package controladoresUsuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelos.modeloMensajePrivado;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("cargarNotificaciones")
public class controladorNotificaciones extends HttpServlet{

	
	modeloMensajePrivado modeloMensajePrivado;
	PrintWriter pw;
	
	/**
	 * Comprueba si el usuario tiene notificaciones y las carga
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try{
			int idUsuario, numNotificaciones = 0;
			modeloMensajePrivado = new modeloMensajePrivado((Connection)req.getSession().getAttribute("conexion"));
			pw = resp.getWriter();
			/*Si el usuario ha iniciado sesion, cargará las notificaciones, si no, no*/
			if(String.valueOf(req.getSession().getAttribute("estado")).equals("Conectado")){
				idUsuario = Integer.parseInt(String.valueOf(((Object[])req.getSession().getAttribute("datosUsuario"))[1]));
				numNotificaciones = modeloMensajePrivado.notificaciones(idUsuario);
			}
			pw.write(numNotificaciones+"");;
		}catch(IOException e){
			System.out.println("Error al obtener el writer del objeto resp - controladorNotificaciones@doPost");
		}
	}
}
