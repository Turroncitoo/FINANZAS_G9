$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoInstitucion : {
				required : true,
				digits : true,
				range : [ 1, 99999 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 80 ]
			},
			descripcionCorta : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 30 ]
			},
			codigoVisanet: {
				rangelength: [0, 5]
			}
		},
		messages : {
			codigoInstitucion : {
				required : "Ingrese un C&oacute;digo de instituci&oacute;n.",
				digits : "El C&oacute;digo de instituci&oacute;n debe contener d&iacute;gitos.",
				range : "El C&oacute;digo de instituc&oacute;n debe estar entre 1 y 99999."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 80 car&aacute;cteres."
			},
			descripcionCorta : {
				required : "Ingrese una descripci&oacute;n corta.",
				notOnlySpace : "La descripci&oacute;n corta no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n corta debe contener entre 3 y 30 car&aacute;cteres."
			},
			codigoVisanet: {
				rangelength: 'El C\u00F3digo VISANET de la instituci\u00F3n debe contener entre {0} y {1} caracteres.'
			}
		}
	});

});