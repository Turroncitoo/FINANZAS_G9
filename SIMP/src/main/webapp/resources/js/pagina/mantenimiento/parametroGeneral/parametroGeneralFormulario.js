$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			fechaProceso : {
				required : true
			},
			binRuteoSwitch : {
				required : true,
				number : true,
			},
			codigoInstitucion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			idEmpresa : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			surchargeSoles : {
				notOnlySpaceOrEmpty : true,
				number : true,
				range : [ 0, 9999 ]
			},
			surchargeDolares : {
				notOnlySpaceOrEmpty : true,
				number : true,
				range : [ 0, 9999 ]
			},
			porcentajeIgv : {
				required : true,
				digits : true,
				range : [ 0, 100 ]
			},
			tiempoReprogramacion : {
				required : true,
				digits : true,
				range : [ 0, 99 ]
			}
		},
		messages : {
			fechaProceso : {
				required : "Seleccione la Fecha de Proceso"
			},
			binRuteoSwitch : {
				required : "Ingrese el BIN Ruteo Switch.",
				number : "El BIN de Ruteo Switch debe contener solo n&uacute;meros.",
			},
			codigoInstitucion : {
				required : "Seleccione una Instituci&oacute;n.",
				number : "El C&oacute;digo de Instituci&oacute;n debe ser un n&uacute;mero.",
				range : "El C&oacute;digo de Instituci&oacute;n debe estar entre 1 y 99."
			},
			idEmpresa : {
				required : "Seleccione una Empresa",
				notOnlySpace : "El Id Empresa no debe contener solo espacios en blanco.",
				selectlength : "El Id Empresa debe contener entre 1 y 4 car&aacute;cteres."
			},
			surchargeSoles : {
				number : "El Surcharge Soles debe ser un n&uacute;meros.",
				range : "El Surcharge Soles debe estar entre 0 y 9999"
			},
			surchargeDolares : {
				number : "El Surcharge D&oacute;lares debe ser un n&uacute;mero.",
				range : "El Surcharge D&oacute;lares debe estar entre 0 y 9999"
			},
			porcentajeIgv : {
				required : "Ingrese el Porcentaje I.G.V.",
				digits : "El Porcentaje I.G.V. debe contener d&iacute;gitos.",
				range : "El Porcentaje I.G.V. debe estar entre 0 y 100."
			},
			tiempoReprogramacion : {
				required : "Ingrese el Tiempo de Reprogramaci\u00f3n.",
				digits : "El Tiempo de Reprogramaci\u00f3n debe contener solo dig&iacute;gitos.",
				range : "El Tiempo de Reprogramaci\u00f3n debe estar entre 0 y 99."
			}
		}
	});

});