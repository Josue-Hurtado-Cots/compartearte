<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>


<%
	/*Solicitamos la session para que inicialice los datos*/
	request.getSession();

	String estado = String.valueOf(session.getAttribute("estado"));
	/*Obtenemos el estado del usuario y mostraremos una seccion de la pagina u otra en funcion de este*/
	if(estado.equals("Conectado")){
%>
  
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<link rel="icon" type="image/png" href="img/favicon.png"/>

	<link rel="icon" type="image/png" href="img/favicon.png"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-select.min.css">
	<link rel="stylesheet" type="text/css" href="css/toastr.min.css">
	<link rel="stylesheet" type="text/css" href="css/fileinput.css">
	<link rel="stylesheet" type="text/css" href="css/estiloPrincipal.css">
	<link rel="stylesheet" type="text/css" href="css/font-awesome.css">
		
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/tether.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/toastr.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-select.min.js"></script>
	<script type="text/javascript" src="js/comparte.js"></script>
	<script type="text/javascript" src="js/fileinput.js"></script>
	<script type="text/javascript" src="js/es.js"></script>
	<script type="text/javascript" src="js/canvas-to-blob.js"></script>
	<script type="text/javascript" src="js/sortable.js"></script>
	<script type="text/javascript" src="js/purify.js"></script>
	<script type="text/javascript" src="js/theme.js"></script>

	<title>¡Comparte libros, peliculas y videojuegos! | ComparteArte</title>
</head>
<body>

	<div id="contenedor">
		<!--CABECERA-->
		<div class="row" id="cabecera">
			<div class="col-xs-9 col-md-3 push-md-2 logoXS">
				<a href="index.jsp"><img class="img-fluid" src="img/portada.png" alt="logo de la pagina"></img></a>
			</div>
			<div class="col-xs-12 col-md-6 push-md-1" id="descripcion">
				<img class="img-fluid" src="img/descripcion.png" alt="descripcion de la pagina">
			</div>
		</div>
		<!--MENU-->
		<div class="row">
			<div class="col-xs-12 col-md-10 push-md-1" id="divMenu">
				<nav class="navbar navbar-light bg-faded" id="menu">
					<button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#nav-content"></button>
					<!-- Nav Content -->
					<div class="collapse navbar-toggleable-sm" id="nav-content">
						<ul class="nav navbar-nav">
							<li class="nav-item">
								<a class="nav-link" href="index.jsp">INICIO</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="ultimos.jsp">ÚLTIMOS</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="foro.jsp">FORO</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="buscar.jsp">BUSCAR</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="perfil.jsp">PERFIL</a>
							</li>
							<%if(session.getAttribute("datosUsuario")!=null && Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[5]))==3){ %>
								<li>
									<div class="dropdown col-md-2" id="botonAdministracion">
										<button class="btn btn-link dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="administracionMenu">
										ADMINISTRACION
										</button>
										<div class="dropdown-menu" aria-labelledby="about-us" id="cuerpoMenuAdministracion">
											<a class="dropdown-item liAdministracion" href="administracionUsuarios.jsp">GESTIÓN DE USUARIOS</a>
											<a class="dropdown-item liAdministracion" href="administracionArticulos.jsp">GESTIÓN DE ARTICULOS</a>
											<a class="dropdown-item liAdministracion" href="/compartearte/generarPDF">GENERAR INFORME</a>
										</div>
									</div>
								</li>
							<%} %>
						</ul>
					</div>
				</nav>
			</div>
		</div>
		<div id="espaciado"></div>

		<!-- FORMULARIO DE SUBIDA -->
		<div class="row">
			<div class="col-xs-12 col-md-10 push-md-1" id="subir">
				<form action="/compartearte/subirProducto" enctype="multipart/form-data" method="POST" id="formSubida">

					<input type="hidden" name="idUsuario" value='<%=String.valueOf(((Object[])session.getAttribute("datosUsuario"))[1])%>'>

					<input type="hidden" name="fecha" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>">
					
					<div class="form-group col-xs-12 col-md-6 campoSubida">
						<label class="col-md-4" for="select">Producto</label>
						<div class="col-md-8">
							<select name="productoSubir" class="form-control selectpicker" required>
								<option value="" disabled selected hidden class="col-xs-12" class="col-xs-12">Selecciona el tipo de producto</option>
								<option value="libro" class="col-xs-12">Libro</option>
								<option value="pelicula" class="col-xs-12">Pelicula</option>
								<option value="videojuego" class="col-xs-12">Videojuego</option>
							</select>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="categoriaLibro">
						<label class="col-md-4" for="select">Categoria</label>
						<div class="col-md-8">
							<select name="categoriaLibro" class="form-control selectpicker">
								<option value="" disabled selected hidden class="col-xs-12">Selecciona la categoria</option>
								<option value="Aventura" class="col-xs-12">Aventura</option>
								<option value="Belico" class="col-xs-12">Bélico</option>
								<option value="Ciencia ficcion" class="col-xs-12">Ciencia Ficción</option>
								<option value="Drama" class="col-xs-12">Drama</option>
								<option value="Fantasia" class="col-xs-12">Fantasia</option>
								<option value="Infantil" class="col-xs-12">Infantil</option>
								<option value="Terror" class="col-xs-12">Terror</option>
								<option value="Romance" class="col-xs-12">Romance</option>
							</select>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="categoriaPelicula">
						<label class="col-md-4" for="select">Categoria</label>
						<div class="col-md-8">
							<select name="categoriaPelicula" class="form-control selectpicker">
								<option value="" disabled selected hidden class="col-xs-12">Selecciona la categoria</option>
								<option value="Accion" class="col-xs-12">Accion</option>
								<option value="Animacion" class="col-xs-12">Animacion</option>
								<option value="Aventura" class="col-xs-12">Aventura</option>
								<option value="Belico" class="col-xs-12">Bélico</option>
								<option value="Ciencia ficcion" class="col-xs-12">Ciencia Ficción</option>
								<option value="Comedia" class="col-xs-12">Comedia</option>
								<option value="Drama" class="col-xs-12">Drama</option>
								<option value="Fantasia" class="col-xs-12">Fantasia</option>
								<option value="Infantil" class="col-xs-12">Infantil</option>
								<option value="Intriga" class="col-xs-12">Intriga</option>
								<option value="Terror" class="col-xs-12">Terror</option>
								<option value="Romance" class="col-xs-12">Romance</option>
							</select>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="categoriaVideojuego">
						<label class="col-md-4" for="select">Categoria</label>
						<div class="col-md-8">
							<select name="categoriaVideojuego" class="form-control selectpicker">
								<option value="" disabled selected hidden class="col-xs-12">Selecciona la categoria</option>
								<option value="Accion" class="col-xs-12">Accion</option>
								<option value="Aventura" class="col-xs-12">Aventura</option>
								<option value="Casual" class="col-xs-12">Casual</option>
								<option value="Conduccion" class="col-xs-12">Conducción</option>
								<option value="Deporte" class="col-xs-12">Deporte</option>
								<option value="Estrategia" class="col-xs-12">Estrategia</option>
								<option value="Rol" class="col-xs-12">Rol</option>
								<option value="Shooter" class="col-xs-12">Shooter</option>
								<option value="Simulacion" class="col-xs-12">Simulacion</option>
								<option value="Terror" class="col-xs-12">Terror</option>
							</select>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoBusqueda" id="provincia">
						<label class="col-md-4" for="select">Provincia</label>
						<div class="col-md-8">
							<select class="form-control selectpicker" required name="provincia">
								<option value="" disabled selected hidden class="col-xs-12">Selecciona la provincia</option>
								<option value='Alava' class="col-xs-12">Álava</option>
								<option value='Albacete' class="col-xs-12">Albacete</option>
								<option value='Alicante' class="col-xs-12">Alicante</option>
								<option value='Almeria' class="col-xs-12">Almería</option>
								<option value='Asturias' class="col-xs-12">Asturias</option>
								<option value='Avila' class="col-xs-12">Ávila</option>
								<option value='Badajoz' class="col-xs-12">Badajoz</option>
								<option value='Barcelona' class="col-xs-12">Barcelona</option>
								<option value='Burgos' class="col-xs-12">Burgos</option>
								<option value='Caceres' class="col-xs-12">Cáceres</option>
								<option value='Cadiz' class="col-xs-12">Cádiz</option>
								<option value='Cantabria' class="col-xs-12">Cantabria</option>
								<option value='Castellon' class="col-xs-12">Castellón</option>
								<option value='Ceuta' class="col-xs-12">Ceuta</option>
								<option value='Ciudad Real' class="col-xs-12">Ciudad Real</option>
								<option value='Cordoba' class="col-xs-12">Córdoba</option>
								<option value='Cuenca' class="col-xs-12">Cuenca</option>
								<option value='Girona' class="col-xs-12">Girona</option>
								<option value='Las Palmas' class="col-xs-12">Las Palmas</option>
								<option value='Granada' class="col-xs-12">Granada</option>
								<option value='Guadalajara' class="col-xs-12">Guadalajara</option>
								<option value='Guipuzcoa' class="col-xs-12">Guipúzcoa</option>
								<option value='Huelva' class="col-xs-12">Huelva</option>
								<option value='Huesca' class="col-xs-12">Huesca</option>
								<option value='Islas Baleares' class="col-xs-12">Islas Baleares</option>
								<option value='Jaen' class="col-xs-12">Jaén</option>
								<option value='La Coruña' class="col-xs-12">A Coruña</option>
								<option value='La Rioja' class="col-xs-12">La Rioja</option>
								<option value='Leon' class="col-xs-12">León</option>
								<option value='Lleida' class="col-xs-12">Lleida</option>
								<option value='Lugo' class="col-xs-12">Lugo</option>
								<option value='Madrid' class="col-xs-12">Madrid</option>
								<option value='Malaga' class="col-xs-12">Málaga</option>
								<option value='Melilla' class="col-xs-12">Melilla</option>
								<option value='Murcia' class="col-xs-12">Murcia</option>
								<option value='Navarra' class="col-xs-12">Navarra</option>
								<option value='Ourense' class="col-xs-12">Ourense</option>
								<option value='Palencia' class="col-xs-12">Palencia</option>
								<option value='Pontevedra' class="col-xs-12">Pontevedra</option>
								<option value='Salamanca' class="col-xs-12">Salamanca</option>
								<option value='Segovia' class="col-xs-12">Segovia</option>
								<option value='Sevilla' class="col-xs-12">Sevilla</option>
								<option value='Soria' class="col-xs-12">Soria</option>
								<option value='Tarragona' class="col-xs-12">Tarragona</option>
								<option value='Tenerife' class="col-xs-12">Santa Cruz de Tenerife</option>
								<option value='Teruel' class="col-xs-12">Teruel</option>
								<option value='Toledo' class="col-xs-12">Toledo</option>
								<option value='Valencia' class="col-xs-12">Valencia</option>
								<option value='Valladolid' class="col-xs-12">Valladolid</option>
								<option value='Vizcaya' class="col-xs-12">Vizcaya</option>
								<option value='Zamora' class="col-xs-12">Zamora</option>
								<option value='Zaragoza' class="col-xs-12">Zaragoza</option>
							</select>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="consola">
						<label class="col-md-4" for="select">Consola</label>
						<div class="col-md-8">
							<select name="consola" class="form-control selectpicker">
								<option value="" disabled selected hidden class="col-xs-12">Selecciona la consola</option>
								<option value="PC" class="col-xs-12">PC</option>
								<option value="PS4" class="col-xs-12">PS4</option>
								<option value="Xbox One" class="col-xs-12">XBOX ONE</option>
								<option value="PS3" class="col-xs-12">PS3</option>
								<option value="Xbox 360" class="col-xs-12">XBOX 360</option>
								<option value="3DS" class="col-xs-12">3DS</option>
								<option value="Wii U" class="col-xs-12">WII U</option>
							</select>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="formato">
						<label class="col-md-4" for="select">Formato</label>
						<div class="col-md-8">
							<select name="formato" class="form-control selectpicker">
								<option value="" disabled selected hidden class="col-xs-12">Selecciona el formato</option>
								<option value="DVD" class="col-xs-12">DVD</option>
								<option value="BLU-RAY" class="col-xs-12">BLU-RAY</option>
							</select>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="titulo">
						<label class="col-md-4" for="nombre">Titulo</label>
						<div class="col-md-8">
							<input name="titulo" type="text" placeholder="Titulo" class="form-control input-md" required>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="trailer">
						<label class="col-md-4" for="nombre">Trailer</label>
						<div class="col-md-8">
							<input name="trailer" type="text" placeholder="Link del trailer en YouTube" class="form-control input-md">
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="descripcionProducto">
						<label for="descripcion" class="col-xs-12 col-md-4" id="labelDescripcion">Descripción:</label>
						<div class="col-xs-12 col-md-8" id="divDescripcion">
							<textarea class="form-control" rows="3" name="descripcion"></textarea>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-6 campoSubida" id="foto">
						<label class="col-md-4" for="foto" id="labelFoto">Fotografía</label>
						<div class="col-md-8 col xs-12">
							<input id="fotoProducto" multiple=true type="file" class="form-control file" multiple data-theme="fa" data-show-cancel="false" data-language="es" data-show-upload="false" name="fotos">
						</div>
					</div>

					<div class="col-xs-12" id="espacioEnviar"></div>
					
					<div class="form-group col-xs-7 col-md-4 push-md-4" id="botonEnviar">
						<button name="enviar" type="submit" class="btn btn-warning col-xs-12">Enviar</button>
					</div>
				</form>
			</div>
		</div>

		<!-- PIE -->
		<div id="espaciadoInferior">
			<p>© ComparteArte.com | Página web diseñada por Josue Hurtado Cots | Todos los derechos reservados</p>
		</div>
	</div>
	
<%
	}else{
		RequestDispatcher redireccion = request.getRequestDispatcher("/perfil.jsp");
		redireccion.forward(request, response);
	}
%>
	
</body>
</html>