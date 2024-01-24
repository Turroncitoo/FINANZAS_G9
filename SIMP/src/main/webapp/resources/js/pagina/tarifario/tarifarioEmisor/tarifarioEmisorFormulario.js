$(document).ready(function() {

	$formTarifario.validate({
		focusCleanup : true,
		rules : {
			codigoInstitucion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			nivel : {
				required : true,
				solodigitos : true,
				range : [ 1, 99 ]
			},
			codigoTipoTarifa : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : [ 1, 2 ]
			},
			codigoGrupoBin : {
				 required : true,
				 notOnlySpace : true,
				 number : true,
				 selectlength : [ 1, 2 ]
			},
			transaccion : {
				required: true,
				notOnlySpace : true,
				number : true,
				selectlength : [ 3, 4 ]
			},
			rangoInicial : {
				required : true,
				solodigitos : true,
				rangelength : [ 1, 8 ]
			},
			rangoFinal : {
				required : true,
				solodigitos : true,
				rangelength : [ 1, 8 ]
			},
			codigoMoneda : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : 3
			},
			tarifaFlat : {
				required : true,
				number : true,
				range: [0, 9999]
			},
			tarifaPorc : {
				required : true,
				number : true,
				range: [0, 9999]
			}
		},
		messages : {
			codigoInstitucion : {
				required : "Ingrese un C&oacute;digo de instituci&oacute;n.",
				number : "El C&oacute;digo de instituci&oacute;n debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de instituc&oacute;n debe estar entre 1 y 99."
			},
			nivel : {
				required : "Ingrese el Nivel de la tarifa.",
				solodigitos : "El Nivel debe contener d&iacute;gitos.",
				range : "El Nivel debe estar entre 1 y 99."
			},
			codigoTipoTarifa : {
				required : "Seleccione un Tipo de tarifa",
				notOnlySpace : "El C&oacute;digo de Origen no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo del Tipo de arifa debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Tipo de tarifa debe contener 2 car&aacute;cteres."
			},
			codigoGrupoBin : {
				 required : "Seleccione un Grupo BIN",
				 notOnlySpace : "El C&oacute;digo del Grupo BIN no puede contener solo espacios en blanco.",
				 number : "El C&oacute;digo del Grupo BIN debe ser un n&uacute;mero.",
				 selectlength : "El C&oacute;digo de Grupo BIN debe contener 2 car&aacute;cteres."
			},
			transaccion : {
				required : "Seleccione una Transacci&oacute;n",
				notOnlySpace : "El C&oacute;digo de Transacci&oacute;n no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo del Transacci&oacute;n debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Transacci&oacute;n debe contener 3 o 4 car&aacute;cteres."
			},
			rangoInicial : {
				required : "Ingrese el Rango inicial.",
				solodigitos : "El Rango inicial debe contener d&iacute;gitos.",
				rangelength : "El Rango inicial debe contener entre 1 y 8 d&iacute;gitos."
			},
			rangoFinal : {
				required : "Ingrese el Rango inicial.",
				solodigitos : "El Rango final debe contener d&iacute;gitos.",
				rangelength : "El Rango final debe contener entre 1 y 8 d&iacute;gitos."
			},
			codigoMoneda : {
				required : "Seleccione una Moneda",
				notOnlySpace : "El C&oacute;digo de Moneda no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo del Moneda debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Moneda debe contener 3 car&aacute;cteres."
			},
			tarifaFlat : {
				required : "Ingrese la Tarifa flat.",
				number : "La Tarifa flat debe ser un n&uacute;mero.",
				range : "La tarifa flat. debe estar entre 0 y 9999."
			},
			tarifaPorc : {
				required : "Ingrese la Tarifa porcentaje.",
				number : "La Tarifa porcentaje debe ser un n&uacute;mero.",
				range : "La tarifa flat. debe estar entre 0 y 9999."
			}
		}
	});

});