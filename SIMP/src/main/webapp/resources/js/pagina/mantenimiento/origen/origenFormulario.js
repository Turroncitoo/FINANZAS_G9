$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoOrigen : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 40 ]
			}
		},
		messages : {
			codigoOrigen : {
				required : "Ingrese un C&oacute;digo de Origen.",
				number : "El C&oacute;digo de Origen debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de Origen debe estar entre 1 y 99."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute; debe contener entre 3 y 40 car&aacute;cteres.",
			}
		}
	});

});