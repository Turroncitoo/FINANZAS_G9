$(document).ready(function() {

	$formCriterioBusquedaAutorizacion.validate({
		focusCleanup : true,
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
			tipoPresentacion : {
				required : true,
				number : true,
				equals : [ "1", "2" ]
			}
		},
		messages : {
			rolTransaccion : {
				required : "Seleccione un Rol de Transacci&oacute;n.",
				rangelength : "El Rol de Transacci&oacute;n debe contener un d&iacute;gito.",
				number : "El Rol de Transacci&oacute;n debe ser un n&uacute;mero."
			},
			codigoRespuestaTransaccion : {
				required : "Seleccione una Respuesta de Transacci&oacute;n.",
				number : "La Respuesta de Transacci&oacute;n debe ser un n&uacute;mero.",
				equals : "La Respuesta de Transacci&oacute;n seleccionada es inv&aacute;lida."
			},
			fechas : {
				required : "Seleccione el Rango de Fechas de la Transacci&oacute;n.",
				notOnlySpace : "El Rango de Fechas no puede contener solo espacios en blanco."
			},
			tipoPresentacion : {
				required : "Seleccione un Tipo de Presentaci&oacute;n.",
				number : "El Tipo de Presentaci&oacute;n seleccionado es inv&aacute;lido.",
				equals : "El Tipo de Presentaci&oacute;n seleccionado es inv&aacute;lido."
			}
		}
	});

});