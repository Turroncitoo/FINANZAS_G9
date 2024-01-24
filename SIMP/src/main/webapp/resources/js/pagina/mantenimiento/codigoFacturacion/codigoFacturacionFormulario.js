$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idCodigoFacturacion : {
				required : true,
				notOnlySpace : true,
				number: true
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			}
		},
		messages : {
			idCodigoFacturacion : {
				required : "Ingrese un C&oacute;odigo de facturaci\u00F3n.",
				notOnlySpace : "El C&oacute;odigo no puede contener solo espacios en blanco.",
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			}
		}
	});
	
});