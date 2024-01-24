$(document).ready(function() {

	$formMantenimiento.validate({
		rules: {
			pathHostWS: {
				notOnlySpaceOrEmpty: true,
				required: true,
				rangelength: [1, 100]
			},
			llaveInstalacion: {
				notOnlySpaceOrEmpty: true,
				required: true,
				rangelength: [1, 32]
			},
			llaveTransporteZMK: {
				notOnlySpaceOrEmpty: true,
				required: true,
				rangelength: [1, 64]
			},
			llaveTrabajoZPK: {
				notOnlySpaceOrEmpty: true,
				required: true,
				rangelength: [1, 64]
			},
			tokenParaHabilitarRestWS: {
				notOnlySpaceOrEmpty: true,
				required: true,
				rangelength: [1, Infinity]
			},
			tiempoExpiracionTokenEnMinutos: {
				required: true,
				digits: true,
				range: [5, 99999]
			},
			tiempoTimeOutUbaEnSegundos: {
				required: true,
				digits: true,
				range: [10, 999999999]
			},
			pathBaseParaConsultaDesdeSIMPWeb: {
				notOnlySpaceOrEmpty: true,
				required: true,
				rangelength: [1, 100]
			},
			ipsPermitidas: {
				notOnlySpaceOrEmpty: true,
				required: false,
				rangelength: [1, 250]
			},
			filtrarIps: {}
		},
		messages: {
			pathHostWS: {
				required: "Ingrese la ruta del Host.",
				rangelength: "La ruta del Host debe tener entre 1 y 100 caracteres.",
				notOnlySpaceOrEmpty: "No se permite espacios ni el campo vac&iacute;o."
			},
			llaveInstalacion: {
				required: "Ingrese la Llave de Instalaci&oacute;n.",
				rangelength: "La Llave de Instalaci&on debe contener entre 1 y 32 caracteres."
			},
			llaveTransporteZMK: {
				required: "Ingrese la Llave de Transporte ZMK.",
				rangelength: "La  LlaveTransporteZMK debe tener entre 1 y 64 caracteres."
			},
			llaveTrabajoZPK: {
				required: "Ingrese la Llave de Trabajo ZPK",
				rangelength: "La Llave de Instalaci&on debe contener entre 1 y 64 caracteres."
			},
			tokenParaHabilitarRestWS: {
				required: "Ingrese el Token para RestWS.",
			},
			tiempoExpiracionTokenEnMinutos: {
				required: "Ingrese el Tiempo de Expiraci&oacute;n.",
				digits: "El Tiempo de Expiraci&oacute;n debe contener d&iacute;gitos.",
				range: "El Tiempo de Expiraci&oacute;n debe estar entre 5 y 99999 minutos."
			},
			tiempoTimeOutUbaEnSegundos: {
				required: "Ingrese el Tiempo Uba seg",
				digits: "El Tiempo Uba debe contener d&iacute;gitos.",
				range: "El Tiempo de Uba debe estar entre 5 y 99999 seg."
			},
			pathBaseParaConsultaDesdeSIMPWeb: {
				required: "Ingrese la ruta para consulta SIMPWEB.",
				rangelength: "El Path debe contener entre 1 y 100 caracteres."
			}
		}
	});

});
