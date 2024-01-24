$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoInstitucion: {
				required: true
			},		
			idEmpresa : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			idCliente : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			codigoMoneda : {
				required : true,
				digits : true,
				range : [ 1, 9999 ]
			},
			codigoMembresia : {
				required : true,
				notOnlySpace : true,
				lettersonly : true,
				selectlength : 1
			},
			codigoClaseServicio : {
				required : true,
				notOnlySpace : true,
				lettersonly : true,
				selectlength : 1
			},
			codigoOrigen : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			codigoClaseTransaccion : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			codigoTransaccion : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			idRolTransaccion : {
				required : true,
				digits : true,
				range : [ 1, 10 ]
			},
			cuentaCargo : {
				required : true,
				soloalfanumericos : true,
				maxlength : 20
			},
			cuentaAbono : {
				required : true,
				soloalfanumericos : true,
				maxlength : 20
			},
			codigoAnalitico : {
				soloalfanumericos : true,
				maxlength : 10
			}
		},
		messages : {
			codigoInstitucion: {
				required: 'Seleccione una Instituci\u00F3n'
			},
			idEmpresa : {
				required : "Seleccione el C&oacute;digo de Empresa.",
				notOnlySpace : "El C&oacute;digo de Empresa no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Empresa debe contener 4 caracteres."
			},
			idCliente : {
				required : "Seleccione el C&oacute;digo de Cliente.",
				notOnlySpace : "El C&oacute;digo de Cliente no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Cliente debe contener 4 caracteres."
			},
			codigoMoneda : {
				required : "Seleccione el C&oacute;digo de Moneda.",
				digits : "El C&oacute;digo de Moneda debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo de Moneda debe estar entre 1 y 9999."
			},
			codigoMembresia : {
				required : "Seleccione el C&oacute;digo de Membres&iacute;a.",
				notOnlySpace : "El C&oacute;digo de Membres&iacute;a no puede contener solo espacios en blanco.",
				lettersonly : "El C&oacute;digo de Membres&iacute;a debe contener solo letras.",
				selectlength : "El C&oacute;digo de Membres&iacute;a debe contener 1 car&aacute;cter."
			},
			codigoClaseServicio : {
				required : "Seleccione el C&oacute;digo de Clase de Servicio.",
				notOnlySpace : "El C&oacute;digo de Clase de Servicio no puede contener solo espacios en blanco.",
				lettersonly : "El C&oacute;digo de Clase de Servicio debe contener solo letras.",
				selectlength : "El C&oacute;digo de Clase de Servicio debe contener 1 car&aacute;cter."
			},
			codigoOrigen : {
				required : "Seleccione el C&oacute;digo de Origen.",
				digits : "El C&oacute;digo de Origen debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo de Origen debe estar entre 1 y 99."
			},
			codigoClaseTransaccion : {
				required : "Seleccione el C&oacute;digo de Clase de Transacci&oacute;n.",
				digits : "El C&oacute;digo de Clase de Transacci&oacute;n debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo de Clase de Transacci&oacute;n debe estar entre 1 y 99."
			},
			codigoTransaccion : {
				required : "Seleccione el C&oacute;digo de Transacci&oacute;n.",
				digits : "El C&oacute;digo de Transacci&oacute;n debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo de Transacci&oacute;n debe estar entre 1 y 99."
			},
			idRolTransaccion : {
				required : "Seleccione el Rol de Transacci&oacute;n.",
				digits : "El Rol de Transacci&oacute;n debe contener solo d&iacute;gitos.",
				range : "El Rol de Transacci&oacute;n debe estar entre 1 y 10."
			},
			cuentaCargo : {
				required : "Ingrese una Cuenta Cargo.",
				soloalfanumericos : "La Cuenta de Cargo debe contener caracteres alfanumericos.",
				maxlength : "La Cuenta Cargo no debe contener m&aacute;s de 20 caracteres."
			},
			cuentaAbono : {
				required : "Ingrese una Cuenta Abono.",
				soloalfanumericos : "La Cuenta de Abono debe contener caracteres alfanumericos.",
				maxlength : "La Cuenta Abono no debe contener m&aacute;s de 20 caracteres."
			},
			codigoAnalitico : {
				soloalfanumericos : "El C&oacute;digo de Anal&iacute;tico debe contener caracteres alfanumericos.",
				maxlength : "La C&oacute;digo de Anal&iacute;tico no puede contener m&aacute;s de 10 caracteres."
			}
		}
	});
});