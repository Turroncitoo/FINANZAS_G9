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
				digits : true,
				range : [ 1, 99 ]
			},
			rangoInicial : {
				required : true,
				digits : true,
				rangelength : [ 1, 8 ]
			},
			rangoFinal : {
				required : true,
				digits : true,
				rangelength : [ 1, 8 ]
			},
			codigoMoneda : {
				required : true,
				notOnlySpace : true,
				number : true,
				selectlength : 3
			},
			surchargeFlat : {
				required : true,
				number : true,
				range: [0, 9999]
			},
			surchargePorc : {
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
				digits : "El Nivel debe contener d&iacute;gitos.",
				range : "El Nivel debe estar entre 1 y 99."
			},
			rangoInicial : {
				required : "Ingrese el Rango inicial.",
				digits : "El Rango inicial debe contener d&iacute;gitos.",
				rangelength : "El Rango inicial debe contener entre 1 y 8 d&iacute;gitos."
			},
			rangoFinal : {
				required : "Ingrese el Rango inicial.",
				digits : "El Rango final debe contener d&iacute;gitos.",
				rangelength : "El Rango final debe contener entre 1 y 8 d&iacute;gitos."
			},
			codigoMoneda : {
				required : "Seleccione una Moneda",
				notOnlySpace : "El C&oacute;digo de Moneda no puede contener solo espacios en blanco.",
				number : "El C&oacute;digo del Moneda debe ser un n&uacute;mero.",
				selectlength : "El C&oacute;digo de Moneda debe contener 3 car&aacute;cteres."
			},
			surchargeFlat : {
				required : "Ingrese la Surcharge flat.",
				number : "La Tarifa flat debe ser un n&uacute;mero.",
				range : "La tarifa flat. debe estar entre 0 y 9999."
			},
			surchargePorc : {
				required : "Ingrese la Surcharge porcentaje.",
				number : "La Tarifa porcentaje debe ser un n&uacute;mero.",
				range : "La tarifa flat. debe estar entre 0 y 9999."
			}
		}
	});

});