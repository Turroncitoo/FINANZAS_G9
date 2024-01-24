$(document).ready(function(){
	
	$formMantenimiento.validate({
		rules : {
			idATM : {
				required : true,
				number : true,
				range : [ 1, 999999 ]
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
			}
		},
		messages : {
			idATM : {
				required : "Seleccione un ATM.",
				number : "El Id de ATM debe ser un n&uacute;mero.",
				range : "El Id de ATM debe estar entre 1 y 999999."
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
				soloalfanumericos : true,
				maxlength : 20
			},
			cuentaAbono : {
				required : function() {
					return $cuentaContables.compensaComisiones == "1";
				},
				soloalfanumericos : true,
				maxlength : 20
			},
			codigoAnalitico : {
				soloalfanumericos : true,
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
				soloalfanumericos : "La Cuenta de Cargo debe contener car&aacute;cteres alfanumericos.",
				maxlength : "La Cuenta Cargo no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			cuentaAbono : {
				required : "Ingrese una Cuenta Abono.",
				soloalfanumericos : "La Cuenta de Abono debe contener car&aacute;cteres alfanumericos.",
				maxlength : "La Cuenta Abono no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			codigoAnalitico : {
				soloalfanumericos : "El C&oacute;digo de Anal&iacute;tico debe contener car&aacute;cteres alfanumericos.",
				maxlength : "La C&oacute;digo de Anal&iacute;tico no puede contener m&aacute;s de 10 car&aacute;cteres."
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
				soloalfanumericos : true,
				maxlength : 20
			},
			cuentaAbono : {
				required : function() {
					return $cuentaContables.compensaFondos == "1";
				},
				soloalfanumericos : true,
				maxlength : 20
			},
			cuentaAtm : {
				required : function() {
					return $cuentaContables.compensaFondos == "1";
				},
				soloalfanumericos : true,
				maxlength : 20
			},
			codigoAnalitico : {
				soloalfanumericos : true,
				maxlength : 10
			}
		},
		messages : {
			cuentaCargo : {
				required : "Ingrese una Cuenta Cargo.",
				soloalfanumericos : "La Cuenta de Cargo debe contener car&aacute;cteres alfanumericos.",
				maxlength : "La Cuenta Cargo no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			cuentaAbono : {
				required : "Ingrese una Cuenta Abono.",
				soloalfanumericos : "La Cuenta de Abono debe contener car&aacute;cteres alfanumericos.",
				maxlength : "La Cuenta Abono no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			cuentaAtm : {
				required : "Ingrese una Cuenta ATM",
				soloalfanumericos : "La Cuenta ATM debe contener car&aacute;cteres alfanumericos.",
				maxlength : "La Cuenta ATM no debe contener m&aacute;s de 20 car&aacute;cteres."
			},
			codigoAnalitico : {
				soloalfanumericos : "El C&oacute;digo de Anal&iacute;tico debe contener car&aacute;cteres alfanumericos.",
				maxlength : "La C&oacute;digo de Anal&iacute;tico no puede contener m&aacute;s de 10 car&aacute;cteres."
			}
		}
	});
	
});