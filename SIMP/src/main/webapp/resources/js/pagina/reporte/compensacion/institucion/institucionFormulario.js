$(document).ready(function() {

	$formCriterioBusquedaReporte.validate({
		rules : {
			rolTransaccion : {
				required : true,
				rangelength : [ 1, 1 ],
				number : true
			},
			codigoRespuestaTransaccion : {
				required : true,
				number : true,
				equals : [ "0", "9998", "9999" ]
			},
			fechas : {
				required : true,
				notOnlySpace : true
			},
			tipoMoneda : {
				required : true,
				number : true,
				equals : [ "604", "840" ]
			},
			idInstitucion: {
				required : true
			}
		},
		messages : {
			rolTransaccion : {
				required : "Seleccione un Rol Transacci&oacute;n.",
				rangelength : "El Rol Transacci&oacute;n debe contener un d&iacute;gito.",
				number : "El Rol Transacci&oacute;n debe ser un n&uacute;mero."
			},
			codigoRespuestaTransaccion : {
				required : "Seleccione una Respuesta Transacci&oacute;n.",
				number : "La Respuesta Transacci&oacute;n debe ser un n&uacute;mero.",
				equals : "La Respuesta Transacci&oacute;n seleccionada es inv&aacute;lida."
			},
			fechas : {
				required : "Seleccione el Rango de Fechas de la Transacci&oacute;n.",
				notOnlySpace : "El Rango de Fechas no puede contener solo espacios en blanco."
			},
			tipoMoneda : {
				required : "Seleccione un Tipo de Moneda.",
				number : "El Tipo de Moneda seleccionado es inv&aacute;lido.",
				equals : "El Tipo de Moneda seleccionado es inv&aacute;lido."
			},
			idInstitucion : {
				required : "Seleccione una Instituci\u00F3n."
			}
		}
	});

});