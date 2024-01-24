$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoSubBIN : {
				required : true,
				number : true,
				rangelength : [ 2, 2 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			},
			codigoBIN : {
				required : true,
				number : true,
				selectlength : [ 6, 11 ]
			}, 
			idRegimen: {
				required : true,
			}
		},
		messages : {
			codigoSubBIN : {
				required : "Ingrese un C&oacute;digo de SubBIN.",
				number : "El C&oacute;digo de SubBIN debe ser un n&uacute;mero.",
				rangelength : "El C&oacute;digo de SubBIN debe contener 2 d&iacute;gitos.",
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			},
			codigoBIN : {
				required : "Seleccione un C&oacute;digo de BIN",
				number : "El C&oacute;digo de BIN debe ser un n&uacute;mero.",
				selectlength : "El c&oacute;digo de BIN debe contener entre 6 y 11 d&iacute;gitos.",
			}, 
			idRegimen: {
				required : "Seleccione un R\u00E9gimen",
			}
		}
	});
});