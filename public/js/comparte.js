/* ---- VARIABLES GLOBALES ---- */

var valorCaptcha1;
var valorCaptcha2;





$(document).ready(function(){
/* ------------ ELEMENTOS GENERALES ------------ */

	/*Cambiar color del subrayado del menu segun la pagina en la que estemos*/
	menuActual();
	/*Carga las notificaciones*/
	cargarNotificaciones();

	/*Para todos los selectpicker*/
	$('.selectpicker').selectpicker();

	/*Obtenemos la pagina donde estamos*/
	var paginaActual=window.location+"";
	var splitPagina=paginaActual.split("/");
	paginaActual=splitPagina[splitPagina.length-1];



/* ------------ PAGINA INDEX ------------ */
	if(paginaActual.indexOf("index.jsp")!=-1 || paginaActual.length==0){	
		cargarUltimosProductos();
		cargarUltimosTemas();
	}



/* ------------ PAGINA ULTIMOS PRODUCTOS ------------ */
	/*Carga la primera página de productos*/
	if(paginaActual=="ultimos.jsp"){	
		cargarUltimos(1);
	}

	$(document).on("click", ".botonNumeroPagina", function(){
		var numeroPag=$(this).attr('data-numeroPag');
		window.scrollTo(0,0);
		cargarUltimos(numeroPag);
	});



/* ------------ PAGINA PRODUCTO ------------ */
	if(paginaActual.indexOf("producto.jsp")!=-1){	
		var tipo = $("#tipo").val();
		var id = $("#id").val();
		cargarProducto(tipo, id);
	}


/* ------------ PAGINA FORO ------------ */
	if(paginaActual.indexOf("foro.jsp")!=-1){	
		cargarSubforos();
	}



/* ------------ PAGINA SUBFORO ------------ */
	if(paginaActual.indexOf("subforo.jsp")!=-1){
		var pagina=$("#pagina").val();
		var subforo=$("#subforo").val();
		cargarTemas(pagina, subforo);
	}

	/*Mandar nueva respuesta*/
	$("#enviarRespuesta").on("click", function(){
		var idUsuario=$("#idUsuario").val();
		var respuesta=$("#editor").val();
		var idTema=$("#tema").val();
		enviarRespuesta(idUsuario, idTema, respuesta);
	})



/* ------------ PAGINA TEMA ------------ */
	if(paginaActual.indexOf("tema.jsp")!=-1){
		var pagina=$("#pagina").val();
		var tema=$("#tema").val();
		cargarTema(pagina, tema);
		/*Carga el editor*/
		$("#editor").summernote({
			lang: "es-ES",
			height: 150,
			toolbar: [
		    ['style', ['bold', 'italic', 'underline', 'clear']],
		    ['font', ['strikethrough', 'superscript', 'subscript']],
		    ['fontsize', ['fontsize']],
		    ['color', ['color']],
		    ['table', ['table']],
		    ['para', ['ul', 'ol', 'paragraph']],
		    ['height', ['height']],
		    ['picture', ['picture']],
		    ['codeview', ['codeview']]
		  ]
		});
	}


/* ------------ PAGINA NUEVO TEMA ------------ */
	if(paginaActual.indexOf("nuevoTema.jsp")!=-1){
		/*Carga el editor*/
		$("#editor").summernote({
			lang: "es-ES",
			height: 150,
			toolbar: [
		    ['style', ['bold', 'italic', 'underline', 'clear']],
		    ['font', ['strikethrough', 'superscript', 'subscript']],
		    ['fontsize', ['fontsize']],
		    ['color', ['color']],
		    ['table', ['table']],
		    ['para', ['ul', 'ol', 'paragraph']],
		    ['height', ['height']],
		    ['picture', ['picture']],
		    ['codeview', ['codeview']]
		  ]
		});
	}


/* ------------ PAGINA MENSAJE PRIVADO ------------ */
	if(paginaActual.indexOf("mensajePrivado.jsp")!=-1){
		/*Carga el editor*/
		$("#editor").summernote({
			lang: "es-ES",
			height: 150,
			toolbar: [
		    ['style', ['bold', 'italic', 'underline', 'clear']],
		    ['font', ['strikethrough', 'superscript', 'subscript']],
		    ['fontsize', ['fontsize']],
		    ['color', ['color']],
		    ['table', ['table']],
		    ['para', ['ul', 'ol', 'paragraph']],
		    ['height', ['height']],
		    ['picture', ['picture']],
		    ['codeview', ['codeview']]
		  ]
		});
	}


/* ------------ PAGINA BUSCAR PRODUCTOS ------------ */
	/*Oculta los elementos de la pagina buscar producto*/
	ocultarCampos();

	/*Va cambiando los componentes de la pagina buscar en funcion del producto que vaya a buscar*/
	$("select[name='productoBuscar'").on("change", function(){
		var elementoSeleccion=$(this).val();
		switch(elementoSeleccion){
			case "libro": ocultarCampos();
				$("#divOrdenBuscar, #divProvBuscar, #divNombreBuscar, #categoriaLibro").show();
				break;
			case "pelicula": ocultarCampos();
				$("#divOrdenBuscar, #divProvBuscar, #divNombreBuscar, #formato, #categoriaPelicula").show();
				break;
			case "videojuego": ocultarCampos();
				$("#divOrdenBuscar, #divProvBuscar, #divNombreBuscar, #consola, #categoriaVideojuego").show();
				break;
		}
	});


	/*Filtro de busqueda por cambio en select*/
	$("#campoTipo, #campoProvincia, #campoOrden, #campoCategoriaLibro, #campoCategoriaPelicula, #campoCategoriaVideojuego, #campoConsola, #campoFormato").on("change", function(){
		busquedaProductos();
	});

	/*Filtro de busqueda por tecleo de nombre*/
	$("#campoNombre").on("keyup", function(){
		busquedaProductos();
	});

	/*Quitar filtros*/
	$("#reset").on("click", function(){
		$("select").val("").change();
		$("#campoNombre").val("");
		ocultarCampos();
		busquedaProductos();
	})



/* ------------ PAGINA VER PERFIL ------------ */
	/*Oculta el div con los productos del usuario, y al hacer click sobre ver productos, los muestra*/
	$("#accordion").hide();
	$("#verProductos").on("click", function(event){
		event.preventDefault();
		if($(this).text()=="Ver productos"){
			$("#accordion").show('slow');
			$(this).text("Ocultar productos");
		}else{
			$("#accordion").hide('slow');
			$(this).text("Ver productos");
		}
	});

	/*Carga automaticamente los datos de los articulos del usuario al iniciar sesion*/
	var idUsuario=$("#idUsuario").val();

	/*Si no esncontramos en la pagina de perfil del usuario, y el campo con id no es nulo, manda una peticion por ajax para que el servidor envie
	la lista de articulos del usuario y los añada en el acordeon*/
	if(paginaActual=="perfil.jsp" && idUsuario!=null){
		cargarProductosUsuario(idUsuario, "perfil");
	}


	/*Configuracion toastr*/
	toastr.options = {
	  "closeButton": false,
	  "debug": false,
	  "newestOnTop": false,
	  "progressBar": false,
	  "positionClass": "toast-top-right",
	  "preventDuplicates": false,
	  "onclick": null,
	  "showDuration": "1000",
	  "hideDuration": "1000",
	  "timeOut": "4000",
	  "extendedTimeOut": "1000",
	  "showEasing": "swing",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}



/* ------------ PAGINA MODIFICAR PERFIL ------------ */

	/*Modifica el usuario, contraseña, o ambos, del usuario*/
	var idUsuario=$("#idModificacion").val();
	modificarPerfil(idUsuario);




/* ------------ PAGINA SUBIR PRODUCTO ------------ */
	/*Oculta los elementos de la pagina subir producto*/
	ocultarCampos();

	/*Va cambiando los componentes de la pagina para subir producto en funcion de lo que vaya seleccionando el usuario*/
	$("select[name='productoSubir'").on("change", function(){
		var elementoSeleccion=$(this).val();
		switch(elementoSeleccion){
			case "libro": ocultarCampos();
				$("#categoriaLibro, #titulo, #provincia, #foto, #descripcionProducto").show();
				break;
			case "pelicula": ocultarCampos();
				$("#categoriaPelicula, #formato, #provincia, #titulo, #trailer, #foto, #descripcionProducto").show();
				break;
			case "videojuego": ocultarCampos();
				$("#categoriaVideojuego, #consola, #provincia, #titulo, #foto, #descripcionProducto").show();
				break;
		}
	});

	/*Carga el fileinput*/
	$("#fotoProducto").fileinput({
		maxFileSize: 1000,
		maxFilesNum: 3,
	});

	/*Si el formulario se manda directamente por http, produce fallo en los caracteres, asi que lo mando por ajax que no los produce*/
	$("#formSubida").on("submit", function(event){
		event.preventDefault();
		var form=new FormData($("#formSubida")[0]);
		$.ajax({
			url: "/compartearte/subirProducto",
			type: "POST",
			data: form,
			cache: false,
			contentType: false,
			processData: false,
			success: function(){
				window.location = "perfil.jsp";
			}
		});
	});



/* ------------ PAGINA REGISTRO ------------ */
	/*Genera 2 random que se usaran como captcha. El usuario debera resolver la suma para mandar el formulario*/
	generaCaptcha();

	/*Comprueba que el captcha se ha resolvido correctamente y depura los campos de registro*/
	$("#formRegistro").on("submit", function(event){
		depuracionRegistro(event);
	});




/* ------------ PAGINA DE ERROR ------------ */
	/*Vuelve a la pagina anterior al pulsar en el enlace*/
	$("#atrasError").on("click", function(){
		window.history.back();
	});




/* ------------ PAGINA DE ADMIN ------------ */
	var ruta = window.location+"";
	if(ruta.indexOf("administracionUsuarios")!=-1){
		cargarUsuarios();
	}

	/*Carga los productos en la pagina de administracion de productos*/
	if(paginaActual.indexOf("administracionArticulos.jsp")!=-1){	
		var pagina = $("#pagina").val();
		cargarProductosAdministracion(pagina);
	}

	/*Cuando se carga la tabla de productos, aparece a la derecha un ojo. Si se pulsa sobre este, mostrará un modal con todos los productos de ese usuario*/
	$(document).on("click", "#botonOjo", function(){
		var idPropietario=$(this).attr("data-idPropietario");
		cargarProductosUsuario(idPropietario, "admin");
		$("#accordion").css({
			display: "block"
		});
	});



/* ------------ PAGINA MODIFICAR USUARIO (DESDE ADMIN) ------------ */
	var ruta = window.location+"";
	if(ruta.indexOf("modificarUsuario")!=-1){
		var id=$("#idUsuario").val();
		cargarDatosUsuario(id);
	}



});/*CIERRE DOCUMENT.READY*/








/* ---------------  FUNCIONES  --------------- */


/*Cambiar color del subrayado del menu segun la pagina en la que estemos*/
function menuActual(){
	var localizacion=window.location+"";
	var pagina=localizacion.split("/");
	localizacion=pagina[pagina.length-1];
	
	/*Para las paginas principales, entra por el default, pero por ejemplo para subir producto, que es una subseccion de 
	perfil, se le indica manualmente que se debe subrayar el elemento del menu 'perfil'*/
	switch(localizacion){
		case "subir.jsp":
			$("#menu a[href='perfil.jsp'").css({
				borderBottom: '2px solid orange'
			});
			break;
			
		case "registro.jsp":
			$("#menu a[href='perfil.jsp'").css({
				borderBottom: '2px solid orange'
			});
			break;

		case "administracionUsuarios.jsp":
			$("#administracionMenu").css({
				borderBottom: '2px solid orange'
			});
			break;

		case "administracionArticulos.jsp":
			$("#administracionMenu").css({
				borderBottom: '2px solid orange'
			});
			break;

		default:
			$("#menu a[href='"+localizacion+"'").css({
				borderBottom: '2px solid orange'
			});
			break;
	}
}




/*Oculta los campos de los formularios*/
function ocultarCampos(){
	$("#categoriaLibro, #categoriaPelicula, #categoriaVideojuego, #provincia, #consola, #formato, #titulo, #trailer, #foto, "+ 
		"#descripcionProducto, #divOrdenBuscar, #divProvBuscar, #divNombreBuscar").hide();
}



/*Genera un captcha propio para el registro*/
function generaCaptcha(){
	valorCaptcha1=Math.floor((Math.random()*20)+1);
	valorCaptcha2=Math.floor((Math.random()*20)+1);
	$("#labelCaptcha").html(valorCaptcha1+" + "+valorCaptcha2+" =");
}




/*Depuracion de registro*/
function depuracionRegistro(event){

	/*Capturamos valores introducidos en los campos*/
	var usuario=$("input[name='usuario'").val();
	var correo=$("input[name='correo'").val();
	var correo2=$("input[name='correo2'").val();
	var telefono=$("input[name='telefono'").val();
	var pass=$("input[name='pass'").val();
	var pass2=$("input[name='pass2'").val();
	var solucionCaptcha=$("input[name='captcha'").val();
	var usuarioCorrecto, correoCorrecto, telefonoCorrecto, passCorrecta, solucionCaptchaCorrecta;
	
	/*Depuramos los campos*/
	/*Antes de nada, quitamos la clase a cada campo por si tenia alguno mal y ya lo tiene bien*/
	$("input[name='usuario'").removeClass('campoErroneo');
	$("input[name='correo'").removeClass('campoErroneo');
	$("input[name='correo2'").removeClass('campoErroneo');
	$("input[name='telefono'").removeClass('campoErroneo');
	$("input[name='pass'").removeClass('campoErroneo');
	$("input[name='pass2'").removeClass('campoErroneo');
	$("input[name='captcha'").removeClass('campoErroneo');

	/*Empezamos las comprobaciones*/
	/*NOMBRE DE USUARIO*/
	if(usuario.length>=4){
		for(i=0;i<usuario.length;i++){
			var caracter=usuario.charCodeAt(i);
			if(caracter>=48 && caracter<=57 || caracter>=65 && caracter<=90 || caracter>=97 && caracter<=122 
				|| caracter>=45 && caracter<=46 || caracter==95){
				usuarioCorrecto=1;
			}else{
				usuarioCorrecto=0;
				$("input[name='usuario'").addClass('campoErroneo');
				break;
			}
		}
	}else{
		usuarioCorrecto=0;
		$("input[name='usuario'").addClass('campoErroneo');
	}

	/*CORREO*/
	if(correo==correo2){
		var patternCorreo=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if(patternCorreo.test(correo)){
			correoCorrecto=1;
		}else{
			correoCorrecto=0;
			$("input[name='correo'").addClass('campoErroneo');
			$("input[name='correo2'").addClass('campoErroneo');
		}
	}else{
		correoCorrecto=0;
		$("input[name='correo'").addClass('campoErroneo');
		$("input[name='correo2'").addClass('campoErroneo');
	}

	/*TELEFONO*/
	if(telefono.length==9){
		for(i=0;i<telefono.length;i++){
			var digito=telefono.charCodeAt(i);
			if(digito>=48 && digito<=57){
				telefonoCorrecto=1;
			}else{
				telefonoCorrecto=0;
				$("input[name='telefono'").addClass('campoErroneo');
				break;
			}
		}
	}else{
		telefonoCorrecto=0;
		$("input[name='telefono'").addClass('campoErroneo');
	}

	/*CONTRASEÑA*/
	if(pass==pass2 && pass.length > 6){
		passCorrecta=1;
	}else{
		passCorrecta=0;
		$("input[name='pass'").addClass('campoErroneo');
		$("input[name='pass2'").addClass('campoErroneo');
	}

	/*CAPTCHA*/
	if((parseInt(valorCaptcha1)+parseInt(valorCaptcha2))==parseInt(solucionCaptcha)){
		solucionCaptchaCorrecta=1;
	}else{
		solucionCaptchaCorrecta=0;
		$("input[name='captcha'").addClass('campoErroneo');
		generaCaptcha();
	}


	/*Si todos los datos estan bien introducidos, se pasa a realizar el registro*/
	if(!usuarioCorrecto || !correoCorrecto || !telefonoCorrecto || !passCorrecta || !solucionCaptchaCorrecta){
		event.preventDefault();
	}

}




/*Funcion que para mandar los datos al servidor de la modificacion de correo/contraseña*/
function modificarPerfil(idUsuario){
	var correoCorrecto=false;

	$("#form-modificar-perfil").on("submit", function(event){
		/*Comprueba si el correo se ha introducido correctamente*/
		var correo=$("#correo").val();
		var patternCorreo=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if(patternCorreo.test(correo)){
			correoCorrecto=true;
		}else{
			$("#correo").addClass('campoErroneo');
		}

		/*Comprueba si los campos de contraseña nueva se han rellenado, y si es asi, que coincidan*/
		var passNueva=$("#nuevaPass").val();
		var passRep=$("#passRep").val();
		var passwordCorrecta=false;
		if(passNueva=="" && passRep==""){
			passwordCorrecta=true;
		}else{
			if(passNueva.length>6 && passNueva==passRep){
				passwordCorrecta=true;
			}else{
				$("#nuevaPass").addClass('campoErroneo');
				$("#passRep").addClass('campoErroneo');
			}
		}

		/*Por ultimo, comprueba los booleanos, y si alguno es false, no manda el form*/
		if(correoCorrecto==false || passwordCorrecta==false){
			event.preventDefault();
		}
	});
}




/*Carga los productos del usuario, llamado desde perfil.jsp. Los carga dentro del acordeon*/
function cargarProductosUsuario(idUsuario, paginaActual){
	$.ajax({
		url: "/compartearte/cargarProductos",
		type: "POST",
		dataType: "json",
		data: {id: idUsuario, origen: paginaActual},
		success: function(resp){
			/*Carga los libros en el acordeon*/
			var cadena="";
			var origen="";

			if(paginaActual=="perfil"){
				origen=paginaActual;
			}else{
				origen="administracion";
			}
			
			for(libro=0;libro<resp[0].length;libro++){
				var id=resp[0][libro][7];
				var titulo=resp[0][libro][0];
				var img1=resp[0][libro][4];
				var img2=resp[0][libro][5];
				var img3=resp[0][libro][6];
				var categoria=resp[0][libro][3];
				var provincia=resp[0][libro][2];
				var descripcion=resp[0][libro][1];

				cadena+="<div class='card'>"+
					"<div class='card-header' role='tab' id='heading-L-"+id+"'>"+
						"<h5 class='mb-0'>"+
						"<a data-toggle='collapse' data-parent='#accordion' href='#producto-L-"+id+"' aria-expanded='true' aria-controls='producto-L-"+id+"'>"+titulo+"</a>"+
						"</h5>"+
					"</div>"+
					"<div id='producto-L-"+id+"' class='collapse show' role='tabpanel' aria-labelledby='heading-L-"+id+"'>"+
						"<div class='card-block'>"+
							"<div class='col-xs-12 col-md-3'>"+
								"<img src='"+img1+"' alt='imagen de producto' class='img-fluid'>"+
							"</div>"+
							"<div class='col-xs-12 col-md-9'>"+
								"<p><strong class='orange'>Categoría: </strong>"+categoria+"</p>"+
								"<p><strong class='orange'>Provincia: </strong>"+provincia+"</p>"+
								"<p><strong class='orange'>Descripción: </strong>"+descripcion+"</p>"+
							"</div>"+
							"<div class='col-xs-12 opcionesProductos'>"+
								"<a href='/compartearte/modificarProducto?tipo=libro&id="+id+"&origen="+origen+"' class='btn btn-warning col-xs-12 col-md-4 push-md-1'>Modificar producto</a>"+
								"<a href='/compartearte/borrarProducto?tipo=libro&id="+id+"&origen="+origen+"' class='btn btn-warning col-xs-12 col-md-4 push-md-1'>Eliminar producto</a>"+
							"</div>"+
						"</div>"+
					"</div>"+
				"</div>";
			}
			/*Carga las peliculas en el acordeon*/
			for(pelicula=0;pelicula<resp[1].length;pelicula++){
				var formato=resp[1][pelicula][9];
				var id=resp[1][pelicula][7];
				var trailer=resp[1][pelicula][8];
				var titulo=resp[1][pelicula][0];
				var img1=resp[1][pelicula][4];
				var img2=resp[1][pelicula][5];
				var img3=resp[1][pelicula][6];
				var categoria=resp[1][pelicula][3];
				var provincia=resp[1][pelicula][2];
				var descripcion=resp[1][pelicula][1];

				trailer = trailer.substring(trailer.indexOf("watch?v=")+8, trailer.length);

				cadena+="<div class='card'>"+
					"<div class='card-header' role='tab' id='heading-P-"+id+"'>"+
						"<h5 class='mb-0'>"+
						"<a class='collapsed' data-toggle='collapse' data-parent='#accordion' href='#producto-P-"+id+"' aria-expanded='false' aria-controls='producto-P-"+id+"'>"+titulo+"</a>"+
						"</h5>"+
					"</div>"+
					"<div id='producto-P-"+id+"' class='collapse' role='tabpanel' aria-labelledby='heading-P-"+id+"'>"+
						"<div class='card-block'>"+
							"<div class='col-xs-12 col-md-3'>"+
								"<img src='"+img1+"' alt='imagen de producto' class='img-fluid'>"+
							"</div>"+
							"<div class='col-xs-12 col-md-9'>"+
								"<p><strong class='orange'>Categoría: </strong>"+categoria+"</p>"+
								"<p><strong class='orange'>Provincia: </strong>"+provincia+"</p>"+
								"<p><strong class='orange'>Formato: </strong>"+formato+"</p>"+
								"<p><strong class='orange'>Descripción: </strong>"+descripcion+"</p>"+
								"<p><strong class='orange'>Tráiler: </strong></p>"+
								"<p>"+
									"<div style='position:relative;height:0;padding-bottom:56.25%'><iframe src='https://www.youtube.com/embed/"+trailer+"' style='position:absolute;width:100%;height:100%;left:0' width='640' height='360' frameborder='0' allowfullscreen></iframe></div>"+
								"</p>"+
							"</div>"+
							"<div class='col-xs-12 opcionesProductos'>"+
								"<a href='/compartearte/modificarProducto?tipo=pelicula&id="+id+"&origen="+origen+"' class='btn btn-warning col-xs-12 col-md-4 push-md-1'>Modificar producto</a>"+
								"<a href='/compartearte/borrarProducto?tipo=pelicula&id="+id+"&origen="+origen+"' class='btn btn-warning col-xs-12 col-md-4 push-md-1'>Eliminar producto</a>"+
							"</div>"+
						"</div>"+
					"</div>"+
				"</div>";
			}
			/*Carga los videojuegos en el acordeon*/
			for(videojuego=0;videojuego<resp[2].length;videojuego++){
				var consola=resp[2][videojuego][8];
				var id=resp[2][videojuego][7];
				var titulo=resp[2][videojuego][0];
				var img1=resp[2][videojuego][4];
				var img2=resp[2][videojuego][5];
				var img3=resp[2][videojuego][6];
				var categoria=resp[2][videojuego][3];
				var provincia=resp[2][videojuego][2];
				var descripcion=resp[2][videojuego][1];

				cadena+="<div class='card'>"+
					"<div class='card-header' role='tab' id='heading-V-"+id+"'>"+
						"<h5 class='mb-0'>"+
						"<a class='collapsed' data-toggle='collapse' data-parent='#accordion' href='#producto-V-"+id+"' aria-expanded='false' aria-controls='producto-V-"+id+"'>"+titulo+"</a>"+
						"</h5>"+
					"</div>"+
					"<div id='producto-V-"+id+"' class='collapse' role='tabpanel' aria-labelledby='heading-V-"+id+"'>"+
						"<div class='card-block'>"+
							"<div class='col-xs-12 col-md-3'>"+
								"<img src='"+img1+"' alt='imagen de producto' class='img-fluid'>"+
							"</div>"+
							"<div class='col-xs-12 col-md-9'>"+
								"<p><strong class='orange'>Categoría: </strong>"+categoria+"</p>"+
								"<p><strong class='orange'>Provincia: </strong>"+provincia+"</p>"+
								"<p><strong class='orange'>Consola: </strong>"+consola+"</p>"+
								"<p><strong class='orange'>Descripción: </strong>"+descripcion+"</p>"+
							"</div>"+
							"<div class='col-xs-12 opcionesProductos'>"+
								"<a href='/compartearte/modificarProducto?tipo=videojuego&id="+id+"&origen="+origen+"' class='btn btn-warning col-xs-12 col-md-4 push-md-1'>Modificar producto</a>"+
								"<a href='/compartearte/borrarProducto?tipo=videojuego&id="+id+"&origen="+origen+"' class='btn btn-warning col-xs-12 col-md-4 push-md-1'>Eliminar producto</a>"+
							"</div>"+
						"</div>"+
					"</div>"+
				"</div>"
			}
			/*Añade la cadena al acordeon*/
			$("#accordion").html(cadena);
		}
	});
}




/*Funcion para la paginacion de los ultimos productos*/
function paginacion(numPag){
	/*Montamos la paginacion*/
	$.ajax({
		url: "/compartearte/paginacion",
		type: "POST",
		data: {numPag: numPag},
		success: function(resp){
			/*Obtenemos el numero de productos, lo dividimos por el numero de elementos de cada pagina, y redondeamos hacia arriba*/
			var numeroPaginas=Math.ceil(resp/10);
			/*Preparamos la paginacion*/
			var cadena="<nav><ul class='pagination'>";
			for(i=1;i<=numeroPaginas;i++){
				if(i==numPag){
					cadena+="<li class='page-item liNumPag active'><button data-numeroPag='"+i+"' class='page-link botonNumeroPagina'>"+i+"</button></li>";
				}else{
					cadena+="<li class='page-item liNumPag'><button data-numeroPag='"+i+"' class='page-link botonNumeroPagina'>"+i+"</button></li>";
				}
			}
			cadena+="</ul></nav></div>";
			$("div.paginacionUltimos").html(cadena);
		}
	});
}


/*Carga los ultimos productos subidos a la pagina*/
function cargarUltimos(numPag){
	$.ajax({
		url: "/compartearte/ultimosArticulos",
		type: "POST",
		dataType: "json",
		data: {numPag: numPag},
		success: function(resp){
			var cadena="";
			for(i=0;i<resp.length;i++){
				var id=resp[i][0];
				var titulo=resp[i][1];
				var descripcion=resp[i][2];
				var categoria=resp[i][3];
				var provincia=resp[i][4];
				var img1=resp[i][5];
				var tipo=resp[i][8];
				var fecha=resp[i][9];
				/*Reformateamos la fecha*/
				var arrayFecha = fecha.split("-");
				fecha = arrayFecha[2]+"/"+arrayFecha[1]+"/"+arrayFecha[0];
				cadena+="<div class='col-xs-12 listaProductos'>"+
					"<div class='col-xs-12 col-md-2 imagenProducto'>"+
						"<img src='"+img1+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 tituloProducto'>"+
						"<h5><strong><a href='/compartearte/producto.jsp?tipo="+tipo+"&id="+id+"'>"+titulo+"</a></strong></h5>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5'>"+
						"<p><strong>Producto: </strong>"+tipo+"</p>"+
						"<p><strong>Categoría: </strong>"+categoria+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5'>"+
						"<p><strong>Provincia: </strong>"+provincia+"</p>"+
						"<p><strong>Publicación: </strong>"+fecha+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 descripcionProducto'>"+
						"<p><strong>Descripción: </strong>"+descripcion+"</p>"+
					"</div>"+
				"</div>";
			}
			cadena+="<div class='col-xs-12 text-xs-center paginacionUltimos'></div>";
			$("#ultimos .row").html(cadena);
			paginacion(numPag);
		}
	});
}




/*Funcion que es llamada cuando se va a cargar la informacion de un producto en concreto, osea, cuando se pincha en un producto
desde ultimos.jsp, o desde buscar.jsp*/
function cargarProducto(tipo, id){
	$.ajax({
		url: "/compartearte/producto",
		type: "POST",
		dataType: "json",
		data: {tipo: tipo, id: id},
		success: function(resp){

			var cadena="";
			var titulo=resp[1];
			var nombreUsuario=resp[2];
			var descripcion=resp[3];
			var provincia=resp[4];
			var categoria=resp[5];
			var img1=resp[6];
			var img2=resp[7];
			var img3=resp[8];
			var fecha=resp[9].split("-");
			fecha=fecha[2]+"-"+fecha[1]+"-"+fecha[0];

			switch(tipo){

				case "Libro":
					var telefono=resp[10];
					cadena+="<div class='col-xs-12 col-md-2 imagenProducto'>"+
						"<img src='"+img1+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 tituloProducto'>"+
						"<h5><strong class='orange'>"+titulo+"</strong></h5>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5 infoProdMargin'>"+
						"<p><strong class='orange'>Producto: </strong>Libro</p>"+
						"<p><strong class='orange'>Categoría: </strong> "+categoria+"</p>"+
						"<p><strong class='orange'>Propietario:</strong> "+nombreUsuario+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5 infoProdMargin'>"+
						"<p><strong class='orange'>Provincia: </strong> "+provincia+"</p>"+
						"<p><strong class='orange'>Publicación: </strong> "+fecha+"</p>"+
						"<p><strong class='orange'>Contacto:</strong> "+telefono+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 descripcionProducto infoProdMargin'>"+
						"<p><strong class='orange'>Descripción:</strong> "+descripcion+"</p>"+
					"</div>"+
					"<div class='col-xs-12'></div>";
					if(img2!="" || img3!=""){
						cadena+="<div class='col-xs-12 col-md-2 push-md-2'>"+
							"<p><strong class='orange'>Otras imágenes:</strong></p>"+
						"</div>";
					}
					cadena+="<div class='col-xs-5 col-md-2 push-md-2'>"+
						"<img src='"+img2+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-5 col-md-2 push-md-2'>"+
						"<img src='"+img3+"' class='img-fluid'>"+
					"</div>";

					$(".infoProducto").html(cadena);
					break;

				case "Pelicula":
					var formato=resp[10];
					var trailer=resp[11].substring(resp[11].indexOf("watch?v=")+8, resp[11].length);
					var telefono=resp[12];
					cadena+="<div class='col-xs-12 col-md-2 imagenProducto'>"+
						"<img src='"+img1+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 tituloProducto'>"+
						"<h5><strong class='orange'>"+titulo+"</strong></h5>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5 infoProdMargin'>"+
						"<p><strong class='orange'>Producto: </strong>Libro</p>"+
						"<p><strong class='orange'>Categoría: </strong> "+categoria+"</p>"+
						"<p><strong class='orange'>Propietario:</strong> "+nombreUsuario+"</p>"+
						"<p><strong class='orange'>Formato:</strong> "+formato+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5 infoProdMargin'>"+
						"<p><strong class='orange'>Provincia: </strong> "+provincia+"</p>"+
						"<p><strong class='orange'>Publicación: </strong> "+fecha+"</p>"+
						"<p><strong class='orange'>Contacto:</strong> "+telefono+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 descripcionProducto infoProdMargin'>"+
						"<p><strong class='orange'>Descripción:</strong> "+descripcion+"</p>"+
					"</div>"+
					"<div class='col-xs-12'></div>";
					if(img2!="" || img3!=""){
						cadena+="<div class='col-xs-12 col-md-2 push-md-2'>"+
							"<p><strong class='orange'>Otras imágenes:</strong></p>"+
						"</div>";
					}
					cadena+="<div class='col-xs-5 col-md-2 push-md-2'>"+
						"<img src='"+img2+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-5 col-md-2 push-md-2'>"+
						"<img src='"+img3+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 push-md-2 descripcionProducto infoProdMargin'>"+
						"<p><strong class='orange'>Tráiler: </strong></p>"+
						"<p>"+
							"<div style='position:relative;height:0;padding-bottom:56.25%'><iframe src='https://www.youtube.com/embed/"+trailer+"' style='position:absolute;width:100%;height:100%;left:0' width='640' height='360' frameborder='0' allowfullscreen></iframe></div>"+
						"</p>"+
					"</div>";

					$(".infoProducto").html(cadena);
					break;

				case "Videojuego":
					var consola=resp[10];
					var telefono=resp[11];
					cadena+="<div class='col-xs-12 col-md-2 imagenProducto'>"+
						"<img src='"+img1+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 tituloProducto'>"+
						"<h5><strong class='orange'>"+titulo+"</strong></h5>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5 infoProdMargin'>"+
						"<p><strong class='orange'>Producto: </strong>Libro</p>"+
						"<p><strong class='orange'>Categoría: </strong> "+categoria+"</p>"+
						"<p><strong class='orange'>Propietario:</strong> "+nombreUsuario+"</p>"+
						"<p><strong class='orange'>Consola:</strong> "+consola+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5 infoProdMargin'>"+
						"<p><strong class='orange'>Provincia: </strong> "+provincia+"</p>"+
						"<p><strong class='orange'>Publicación: </strong> "+fecha+"</p>"+
						"<p><strong class='orange'>Contacto:</strong> "+telefono+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 descripcionProducto infoProdMargin'>"+
						"<p><strong class='orange'>Descripción:</strong> "+descripcion+"</p>"+
					"</div>"+
					"<div class='col-xs-12'></div>";
					if(img2!="" || img3!=""){
						cadena+="<div class='col-xs-12 col-md-2 push-md-2'>"+
							"<p><strong class='orange'>Otras imágenes:</strong></p>"+
						"</div>";
					}
					cadena+="<div class='col-xs-5 col-md-2 push-md-2'>"+
						"<img src='"+img2+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-5 col-md-2 push-md-2'>"+
						"<img src='"+img3+"' class='img-fluid'>"+
					"</div>";

					$(".infoProducto").html(cadena);
					break;
					break;

			}
		}
	})
}



/*Funcion que va a ser llamada desde buscar.jsp. Cada vez que se cambia un select o se escribe una letra en el campo nombre, va filtrando los resultados*/
function busquedaProductos(){
	/*Obtiene todos los valores del filtro*/
	var tipo=$("#campoTipo").val();
	var provincia=$("#campoProvincia").val();
	var orden=$("#campoOrden").val();
	var categoriaLibro=$("#campoCategoriaLibro").val();
	var categoriaPelicula=$("#campoCategoriaPelicula").val();
	var categoriaVideojuego=$("#campoCategoriaVideojuego").val();
	var consola=$("#campoConsola").val();
	var formato=$("#campoFormato").val();
	var nombre=$("#campoNombre").val();
	/*Hace la consulta ajax*/
	$.ajax({
		url: "/compartearte/filtradoBusqueda",
		type: "POST",
		dataType: "json",
		data: {tipo: tipo, provincia: provincia, orden: orden, categoriaLibro: categoriaLibro, categoriaPelicula: categoriaPelicula, categoriaVideojuego: categoriaVideojuego,
			   consola: consola, formato: formato, nombre: nombre},
		success: function(resp){

			var cadena="";

			for(i=0;i<resp.length;i++){
				var tipo=resp[i][0];
				var id=resp[i][1];
				var idPropietario=resp[i][2];
				var titulo=resp[i][3];
				var descripcion=resp[i][4];
				var provincia=resp[i][5];
				var categoria=resp[i][6];
				var img1=resp[i][7];
				var fecha=resp[i][8].split("-");

				fecha=fecha[2]+"-"+fecha[1]+"-"+fecha[0];

				cadena+="<div class='col-xs-12 listaProductos'>"+
					"<div class='col-xs-12 col-md-2 imagenProducto'>"+
						"<img src='"+img1+"' class='img-fluid'>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 tituloProducto'>"+
						"<h5><strong><a href='/compartearte/producto.jsp?tipo="+tipo+"&id="+id+"'>"+titulo+"</a></strong></h5>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5'>"+
						"<p><strong>Producto: </strong>"+tipo+"</p>"+
						"<p><strong>Categoría: </strong>"+categoria+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-5'>"+
						"<p><strong>Provincia: </strong>"+provincia+"</p>"+
						"<p><strong>Publicación: </strong>"+fecha+"</p>"+
					"</div>"+
					"<div class='col-xs-12 col-md-10 descripcionProducto'>"+
						"<p><strong>Descripción: </strong>"+descripcion+"</p>"+
					"</div>"+
				"</div>";
			}
			cadena+="<div class='col-xs-12 text-xs-center paginacionUltimos'></div>";
			$("#resultadoFiltro").html(cadena);
		}
	});
}




/*Funcion llamada desde la tabla de usuarios*/
function cargarUsuarios(){
	$.ajax({
		url: "/compartearte/cargarUsuarios",
		type: "POST",
		dataType: "json",
		success: function(resp){
			var cadena="";
			for(i=0;i<resp.length;i++){
				var id=resp[i][0];
				var correo=resp[i][1];
				var nombreUsuario = resp[i][2];
				var telefono = resp[i][3];
				var nivelPermisos = resp[i][4];
				var baneado = resp[i][5];
				var incidencias = resp[i][6];
				/*Preparamos el rol que tiene el usuario*/
				switch(nivelPermisos){
					case "3": nivelPermisos="Administrador";
						break;
					case "2": nivelPermisos="Moderador";
						break;
					case "1": nivelPermisos="Usuario";
						break;
				}
				/*Preparamos la variable para indicar si esta baneado o no*/
				if(baneado=="true"){
					baneado="<strong class='red'>Si</strong>";
				}else{
					baneado="No";
				}
				cadena+="<tr>"+
							"<td class='td-id'>"+id+"</td>"+
							"<td>"+correo+"</td>"+
							"<td>"+nombreUsuario+"</td>"+
							"<td>"+telefono+"</td>"+
							"<td>"+nivelPermisos+"</td>"+
							"<td>"+baneado+"</td>"+
							"<td>"+incidencias+"</td>"+
							"<td class='textoCentrado'><a href='/compartearte/modificarUsuario.jsp?id="+id+"' class='btn btn-link fa fa-pencil iconoModificar'>"+
								"</button></td>"+
							"<td class='textoCentrado'><a href='/compartearte/banearUsuario?id="+id+"' class='fa fa-ban iconoBanear'></a></td>"+
							"<td class='textoCentrado'><a class='fa fa-plus-circle iconoVerde' href='/compartearte/aniadirIncidencia?id="+id+"'></a></td>"+
						"</tr>";
			}
			$("tbody").html(cadena);
			$(".tablaMantenimiento").DataTable({
				    "language":{
					    "lengthMenu":"Mostrar _MENU_ registros por página.",
					    "zeroRecords": "Lo sentimos. No se encontraron registros.",
			            "info": "Mostrando página _PAGE_ de _PAGES_",
			            "infoEmpty": "No hay registros aún.",
			            "infoFiltered": "(filtrados de un total de _MAX_ registros)",
			            "search" : "Búsqueda",
			            "LoadingRecords": "Cargando ...",
			            "Processing": "Procesando...",
			            "SearchPlaceholder": "Comience a teclear...",
			            "paginate": {
						    "previous": "Anterior",
						    "next": "Siguiente", 
				        }
      				}
     		});
		}
	});
}





/*Funcion para cargar la lista de productos paginada*/
function cargarProductosAdministracion(pagina){
	$.ajax({
		url: "/compartearte/cargarProductosPaginacion",
		type: "POST",
		dataType: "json",
		data: {pagina: pagina},
		success: function(resp){
			var cadena="";
			for(i=0;i<resp.length;i++){
				var id=resp[i][0];
				var idPropietario=resp[i][1];
				var usuario=resp[i][2];
				var titulo=resp[i][3];
				var categoria=resp[i][4];
				var provincia=resp[i][5];
				var fecha=resp[i][6];
				cadena+="<tr>"+
							"<td class='td-id'>"+usuario+"</td>"+
							"<td>"+titulo+"</td>"+
							"<td>"+categoria+"</td>"+
							"<td>"+provincia+"</td>"+
							"<td>"+fecha+"</td>"+
							"<td class='textoCentrado'><button type='button' id='botonOjo' data-idPropietario='"+idPropietario+"' class='btn btn-link fa fa-eye ojoVermas' data-toggle='modal' data-target='#modal'>"+
								"</button></td>"+
						"</tr>";
			}
			$("tbody").html(cadena);
			paginacionAdministracionProductos(pagina);
		}
	});
}



/*Carga la paginacion de la pagina de administracion de productos*/
function paginacionAdministracionProductos(pagina){
	$.ajax({
		url: "/compartearte/paginacion",
		type: "POST",
		success: function(resp){
			var numeroPaginas=Math.ceil(resp/10);
			var cadena="";
			for(i=1;i<=numeroPaginas;i++){
				if(i==pagina){
					cadena+="<li class='page-item active'><a href='administracionArticulos.jsp?pagina="+i+"' class='page-link'>"+i+"</a></li>";
				}else{
					cadena+="<li class='page-item'><a href='administracionArticulos.jsp?pagina="+i+"' class='page-link'>"+i+"</a></li>";
				}
			}
			$("#paginacion").html(cadena);
		}
	})
}



/*Carga los datos del usuario para modificarlos*/
function cargarDatosUsuario(id){
	$.ajax({
		url: "/compartearte/modificarUsuario",
		type: "GET",
		dataType: "json",
		data: {idUsuario: id},
		success: function(resp){
			var correo = resp[0];
			var nombreUsuario = resp[1];
			var telefono = resp[2];
			var baneado = resp[3];
			var incidencias = resp[4];
			var nivelPermisos = resp[5];

			$("#correo").val(correo);
			$("#usuario").val(nombreUsuario);
			$("#telefono").val(telefono);
			$("#incidencias").val(incidencias);
			$("option[value="+nivelPermisos+"]").attr("selected", true);

			if(baneado==true){
				$("#estaBaneado").attr("checked", "checked");
			}else{
				$("#noEstaBaneado").attr("checked", "checked");
			}
		}
	});
}



/*Carga la lista de subforos*/
function cargarSubforos(){
	$("#background-blanco, #carga").show();
	$.ajax({
		url: "/compartearte/listadoSubforos",
		type: "POST",
		dataType: "json",
		success: function(resp){
			var subforoLibro = "<h5>LIBROS</h5>";
			var subforoPelicula = "<h5>PELICULAS</h5>";
			var subforoVideojuego = "<h5>VIDEOJUEGOS</h5>";
			var subforoOtros = "<h5>ERRORES Y SUGERENCIAS</h5>";

			/*Rellenamos cada seccion del foro*/
			for(i=0;i<resp.length;i++){
				var id=resp[i][0];
				var titulo=resp[i][1];
				var urlImg=resp[i][2];
				var temas=resp[i][5];
				var respuestas=resp[i][6];
				/*Rellenamos una cadena u otra en funcion del tipo de subforo*/
				switch(resp[i][4]){

					/*Subforo libro*/
					case "1": subforoLibro+="<div class='subforo'>"+
												"<img src='"+urlImg+"' alt='imagen de categoria "+titulo+"' class='img-fluid'>"+
												"<a href='/compartearte/subforo.jsp?id="+id+"&pag=1'>"+titulo+"</a>"+
												"<div class='ultimaRespuesta'>"+
													"<p><strong>Temas:</strong> "+temas+"</p>"+
													"<p><strong>Respuestas:</strong> "+respuestas+"</p>"+
												"</div>"+
											"</div>";
						break;

					/*Subforo pelicula*/
					case "2": subforoPelicula+="<div class='subforo'>"+
												"<img src='"+urlImg+"' alt='imagen de categoria "+titulo+"' class='img-fluid'>"+
												"<a href='/compartearte/subforo.jsp?id="+id+"&pag=1'>"+titulo+"</a>"+
												"<div class='ultimaRespuesta'>"+
													"<p><strong>Temas:</strong> "+temas+"</p>"+
													"<p><strong>Respuestas:</strong> "+respuestas+"</p>"+
												"</div>"+
											"</div>";
						break;

					/*Subforo videojuego*/
					case "3": subforoVideojuego+="<div class='subforo'>"+
												"<img src='"+urlImg+"' alt='imagen de categoria "+titulo+"' class='img-fluid'>"+
												"<a href='/compartearte/subforo.jsp?id="+id+"&pag=1'>"+titulo+"</a>"+
												"<div class='ultimaRespuesta'>"+
													"<p><strong>Temas:</strong> "+temas+"</p>"+
													"<p><strong>Respuestas:</strong> "+respuestas+"</p>"+
												"</div>"+
											"</div>";
						break;

					/*Subforo errores y sugerecias*/
					case "4": subforoOtros+="<div class='subforo'>"+
												"<img src='"+urlImg+"' alt='imagen de categoria "+titulo+"' class='img-fluid'>"+
												"<a href='/compartearte/subforo.jsp?id="+id+"&pag=1'>"+titulo+"</a>"+
												"<div class='ultimaRespuesta'>"+
													"<p><strong>Temas:</strong> "+temas+"</p>"+
													"<p><strong>Respuestas:</strong> "+respuestas+"</p>"+
												"</div>"+
											"</div>";
						break;

				}
			}
			$("#libros").html(subforoLibro);
			$("#peliculas").html(subforoPelicula);
			$("#videojuegos").html(subforoVideojuego);
			$("#reportes").html(subforoOtros);
		},
		complete: function(){
			setTimeout(function(){
				$("#background-blanco, #carga").fadeOut('slow');
			}, 500);
		}
	})
}



/*Carga los temas de un subforo*/
function cargarTemas(pagina, subforo){
	$("#background-blanco, #carga").show();
	$.ajax({
		url: "/compartearte/cargarTemas",
		type: "POST",
		dataType: "json",
		data: {pag: pagina, id: subforo},
		success: function(resp){
			var cadena = "<h5>Subforo de "+resp[0][4]+"</h5>";

			for(i=0;i<resp.length;i++){
				var id=resp[i][0];
				var titulo=resp[i][1];
				var fechaCompleta=resp[i][2];
				var autor=resp[i][3];

				/*Formato a fecha*/
				var fecha=fechaCompleta.split(" ");
				fecha=fecha[0].split("-");
				fecha=fecha[2]+"-"+fecha[1]+"-"+fecha[0];

				var hora=fechaCompleta.split(" ");
				hora=hora[1].split(".");
				hora=hora[0];

				cadena+="<div class='tema'>"+
							"<a href='/compartearte/tema.jsp?id="+id+"&pag=1'>"+titulo+"</a>"+
							"<div class='ultimaRespuesta'>"+
								"<p><strong>Creación:</strong> "+fecha+" a las "+hora+"</p>"+
								"<p><strong>Autor:</strong> "+autor+"</p>"+
							"</div>"+
						"</div>";
			}
			$("#temas").html(cadena);
			paginacionTemas(pagina, subforo);
		},
		complete: function(){
			setTimeout(function(){
				$("#background-blanco, #carga").fadeOut('slow');
			}, 200);
		}
	})
}



/*Carga la paginación de los distintos temas*/
function paginacionTemas(pagina, subforo){
	$.ajax({
		url: "/compartearte/paginacionTemas",
		type: "POST",
		data: {idSubforo: subforo},
		success: function(resp){
			var numeroPaginas=Math.ceil(resp/20);
			var cadena="";
			for(i=1;i<=numeroPaginas;i++){
				if(i==pagina){
					cadena+="<li class='page-item active'><a href='/compartearte/subforo.jsp?id="+subforo+"&pag="+i+"' class='page-link'>"+i+"</a></li>";
				}else{
					cadena+="<li class='page-item'><a href='/compartearte/subforo.jsp?id="+subforo+"&pag="+i+"' class='page-link'>"+i+"</a></li>";
				}
			}
			$("#paginacion").html(cadena);
		}
	})
}



/*Carga el tema cuyo id coincida con el pasado por parametro*/
function cargarTema(pagina, tema){
	$.ajax({
		url: "/compartearte/cargarTema",
		type: "POST",
		dataType: "json",
		data: {pag: pagina, idTema: tema},
		success: function(resp){
			var cadena="";
			var id=resp[0];
			var fechaCompleta=resp[1];
			var titulo=resp[2];
			var mensaje=resp[3];
			var cerrado=resp[4];
			var idAutor=resp[5];
			var nombreAutor=resp[6];
			var tipoUsuario=resp[7];

			/*Formato a fecha*/
			var fecha=fechaCompleta.split(" ");
			fecha=fecha[0].split("-");
			fecha=fecha[2]+"-"+fecha[1]+"-"+fecha[0];

			var hora=fechaCompleta.split(" ");
			hora=hora[1].split(".");
			hora=hora[0];

			switch(tipoUsuario){
				case "1":
					tipoUsuario="Usuario";
					break;
				case "2":
					tipoUsuario="Moderador";
					break;
				case "3":
					tipoUsuario="Administrador";
					break;
			}

			cadena+="<div class='col-xs-12 text-xs-center' id='tituloPost'>"+
						"<h3><strong>"+titulo+"</strong></h3>"+
					"</div>"+
					"<div class='col-xs-12 mensaje'>"+
						"<div class='col-md-2 datos'>"+
							"<div class='row text-xs-center nombreUsuario'><strong>"+nombreAutor+"</strong></div>"+
							"<div class='row fotoPerfil text-xs-center'>"+
								"<img src='img/perfil/general.png' class='img-fluid'>"+
							"</div>"+
							"<div class='row infoUsuario'>"+
								"<p><strong>Usuario:</strong> "+tipoUsuario+"</p>"+
								"<p><a href='/compartearte/mensajePrivado.jsp?id="+idAutor+"'><strong>Mensaje privado</strong></a></p>"+
							"</div>"+
						"</div>"+
						"<div class='col-md-10 infoTema'>"+
							"<div class='col-xs-12 fechaTema'>"+
								"<div class='col-xs-10'><span>Escrita el <i>"+fecha+"</i> a las <i>"+hora+"</i></span></div>"+
								"<div class='text-xs-right col-xs-2'><span><strong>#1</strong></span></div>"+
							"</div>"+
							"<div class='col-xs-12 cuerpoMensaje'>"+mensaje+"</div>"+
						"</div>"+
					"</div>";

			$("#informacionTema").html(cadena);
			cargarRespuestas(pagina, tema);
		}
	});
}



/*Carga las respuestas de un tema*/
function cargarRespuestas(pagina, tema){
	$.ajax({
		url: "/compartearte/cargarRespuestas",
		type: "POST",
		dataType: "json",
		data: {pag: pagina, idTema: tema},
		success: function(resp){
			var cadena=$("#informacionTema").html();
			for(i=0;i<resp.length;i++){
				var id=resp[i][0];
				var fechaCompleta=resp[i][1];
				var mensaje=resp[i][2];
				var idAutor=resp[i][3];
				var nombreAutor=resp[i][4];
				var tipoUsuario=resp[i][5];

				/*Formato a fecha*/
				var fecha=fechaCompleta.split(" ");
				fecha=fecha[0].split("-");
				fecha=fecha[2]+"-"+fecha[1]+"-"+fecha[0];

				var hora=fechaCompleta.split(" ");
				hora=hora[1].split(".");
				hora=hora[0];

				switch(tipoUsuario){
				case "1":
					tipoUsuario="Usuario";
					break;
				case "2":
					tipoUsuario="Moderador";
					break;
				case "3":
					tipoUsuario="Administrador";
					break;
				}

				cadena+="<div class='col-xs-12 mensaje'>"+
							"<div class='col-md-2 datos'>"+
								"<div class='row text-xs-center nombreUsuario'><strong>"+nombreAutor+"</strong></div>"+
								"<div class='row fotoPerfil text-xs-center'>"+
									"<img src='img/perfil/general.png' class='img-fluid'>"+
								"</div>"+
								"<div class='row infoUsuario'>"+
									"<p><strong>Usuario:</strong> "+tipoUsuario+"</p>"+
									"<p><a href='/compartearte/mensajePrivado.jsp?id="+idAutor+"'><strong>Mensaje privado</strong></a></p>"+
								"</div>"+
							"</div>"+
							"<div class='col-md-10 infoTema'>"+
								"<div class='col-xs-12 fechaTema'>"+
									"<div class='col-xs-10'><span>Escrita el <i>"+fecha+"</i> a las <i>"+hora+"</i></span></div>"+
									"<div class='text-xs-right col-xs-2'><span><strong>#"+(i+2)+"</strong></span></div>"+
								"</div>"+
								"<div class='col-xs-12 cuerpoMensaje'>"+mensaje+"</div>"+
							"</div>"+
						"</div>";
			}
			$("#informacionTema").html(cadena);
		}
	});
}


/*Manda la respuesta que el usuario ha escrito en un tema*/
function enviarRespuesta(idUsuario, idTema, respuesta){
	$.ajax({
		url: "/compartearte/guardarRespuesta",
		type: "POST",
		data: {
			idUsuario: idUsuario,
			idTema: idTema,
			respuesta: respuesta
		},
		success: function(resp){
			window.location=window.location;
		}
	})
}


/*Carga las notificaciones del usuario*/
function cargarNotificaciones(){
	$.ajax({
		url: "/compartearte/cargarNotificaciones",
		type: "POST",
		success: function(resp){
			if(resp>0){
				$('a[href="perfil.jsp"]').css({display: "inline-block"})
				$('a[href="perfil.jsp"]').after("<span id='numNotificaciones'>"+resp+"</span>");
			}
		}
	})
}



/*Carga los ultimos productos en el index*/
function cargarUltimosProductos(){
	$.ajax({
		url: "/compartearte/ultimosArticulos",
		type: "POST",
		dataType: "json",
		data: {numPag: 1},
		success: function(resp){
			var cadena="<h5>Últimas publicaciones</h5>";
			for(i=0;i<resp.length;i++){
				var tipo=resp[i][8];
				var id=resp[i][0];
				var nombre=resp[i][1];
				cadena+="<div><a href='/compartearte/producto.jsp?tipo="+tipo+"&id="+id+"'>"+nombre+"</a><span>"+tipo+"</span></div>";
			}
			$("#divUltimosProductos").html(cadena);
		}
	});
}



/*Carga los ultimos temas en el index*/
function cargarUltimosTemas(){
	$.ajax({
		url: "/compartearte/cargaUltimosTemas",
		type: "POST",
		dataType: "json",
		success: function(resp){
			var cadena="<h5>Últimos temas</h5>";
			for(i=0;i<resp.length;i++){
				var idTema=resp[i][0];
				var autor=resp[i][1];
				var titulo=resp[i][2];
				cadena+="<div><a href='/compartearte/tema.jsp?id="+idTema+"&pag=1'>"+titulo+"</a><span>"+autor+"</span></div>"
			}
			$("#divUltimosTemas").html(cadena);
		}
	});
}