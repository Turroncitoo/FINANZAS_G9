$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoPrograma : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 4 ],
				soloalfanumericos : true
			},
			codigoSubModulo : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			codigoGrupo : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 20 ]
			},
			procedimiento : {
				notOnlySpaceOrEmpty : true,
				maxlength : 100
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			},
			archivo : {
				notOnlySpaceOrEmpty : true,
				rangelength : [ 1, 200 ]
			},
			ordenEjecucion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			},
			periodoEjecucion : {
				required : true,
				rangelength : [ 1, 1 ],
				lettersonly : true
			},
			procesaSabado : {
				number : true,
				range : [ 0, 1 ]
			},
			longitud : {
				number : true,
				range : [ 1, 99999 ]
			}
		},
		messages : {
			codigoPrograma : {
				required : "Ingrese el C&oacute;digo del programa.",
				notOnlySpace : "El C&oacute;digo no puede contener solo espacios en blanco.",
				rangelength : "El C&oacute;digo del programa debe tener 4 caracteres.",
				soloalfanumericos : "El C&oacute;digo del Programa debe contener car&aacute;cteres alfanum&eacute;ricos."
			},
			codigoSubModulo : {
				required : "Seleccione un sub m&oacute;dulo.",
				notOnlySpace : "El C&oacute;digo no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo del sub m&oacute;dulo debe tener 4 caracteres."
			},
			codigoGrupo : {
				required : "Seleccione un Grupo.",
				notOnlySpace : "El C&oacute;digo de Grupo no debe contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Grupo debe contener m&aacute;s 20 caracteres."
			},
			procedimiento : {
				notOnlySpaceOrEmpty : "El Procedimiento no debe contener solo espacios en blanco.",
				maxlength : "El Procedimiento no debe contener m&aacute;s de 100 car&aacute;cteres."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n no debe contener entre 3 y 50 car&aacute;cteres."
			},
			archivo : {
				notOnlySpaceOrEmpty : "El archivo no debe contener solo espacios en blanco.",
				rangelength : "El archivo no debe contener m&aacute;s de 200 caracteres."
			},
			ordenEjecucion : {
				required : "Ingrese el orden de ejecuci&oacute;n.",
				number : "El valor de orden de ejecuci&oacute;n debe ser un n&uacute;mero.",
				range : "El valor de orden de ejecuci&oacute;n debe ser 1 o 99."
			},
			periodoEjecucion : {
				required : "Ingrese el periodo de ejecuci&oacute;n.",
				rangelength : "El periodo de ejecuci&oacute;n debe tener 1 caracter.",
				lettersonly : "El periodo de ejecuci&oacute;n debe ser solo car&aacute;cteres."
			},
			procesaSabado : {
				number : "El valor de Procesa S&aacute;bado debe ser un n&uacute;mero.",
				range : "El valor de Procesa S&aacute;bado debe ser 0 o 1."
			},
			longitud : {
				number : "El valor de longitud debe ser un n&uacute;mero.",
				range : "El valor de longitud debe estar entre 1 y 99999."
			}
		}
	});
});