$(document).ready(function () {
    $formBusquedaCriterios.validate({
        rules: {
            idInstitucion: {
                required: true
            }
        },
        messages: {
            idInstitucion: {
                required: "Seleccione una Instituci\u00f3n.",
            }
        }
    });

    $formBusquedaTipoDocumento.validate({
        rules : {
            tipoDocumento : {
                required : true,
                number : true,
                rangelength : [ 1, 4 ]
            },
            numeroDocumento : {
                required : true,
                notOnlySpace : true,
                rangelength : [ 1, 20 ]
            }
        },
        messages : {
            tipoDocumento : {
                required : "Seleccione un Tipo Documento.",
                number : "El Tipo Documento debe ser un n&uacute;mero.",
                rangelength : "El Tipo Documento debe contener entre {0} y {1} d&iacute;gitos."
            },
            numeroDocumento : {
                required : "Ingrese un N&uacute;mero Documento.",
                notOnlySpace : "El N&uacute;mero Documento no puede contener solo espacios en blanco.",
                rangelength : "El N&uacute;mero Documento debe contener entre {0} y {1} caracteres."
            }
        }
    });

    $formAsociarCliente.validate({
        rules : {
            clienteUBAAsociar : {
                required : true,
                number : true
            },
            codigoEmpresa : {
                required : true
            }
        },
        messages : {
            clienteUBAAsociar : {
                required : "Ingrese un cliente",
                number : "El cliente debe ser un n\u00FAmero.",
            },
            codigoEmpresa : {
                required : "Ingrese una empresa",
            }
        }
    });

    $formRegimen.validate({
        rules : {
            regimen : {
                required : true
            }
        },
        messages : {
            clienteUBAAsociar : {
                required : "Ingrese un r\u00E9gimen",
            }
        }
    });

    $formBloqueo.validate({
        rules : {
            motivoBloqueo : {
                required : true
            }
        },
        messages : {
            motivoBloqueo : {
                required : "Ingrese un motivo",
            }
        }
    });

    $formReasignar.validate({
        rules: {
            codigoSeguimientoNuevo: {
                required: true
            }
        },
        messages: {
            codigoSeguimientoNuevo: {
                required: "Ingrese un c\u00F3digo de seguimiento nuevo",
            }
        }
    });

    $formEmitirTarjetaVirtual.validate({
        rules : {
            tipoDocumento : {
                required : true
            },
            numeroDocumento: {
                required : true
            },
            afinidad: {
                required : true
            },
            programaLealtad: {
                required : true
            },
            empresa: {
                required : true
            },
        },
        messages : {
            tipoDocumento : {
                required : "Ingrese un tipo de documento",
            },
            numeroDocumento: {
                required : "Ingrese un n\u00FAmero de documento",
            },
            afinidad: {
                required : "Ingrese una afinidad",
            },
            programaLealtad: {
                required : "Ingrese un programa de lealtad",
            },
            empresa: {
                required : "Ingrese una empresa",
            },
        }
    });

    $formRecarga.validate({
        rules : {
            ciudad : {
                required : true
            },
            terminal: {
                required : true
            },
            direccion: {
                required : true
            },
            monedaComision: {
                required : true
            },
            montoComision: {
                required : true
            },
            comercio: {
                required : true
            },
            monedaRecarga: {
                required : true
            },
            montoRecarga: {
                required : true
            },
        },
        messages : {
            ciudad : {
                required : "Ingrese una ciudad",
            },
            terminal: {
                required : "Ingrese una terminal",
            },
            direccion: {
                required : "Ingrese una direcci\u00F3n",
            },
            monedaComision: {
                required : "Ingrese una moneda de la comisi\u00F3n",
            },
            montoComision: {
                required : "Ingrese una comisi\u00F3n",
            },
            comercio: {
                required : "Ingrese un comercio",
            },
            monedaRecarga: {
                required : "Ingrese un moneda de la recarga",
            },
            montoRecarga: {
                required : "Ingrese un monto de la recarga",
            },
        }
    });

    $formDebito.validate({
        rules : {
            ciudad : {
                required : true
            },
            direccion: {
                required : true
            },
            terminal: {
                required : true
            },
            monedaDebito: {
                required : true
            },
            montoDebito: {
                required : true
            },
        },
        messages : {
            ciudad : {
                required : "Ingrese una ciudad",
            },
            direccion: {
                required : "Ingrese una direcci\u00F3n",
            },
            terminal: {
                required : "Ingrese un terminal",
            },
            monedaDebito: {
                required : "Ingrese una moneda",
            },
            montoDebito: {
                required : "Ingrese un monto del d\u00E9bito",
            },
        }
    });


    $formTransferencia.validate({
        rules : {
            ciudad : {
                required : true
            },
            direccion: {
                required : true
            },
            codigoSeguimientoDestino: {
                required : true
            },
            monedaTransferencia: {
                required : true
            },
            montoTransferencia: {
                required : true
            },
            terminal: {
                required : true
            },
            comercio: {
                required : true
            },
        },
        messages : {
            ciudad : {
                required : "Ingrese una ciudad",
            },
            direccion: {
                required : "Ingrese una direcci\u00F3n",
            },
            codigoSeguimientoDestino: {
                required : "Ingrese una direcci\u00F3n",
            },
            monedaTransferencia: {
                required : "Ingrese una moneda",
            },
            montoTransferencia: {
                required : "Ingrese una monto",
            },
            terminal: {
                required : "Ingrese un terminal",
            },
            comercio: {
                required : "Ingrese un comercio",
            },
        }
    });
});