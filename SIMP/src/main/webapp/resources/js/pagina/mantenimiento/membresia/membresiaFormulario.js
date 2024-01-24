$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoMembresia : {
				required : true,
				notOnlySpace : true,
				lettersonly: true,
				rangelength : [ 1, 1 ],
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 20 ]
			}
		},
		messages : {
			codigoMembresia : {
				required : "Ingrese un C&oacute;digo de Membres&iacute;a.",
				notOnlySpace : "El C&oacute;digo de Membres&iacute;a no debe contener solo espacios en blanco.",
				lettersonly: "El C&oacute;digo de Membres&iacute;a debe contener solo letras.",
				rangelength : "El C&oacute;digo de Membres&iacute;a debe contener 1 car&aacute;cter."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci\u00F3n debe contener entre 3 y 20 car&aacute;cteres."
			}
		}
	});
});