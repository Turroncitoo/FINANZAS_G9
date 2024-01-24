$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idEmpresa : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			idCliente : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			codigoMoneda : {
				required : true,
				number : true,
				range : [ 1, 9999 ]
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
			},
			codigoBIN : {
				required : true,
				number : true,
				selectlength : [ 6, 11 ]
			},
			
			codigoProducto : {
				required : true
			},
			codigoOrigen : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			codigoClaseTransaccion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			codigoTransaccion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			idRolTransaccion : {
				required : true,
				number : true,
				range : [ 1, 10 ]
			}
		},
		messages : {
			idEmpresa : {
				required : "Seleccione el C&oacute;digo de Empresa.",
				notOnlySpace : "El C&oacute;digo de Empresa no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Empresa debe contener 4 car&aacute;cteres."
			},
			idCliente : {
				required : "Seleccione el C&oacute;digo de Cliente.",
				notOnlySpace : "El C&oacute;digo de Cliente no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Cliente debe contener 4 car&aacute;cteres."
			},
			codigoMoneda : {
				required : "Seleccione el C&oacute;digo de Moneda.",
				number : "El C&oacute;digo de Moneda debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de Moneda debe estar entre 1 y 9999."
			},
			codigoMembresia : {
				required : "Seleccione el C&oacute;digo de Membres&iacute;a.",
				notOnlySpace : "El C&oacute;digo de Membres&iacute;a no puede contener solo espacios en blanco.",
				lettersonly : "El C&oacute;digo de Membres&iacute;a debe contener solo letras.",
				selectlength : "El C&oacute;digo de Membres&iacute;a debe contener 1 car&aacute;cter."
			},
			codigoClaseServicio : {
				required : "Seleccione el C&oacute;digo de Clase de Servicio.",
				notOnlySpace : "El C&oacute;digo de Clase de Servicio no puede contener solo espacios en blanco.",
				lettersonly : "El C&oacute;digo de Clase de Servicio debe contener solo letras.",
				selectlength : "El C&oacute;digo de Clase de Servicio debe contener 1 car&aacute;cter."
			},
			codigoBIN : {
				required : "Seleccione el C&oacute;digo de BIN.",
				number : "El C&oacute;digo de BIN debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de BIN debe contener entre 6 y 11 d&iacute;gitos."
			},
			codigoProducto : {
				required : "Seleccione el C&oacute;digo de Producto."
			},
			codigoOrigen : {
				required : "Seleccione el C&oacute;digo de Origen.",
				number : "El C&oacute;digo de Origen debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de Origen debe estar entre 1 y 99."
			},
			codigoClaseTransaccion : {
				required : "Seleccione el C&oacute;digo de Clase de Transacci&oacute;n.",
				number : "El C&oacute;digo de Clase de Transacci&oacute;n debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de Clase de Transacci&oacute;n debe estar entre 1 y 99."
			},
			codigoTransaccion : {
				required : "Seleccione el C&oacute;digo de Transacci&oacute;n.",
				number : "El C&oacute;digo de Transacci&oacute;n debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de Transacci&oacute;n debe estar entre 1 y 99."
			},
			idRolTransaccion : {
				required : "Seleccione el Rol de Transacci&oacute;n.",
				number : "El Rol de Transacci&oacute;n debe ser un n&uacute;mero.",
				range : "El Rol de Transacci&oacute;n debe estar entre 1 y 10."
			}
		}
	});

	$formComisiones.validate({
		ignore : "",
		rules : {
			idConceptoComision : {
				required : function() {
					return $cuentaContables.compensaComisiones == "1";
				},
				number : true,
				selectlength : 1
			},
			cuentaCargo : {
				required : function() {
					return $cuentaContables.compensaComisiones == "1";
				},
				maxlength : 20
			},
			cuentaAbono : {
				required : function() {
					return $cuentaContables.compensaComisiones == "1";
				},
				maxlength : 20
			},
			codigoAnalitico : {
				maxlength : 10
			}
		},
		messages : {
			idConceptoComision : {
				required : "Selecciona un Concepto de Comisi\u00F3n.",
				number : "El Concepto de Comisi\u00F3n debe ser un n\u00FAmero.",
				selectlength : "El Concepto de Comisi\u00F3n debe contener un d\u00EDgito."
			},
			cuentaCargo : {
				required : "Ingrese una Cuenta Cargo.",
				maxlength : "La Cuenta Cargo no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			cuentaAbono : {
				required : "Ingrese una Cuenta Abono.",
				maxlength : "La Cuenta Abono no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			codigoAnalitico : {
				maxlength : "La C&oacute;digo de Anal&iacute;tico no puede contener m&aacute;s de 10 car&aacute;cteres."
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

	$formFondo.validate({
		ignore : "",
		rules : {
			cuentaCargo : {
				required : function() {
					return $cuentaContables.compensaFondos == "1";
				},
				maxlength : 20
			},
			cuentaAbono : {
				required : function() {
					return $cuentaContables.compensaFondos == "1";
				},
				maxlength : 20
			},
			codigoAnalitico : {
				maxlength : 10
			}
		},
		messages : {
			cuentaCargo : {
				required : "Ingrese una Cuenta Cargo.",
				maxlength : "La Cuenta Cargo no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			cuentaAbono : {
				required : "Ingrese una Cuenta Abono.",
				maxlength : "La Cuenta Abono no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			codigoAnalitico : {
				maxlength : "La C&oacute;digo de Anal&iacute;tico no puede contener m&aacute;s de 10 car&aacute;cteres."
			}
		}
	});

});