$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoBIN : {
				required : true,
				number : true,
				rangelength : [ 6, 11 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			},
			codigoInstitucion: {
				required : true,
				notOnlySpace : true
			},
			codigoMembresia : {
				required : true,
				notOnlySpace : true,
				lettersonly : true,
				selectlength : 1
			},
			codigoClaseServicio : {
				required : true,
				notOnlySpace : true,
				lettersonly : true,
				selectlength : 1
			}
		},
		messages : {
			codigoBIN : {
				required : "Ingrese un C&oacute;digo de BIN.",
				number : "El C&oacute;digo de BIN debe ser un n&uacute;mero.",
				rangelength : "El C&oacute;digo de BIN debe contener entre 6 y 11 d&iacute;gitos."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			},
			codigoInstitucion : {
				required : "Seleccione una instituci&oacute;n.",
				notOnlySpace : "El C&oacute;digo de Instituci&oacute;n no puede contener solo espacios en blanco."
			},
			codigoMembresia : {
				required : "Seleccione una membres&iacute;a",
				notOnlySpace : "El C&oacute;digo de Membres&iacute;a no puede contener solo espacios en blanco.",
				lettersonly : "El C&oacute;digo de Membres&iacute;a debe contener solo letras.",
				selectlength : "El C&oacute;digo de Membres&iacute;a debe contener 1 car&aacute;cter."
			},
			codigoClaseServicio : {
				required : "Seleccione una Clase de Servicio.",
				notOnlySpace : "El C&oacute;digo de Clase de servicio no puede contener solo espacios en blanco.",
				lettersonly : "El C&oacute;digo de Clase de Servicio debe contener solo letras.",
				selectlength : "El C&oacute;digo de Clase de servicio debe contener 1 car&aacute;cter."
			}
		}
	});
});