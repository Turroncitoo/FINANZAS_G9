$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoRespuestaVisa : {
				required : true,
				number : true,
				rangelength : [ 1, 2 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 80 ]
			}
		},
		messages : {
			codigoRespuestaVisa : {
				required : "Ingrese un C&oacute;digo de Respuesta Visa.",
				number : "El C&oacute;digo de Respuesta Visa debe ser un n&uacute;mero.",
				rangelength : "El C&oacute;digo de Respuesta Visa debe contener entre 1 y 2 d&iacute;gitos."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 80 car&aacute;cteres."
			}
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement : 'span',
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});

});