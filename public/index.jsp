<%@ page contentType="text/html" pageEncoding="UTF-8"%>


<%
	/*Solicitamos la session para que inicialice los datos*/
	request.getSession();
%>

  
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="x-ua-compatible" content="ie=edge">

	<link rel="icon" type="image/png" href="img/favicon.png"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-select.min.css">
	<link rel="stylesheet" type="text/css" href="css/toastr.min.css">
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

		<!-- CUERPO -->
		<div class="row">
			<div class="col-xs-12 col-md-7 push-md-1" id="cuerpoPresentacion">
				<div id="tituloCuerpo">
					<h3>¿Que ofrece ComparteArte?</h3>
				</div>
				<div id="primeraFrase" class="col-xs-12">
					ComparteArte es tu sitio web para poder compartir o intercambiar totalmente gratis, 
					con otros usuarios, diferentes formas de arte. ¿Que formas de arte?
				</div>

				<!-- CAPAS DE INFORMACION PRINCIPAL -->
				<div class="col-xs-12 capaComparte">
					<img src="img/libros.png" alt="Imagen de libros" class="img-fluid col-xs-12 col-md-5">
					<div class="col-xs-12 col-md-7">
						<h4>LIBROS</h4>
						<p>Puedes intercambiar libros con cualquier otro usuario, sin coste para ninguno. ¡De esta forma
						 puedes disfrutar de todos los libros que desees!</p>
					</div>
				</div>
				<div class="col-xs-12 capaComparte">
					<img src="img/cine.jpg" alt="Imagen de cine" class="img-fluid col-xs-12 col-md-5 push-md-7">
					<div class="col-xs-12 col-md-7 pull-md-5">
						<h4>PELICULAS</h4>
						<p>¡Miles de peliculas para poder intercambiar sin coste alguno para tu bolsillo!</p>
					</div>
				</div>
				<div class="col-xs-12 capaComparte">
					<img src="img/juegos.jpg" alt="Imagen de libros" class="img-fluid col-xs-12 col-md-5">
					<div class="col-xs-12 col-md-7">
						<h4>VIDEOJUEGOS</h4>
						<p>Por último, tambien puedes intercambiar videojuegos con otros usuarios, ¡para que asi 
						puedas jugar a todos tus favoritos!</p>
					</div>
				</div>
				<div class="col-xs-12 capaComparte">
					<img src="img/foro.jpg" alt="Imagen de foro" class="img-fluid col-xs-12 col-md-5 push-md-7">
					<div class="col-xs-12 col-md-7 pull-md-5">
						<h4>ADEMAS...</h4>
						<p>Puedes participar en nuestro foro, dar tu opinion sobre un libro, pelicula, o videojuego,
						 crear temas de conversacion sobre estos, y mucho mas...</p>
					</div>
				</div>
			</div>

			<!-- COLUMNAS DE LA DERECHA -->
			<div class="col-xs-12 col-md-3 push-md-2 espacioPanelxs ultimosPresentacion">
				<div class="espacioPanelxs espacioPanelmd ultimasPublicaciones" id="divUltimosProductos">

				</div>
				<div class="espacioPanelxs espacioPanelmd ultimasPublicaciones" id="divUltimosTemas">
					
					
					<div><a href="#">Análisis de la ultima expansion del wow</a><span>Marco Romero</span></div>
					<div><a href="#">Recomendada la pelicula la la land</a><span>Raul Gonzalez</span></div>
					<div><a href="#">No os molesteis en probar el No man´s sky</a><span>Pablo Oliva</span></div>
					<div><a href="#">El fin de una saga</a><span>Edu Gonzalez</span></div>
					<div><a href="#">Star Wars VII: 7/10</a><span>Adrian Dore</span></div>
					<div><a href="#">Harry Potter y el legado maldito...</a><span>Dani Benitez</span></div>
					<div><a href="#">Los herederos de la tierra me ha decepcionado</a><span>Manu Delgado</span></div>
					<div><a href="#">Sobre el fifa 17...</a><span>Raul Gonzalez</span></div>
					<div><a href="#">Me parece tremenda la pelicula el expediente Warren</a><span>Ruben Valiente</span></div>
				</div>
				<div class="espacioPanelxs espacioPanelmd ultimasPublicaciones">
					<h5>Contacto</h5>
					<p>Ante cualquier duda o sugerencia que desee comunicarnos, no dude en ponerse en contacto con nosotros
					a través de la direccion de correo</p>
					<p id="correoContacto">compartearte.web@gmail.com</p>
				</div>
			</div>
		</div>

		<!-- PIE -->
		<div id="espaciadoInferior">
			<p>© ComparteArte.com | Página web diseñada por Josue Hurtado Cots | Todos los derechos reservados</p>
		</div>
	</div>

</body>
</html>