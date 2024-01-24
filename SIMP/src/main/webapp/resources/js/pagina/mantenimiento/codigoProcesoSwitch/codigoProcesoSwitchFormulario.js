$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoProcesoSwitch : {
				required : true,
				notOnlySpace : true,
				number : true,
				rangelength : [ 2, 2 ]
			},
			codigoClaseTransaccion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			codigoTransaccion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 80 ]
			}
		},
		messages : {
			codigoProcesoSwitch : {
				required : "Ingrese un C&oacute;digo de Proceso de Switch.",
				notOnlySpace : "El C&oacute;digo de Proceso de Switch no debe contener solo espacios en blanco.",
				number : "El C&oacute;digo de Proceso de Switch debe contener solo n&uacute;meros.",
				rangelength : "El C&oacute;digo de Proceso de Switch  debe contener 2 d\u00edgitos."
			},
			codigoClaseTransaccion : {
				required : "Seleccione un C&oacute;digo de Clase de Transacci&oacute;n.",
				range : "El C&oacute;digo de Clase de Transacci&oacute;n debe estar entre 1 y 99.",
				number : "El C&oacute;digo de Clase de Transacci&oacute;n debe contener solo n&uacute;meros."
			},
			codigoTransaccion : {
				required : "Seleccione un C&oacute;digo de Transacci&oacute;n.",
				range : "El C&oacute;digo de Transacci&oacute;n debe estar entre 1 y 99.",
				number : "El C&oacute;digo de Transacci&oacute;n debe contener solo n&uacute;meros."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener contener entre 3 y 80 car&aacute;cteres."
			}
		}
	});

});