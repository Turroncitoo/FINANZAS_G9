$(document).ready(function() {

	$formTarifario.validate({
		focusCleanup : true,
		rules : {
			tipoTarifa : {
				required : true,
				solodigitos : true,
				range : [ 1, 99 ]
			},
			aplicaBin : {
				number : true,
				range : [ 0, 1 ]
			},
			diferenteTran : {
				number : true,
				range : [ 0, 1 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			},
			codigoEsquema : {
				required : true,
				notOnlySpace : true,
				solodigitos : true,
				selectlength : [ 1, 2 ]
			}
		},
		messages : {
			tipoTarifa : {
				required : "Ingrese un C&oacute;digo del tipo de tarifa.",
				solodigitos : "El C&oacute;digo del tipo de tarifa debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo del tipo de tarifa debe estar entre 1 y 99."
			},
			aplicaBin : {
				number : "El valor de Aplica BIN debe ser un n&uacute;mero.",
				range : "El valor de Aplica BIN debe ser 0 o 1."
			},
			diferenteTran : {
				number : "El valor de Diferencia Transacci&oacute;n debe ser un n&uacute;mero.",
				range : "El valor de Diferencia Transacci&oacute;n debe ser 0 o 1."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			},
			codigoEsquema : {
				required : "Seleccione un esquema",
				notOnlySpace : "El C&oacute;digo de Esquema no puede contener solo espacios en blanco.",
				solodigitos : "El C&oacute;digo de esquema debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Esquema debe contener 2 d&iacute;gitos."
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
			} else if (element.parent().find("span.select2").length > 0) {
				error.appendTo(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});

});