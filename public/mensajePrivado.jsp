<%@page import="modelos.modeloUsuario"%>
<%@page import="java.sql.*"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>


<%
	/*Solicitamos la session para que inicialice los datos*/
	request.getSession();
	modeloUsuario modeloUsuario = new modeloUsuario((Connection)request.getSession().getAttribute("conexion"));
	int idReceptor = Integer.parseInt(request.getParameter("id"));
	String usuarioReceptor = modeloUsuario.getNombreUsuario(idReceptor);
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
	<link rel="stylesheet" type="text/css" href="css/summernote.css">
		
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
	<script type="text/javascript" src="js/summernote.js"></script>
	<script type="text/javascript" src="js/summernote-es-ES.js"></script>
	
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
		
		<%
			String estado = String.valueOf(session.getAttribute("estado"));
			/*Obtenemos el estado del usuario y mostraremos una seccion de la pagina u otra en funcion de este*/
			if(!estado.equals("Conectado")){
				response.sendRedirect("perfil.jsp");
			}else{
		%>
			<div class="row">
				<div class="col-xs-12 col-md-10" id="cuerpoNuevoTema">
					<div class="col-xs-12 col-md-8 push-md-2">
						<form action="/compartearte/nuevoMensaje" method="post">

							<input type="hidden" name="idAutor" value='<%=Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[1]))%>'>
							<input type="hidden" name="idReceptor" value='<%=request.getParameter("id")%>'>
							
							<div class="form-group">
								<label class="col-md-3" for="para"><strong>Para</strong></label>
								<div class="col-md-8 campoRegistro">
									<input type="text" readonly class="form-control input-md" value="<%=usuarioReceptor%>">
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-3" for="asunto"><strong>Asunto</strong></label>
								<div class="col-md-8 campoRegistro">
									<input name="asunto" type="text" placeholder="Asunto" class="form-control input-md" required>
								</div>
							</div>
							
							<div class="col-xs-12" id="divMensaje">
								<textarea id="editor" name="mensaje" required></textarea>
							</div>
							
							<div class="col-xs-12 form-group">
								<input type="submit" value="Enviar" class="col-xs-12 col-md-6 push-md-3 btn btn-warning">
							</div>

						</form>
					</div>
				</div>
			</div>
		<%
			}
		%>
	</div>


</body>
</html>