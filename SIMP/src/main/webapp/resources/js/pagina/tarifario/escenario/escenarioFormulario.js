$(document).ready(function() {

	$formTarifario.validate({
		focusCleanup : true,
		rules : {
			codigoInstitucion : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : [ 1, 2 ]
			},
			codigoMembresia : {
				required : true,
				notOnlySpace : true,
				selectlength : 1
			},
			codigoClaseServicio : {
				required : true,
				notOnlySpace : true,
				selectlength : 1
			},
			codigoClaseTransaccion : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : [ 1, 2 ]
			},
			codigoTransaccion : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : [ 1, 2 ]
			},
			codigoOrigen : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : [ 1, 2 ]
			},
			codigoTipoTarifa : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : [ 1, 2 ]
			}
		},
		messages : {
			codigoInstitucion : {
				required : "Seleccione un esquema",
				notOnlySpace : "El C&oacute;digo de Intituci&oacute;n no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Intituci&oacute;n debe contener 2 car&aacute;cteres."
			},
			codigoMembresia : {
				required : "Seleccione una membres&iacute;a",
				notOnlySpace : "El C&oacute;digo de Membres&iacute;a no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo de membres&iacute;a debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Membres&iacute;a debe contener 1 car&aacute;cter."
			},
			codigoClaseServicio : {
				required : "Seleccione una clase servicio",
				notOnlySpace : "El C&oacute;digo de Clase Servicio no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo de Clase Servicio debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Clase Servicio debe contener 1 car&aacute;cter."
			},
			codigoClaseTransaccion : {
				required : "Seleccione una clase transacci\u00F3n",
				notOnlySpace : "El C&oacute;digo de Clase Transacci&oacute;n no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Clase Transacci&oacute;n debe contener 2 car&aacute;cteres."
			},
			codigoTransaccion : {
				required : "Seleccione una c&oacute;digo transacci\u00F3n",
				notOnlySpace : "El C&oacute;digo de C&oacute;digo Transacci&oacute;n no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo de C&oacute;digo Transacci&oacute;n debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de C&oacute;digo Transacci&oacute;n debe contener 2 car&aacute;cteres."
			},
			codigoOrigen : {
				required : "Seleccione un origen",
				notOnlySpace : "El C&oacute;digo de Origen no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo de Origen debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Origen debe contener 2 car&aacute;cteres."
			},
			codigoTipoTarifa : {
				required : "Seleccione un tipo de tarifa",
				notOnlySpace : "El C&oacute;digo de Tipo Tarifa no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo de Tipo Tarifa debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Tipo Tarifa debe contener 2 car&aacute;cteres."
			}
		}
	});
});