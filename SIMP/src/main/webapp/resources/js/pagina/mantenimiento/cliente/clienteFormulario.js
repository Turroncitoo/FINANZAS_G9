$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idCliente : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 4 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 40 ]
			},
			idEmpresa : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			}
		},
		messages : {
			idCliente : {
				required : "Ingrese un C&oacute;digo de Cliente.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "El C&oacute;digo de Cliente debe contener entre 1 y 4 car&aacute;cteres."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre {0} y {1} car&aacute;cteres."
			},
			idEmpresa : {
				required : "Seleccione una empresa",
				notOnlySpace : "El C&oacute;digo de Empresa no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Empresa debe contener entre 1 y 4 car&aacute;cteres."
			}
		}
	});

});