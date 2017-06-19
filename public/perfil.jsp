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
		
		<%
			String estado = String.valueOf(session.getAttribute("estado"));
			/*Obtenemos el estado del usuario y mostraremos una seccion de la pagina u otra en funcion de este*/
			if(!estado.equals("Conectado")){
		%>
			<div class="row">
				<div class="col-xs-12 col-md-8 push-md-2" id="panelPerfil">
					<form action="/compartearte/iniciarSesion" method="POST">
						<div class="form-group col-xs-12 col-md-8 push-md-2">
							<label class="col-md-3 col-xs-12" for="correo"><strong>Correo</strong></label>
							<div class="col-md-8 col-xs-12">
								<input type="text" name="correo" class="form-control">
							</div>
						</div>
						<div class="form-group col-xs-12 col-md-8 push-md-2">
							<label class="col-md-3 col-xs-12" for="pass"><strong>Contraseña</strong></label>
							<div class="col-md-8 col-xs-12">
								<input type="password" name="pass" class="form-control">
							</div>
						</div>
						<div class="row col-xs-12" id="botonesLogin">
							<div class="form-group col-xs-12 col-md-4 push-md-2">
								<input type="submit" value="Iniciar sesión" class="btn btn-warning col-xs-12 form-control">
							</div>
							<div class="form-group col-xs-12 col-md-4 push-md-2">
								<a href="/compartearte/registro.jsp" class="btn btn-warning form-control">Registrarse</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		<%
			}else{
		%>

		<!-- CUERPO -->
		<div class="row">
			<div class="col-xs-12 col-md-10 push-md-1" id="panelPerfil">
				<!-- FOTO DE PERFIL -->
				<div class="row">
					<div class="col-xs-12 col-md-2" id="fotoPerfil">
						<img src="img/perfil/general.png" alt="foto de perfil" class="img-fluid">
					</div>
					<div class="col-xs-12 col-md-3" id="infoUsuario">
						<input type="hidden" value="<%=Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[1]))%>" id="idUsuario">
						<span id="nombreUsuario"><%= String.valueOf(((Object[])session.getAttribute("datosUsuario"))[3]) %></span>
						
						<%
							String rol = "";
							int tipo = Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[5]));
							switch(tipo){
								case 1: rol = "Usuario";
									break;
								case 2: rol = "Moderador";
									break;
								case 3: rol = "Administrador";
									break;
							}
						%>
						
						<span id="tipoUsuario">Usuario: <%=rol %></span>
						<span id="spanDesconexion"><i class="fa fa-power-off"></i><a href="/compartearte/desconexion" id="desconexion"> Desconexión</a></span>
					</div>
					<div class="col-xs-12 col-md-4 push-md-2" id="infoUsuario2">
						<span id="mensajes">Mensajes: <%=String.valueOf(((Object[])session.getAttribute("datosUsuario"))[6]) %></span>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12" id="encontrarMensajes">
						<hr>
						<div class="col-xs-12" id="divModificar">
							<a href='/compartearte/modificarPerfil.jsp?id=<%=Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[1]))%>'>Modificar <strong>perfil</strong></a>
						</div>
						<div class="col-xs-12">
							<a href="/mensajes?id=<%=Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[1]))%>">Encontrar <strong>mensajes</strong> de <%= String.valueOf(((Object[])session.getAttribute("datosUsuario"))[3]) %></a>
						</div>
						<div class="col-xs-12">
							<a href="/temas?id=<%=Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[1]))%>">Encontrar <strong>temas iniciados</strong> de <%= String.valueOf(((Object[])session.getAttribute("datosUsuario"))[3]) %></a>
						</div>
						<div class="meterClear"></div>
						<hr>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 opcionesProductos">
						<a href="subir.jsp" class="btn btn-warning col-xs-12 col-md-4 push-md-1">Subir producto</a>
						<a href="" class="btn btn-warning col-xs-12 col-md-4 push-md-1" id="verProductos">Ver productos</a>
					</div>
				</div>
				<!-- VER PRODUCTOS -->
				<div id="accordion" role="tablist" aria-multiselectable="true" class="col-xs-12 col-md-10 push-md-1">
					<!--CARGADO DINAMICAMENTE-->
				</div>
			</div>
		</div>
		<%
			}
		%>
		
		
		
		
		
		<%
			/*---------- LISTA DE MENSAJES QUE IRA MOSTRANDO EL PLUGIN TOASTR EN FUNCION DE LAS DISTINTAS ACCIONES DEL USUARIO -----------*/
		
		
			/*MENSAJES TOASTR, para cuando el usuario haga login y sea erroneo*/
			if(estado.equals("LoginErroneo")){
				/*Reinicia el mensaje de la session para que no muestre el mensaje de nuevo*/
				session.setAttribute("estado", "Invitado");
		%>
			<script type="text/javascript">
				toastr.error("El usuario o contraseña que ha introducido son incorrectos");
			</script>
		<%
			}
		%>
		
		
		
		
		
		
		<%
			/*MENSAJES TOASTR, para cuando el usuario haga login y este baneado*/
			if(estado.equals("Baneado")){
				/*Aqui se hace lo mismo que cuando el login erroneo*/
				session.setAttribute("estado", "Invitado");
		%>
			<script type="text/javascript">
				toastr.error('La cuenta <%= String.valueOf(((Object[])session.getAttribute("datosUsuario"))[2]) %> actualmente se encuentra baneada');
			</script>
		<%
			}
		%>
		
		
		
		
		
		<%
			/*MENSAJES TOASTR, para cuando el usuario se registre correctamente*/
			if(estado.equals("registroCorrecto")){
				/*Se cambia el estado a conectado, para que pueda iniciar sesion una vez registrado*/
				session.setAttribute("estado", "Invitado");
		%>
			<script type="text/javascript">
				toastr.success('Se ha registrado correctamente. Ya puede iniciar sesión con su cuenta.');
			</script>
		<%
			}
		%>
		
		
		
		
		<%
			/*MENSAJES TOASTR, para cuando se inserta con exito un producto nuevo*/
			if(request.getSession().getAttribute("exitoInsercion")!=null){
		%>
			<script type="text/javascript">
				toastr.success('<%=String.valueOf(request.getSession().getAttribute("exitoInsercion"))%>');
				
			</script>
		<%
			request.getSession().removeAttribute("exitoInsercion");
			}
		
		%>



		<%
			/*MENSAJES TOASTR, para cuando no se inserta con exito un producto nuevo*/
			if(request.getSession().getAttribute("errorInsercion")!=null){
		%>
			<script type="text/javascript">
				toastr.error('<%=String.valueOf(request.getSession().getAttribute("errorInsercion"))%>');
			</script>
		<%
			request.getSession().removeAttribute("errorInsercion");
			}
		
		%>
		
		
		
		
		<%
			/*MENSAJES TOASTR, para cuando se inserta con exito un producto nuevo*/
			if(request.getSession().getAttribute("exitoBorrado")!=null){
		%>
			<script type="text/javascript">
				toastr.success('<%=String.valueOf(request.getSession().getAttribute("exitoBorrado"))%>');
				
			</script>
		<%
			request.getSession().removeAttribute("exitoBorrado");
			}
		
		%>



		<%
			/*MENSAJES TOASTR, para cuando no se inserta con exito un producto nuevo*/
			if(request.getSession().getAttribute("errorBorrado")!=null){
		%>
			<script type="text/javascript">
				toastr.error('<%=String.valueOf(request.getSession().getAttribute("errorBorrado"))%>');
			</script>
		<%
			request.getSession().removeAttribute("errorBorrado");
			}
		%>
		
		
		
		<%
			/*MENSAJES TOASTR, para cuando se modifica el perfil*/
			if(request.getSession().getAttribute("exitoActualizacion")!=null){
		%>
			<script type="text/javascript">
				toastr.success('<%=String.valueOf(request.getSession().getAttribute("exitoActualizacion"))%>');
			</script>
		<%
			request.getSession().removeAttribute("exitoActualizacion");
			}
		%>



		<%
			/*MENSAJES TOASTR, para cuando se crea con exito un nuevo mensaje privado*/
			if(request.getSession().getAttribute("exitoPrivado")!=null){
		%>
			<script type="text/javascript">
				toastr.success('<%=String.valueOf(request.getSession().getAttribute("exitoPrivado"))%>');
			</script>
		<%
			request.getSession().removeAttribute("exitoPrivado");
			}
		
		%>



		<%
			/*MENSAJES TOASTR, para cuando no se crea con exito un nuevo mensaje privado*/
			if(request.getSession().getAttribute("errorPrivado")!=null){
		%>
			<script type="text/javascript">
				toastr.error('<%=String.valueOf(request.getSession().getAttribute("errorPrivado"))%>');
			</script>
		<%
			request.getSession().removeAttribute("errorPrivado");
			}
		
		%>
			
		
		
		

		<!-- PIE -->
		<div id="espaciadoInferior">
			<p>© ComparteArte.com | Página web diseñada por Josue Hurtado Cots | Todos los derechos reservados</p>
		</div>
	</div>
	
</body>
</html>