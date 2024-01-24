$(document).ready(function () {
    $formMantenimiento.validate({
        rules: {
            tipoCuenta: {
                required: true,
            },
            numeroCuentaContable: {
                required: true,
                notOnlySpace: true,
                alphanumericWithSpaces: true,
                maxlength: 20
            },
            descripcion: {
                required: true,
                notOnlySpace: true,
                alphanumericWithSpaces: true,
                maxlength: 50
            },
            codigoMoneda: {
                required: true
            }
        },
        messages: {
            tipoCuenta: {
                required: "Ingrese un tipo de Cuenta Contable.",
            },
            numeroCuentaContable: {
                required: "Ingrese cuenta contable.",
                notOnlySpace: "La cuenta contable no debe contener solo espacios en blanco.",
                maxlength: "La cuenta contable no debe tener m&aacute;s de {0} d&iacute;gitos."
            },
            descripcion: {
                required: "Ingrese una descripci\u00F3n.",
                notOnlySpace: "La descripci\u00F3n no debe contener solo espacios en blanco.",
                maxlength: "La descripci\u00F3n no debe tener m\u00E1s de {0} car&aacute;cteres."
            },
            codigoMoneda: {
                required: "Seleccione una Moneda."
            }
        }
    });
});