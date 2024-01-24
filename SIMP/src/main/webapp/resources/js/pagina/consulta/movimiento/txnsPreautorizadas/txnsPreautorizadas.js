$(document).ready(function () {
    let $local = {
        $tablaConsultaWS: $("#tablaConsultaWS"),
        tablaConsultaWS: "",
        filtrosSeleccionables: [],
        $buscarPorCriterio: $("#buscarPorCriterio"),
        arregloSiNo: [1, 0],

        //Selects form criterios
        $selectInstitucion: $("#selectInstitucion"),
        $selectEmpresa: $("#selectEmpresa"),
        $selectCliente: $("#selectCliente"),
        $selectEstado: $("#selectEstado"),

        $rangoFechas: $("#fechaSolicitud"),

        // Exportacion
        $exportarPorCriterio: $("#exportarPorCriterio"),

        $filaSeleccionadaConsulta: {},
    };

    $formCriterioBusquedaConsulta = $("#formCriterioBusquedaConsulta");

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechas, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectInstitucion);
    $funcionUtil.crearSelect2($local.$selectEmpresa);
    $funcionUtil.crearSelect2Multiple($local.$selectCliente, "-1", "TODOS");
    $funcionUtil.crearSelect2Multiple($local.$selectEstado, "-1", "TODOS");

    $.fn.dataTable.ext.errMode = 'none';

    $local.$selectEmpresa.on("change", () => {
        const opcionSeleccionada = $local.$selectEmpresa.val();
        if (!opcionSeleccionada) {
            $local.$selectCliente.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectCliente.find("option").remove();
                $local.$selectCliente.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectCliente.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$selectCliente.parent().find(".cargando").remove();
            }
        });
    });

    $local.tablaConsultaWS = $local.$tablaConsultaWS.DataTable({
        "language": {
            "emptyTable": "No se han encontrado movimientos con los criterios definidos."
        },
        "initComplete": function () {
            $local.$tablaConsultaWS.wrap("<div class='table-responsive'></div>");
            $local.filtrosSeleccionables["20"] = $local.arregloSiNo;
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaWS, $local.filtrosSeleccionables);
        },
        "columnDefs": [{
            "targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 17, 19, 22, 23, 24],
            "className": "all filtrable",
        }, {
            "targets": [16, 18, 25, 26],
            "className": "all filtrable dt-center",
        }, {
            "targets": 14,
            "className": "all filtrable dt-right monto",
        }, {
            "targets": [21, 27],
            "className": "all filtrable dt-right",
        }, {
            "targets": 20,
            "className": "all seleccionable data-no-definida dt-center",
            "render": function (data, type, row) {
                return $funcionUtil.insertarEtiquetaSiNo(row.exito);
            }
        }, {
            "targets": 28,
            "className": "all dt-center",
            "width": "10%",
            "render": function (data, type, row) {
                if (row.estado == 'P' || row.estado == 'R') {
                    return $variableUtil.botonAutorizarWS + ' ' + $variableUtil.botonDenegarWS;
                }
                return "";
            }
        }],
        "columns": [{
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descInstitucion);
            },
            "title": "Instituci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa);
            },
            "title": "Empresa"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente);
            },
            "title": "Cliente"
        }, {
            "data": 'tipoOperacion',
            "title": "Tipo Operaci\u00f3n"
        }, {
            "data": 'operacion',
            "title": "Operaci\u00f3n"
        }, {
            "data": 'codigoSeguimiento',
            "title": "C\u00f3digo Seguimiento"
        }, {
            "data": 'id',
            "title": "ID"
        }, {
            "data": 'numeroTarjeta',
            "title": "N\u00famero Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
            },
            "title": "Tipo Documento"
        }, {
            "data": 'numeroDocumento',
            "title": "N\u00famero Documento"
        }, {
            "data": 'nombres',
            "title": "Nombres"
        }, {
            "data": 'apellidoPaterno',
            "title": "Apellido Paterno"
        }, {
            "data": 'apellidoMaterno',
            "title": "Apellido Materno"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaTransaccion, row.descripcionMonedaTransaccion);
            },
            "title": "Moneda"
        }, {
            "data": 'montoTransaccion',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.estado, row.descripcionEstado);
            },
            "title": "Estado"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaSolicitudCadena, row.horaSolicitud);
            },
            "title": "Fecha Solicitud"
        }, {
            "data": 'usuarioSolicitud',
            "title": "Usuario Solicitud"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaAprobacionCadena, row.horaAprobacion);
            },
            "title": "Fecha Aprobaci\u00f3n"
        }, {
            "data": 'usuarioAprobacion',
            "title": "Usuario Aprobaci\u00f3n"
        }, {
            "data": 'exito',
            "title": "\u00C9xito"
        }, {
            "data": 'numeroReintentos',
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Nro. Reintentos"
        }, {
            "data": 'idTransaccion',
            "title": "ID Transacci\u00f3n"
        }, {
            "data": 'numeroTrace',
            "title": "Trace"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descripcionRespuesta);
            },
            "title": "Respuesta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaEnvioTransaccionCadena, row.horaEnvioTransaccion);
            },
            "title": "Env\u00edo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaRecepcionTransaccionCadena, row.horaRecepcionTransaccion);
            },
            "title": "Recepci\u00f3n"
        }, {
            "data": 'tiempoTotal',
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Tiempo Total (ms)"
        }, {
            "data": null,
            "title": "Acci\u00F3n"
        }],
        "createdRow": function (row) {
            $(row).find(".monto").filter(function () {
                var celda = $(this);
                var valor = parseFloat(celda.text());
                if (valor > 0) {
                    celda.addClass("color-blue");
                } else if (valor < 0) {
                    celda.addClass("color-red");
                } else {
                    celda.addClass("color-inherit");
                }
            });
        },
        "order": []
    });

    $local.$tablaConsultaWS.find("thead").on('keyup', 'input.filtrable', function () {
        $local.tablaConsultaWS.column($(this).parent().index() + ':visible').search(this.value).draw();
    });

    $local.$tablaConsultaWS.find("thead").on('change', 'select', function () {
        var val = $.fn.dataTable.util.escapeRegex($(this).val());
        $local.tablaConsultaWS.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
    });

    $local.$buscarPorCriterio.on("click", function () {
        if (!$formCriterioBusquedaConsulta.valid()) {
            return;
        }
        const rangoFechas = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechas);
        let criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
        criterioBusqueda.fechaInicio = new Date(rangoFechas.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaFin = new Date(rangoFechas.fechaFin.replaceAll('-', '/'));
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCliente, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectEstado, "estados");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}txnsWebServices/preAutorizadas?accion=buscarPreAutorizadas&${paramCriterioBusqueda}`,
            contentType: "application/json",
            dataType: "json",
            statusCode: {
                400: function (response) {
                    $funcionUtil.limpiarMensajesDeError($formCriterioBusquedaConsulta);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCriterioBusquedaConsulta);
                }
            },
            beforeSend: function () {
                $local.tablaConsultaWS.clear().draw();
                $local.$buscarPorCriterio.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: function (transaccionWS) {
                if (transaccionWS.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
                    return;
                }
                $local.tablaConsultaWS.rows.add(transaccionWS).draw();
            },
            complete: function () {
                $local.$buscarPorCriterio.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$exportarPorCriterio.on("click", function () {
        if (!$formCriterioBusquedaConsulta.valid()) {
            return;
        }
        const rangoFechas = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechas);
        let criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
        criterioBusqueda.fechaInicio = new Date(rangoFechas.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaFin = new Date(rangoFechas.fechaFin.replaceAll('-', '/'));
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucion.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresa.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresa.find("option:selected").text();
        criterioBusqueda.descripcionRangoFechas = $local.$rangoFechas.val();
        criterioBusqueda.descripcionCliente = !!$local.$selectCliente.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCliente, "; ") : "TODOS";
        criterioBusqueda.descripcionEstado = !!$local.$selectEstado.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectEstado, "; ") : "TODOS";
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCliente, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectEstado, "estados");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}txnsWebServices/preAutorizadas?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterio&${paramCriterioBusqueda}`;
    });

    $local.$tablaConsultaWS.children("tbody").on("click", ".autorizar", function () {
        var btn = $(this);
        $local.$filaSeleccionadaConsulta = $(this).parents("tr");
        var txn = $local.tablaConsultaWS.row($local.$filaSeleccionadaConsulta).data();
        var criterio = {}
        criterio.id = txn.id;
        var paramCriterioBusqueda = $.param(criterio);
        $.confirm({
            icon: "fa fa-check",
            title: "Confirmar preautorizacion",
            content: "Se autorizar\u00E1 de la transacci\u00F3n.</br><b>Cliente: </b> " + $funcionUtil.aplicarTrim(txn.nombreCompleto) + "</br><b>Documento: </b>" + $funcionUtil.aplicarTrim(txn.numeroDocumento) + "</br><b>Tarjeta: </b>" + $funcionUtil.validarNull(txn.numeroTarjeta) + "</br><b>Operaci\u00F3n a autorizar: </b>" + $funcionUtil.validarNull(txn.tipoOperacion) + "</br><b>Moneda: </b>" + $funcionUtil.validarNull(txn.monedaTransaccion) + "</br><b>Monto: </b>" + $funcionUtil.validarNull(txn.montoTransaccion),
            buttons: {
                Aceptar: {
                    action: function () {
                        var confirmar = $.confirm({
                            icon: 'fa fa-spinner fa-pulse fa-fw',
                            title: "Realizando transacci\u00F3n...",
                            theme: "bootstrap",
                            content: function () {
                                var self = this;
                                self.buttons.close.hide();
                                $.ajax({
                                    type: "GET",
                                    url: $variableUtil.root + 'txnsWebServices/api/autorizar?' + paramCriterioBusqueda,
                                    contentType: "application/json",
                                    dataType: "json",
                                    beforeSend: function (xhr) {
                                        btn.attr("disabled", true).find("i").removeClass("fa-retweet").addClass("fa-spinner fa-pulse fa-fw");
                                        xhr.setRequestHeader('Content-Type', 'application/json');
                                        xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
                                    },
                                    success: function (data) {
                                        if (data.codigoRespuesta == "0") {
                                            $funcionUtil.notificarException("Transacci\u00F3n realizada con \u00E9xito.", "fa-check", "Autorizaci\u00F3n", "success");
                                        } else {
                                            $funcionUtil.notificarException("Reintentar operaci\u00F3n. Revise el c\u00F3digo de respuesta para saber el motivo.", "fa-warning", "Autorizaci\u00F3n", "warning");
                                        }
                                        $local.tablaConsultaWS.row($local.$filaSeleccionadaConsulta).remove().draw(false);
                                        var row = $local.tablaConsultaWS.row.add(data).draw();
                                        row.show().draw(false);
                                        $(row.node()).animateHighlight();
                                    },
                                    complete: function (response) {
                                        btn.attr("disabled", false).find("i").addClass("fa-retweet").removeClass("fa-spinner fa-pulse fa-fw");
                                        confirmar.close();
                                    }
                                });
                            },
                            buttons: {
                                close: {
                                    text: 'Aceptar'
                                }
                            }
                        });
                    },
                    keys: ['enter'],
                    btnClass: "btn-primary"
                },
                Cancelar: {
                    keys: ['esc']
                },
            }
        });
    });

    $local.$tablaConsultaWS.children("tbody").on("click", ".denegar", function () {
        var btn = $(this);
        $local.$filaSeleccionadaConsulta = $(this).parents("tr");
        var txn = $local.tablaConsultaWS.row($local.$filaSeleccionadaConsulta).data();
        var paramCriterioBusqueda = $.param(txn);
        $.confirm({
            icon: "fa fa-ban",
            title: "Rechazar preautorizacion",
            content: "Se denegar\u00E1 la transacci\u00F3n.</br><b>Cliente: </b> " + $funcionUtil.aplicarTrim(txn.nombreCompleto) + "</br><b>Documento: </b>" + $funcionUtil.aplicarTrim(txn.numeroDocumento) + "</br><b>Tarjeta: </b>" + $funcionUtil.validarNull(txn.numeroTarjeta) + "</br><b>Operaci\u00F3n a denegar: </b>" + $funcionUtil.validarNull(txn.tipoOperacion) + "</br><b>Moneda: </b>" + $funcionUtil.validarNull(txn.monedaTransaccion) + "</br><b>Monto: </b>" + $funcionUtil.validarNull(txn.montoTransaccion),
            buttons: {
                Aceptar: {
                    action: function () {
                        var confirmar = $.confirm({
                            icon: 'fa fa-spinner fa-pulse fa-fw',
                            title: "Realizando transacci\u00F3n...",
                            theme: "bootstrap",
                            content: function () {
                                var self = this;
                                self.buttons.close.hide();
                                $.ajax({
                                    type: "GET",
                                    url: $variableUtil.root + 'txnsWebServices/api/denegar?' + paramCriterioBusqueda,
                                    contentType: "application/json",
                                    dataType: "json",
                                    beforeSend: function (xhr) {
                                        btn.attr("disabled", true).find("i").removeClass("fa-retweet").addClass("fa-spinner fa-pulse fa-fw");
                                        xhr.setRequestHeader('Content-Type', 'application/json');
                                        xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
                                    },
                                    success: function (data) {
                                        $funcionUtil.notificarException("Transacci\u00F3n denegada con \u00E9xito.", "fa-ban", "Autorizaci\u00F3n", "success");
                                        $local.tablaConsultaWS.row($local.$filaSeleccionadaConsulta).remove().draw(false);
                                        var row = $local.tablaConsultaWS.row.add(data).draw();
                                        row.show().draw(false);
                                        $(row.node()).animateHighlight();
                                    },
                                    complete: function (response) {
                                        btn.attr("disabled", false).find("i").addClass("fa-retweet").removeClass("fa-spinner fa-pulse fa-fw");
                                        confirmar.close();
                                    }
                                });
                            },
                            buttons: {
                                close: {
                                    text: 'Aceptar'
                                }
                            }
                        });
                    },
                    keys: ['enter'],
                    btnClass: "btn-primary"
                },
                Cancelar: {
                    keys: ['esc']
                },
            }
        });
    });
});