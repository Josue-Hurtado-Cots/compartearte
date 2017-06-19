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
				<form action="/compartearte/modificarUsuario" method="post">
					
					<input type="hidden" value='<%=request.getParameter("id")%>' id="idUsuario" name="idUsuario">
					
					<div class="form-group">
						<label class="col-md-3 push-md-1" for="correo">Correo</label>
						<div class="col-md-8 campoRegistro">
							<input name="correo" type="text" class="form-control input-md" id="correo">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 push-md-1" for="usuario">Usuario</label>
						<div class="col-md-8 campoRegistro">
							<input name="usuario" type="text" class="form-control input-md" id="usuario">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 push-md-1" for="telefono">Telefono</label>
						<div class="col-md-8 campoRegistro">
							<input name="telefono" type="text" placeholder="Telefono de contacto" class="form-control input-md" id="telefono">
						</div>
					</div>

					<div class="form-group">
						<label for="baneado" class="col-md-3 push-md-1 col-form-label">Baneado</label>
						<div class="col-md-8 campoRegistro">
							<div class="col-xs-12 col-md-12">
								<label class="custom-control custom-radio">
									<input name="baneado" type="radio" class="custom-control-input" required value="true" id="estaBaneado">
									<span class="custom-control-indicator"></span>
									<span class="custom-control-description">Si</span>
								</label>
								<label class="custom-control custom-radio">
									<input name="baneado" type="radio" class="custom-control-input" required value="false" id="noEstaBaneado">
									<span class="custom-control-indicator"></span>
									<span class="custom-control-description">No</span>
								</label>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 push-md-1" for="telefono">Incidencias</label>
						<div class="col-md-8 campoRegistro">
							<input name="incidencias" type="text" class="form-control input-md" id="incidencias">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 push-md-1" for="select">Nivel de permisos</label>
						<div class="col-md-8">
							<select name="nivelPermisos" class="form-control selectpicker" required id="nivelPermisos">
								<option value="1" class="col-xs-12">Usuario</option>
								<option value="2" class="col-xs-12">Moderador</option>
								<option value="3" class="col-xs-12">Administrador</option>
							</select>
						</div>
					</div>

					<div class="col-xs-12 espaciadoInputMod"></div>
					
					<div class="col-xs-12 form-group">
						<input type="submit" value="Actualizar usuario" class="col-xs-12 col-md-6 push-md-3 btn btn-warning">
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
			/*MENSAJES TOASTR, para cuando no se actualiza con exito un usuario*/
			if(request.getSession().getAttribute("errorModificacion")!=null){
		%>
			<script type="text/javascript">
				toastr.error('<%=String.valueOf(request.getSession().getAttribute("errorModificacion"))%>');
			</script>
		<%
			request.getSession().removeAttribute("errorModificacion");
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