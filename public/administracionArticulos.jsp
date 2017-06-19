<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%! int pagina; %>

<%
	/*Solicitamos la session para que inicialice los datos*/
	request.getSession();
%>


<%if(session.getAttribute("datosUsuario")!=null && Integer.parseInt(String.valueOf(((Object[])session.getAttribute("datosUsuario"))[5]))==3){ %>
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
				<%
					/*Preparamos el numero de pagina para la paginacion*/
					if(request.getParameter("pagina")==null){
						pagina = 1;
					}else{
						pagina = Integer.valueOf(request.getParameter("pagina"));
					}
				%>
				<input type="hidden" id="pagina" value="<%=pagina%>">
				<div class="col-xs-12 col-md-10 push-md-1" id="cuerpoArticulos">
					<h1 id="tituloAdmin">Gestión de articulos</h1>
					<div class="row">
						<div class="col-xs-12 col-md-2"></div>
						<div class="col-xs-12 col-md-8" id="panelArticulos">
							<table class="table table-responsive table-striped tablaMantenimiento">
								<thead class="thead-inverse">
									<th>Propietario</th>
									<th>Titulo</th>
									<th>Categoria</th>
									<th>Provincia</th>
									<th>Fecha</th>
									<th>Ver mas del usuario</th>
								</thead>
								<tbody>
									<!--CARGADO DINAMICAMENTE-->
								</tbody>
							</table>
							<div class="col-xs-12 paginacion">				
								<nav>
									<ul class="pagination" id="paginacion">
										<!--CARGADO DINAMICAMENTE-->
									</ul>
								</nav>
							</div>
						</div>
						<div class="col-xs-12 col-md-2"></div>
					</div>
				</div>
			</div>
			<!--MODALES-->
			<div id="modales">
				<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabelLarge" aria-hidden="true">
					<div class="modal-dialog modal-lg" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title"><strong>Articulos del usuario</strong></h5>
							</div>
								<div class="col-xs-12 modal-body">
									<div id="accordion" role="tablist" aria-multiselectable="true" class="col-xs-12 col-md-10 push-md-1 acordeon">
										<!--CARGADO DINAMICAMENTE-->
									</div>
								</div>
							<div class="modal-footer">
								<div class="col-xs-12 col-md-4 push-md-4 botonesModUsuario">
									<button type="button" class="btn btn-secondary col-xs-12" data-dismiss="modal">Cerrar ventana</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--FIN MODALES-->
			<!-- PIE -->
			<div id="espaciadoInferior">
				<p>© ComparteArte.com | Página web diseñada por Josue Hurtado Cots | Todos los derechos reservados</p>
			</div>
		</div>
		
	</body>
	</html>
	
	
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
	
	
<%}else{
	response.sendRedirect("index.jsp");
}%>