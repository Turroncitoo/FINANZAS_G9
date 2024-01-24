$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idMotivo : {
				required : true,
				number : true,
				range : [ 1, 999 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 60 ]
			}
		},
		messages : {
			idMotivo : {
				required : "Ingrese un identificador de motivo de reclamo.",
				number : "El identificador de motivo de reclamo debe ser un n&uacute;mero.",
				range : "El identificador de motivo de reclamo debe estar entre 1 y 999."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 60 car&aacute;cteres."
			}
		}
	});

});