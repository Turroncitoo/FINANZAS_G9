$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoClaseTransaccion : {
				required : true,
				number : true,
				range : [ 1, 9999 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 80 ]
			}
		},
		messages : {
			codigoClaseTransaccion : {
				required : "Ingrese un C&oacute;digo de Clase de Transacci&oacute;n.",
				number : "El C&oacute;digo de Clase de Transacci&oacute;n debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de Clase de Transacci&oacute;n debe estar entre {0} y {1}."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre {0} y {1} car&aacute;cteres.",
			}
		}
	});

});