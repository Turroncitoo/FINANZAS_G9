$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			tipoTransaccion : {
				required : true,
				rangelength : [ 1, 5 ],
				number : true
			},
			codigoCanalEmisor : {
				required : true,
				selectlength : [ 1, 2 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			}
		},
		messages : {
			tipoTransaccion : {
				required : "Ingrese el Tipo de Transacci&oacute;n.",
				rangelength : "El Tipo de Transacci&oacute;n debe contener entre 1 y 5 d&iacute;gitos.",
				number : "El Tipo de Transacci&oacute;n debe ser un n&uacute;mero."
			},
			codigoCanalEmisor : {
				required : "Ingrese un C&oacute;digo de Canal de Emisor.",
				selectlength : "El C&oacute;digo de Canal de Emisor debe contener como m&aacute;ximo 2 car&aacute;cteres."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			}
		}
	});

});