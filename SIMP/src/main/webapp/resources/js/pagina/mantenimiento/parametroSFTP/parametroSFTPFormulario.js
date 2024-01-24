$(document).ready(function() {
	
	$formMantenimiento.validate({
		rules : {
			usuario : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 30 ]
			},
			host : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 30 ]
			},
			contrasenia : {
				rangelength : [ 0, 30 ]
			},
			puerto : {
				required : true,
				number : true,
				range : [ 1, 65535 ]
			} 
		},
		messages : {
			usuario : {
				required : "Ingrese un usuario.",
				notOnlySpace : "El usuario no puede contener solo espacios en blanco.",
				rangelength : "El usuario debe tener entre {0} y {1} car&aacute;cteres."
			},
			host : {
				required : "Ingrese un host.",
				notOnlySpace : "El host no puede contener solo espacios en blanco.",
				rangelength : "El host debe tener entre {0} y {1} car&aacute;cteres."
			},
			contrasenia : {
				required : "Ingrese una contrase\u00F1a.",
				notOnlySpace : "La contrase\u00F1a no puede contener solo espacios en blanco.",
				rangelength : "La contrase\u00F1a debe contener entre {0} y {1} car&aacute;cteres."
			},
			puerto : {
				required : "Ingrese un puerto.",
				number : "El puerto debe ser un n&uacute;mero.",
				range : "El puerto debe estar entre {0} y {1}."
			} 
		}
	});

});