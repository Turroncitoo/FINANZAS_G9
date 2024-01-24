$(document).ready(function() {

	$formMantenimiento.validate({
		rules: {
			codigoTransaccion: {
				required: true,
				number: true,
				range: [1, 9999]
			},
			codigoClaseTransaccion: {
				required: true,
				number: true,
				range: [1, 9999]
			},
			descripcion: {
				required: true,
				notOnlySpace: true,
				rangelength: [3, 100]
			}
		},
		messages: {
			codigoTransaccion: {
				required: "Ingrese un C&oacute;digo de Transacci&oacute;n.",
				number: "El C&oacute;digo de Transacci&oacute;n debe ser un n&uacute;mero.",
				range: "El C&oacute;digo de Transacci&oacute;n debe estar entre {0} y {1}."
			},
			codigoClaseTransaccion: {
				required: "Seleccione una Clase de Transacci&oacute;n.",
				number: "La Clase de Transacci&oacute;n debe ser un n&uacute;mero.",
				range: "La Clase de Transacci&oacute;n debe estar entre {0} y {1}."
			},
			descripcion: {
				required: "Ingrese una descripci&oacute;n.",
				notOnlySpace: "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength: "La descripci&oacute;n deben contener {0} y {1} car&aacute;cteres."
			}
		},
		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement: 'span',
		errorClass: 'help-block',
		errorPlacement: function(error, element) {
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