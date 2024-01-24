$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoCanalEmisor : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 2 ]
			},
			tipoTransaccion : {
				required : true,
				selectlength : [ 1, 5 ],
				number : true
			},
			codTransaccion : {
				required : true,
				number : true,
				rangelength : [ 6, 6 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 60 ]
			}
		},
		messages : {
			codigoCanalEmisor : {
				required : "Ingrese un C&oacute;digo de Canal de Emisor.",
				notOnlySpace : "El C&oacute;digo de Canal de Emisor no debe contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Canal de Emisor debe contener m&aacute;s de 2 car&aacute;teres.",
			},
			tipoTransaccion : {
				required : "Ingrese el Tipo de Transacci&oacute;n.",
				selectlength : "El Tipo de Transacci&oacute;n debe contener entre 1 y 5 d&iacute;gitos.",
				number : "El Tipo de Transacci&oacute;n debe contener solo n&uacute;meros."
			},
			codTransaccion : {
				required : "Ingrese el C&oacute;digo de Transacci&oacute;n.",
				number : "El C&oacute;digo de Transacci&oacute;n debe contener solo n&uacute;meros.",
				rangelength : "El C&oacute;digo de Transacci&oacute;n debe contener 6 d&iacute;gitos."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener 3 y 60 car&aacute;cteres."
			}
		}
	});

});