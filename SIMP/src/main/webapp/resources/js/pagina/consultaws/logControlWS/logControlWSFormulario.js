$(document).ready(function () {
    $formBusqueda.validate({
        focusCleanup: true,
        rules: {
            fechaTransaccion: {
                required: true
            },
            codigoInstitucion: {
                required: true
            }
        },
        messages: {
            fechaTransaccion: {
                required: "Seleccione una Fecha Transacci\u00f3n.",
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