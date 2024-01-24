$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idEmpresa : {
				required : true,
				soloalfanumericos: true,
				rangelength : [ 1, 4 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 70 ]
			},
			ruc : {
				required : true,
				notOnlySpace : true,
				number : true,
				rangelength : [ 11, 11 ]
			},
			direccion : {
				required : true,
				notOnlySpace : true,
			}
		},
		messages : {
			idEmpresa : {
				required : "Ingrese un C&oacute;digo de Empresa",
				soloalfanumericos : "El C&oacute;digo de Empresa debe contener solo car&aacute;cteres alfanumericos.",
				rangelength : "El C&oacute;digo de Empresa debe contener entre 1 y 4 car&aacute;cteres."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 70 car&aacute;cteres."
			},
			ruc : {
				required : "Ingrese un RUC.",
				notOnlySpace : "El RUC no puede contener solo espacios en blanco.",
				number : "El RUC debe contener debe contener solo n&uacute;meros.",
				rangelength : "El RUC debe contener 11 dig&iacute;tos."
			},
			direccion : {
				required : "Ingrese una direcci&oacute;n.",
				notOnlySpace : "La direcci&oacute;n. no puede contener solo espacios en blanco."
			}
		}
	});

});