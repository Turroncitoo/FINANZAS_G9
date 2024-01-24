$(document).ready(function () {
    let $local = {
        $tablaTransaccionAjustes: $("#tablaConsulta"),
        tablaTransaccionAjustes: "",
        $filaSeleccionada: "",
        $tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
        $tipoDocumento: $("#tipoDocumento"),
        $criterios: $("#criterios"),
        $btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
        $buscarCriterios: $("#buscarCriterios"),
        $modalDetalleConsulta: $("#modalDetalleConsulta"),

        $selectTipoDocumento: $("#selectTipoDocumento"),

        $rangoFechasProceso: $("#fechaProcesoTxnsAjustes"),

        //Exportacion
        $exportarPorTipoDocumento: $("#exportarPorTipoDocumento"),
        $exportarPorCriterios: $("#exportarPorCriterio"),

        //Selects form criterios
        $selectInstitucionTxnsAjustes: $("#selectInstitucionTxnsAjustes"),
        $selectEmpresaTxnsAjustes: $("#selectEmpresaTxnsAjustes"),
        $selectClienteTxnsAjustes: $("#selectClienteTxnsAjustes")
    };

    $formBusquedaCriterios = $("#formBusquedaCriterios");
    $formBusquedaTipoDocumento = $("#formParamIniciales");

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasProceso, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectTipoDocumento);
    $funcionUtil.crearSelect2($local.$selectInstitucionTxnsAjustes);
    $funcionUtil.crearSelect2($local.$selectEmpresaTxnsAjustes);
    $funcionUtil.crearSelect2Multiple($local.$selectClienteTxnsAjustes, "-1", "TODOS");

    $local.$selectEmpresaTxnsAjustes.on("change", () => {
        const opcionSeleccionada = $local.$selectEmpresaTxnsAjustes.val();
        if (!opcionSeleccionada) {
            $local.$selectClienteTxnsAjustes.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectClienteTxnsAjustes.find("option").remove();
                $local.$selectClienteTxnsAjustes.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectClienteTxnsAjustes.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$selectClienteTxnsAjustes.parent().find(".cargando").remove();
            }
        });
    });

    $.fn.dataTable.ext.errMode = 'none';

    $local.$tablaTransaccionAjustes.on('xhr.dt', function (e, settings, json, xhr) {
    });

    $local.tablaTransaccionAjustes = $local.$tablaTransaccionAjustes.DataTable({
        "language": {
            "emptyTable": "No se han encontrado Transacciones Ajuste con los criterios definidos."
        },
        "initComplete": function () {
            $local.$tablaTransaccionAjustes.wrap("<div class='table-responsive'></div>");
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTransaccionAjustes)
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 24, 25, 26, 27, 28, 29, 30, 31],
            "className": "all filtrable"
        }, {
            "targets": [0, 18],
            "className": "all filtrable dt-center"
        }, {
            "targets": [20, 21, 22, 23],
            "className": "all filtrable dt-right monto"
        }],
        "columns": [{
            "data": 'fechaProceso',
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
                return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descripcionRolTransaccion);
            },
            "title": "Rol Transacci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.membresia, row.descMembresia);
            },
            "title": "Membres\u00eda"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.claseServicio, row.descClaseServicio);
            },
            "title": "Servicio"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.origen, row.descOrigen);
            },
            "title": "Origen"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.claseTransaccion, row.descClaseTransaccion);
            },
            "title": "Clase Transacci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descCodigoTransaccion);
            },
            "title": "C\u00f3digo Transacci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.proceso, row.descProceso);
            },
            "title": "C\u00f3digo Proceso"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descCodigoRespuesta);
            },
            "title": "C\u00f3digo Respuesta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.canal, row.descCanal);
            },
            "title": "Canal"
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
            "data": 'secuenciaTransaccion',
            "title": "Secuencia"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoMovimiento, row.descTipoMovimiento);
            },
            "title": "Tipo Movimiento"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.horaTransaccion);
            },
            "title": "Fecha Transacci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaCompensacion, row.descMonedaCompensacion);
            },
            "title": "Moneda Compensaci\u00f3n"
        }, {
            "data": 'valorAutorizacion',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Autorizaci\u00f3n"
        }, {
            "data": 'valorCompensacion',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Compensaci\u00f3n"
        }, {
            "data": 'valorOIF',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe OIF"
        }, {
            "data": 'valorDiferencia',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Diferencia"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.registroContable, row.descRegistroContable);
            },
            "title": "Registro Contable"
        }, {
            "data": 'numeroVoucher',
            "title": "Trace"
        }, {
            "data": 'autorizacion',
            "title": "C\u00f3digo Autorizaci\u00f3n"
        }, {
            "data": 'nombreAfiliado',
            "title": "Nombre Afiliado"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.contabiliza, row.descContabiliza);
            },
            "title": "Ind. Contabilizaci\u00f3n"
        }, {
            "data": 'cuentaCargo',
            "title": "Cta. Cargo"
        }, {
            "data": 'cuentaAbono',
            "title": "Cta. Abono"
        }, {
            "data": 'codigoAnalitico',
            "title": "C\u00f3digo Anal\u00edtico"
        }],
        "createdRow": function (row) {
            $(row).find(".monto").filter(function () {
                const celda = $(this);
                const valor = parseFloat(celda.text());
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

    $local.$tablaTransaccionAjustes.find("thead").on('keyup', 'input.filtrable', function () {
        $local.tablaTransaccionAjustes.column($(this).parent().index() + ':visible').search(this.value).draw();
    });

    $local.$tipoBusqueda.on("change", function () {
        var tipoBusqueda = $(this).val();
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
            url: `${$variableUtil.root}txnsAjustes?accion=buscarPorTipoDocumento`,
            data: criterioBusqueda,
            statusCode: {
                400: function (response) {
                    $funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
                    $funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
                }
            },
            beforeSend: function () {
                $local.tablaTransaccionAjustes.clear().draw();
                $local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: function (transaccionAjustes) {
                if (transaccionAjustes.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaTransaccionAjustes.rows.add(transaccionAjustes).draw();
            },
            complete: function () {
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
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsAjustes, "clientes");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}txnsAjustes?accion=buscarPorCriterios&${paramCriterioBusqueda}`,
            contentType: "application/json",
            dataType: "json",
            statusCode: {
                400: function (response) {
                    $funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
                    $funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
                }
            },
            beforeSend: function () {
                $local.tablaTransaccionAjustes.clear().draw();
                $local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: function (transaccionAjustes) {
                if (transaccionAjustes.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaTransaccionAjustes.rows.add(transaccionAjustes).draw();
            },
            complete: function () {
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
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionTxnsAjustes.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaTxnsAjustes.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresaTxnsAjustes.find("option:selected").text();
        criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
        criterioBusqueda.descripcionCliente = !!$local.$selectClienteTxnsAjustes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteTxnsAjustes, "; ") : "TODOS";
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsAjustes, "clientes");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}reporte/movimientos/txnsAjustes?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterios&${paramCriterioBusqueda}`;
    });

    $local.$exportarPorTipoDocumento.on("click", function () {
        if (!$formBusquedaTipoDocumento.valid()) {
            return;
        }
        let criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find("option:selected").val() === "" ? "" : $local.$selectTipoDocumento.find("option:selected").text();
        const paramCriterioBusqueda = $.param(criterioBusqueda);
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}reporte/movimientos/txnsAjustes?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorTipoDocumento&${paramCriterioBusqueda}`;
    });
});