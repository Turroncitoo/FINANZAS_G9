$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoClaseServicio : {
				required : true,
				notOnlySpace : true,
				lettersonly: true,
				rangelength : [ 1, 1 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 30 ]
			},
			codigoMembresia : {
				required : true,
				notOnlySpace : true,
				lettersonly: true,
				selectlength : 1
			}
		},
		messages : {
			codigoClaseServicio : {
				required : "Ingrese un C&oacute;digo de Clase de Servicio.",
				notOnlySpace : "El C&oacute;digo de Clase de Servicio no puede contener solo espacios en blanco.",
				lettersonly: "El C&oacute;digo de Clase de Servcio debe contener solo letras.",
				rangelength : "El C&oacute;digo de Clase de Servcio debe contener 1 car&aacute;cter."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 30 car&aacute;cteres."
			},
			codigoMembresia : {
				required : "Seleccione una Membres&iacute;a",
				notOnlySpace : "El C&oacute;digo de Membres&iacute;a no puede contener solo espacios en blanco.",
				lettersonly: "El C&oacute;digo de Membres&iacute;a debe contener solo letras.",
				selectlength : "El C&oacute;digo de Membres&iacute;a debe contener 1 car&aacute;cter."
			}
		}
	});

});