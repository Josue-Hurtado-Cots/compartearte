<%@ page contentType="text/html" pageEncoding="UTF-8"%>


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

		<!-- FORMULARIO DE MODIFICACION -->
		<div class="row">
			<div class="col-xs-12 col-md-10 push-md-1" id="modificarPerfil">
				<form action="/compartearte/modificarPerfil" method="POST" id="form-modificar-perfil">
					<input type="hidden" name="id" id="idModificacion" value='<%=request.getParameter("id")%>'>
					<div class="form-group col-xs-12 col-md-8 push-md-2">
						<label class="col-md-3 col-xs-12" for="pass"><strong>Contraseña actual</strong></label>
						<div class="col-md-8 col-xs-12">
							<input type="password" name="passActual" class="form-control">
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-8 push-md-2">
						<label class="col-md-3 col-xs-12" for="correo"><strong>Correo</strong></label>
						<div class="col-md-8 col-xs-12">
							<input type="text" name="correo" class="form-control" id="correo" value='<%=String.valueOf(((Object[])session.getAttribute("datosUsuario"))[2])%>'>
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-8 push-md-2">
						<label class="col-md-3 col-xs-12" for="nuevaPass"><strong>Contraseña nueva</strong></label>
						<div class="col-md-8 col-xs-12">
							<input type="password" name="nuevaPass" class="form-control" id="nuevaPass" value="">
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-8 push-md-2">
						<label class="col-md-3 col-xs-12" for="pass"><strong>Repetir contraseña</strong></label>
						<div class="col-md-8 col-xs-12">
							<input type="password" class="form-control" id="passRep" value="">
						</div>
					</div>
					<div class="row col-xs-12" id="botonesLogin">
						<div class="form-group col-xs-12 col-md-4 push-md-4">
							<input type="submit" value="Confirmar cambios" class="btn btn-warning col-xs-12 form-control">
						</div>
					</div>
					<div class="col-xs-10 push-xs-1" id="mensajeModificacion">
						<strong>*Para realizar cualquier cualquier modificación, primeramente introduzca su contraseña actual, y posteriormente podrá modificar su correo, cambiar su contraseña, o ambos.</strong>
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
			/*MENSAJES TOASTR, para cuando no se modifica el perfil porque introduce la contraseña mal*/
			if(request.getSession().getAttribute("errorActualizacion")!=null){
		%>
			<script type="text/javascript">
				toastr.error('<%=String.valueOf(request.getSession().getAttribute("errorActualizacion"))%>');
			</script>
		<%
			request.getSession().removeAttribute("errorActualizacion");
			}
		%>
	
	
<%
	}else{
		RequestDispatcher redireccion = request.getRequestDispatcher("/perfil.jsp");
		redireccion.forward(request, response);
	}
%>
	
</body>
</html>