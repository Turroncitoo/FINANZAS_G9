$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			rolTransaccion : {
				required : true
			},
			monedaCompensacion : {
				required : true
			},
			tipoMovimiento : {
				required : true
			},
			registroContable : {
				required : true
			},
			cuentaCargo: {
				required: true,
				notOnlySpace: true,
				alphanumericWithSpaces : true,
				maxlength : 20
			},
			cuentaAbono:{
				required: true,
				notOnlySpace: true,
				alphanumericWithSpaces : true,
				maxlength: 20
			},
			codigoAnalitico:{
				maxlength: 10,
				soloalfanumericos: true
			}
		},
		messages : {
			rolTransaccion:{
				required: "Seleccione un rol"
			},
			monedaCompensacion:{
				required: "Seleccione una moneda"
			},
			tipoMovimiento:{
				required: "Seleccione un tipo de movimiento"
			},
			registroContable:{
				required:"Seleccione un registro contable"
			},
			cuentaCargo : {
				required : "Ingrese una Cuenta Cargo.",
				notOnlySpace : "La Cuenta Cargo no debe contener solo espacios en blanco.",
				rangelength : "La Cuenta Cargo no debe tener m\u00E1s de 20 caracteres.",
				alphanumericWithSpaces : "La Cuenta Cargo debe contener alfanum&eacute;ricos o espacios.",
				maxlength : "La Cuenta Cargo no debe tener m\u00E1s de 20 caracteres."
			},
			cuentaAbono : {
				required : "Ingrese una Cuenta Abono.",
				notOnlySpace : "La Cuenta Abono no debe contener solo espacios en blanco.",
				maxlength : "La Cuenta Abono no debe tener m\u00E1s de 20 caracteres.",
				alphanumericWithSpaces : "La Cuenta Abono debe contener alfanum&eacute;ricos o espacios."
			},
			codigoAnalitico:{
				maxlength: "El C&oacute;digo anal\u00EDtico no debe tener m\u00E1s de 10 caracteres.",
				soloalfanumericos: "El c&oacute;digo anal\u00EDtico debe contener solo alfanum\u00E9ricos."
			}
		}
	});
});