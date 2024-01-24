$(document).ready(function() {
	$("#formMantenimiento").validate({
		focusCleanup: true,
		rules: {
			idInstitucion: {
				required: true,
				rangelength: [0, 1]
			},
			idLogo: {
				required: true,
				notOnlySpace: true,
				rangelength: [1, 11],
				soloalfanumericos: true,
			},
			descripcion: {
				required: true,
				notOnlySpace: true,
				rangelength: [3, 40],
				// noSpaces: true
			},
			idMembresia: {
				required: true,
				rangelength: [1, 1]
			},
			idClaseServicio: {
				required: true,
				rangelength: [1, 1]
			},
			longitudBin: {
				required: true,
				range: [3, 11],
				number: true,
			},
			longitudPan: {
				required: true,
				range: [14, 19],
				number: true,
			},
			idBin: {
				required: true,
				tamanioRequerido: "#longitudBin",
				number: true,
			},
			segundoIdBin: {
				required: true,
				tamanioRequerido: "#longitudBin",
				number: true,
			},
			restoRangoInicial: {
				required: true,
				number: true,
				rangoInicial: ["#longitudBin", "#longitudPan"],
				smallerThan: "#restaRangoFinal"
				/*min : 16,
				max : 19*/
			},
			restoRangoFinal: {
				required: true,
				number: true,
				rangoInicial: ["#longitudBin", "#longitudPan"],
				greaterThan: "#restoRangoInicial"
				/*rangelength: [16, 16],
				minimoDependiente: "#bines",
				maximoDependiente: "#bines"*/
			},
		},
		messages: {
			idInstitucion: {
				required: "Ingrese un C\u00F3digo Instituci\u00F3n.",
				rangelength: "El C\u00F3digo de Instituci\u00F3n debe contener {1} d\u00EDgitos."
			},
			idLogo: {
				required: "Ingrese un C\u00F3digo Logo.",
				notOnlySpace: "El C\u00F3digo de Logo no puede contener solo espacios en blanco.",
				rangelength: "El C\u00F3digo de Logo debe contener entre {0} y {1} caracteres."
			},
			descripcion: {
				required: "Ingrese una Descripci\u00F3n.",
				notOnlySpace: "La Descripci\u00F3n no puede contener solo espacios en blanco.",
				rangelength: "La Descripci\u00F3n debe contener entre {0} y {1} caracteres."
			},
			idMembresia: {
				required: "Seleccione una Membres\u00EDa.",
				rangelength: "El C\u00F3digo Membres\u00EDa debe contener entre {0} caracteres."
			},
			idClaseServicio: {
				required: "Seleccione una Clase Servicio.",
				rangelength: "El C\u00F3digo de Clase Servicio debe contener entre {0} caracteres."
			},
			longitudBin: {
				required: "Ingrese una Longitud BIN.",
				range: "La Longitud BIN debe estar entre {0} y {1}."
			},
			longitudPan: {
				required: "Ingrese una Longitud PAN.",
				range: "La Longitud debe estar entre {0} y {1}."
			},
			idBin: {
				required: "Ingrese BIN.",
				//range: "La Longitud debe estar entre {0} y {1}."
			},
			segundoIdBin: {
				required: "Ingrese BIN.",
				//range: "La Longitud debe estar entre {0} y {1}."
			},
			restoRangoInicial: {
				required: "Ingrese Rango Inicial.",
				number: "El Rango Inicial debe ser n\u00FAmero.",
				notOnlySpace: "El Rango Inicial no puede contener solo espacios en blanco.",
				rangelength: "El Rango Inicial debe tener 16 d\u00EDgitos"
			},
			restoRangoFinal: {
				required: "Ingrese Rango Final.",
				number: "El Rango Final debe ser n\u00FAmero.",
				notOnlySpace: "El Rango Final no puede contener solo espacios en blanco.",
				// lettersonly: "El C\u00F3digo de Membres\u00EDa debe contener solo letras.",
				rangelength: "El Rango Inicial debe tener 16 d\u00EDgitos.",
				greaterThan: "El valor debe ser mayor al Rango Inicial."
			},
		}
	});
});