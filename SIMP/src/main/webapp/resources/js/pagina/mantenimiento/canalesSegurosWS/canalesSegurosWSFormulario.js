$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			idCanal : {
				required : true,
				rangelength : [ 4, 30 ]
			},
			contraseniaIngresada : {
				required : true,
				rangelength : [ 4, 60 ]
			},
			password2 : {
				required : true,
				rangelength : [ 4, 60 ]
			},
			idEmpresa : {
				required : true
			},
			idCliente : {
				required : true
			},
		},
		messages : {
			idCanal : {
				required : "Ingrese un canal seguro.",
				rangelength : "El canal seguro debe contener entre 4 y 30 caracteres."
			},
			contraseniaIngresada : {
				required : "Ingrese una contrase&ntilde;a.",
				rangelength : "La contrase&ntilde;a debe contener entre 4 y 60 car&aacute;cteres."
			},
			password2 : {
				required : "Ingrese la confirmaci&oacute;n contrase&ntilde;a.",
				rangelength : "Debe contener entre 4 y 60 car&aacute;cteres."
			},
			idEmpresa : {
				required : "Ingrese una empresa."
			},
			idCliente : {
				required : "Ingrese un cliente."
			}
		}
	});
});