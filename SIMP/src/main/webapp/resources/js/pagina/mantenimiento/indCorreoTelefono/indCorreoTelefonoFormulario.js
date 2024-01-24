$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoIndCorreoTelefono : {
				numberorempty : true,
				rangelength : [ 1, 1 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 80 ]
			}
		},
		messages : {
			codigoIndCorreoTelefono : {
				numberorempty : "El C&oacute;digo de Indicador debe contener solo n&uacute;meros o estar vac&iacute;o.",
				rangelength : "El C&oacute;digo de Indicador debe contener un d&iacute;gito."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 80 car&aacute;cteres."
			}
		}
	});
});