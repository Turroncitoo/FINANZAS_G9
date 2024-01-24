$(document).ready(function () {
    $formBusquedaTipoDocumento.validate({
        focusCleanup: true,
        rules: {
            tipoDocumento: {
                required: true,
                number: true,
                rangelength: [1, 4]
            },
            numeroDocumento: {
                required: true,
                notOnlySpace: true,
                rangelength: [1, 12]
            }
        },
        messages: {
            tipoDocumento: {
                required: "Seleccione un Tipo Documento.",
                number: "El Tipo Documento debe ser un n&uacute;mero.",
                rangelength: "El Tipo Documento debe contener entre 1 y 4 d&iacute;gitos."
            },
            numeroDocumento: {
                required: "Ingrese un N&uacute;mero Documento.",
                notOnlySpace: "El N&uacute;mero Documento no puede contener solo espacios en blanco.",
                rangelength: "El N&uacute;mero Documento debe contener entre 1 y 12 d\u00EDgitos."
            }
        },
        highlight: function (element) {
            $(element).parents(".group, .form-group").first().addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).parents(".group, .form-group").first().removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else if (element.parent().find("span.select2").length > 0) {
                error.appendTo(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });

    $formBusquedaCriterios.validate({
        focusCleanup: true,
        rules: {
            fechaProceso: {
                required: true
            },
            codigoInstitucion: {
                required: true
            }
        },
        messages: {
            fechaProceso: {
                required: "Seleccione una Fecha Proceso.",
            },
            codigoInstitucion: {
                required: "Seleccione una Instituci\u00f3n.",
            }
        },
        highlight: function (element) {
            $(element).parents(".group, .form-group").first().addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).parents(".group, .form-group").first().removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else if (element.parent().find("span.select2").length > 0) {
                error.appendTo(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
});