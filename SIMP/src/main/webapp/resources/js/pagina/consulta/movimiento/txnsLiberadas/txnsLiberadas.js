$(document).ready(() => {
    let $local = {
        $tablaMantenimiento: $("#tablaConsulta"),
        tablaMantenimiento: "",
        $filaSeleccionada: "",
        $tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
        $tipoDocumento: $("#tipoDocumento"),
        $btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
        $selectTipoDocumento: $("#selectTipoDocumento"),
        $txtNumDocumentoCliente: $("#txtNumDocumentoCliente"),

        //Selects form criterios
        $selectInstitucionTxnsLiberadas: $("#selectInstitucionTxnsLiberadas"),
        $selectEmpresaTxnsLiberadas: $("#selectEmpresaTxnsLiberadas"),
        $selectClienteTxnsLiberadas: $("#selectClienteTxnsLiberadas"),
        $selectCodigoProcesoTxnsLiberadas: $("#selectCodigoProcesoTxnsLiberadas"),
        $selectCanalTxnsLiberadas: $("#selectCanalTxnsLiberadas"),

        $buscarCriterios: $("#buscarCriterios"),
        $rangoFechasProceso: $("#fechaProcesoTxnsLiberadas"),

        $criterios: $("#criterios"),

        // Exportacion
        $exportarPorCriterios: $("#exportarPorCriterio"),
        $exportarPorTipoDocumento: $("#exportarPorTipoDocumento"),
    };

    $formBusquedaCriterios = $("#formBusquedaCriterios");
    $formBusquedaTipoDocumento = $("#formParamIniciales");

    $.fn.dataTable.ext.errMode = 'none';

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasProceso, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectTipoDocumento);
    $funcionUtil.crearSelect2($local.$selectInstitucionTxnsLiberadas);
    $funcionUtil.crearSelect2($local.$selectEmpresaTxnsLiberadas);
    $funcionUtil.crearSelect2Multiple($local.$selectClienteTxnsLiberadas, "-1", "TODOS");
    $funcionUtil.crearSelect2Multiple($local.$selectCodigoProcesoTxnsLiberadas, "-1", "TODOS");
    $funcionUtil.crearSelect2Multiple($local.$selectCanalTxnsLiberadas, "-1", "TODOS");

    $local.$selectEmpresaTxnsLiberadas.on("change", () => {
        const opcionSeleccionada = $local.$selectEmpresaTxnsLiberadas.val();
        if (!opcionSeleccionada) {
            $local.$selectClienteTxnsLiberadas.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectClienteTxnsLiberadas.find("option").remove();
                $local.$selectClienteTxnsLiberadas.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectClienteTxnsLiberadas.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$selectClienteTxnsLiberadas.parent().find(".cargando").remove();
            }
        });
    });

    $local.$tablaMantenimiento.on('xhr.dt', function (e, settings, json, xhr) {
        switch (xhr.status) {
            case 500:
                $local.tablaMantenimiento.clear().draw();
                break;
        }
    });

    $local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
        "language": {
            "emptyTable": "No se han encontrado Transacciones Liberadas con los criterios definidos."
        },
        "initComplete": function () {
            $local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 18, 19, 20, 21, 22, 23, 24, 27, 28],
            "className": "all filtrable",
        }, {
            "targets": [0, 15, 25, 26],
            "className": "all filtrable dt-center",
        }, {
            "targets": 17,
            "className": "all filtrable dt-right monto"
        }],
        "columns": [{
            "data": "fechaProceso",
            "title": "Fecha Proceso"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
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
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descRolTransaccion);
            },
            "title": "Rol Transacci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.origen, row.descripcionOrigen);
            },
            "title": "Origen"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descCanal);
            },
            "title": "Canal"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.proceso, row.descripcionProceso);
            },
            "title": "C\u00f3digo Proceso"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descCodigoRespuesta);
            },
            "title": "C\u00f3digo Respuesta"
        }, {
            "data": "tipoMensaje",
            "title": "Tipo Mensaje"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.motivoLiberacion, row.descMotivoLiberacion);
            },
            "title": "Motivo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
            },
            "title": "Tipo Documento"
        }, {
            "data": 'numeroDocumento',
            "title": "N\u00famero Documento"
        }, {
            "data": 'numeroTarjeta',
            "title": "N\u00famero Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.estadoTarjeta, row.descEstadoTarjeta);
            },
            "title": "Estado"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.horaTransaccion);
            },
            "title": "Fecha Transacci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaAutorizacion, row.descMonedaAutorizacion);
            },
            "title": "Moneda Autorizaci\u00f3n"
        }, {
            "data": 'valorAutorizacion',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Autorizaci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.modoEntradaPos, row.descModoEntradaPos);
            },
            "title": "Modo Entrada POS"
        }, {
            "data": 'trace',
            "title": "Trace"
        }, {
            "data": 'numeroReferencia',
            "title": "N\u00famero Referencia"
        }, {
            "data": 'nombreAfiliado',
            "title": "Nombre Afiliado"
        }, {
            "data": 'cuentaFrom',
            "title": "Cta. From"
        }, {
            "data": 'cuentaTo',
            "title": "Cta. To"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.conciliacionAutorizacion, row.descConciliacionAutorizacion);
            },
            "title": "Conciliaci\u00f3n Autorizaci\u00f3n"
        }, {
            "data": 'fechaCaptura',
            "title": "Fecha Captura"
        }, {
            "data": 'fechaCapturaSwitch',
            "title": "Fecha Captura Switch"
        }, {
            "data": 'numeroDocumentoLiberada',
            "title": "N\u00famero Documento"
        }, {
            "data": 'codigoAutorizacion',
            "title": "C\u00f3digo Autorizaci\u00f3n"
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

    $local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function () {
        $local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
    });

    $local.$tipoBusqueda.on("change", () => {
        const tipoBusqueda = $("input[type=radio][name=tipoBusqueda]:checked").val();
        switch (tipoBusqueda) {
            case "tipoDocumento":
                $local.$tipoDocumento.removeClass("hidden");
                $local.$criterios.addClass("hidden");
                break;
            case "criterios":
                $local.$tipoDocumento.addClass("hidden");
                $local.$criterios.removeClass("hidden");
                break;
            default:
                $funcionUtil.notificarException("Seleccione un Tipo de B\u00fasqueda v\u00e1lido", "fa-warning", "Aviso", "warning");
        }
    });

    $local.$btnBuscarPorDocumentoCliente.on("click", function () {
        if (!$formBusquedaTipoDocumento.valid()) {
            return;
        }
        const criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}txnsLiberadas?accion=buscarPorDocumento`,
            data: criterioBusqueda,
            statusCode: {
                400: (response) => {
                    $funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
                    $funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
                }
            },
            beforeSend: () => {
                $local.tablaMantenimiento.clear().draw();
                $local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: (transaccionLiberadas) => {
                if (transaccionLiberadas.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaMantenimiento.rows.add(transaccionLiberadas).draw();
            },
            complete: () => {
                $local.$btnBuscarPorDocumentoCliente.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$buscarCriterios.on("click", function () {
        if (!$formBusquedaCriterios.valid()) {
            return;
        }
        const rangoFechasProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasProceso);
        let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
        criterioBusqueda.fechaInicioProceso = new Date(rangoFechasProceso.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaFinProceso = new Date(rangoFechasProceso.fechaFin.replaceAll('-', '/'));
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsLiberadas, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCodigoProcesoTxnsLiberadas, "procesos");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCanalTxnsLiberadas, "canales");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}txnsLiberadas?accion=buscarPorFiltro&${paramCriterioBusqueda}`,
            contentType: "application/json",
            dataType: "json",
            statusCode: {
                400: (response) => {
                    $funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
                    $funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
                }
            },
            beforeSend: () => {
                $local.tablaMantenimiento.clear().draw();
                $local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: (transaccionLiberadas) => {
                if (transaccionLiberadas.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaMantenimiento.rows.add(transaccionLiberadas).draw();
            },
            complete: () => {
                $local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$exportarPorCriterios.on("click", function () {
        if (!$formBusquedaCriterios.valid()) {
            return;
        }
        const rangoFechasProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasProceso);
        let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
        criterioBusqueda.fechaInicioProceso = new Date(rangoFechasProceso.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaFinProceso = new Date(rangoFechasProceso.fechaFin.replaceAll('-', '/'));
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionTxnsLiberadas.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaTxnsLiberadas.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresaTxnsLiberadas.find("option:selected").text();
        criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
        criterioBusqueda.descripcionCliente = !!$local.$selectClienteTxnsLiberadas.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteTxnsLiberadas, "; ") : "TODOS";
        criterioBusqueda.descripcionCodigoProceso = !!$local.$selectCodigoProcesoTxnsLiberadas.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCodigoProcesoTxnsLiberadas, "; ") : "TODOS";
        criterioBusqueda.descripcionCanal = !!$local.$selectCanalTxnsLiberadas.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCanalTxnsLiberadas, "; ") : "TODOS";
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsLiberadas, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCodigoProcesoTxnsLiberadas, "procesos");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCanalTxnsLiberadas, "canales");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}prepago/consulta/txnsLiberadas?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterios&${paramCriterioBusqueda}`;
    });

    $local.$exportarPorTipoDocumento.on("click", function () {
        if (!$formBusquedaTipoDocumento.valid()) {
            return;
        }
        let criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find("option:selected").val() === "" ? "" : $local.$selectTipoDocumento.find("option:selected").text();
        const paramCriterioBusqueda = $.param(criterioBusqueda);
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}prepago/consulta/txnsLiberadas?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorTipoDocumento&${paramCriterioBusqueda}`;
    });
});