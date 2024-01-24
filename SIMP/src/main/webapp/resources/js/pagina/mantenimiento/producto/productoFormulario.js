$(document).ready(function() {

	$("#formMantenimiento").validate({
		focusCleanup: true,
		rules: {
			idLogo: {
				required: true,
				notOnlySpace: true,
				rangelength: [1, 11]
			},
			codigoProducto: {
				required: true,
				notOnlySpace: true,
				rangelength: [1, 8],
				soloalfanumericos: true,
			},
			descProducto: {
				required: true,
				notOnlySpace: true,
				rangelength: [5, 30],
			},
			codigoMoneda: {
				required: true,
				notOnlySpace: true,
				rangelength: [1, 3]
			},
			idBin: {
				required: true,
				number: true,
			},
			segundoIdBin: {
				required: true,
				number: true,
			}
		},
		messages: {
			idLogo: {
				required: "Ingrese un C\u00F3digo Logo.",
				notOnlySpace: "El C\u00F3digo Producto no puede contener solo espacios en blanco.",
				rangelength: "El C\u00F3digo Producto debe contener entre {0} y {1} caracteres."
			},
			codigoProducto: {
				required: "Ingrese un C\u00F3digo Producto.",
				notOnlySpace: "El C\u00F3digo Producto no puede contener solo espacios en blanco.",
				rangelength: "El C\u00F3digo Producto debe contener entre {0} y {1} caracteres."
			},
			descProducto: {
				required: "Ingrese una Descripci\u00F3n.",
				notOnlySpace: "La Descripci\u00F3n no puede contener solo espacios en blanco.",
				rangelength: "La Descripci\u00F3n debe contener entre {0} y {1} caracteres."
			},
			codigoMoneda: {
				required: "Seleccione una Moneda",
				notOnlySpace: "La Moneda no puede contener solo espacios en blanco.",
				rangelength: "El C&oacute;digo Moneda debe contener entre {0} y {1} caracteres."
			},
			idBin: {
				required: "Seleccione un Logo.",
				rangelength: "El C\u00F3digo BIN debe contener  8 d\u00EDgitos."
			},
			segundoIdBin: {
				required: "Seleccione un Logo.",
				//range: "La Longitud debe estar entre {0} y {1}."
			},
			longitudPan: {
				required: "Ingrese una longitud Pan.",
				min: "Debe contener valores entre 16 y 19.",
				max: "Debe contener valores entre 16 y 19."
			},
		}
	});

	$("#formAsociacionClienteProducto").validate({
		focusCleanup: true,
		rules: {
			idEmpresa: {
				required: true
			},
			idCliente: {
				required: true,
			},
		},
		messages: {
			idEmpresa: {
				required: "Seleccione una Empresa",
			},
			idCliente: {
				required: "Seleccione un Cliente",
			},
		}
	});

});