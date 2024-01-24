$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idLogo : {
				required : true,
				rangelength : [ 1, 11 ]
			},
			codigo : {
				required : true,
				rangelength : [ 1, 4 ],
				noWhitespace : true
			},
			descripcionAfinidad : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			}
		},
		messages : {
			idLogo : {
				required : "Ingrese un Logo.",
				rangelength : "El identificador de Afinidad debe tener entre {0} y {1} caracteres."
			},
			codigo : {
				required : "Ingrese un identificador de Afinidad.",
				rangelength : "El identificador de Afinidad debe tener entre {0} y {1} caracteres.",
				noWhitespace : "El c&oacute;digo no debe tener espacios"
			},
			descripcionAfinidad : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre {0} y {1} car&aacute;cteres."
			}
		}
	});

});