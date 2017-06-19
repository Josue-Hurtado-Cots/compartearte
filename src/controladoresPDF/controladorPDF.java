package controladoresPDF;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Calendar;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.*;
import modelos.modeloLibro;
import modelos.modeloPelicula;
import modelos.modeloRespuesta;
import modelos.modeloTema;
import modelos.modeloUsuario;
import modelos.modeloVideojuego;

@WebServlet("generarPDF")
public class controladorPDF extends HttpServlet{
	
	modeloLibro modeloLibro;
	modeloPelicula modeloPelicula;
	modeloVideojuego modeloVideojuego;
	modeloUsuario modeloUsuario;
	modeloTema modeloTema;
	modeloRespuesta modeloRespuesta;
	
	/**
	 * Metodo doGet del controladorPDF. Hara la llamada al metodo generarPDF() y mandara el pdf que este genere al cliente
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		/*Declaramos las variables que vamos a necesitar para descargar el pdf*/
		FileInputStream archivo;
		int longitud;
		byte[] datos;
		/*Declaramos los modelos*/
		modeloLibro = new modeloLibro((Connection)req.getSession().getAttribute("conexion"));
		modeloPelicula = new modeloPelicula((Connection)req.getSession().getAttribute("conexion"));
		modeloVideojuego = new modeloVideojuego((Connection)req.getSession().getAttribute("conexion"));
		modeloUsuario = new modeloUsuario((Connection)req.getSession().getAttribute("conexion"));
		modeloTema = new modeloTema((Connection)req.getSession().getAttribute("conexion"));
		modeloRespuesta = new modeloRespuesta((Connection)req.getSession().getAttribute("conexion"));
		/*Declaramos el array con la informacion que vamos a pasar al pdf*/
		int[] cantidades = new int[7];
		/*Obtenemos el mes actual y lo preparamos para pasarlo a lo modelos*/
		Calendar calendar = Calendar.getInstance();
		int mes = calendar.get(Calendar.MONTH)+1;
		String fecha;
		try{
			/*Preparamos el string para que la consulta devuelva los productos del mes actual*/
			if(mes==1 || mes==2 || mes==3 || mes==4 || mes==5 || mes==6 || mes==7 || mes==8 || mes==9){
				fecha = "-0"+String.valueOf(mes)+"-";
			}else{
				fecha = String.valueOf(mes);
			}
			cantidades[0] = modeloLibro.getCantidadLibros(fecha);
			cantidades[1] = modeloPelicula.getCantidadPeliculas(fecha);
			cantidades[2] = modeloVideojuego.getCantidadVideojuegos(fecha);
			cantidades[3] = modeloUsuario.getUsuarios();
			cantidades[4] = modeloUsuario.getUsuariosBaneados();
			cantidades[5] = modeloTema.getTemasMes(fecha);
			cantidades[6] = modeloRespuesta.getRespuestasMes(fecha);
			/*Mandamos el archivo*/
			archivo = new FileInputStream(generarPDF(cantidades, mes));
			longitud = archivo.available();
			datos = new byte[longitud];
			archivo.read(datos);
			archivo.close();
			resp.addHeader("Content-Disposition","attachment;filename=Informe_ComparteArte.pdf");
			resp.setContentLength(longitud);
			resp.getOutputStream().write(datos);
			resp.getOutputStream().flush();
			resp.getOutputStream().close(); 
		}catch(IOException e){
			System.out.println("Error al cargar el pdf - controladorPDF@doGet");
		}
	}
	
	
	/**
	 * Genera el pdf y lo devuelve al metodo doGet, el cual se encargará de enviarlo al cliente.
	 * @return
	 */
	private File generarPDF(int[] cantidades, int mes){
		Document document;
		String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		File pdf = new File(getServletContext().getRealPath("docs/Informe_"+meses[mes-1]+".pdf"));
		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
	    Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
	    Font SpecialParagraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
	    String rutaImagen = "public/img/portada.png";
	    Image image;
	    Chunk chunk;
	    Chapter chapter;
		try {
			/*Creamos el documento pdf*/
		    document = new Document();
		    pdf.createNewFile();
		    PdfWriter.getInstance(document, new FileOutputStream(pdf));
		    document.open();
		    /*Le indicamos los metadatos*/
		    document.addSubject("Informe de articulos");
		    document.addKeywords("articulos, informe, pdf, nuevos");
		    document.addAuthor("Josué Hurtado");
		    document.addCreator("Josué Hurtado");

		    chunk = new Chunk("Informe de "+meses[mes-1], chapterFont);
            chunk.setBackground(BaseColor.YELLOW);
            /*Creamos la primera pagina*/
            chapter = new Chapter(1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph(chunk));
            /*Añadimos los distintos parrafos que va a tener el pdf*/
            Paragraph Pcantidades = new Paragraph("·Nuevos articulos de cada categoría:", SpecialParagraphFont);
            Pcantidades.setLeading(60);
            chapter.add(Pcantidades);
            Paragraph Plibros = new Paragraph("-Libros: "+cantidades[0]+" libros subidos");
            Paragraph Ppeliculas = new Paragraph("-Peliculas: "+cantidades[1]+" peliculas subidas");
            Paragraph Pvideojuegos = new Paragraph("-Videojuegos: "+cantidades[2]+" videojuegos subidos");
            Paragraph Ptotal = new Paragraph("-Total de productos nuevos: "+(cantidades[0]+cantidades[1]+cantidades[2]), SpecialParagraphFont);
            Plibros.setIndentationLeft(15);
            Ppeliculas.setIndentationLeft(15);
            Pvideojuegos.setIndentationLeft(15);
            Ptotal.setIndentationLeft(15);
            Plibros.setLeading(35);
            chapter.add(Plibros);
            chapter.add(Ppeliculas);
            chapter.add(Pvideojuegos);
            chapter.add(Ptotal);
            Paragraph Pusuarios = new Paragraph("·Total de usuarios: "+cantidades[3], SpecialParagraphFont);
            Pusuarios.setLeading(60);
            Paragraph Pbaneados = new Paragraph("·Usuarios baneados: "+cantidades[4], SpecialParagraphFont);
            Pbaneados.setLeading(25);
            chapter.add(Pusuarios);
            chapter.add(Pbaneados);
            Paragraph Ptemas = new Paragraph("·Temas creados: "+cantidades[5], SpecialParagraphFont);
            Ptemas.setLeading(60);
            Paragraph Prespuestas = new Paragraph("·Respuestas escritas: "+cantidades[6], SpecialParagraphFont);
            Prespuestas.setLeading(25);
            chapter.add(Ptemas);
            chapter.add(Prespuestas);
		    document.add(chapter);
		    
		    document.close();
		} catch (DocumentException documentException) {
		    System.out.println("Se ha producido un error al generar el pdf - controladorPDF@generarPDF");
		} catch (FileNotFoundException fileNotFoundException) {
	        System.out.println("Archivo no encontrado - controladorPDF@generarPDF");
	    } catch (MalformedURLException e) {
			System.out.println("La ruta está mal indicada - controladorPDF@generarPDF");
		} catch (IOException e) {
			System.out.println("Error al leer imagen - controladorPDF@generarPDF");
		} 

		return pdf;
	}
}
