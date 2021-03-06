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
			<div class="col-xs-12 col-md-11" id="cuerpoError">
				<div class="col-xs-12 col-md-6 push-md-1">
					<h1>Incluso las cosas que amamos se rompen a veces...</h1>
					<h2>Por favor, tenga paciencia mientras recomponemos los pezados. Mientras, usted puede:</h2>
					<ul>
						<li>Volver a la pagina anterior pulsando <a href="#" id="atrasError">aquí</a></li>
						<li>Ponerse en contacto con nosotros a traves de nuestro correo</li>
						<li>Seguir llorando por lo que pudo ser pero al final no...</li>
					</ul>
				</div>
				<div class="col-xs-12 col-md-4 push-md-1" id="divImagen">
					<img src="img/error.png" alt="imagen de error" class="img-fluid">
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