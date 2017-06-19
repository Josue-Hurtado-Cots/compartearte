<%@ page contentType="text/html" pageEncoding="UTF-8"%>

	<%
		/*Solicitamos la sesion para que inicialice los datos*/
		request.getSession();
		/*Comprobamos que no haya iniciado sesion para mostrar el registro*/
		if(String.valueOf(session.getAttribute("estado")).equals("Invitado")){
			
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
						</ul>
					</div>
				</nav>
			</div>
		</div>
		<div id="espaciado"></div>


		<!-- CUERPO -->
		<div class="row">
			<div class="col-xs-12 col-md-10" id="registro">
				<div class="col-xs-12 col-md-8 push-md-2">
					<form action="/compartearte/registroUsuario" id="formRegistro" method="post">

						<div class="form-group">
							<label class="col-md-3" for="usuario">Usuario</label>
							<div class="col-md-8 campoRegistro">
								<input name="usuario" type="text" placeholder="Nombre de usuario" class="form-control input-md">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-3" for="correo">Correo</label>
							<div class="col-md-8 campoRegistro">
								<input name="correo" type="text" placeholder="Correo electrónico" class="form-control input-md">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3" for="correo2">Repita correo</label>
							<div class="col-md-8 campoRegistro">
								<input name="correo2" type="text" placeholder="Repita su correo electrónico" class="form-control input-md">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3" for="telefono">Telefono</label>
							<div class="col-md-8 campoRegistro">
								<input name="telefono" type="text" placeholder="Telefono de contacto" class="form-control input-md">
							</div>
						</div>
						<div class="col-xs-12"></div>

						<div class="form-group">
							<label class="col-md-3" for="pass">Contraseña</label>
							<div class="col-md-8 campoRegistro">
								<input name="pass" type="password" placeholder="Introduzca su contraseña" class="form-control input-md">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3" for="pass2">Repita contraseña</label>
							<div class="col-md-8 campoRegistro">
								<input name="pass2" type="password" placeholder="Repita su conraseña" class="form-control input-md">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 captcha" for="captcha" id="labelCaptcha"></label>
							<div class="col-md-8 campoRegistro captcha">
								<input name="captcha" type="text" placeholder="Solución" class="form-control input-md">
							</div>
						</div>
						
						<div class="col-xs-12 form-group">
							<input type="submit" value="Enviar" id="confirmarRegistro" class="col-xs-12 col-md-6 push-md-3 btn btn-warning">
						</div>

					</form>
				</div>
			</div>
		</div>
			
		<%
			/*MENSAJES TOASTR, para cuando el usuario se registre y se produzca un fallo*/
			if(session.getAttribute("estado")!=null && (session.getAttribute("estado")).equals("registroErroneo")){
				/*Se reinicia el atributo estado para que no muestre el mensaje otra vez*/
				session.setAttribute("estado", "Invitado");
		%>
			<script type="text/javascript">
				toastr.error('No se ha podido realizar el registro. Por favor, inténtelo de nuevo.');
			</script>
		<%
			}
		%>
				

		<!-- PIE -->
		<div id="espaciadoInferior">
			<p>© ComparteArte.com | Página web diseñada por Josue Hurtado Cots | Todos los derechos reservados</p>
		</div>
	</div>
	
</body>
</html>


	<%
		}else{
			response.sendRedirect("index.jsp");
		}
	%>