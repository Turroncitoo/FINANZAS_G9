$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup: true,
		rules: {
			idComercio: {
				required: true,
			},
			idEmpresa: {
				required: true,
			},
			idCliente: {
				required: true,
			},
			operacion: {
				required: true,
			},
			monedaRecarga: {
				required: true,
			},
			cuentaCargo: {
				required: true,
				maxlength: 100
			},
			cuentaAbono: {
				required: true,
				maxlength: 100
			}
		},
		messages: {
			idComercio: {
				required: "Seleccione un comercio.",
			},
			idEmpresa: {
				required: "Seleccione una empresa.",
			},
			idCliente: {
				required: "Seleccione un cliente.",
			},
			operacion: {
				required: "Seleccione un tipo de operaci&oacute;n",
			},
			monedaRecarga: {
				required: "Seleccione una moneda",
			},
			cuentaCargo: {
				required: "Digite la cuenta cargo",
				notOnlySpace: "La cuenta cargo no puede contener solo espacios en blanco.",
				maxlength: "La Cuenta Abono no debe tener m&aacute;s de 100 caracteres.",
			},
			cuentaAbono: {
				required: "Digite la cuenta abono",
				notOnlySpace: "La cuenta abono no puede contener solo espacios en blanco.",
				maxlength: "La Cuenta Abono no debe tener m&aacute;s de 100 caracteres.",
			}
		}
	});
});