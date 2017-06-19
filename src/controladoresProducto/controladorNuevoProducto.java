package controladoresProducto;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import modelos.modeloLibro;
import modelos.modeloPelicula;
import modelos.modeloVideojuego;

@WebServlet("subirProducto")
public class controladorNuevoProducto extends HttpServlet{
	
	modeloLibro modeloLibro;
	modeloPelicula modeloPelicula;
	modeloVideojuego modeloVideojuego;


	/**
	 * Metodo doPost del controladorNuevoProducto. Cuando se quiere subir un nuevo producto, recibe una peticion con todos los datos del nuevo producto.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		/*Variables locales que se van a necesitar*/
		FileItemFactory factory;
		ServletFileUpload archivos;
		List<FileItem> items;
		Iterator iterador;
		String nombreArchivo, idUsuario="", tipoProducto="", provincia="", titulo="", urlImg1="", urlImg2="", urlImg3="", descripcion="", categoriaLibro="", 
			categoriaPelicula="", categoriaVideojuego="", formato="", trailer="", consola="", fecha="";
		File directorio, archivo;
		FileItem item;
		int numeroFotografia = 1;

		try{
			/*Si el request tiene contenido multipart/form-data entra*/
		    if (ServletFileUpload.isMultipartContent(req)) {
		        factory = new DiskFileItemFactory();
		        archivos = new ServletFileUpload(factory);
		        items = archivos.parseRequest(req);
		        iterador = items.iterator();
		        /*Recorre la lista de elementos de la lista*/
		        while (iterador.hasNext()) {
		            item = (FileItem) iterador.next();
		            /*Si el elemento no es un campo de formulario entra*/
		            if (!item.isFormField()) {
		            	/*Obtiene el nombre de la foto*/
		                nombreArchivo = item.getName();
		                /*Si existe el directorio no hace nada, si no existe lo crea*/
		                directorio = new File(getServletContext().getRealPath("img/productos/"+idUsuario));
		                if (!directorio.exists()) {
		                    directorio.mkdirs();
		                }
		                /*Prepara y guarda el archivo*/
		                archivo = new File(directorio + "/" + nombreArchivo);
		                /*El input file entra por aqui si o si, asi que se controla si el archivo ocupa mas de 0 kb, y sies asi lo guarda*/
		                if(item.getSize()>0){
		                	item.write(archivo);
		                }
		                /*Almacenamos en sus respectivas variables las rutas*/
		                switch (numeroFotografia) {
							case 1: urlImg1 = "img/productos/"+idUsuario+"/"+nombreArchivo;
								numeroFotografia++;
								break;
							case 2: urlImg2 = "img/productos/"+idUsuario+"/"+nombreArchivo;
								numeroFotografia++;
								break;
							case 3: urlImg3 = "img/productos/"+idUsuario+"/"+nombreArchivo;
								break;
						}
		            }else{
		            	/*Como los servlets 3.0 aun no tienen implementado que se pueda extraer los parametros directamente del request cuando se envia
		            	 * como multipart/form-data, hay que ir extrayendolos uno a uno*/
		            	switch (item.getFieldName()) {
							case "idUsuario": idUsuario = item.getString();
								break;
							case "productoSubir": tipoProducto = item.getString();
								break;
							case "categoriaLibro": categoriaLibro = item.getString();
								break;
							case "categoriaPelicula": categoriaPelicula = item.getString();
								break;
							case "categoriaVideojuego": categoriaVideojuego = item.getString();
								break;
							case "provincia": provincia = item.getString();
								break;
							case "consola":consola  = item.getString();
								break;
							case "formato": formato = item.getString();
								break;
							case "titulo": titulo = item.getString();
								break;
							case "trailer": trailer = item.getString();
								break;
							case "descripcion": descripcion = item.getString();
								break;
							case "fecha": fecha = item.getString();
								break;
		            	}
		            }
		        }
		    }
		    /*Llama al metodo almacenarProducto pasandole todos los campos, donde el se encargara de llamar a un modelo u otro*/
		    almacenarProducto(req, resp, Integer.valueOf(idUsuario), tipoProducto, provincia, titulo, urlImg1, urlImg2, urlImg3, descripcion, categoriaLibro, categoriaPelicula, categoriaVideojuego, formato, trailer, consola, fecha);
		}catch (FileUploadException e) {
	        System.out.println("Error al subir archivos - controladorNuevoProducto@doPost");
	    } catch (Exception e) {
	    	System.out.println("Error al guardar la foto del producto - controladorNuevoProducto@doPost");
	    }
	}
	
	
	/**
	 * En funcion del tipo de producto que recibe, llama a un modelo u otro. Tras el exito o no de la insercion, redirecciona al perfil mostrando un mensaje
	 * de exito o error.
	 * @param req
	 * @param resp
	 * @param idUsuario
	 * @param tipoProducto
	 * @param provincia
	 * @param titulo
	 * @param urlImg1
	 * @param urlImg2
	 * @param urlImg3
	 * @param descripcion
	 * @param categoriaLibro
	 * @param categoriaPelicula
	 * @param categoriaVideojuego
	 * @param formato
	 * @param trailer
	 * @param consola
	 */
	private void almacenarProducto(HttpServletRequest req, HttpServletResponse resp, int idUsuario, String tipoProducto, String provincia, 
			String titulo, String urlImg1, String urlImg2, String urlImg3, String descripcion, String categoriaLibro, String categoriaPelicula, 
			String categoriaVideojuego, String formato, String trailer, String consola, String fecha){
		
		boolean exitoInsercion = false;
		
		try{
			/*En funcion del tipo de producto, llamaremos a la funcion de un modelo u otro*/
			switch(tipoProducto){
				case "libro": modeloLibro = new modeloLibro((Connection)req.getSession().getAttribute("conexion"));
							  exitoInsercion = modeloLibro.almacenarLibro(idUsuario, titulo, descripcion, provincia, categoriaLibro, urlImg1, urlImg2, urlImg3, fecha);
							  break;
					
				case "pelicula": modeloPelicula = new modeloPelicula((Connection)req.getSession().getAttribute("conexion"));
								 exitoInsercion = modeloPelicula.almacenarPelicula(idUsuario, titulo, descripcion, provincia, categoriaPelicula, urlImg1, urlImg2, 
										 urlImg3, formato, trailer, fecha);
								 break;
					
				case "videojuego": modeloVideojuego = new modeloVideojuego((Connection)req.getSession().getAttribute("conexion"));
								   exitoInsercion = modeloVideojuego.almacenarVideojuego(idUsuario, titulo, descripcion, provincia, categoriaVideojuego, urlImg1, 
										   urlImg2, urlImg3, consola, fecha);
								   break;
			}
			/*Comprobamos si se ha realizado correctamente la insercion. En cualquiera de los dos casos, redirige al perfil con un mensaje de exito o error
			 * de insercion*/
			if(exitoInsercion){
				req.getSession().setAttribute("exitoInsercion", "Su producto se ha subido correctamente.");
				
			}else{
				req.getSession().setAttribute("errorInsercion", "No se ha podido subir su producto. Por favor, int�ntelo de nuevo.");
			}
			resp.sendRedirect("/compartearte/perfil.jsp");
		} catch(IOException e){
			System.out.println("Error al redireccionar tras insercion de producto - controladorNuevoProducto@almacenarProducto");
		}
	}
	
	
		
}

