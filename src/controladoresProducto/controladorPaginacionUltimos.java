package controladoresProducto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelos.modeloMultiplesTablas;

@WebServlet("paginacion")
public class controladorPaginacionUltimos extends HttpServlet{

	PrintWriter pw;
	modeloMultiplesTablas modeloProcedimientosAlmacenados;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		int paginas = 0;
		try{
			pw = resp.getWriter();
			/*Obtenemos el numero total de elementos para la paginacion*/
			modeloProcedimientosAlmacenados = new modeloMultiplesTablas((Connection)req.getSession().getAttribute("conexion"));
			paginas = modeloProcedimientosAlmacenados.numeroElementos();
			pw.print(paginas);
		}catch (IOException e){
			System.out.println("Error al obtener el writer del objeto response - controladorPaginacionUltimos@doPost");
		}
	}
	
}
