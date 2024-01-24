$(document).ready(function () {
    let $local = {
        $tablaMantenimiento: $("#tablaConsulta"),
        tablaMantenimiento: "",
        $filaSeleccionada: "",
        filtrosSeleccionables: [],
        $tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
        $tipoDocumento: $("#tipoDocumento"),
        $btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
        $selectTipoDocumento: $("#selectTipoDocumento"),
        $txtNumDocumentoCliente: $("#txtNumDocumentoCliente"),
        $buscarCriterios: $("#buscarCriterios"),

        $rangoFechasProceso: $('#fechaProcesoBloqueos'),
        $rangoFechasBloqueo: $('#fechaBloqueoBloqueos'),

        //Selects form criterios
        $selectInstitucionBloqueos: $("#selectInstitucionBloqueos"),
        $selectEmpresaBloqueos: $("#selectEmpresaBloqueos"),
        $selectClienteBloqueos: $("#selectClienteBloqueos"),

        $numeroTarjeta: $("#numeroTarjeta"),
        $criterios: $("#criterios"),

        // Exportacion
        $exportarPorCriterios: $("#exportarPorCriterio"),
        $exportarPorTipoDocumento: $("#exportarPorTipoDocumento"),
    };

    $formBusquedaCriterios = $("#formBusquedaCriterios");
    $formBusquedaTipoDocumento = $("#formParamIniciales");

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasProceso, "right");
    $funcionUtil.crearDateRangePickerSinValorPorDefecto($local.$rangoFechasBloqueo);

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectTipoDocumento);
    $funcionUtil.crearSelect2($local.$selectInstitucionBloqueos);
    $funcionUtil.crearSelect2($local.$selectEmpresaBloqueos);
    $funcionUtil.crearSelect2Multiple($local.$selectClienteBloqueos, "-1", "TODOS");

    $funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccion un Tipo de Documento");

    $.fn.dataTable.ext.errMode = 'none';

    $local.$selectEmpresaBloqueos.on("change", () => {
        const opcionSeleccionada = $local.$selectEmpresaBloqueos.val();
        if (!opcionSeleccionada) {
            $local.$selectClienteBloqueos.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectClienteBloqueos.find("option").remove();
                $local.$selectClienteBloqueos.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectClienteBloqueos.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$selectClienteBloqueos.parent().find(".cargando").remove();
            }
        });
    });

    $local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
        "language": {
            "emptyTable": "No hay Bloqueos registrados"
        },
        "initComplete": function () {
            $local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 5, 6, 7, 8, 9, 10],
            "className": "all filtrable",
        }, {
            "targets": 0,
            "className": "all filtrable dt-center",
        }, {
            "targets": 4,
            "className": "all filtrable dt-center",
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
                return $funcionUtil.unirCodigoDescripcion(row.fechaBloqueoCadena, row.horaBloqueo);
            },
            "title": "Fecha Bloqueo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descripcionTipoDocumento);
            },
            "title": "Tipo Documento"
        }, {
            "data": 'numeroDocumento',
            "title": "N\u00famero Documento"
        }, {
            "data": 'numeroTarjeta',
            "title": "N\u00famero Tarjeta"
        }, {
            "data": 'codigoBloqueo',
            "title": "C\u00f3digo Bloqueo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoBloqueo, row.descripcionTipoBloqueo);
            },
            "title": "Tipo Bloqueo"
        }, {
            "data": 'comentario',
            "title": "Comentario"
        }],
        "order": []
    });

    $local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function () {
        $local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
    });

    $local.$tipoBusqueda.on("change", function () {
        const tipoBusqueda = $(this).val();
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
            url: `${$variableUtil.root}bloqueo?accion=buscarPorDocumento`,
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
            success: (response) => {
                if (response.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaMantenimiento.rows.add(response).draw();
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
        const rangoFechasBloqueo = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasBloqueo);
        let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
        criterioBusqueda.fechaInicioProceso = new Date(rangoFechasProceso.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaFinProceso = new Date(rangoFechasProceso.fechaFin.replaceAll('-', '/'));
        if (rangoFechasBloqueo.fechaInicio !== "" && rangoFechasBloqueo.fechaFin !== "") {
            criterioBusqueda.fechaInicioBloqueo = new Date(rangoFechasBloqueo.fechaInicio.replaceAll('-', '/'));
            criterioBusqueda.fechaFinBloqueo = new Date(rangoFechasBloqueo.fechaFin.replaceAll('-', '/'));
        }
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteBloqueos, "clientes");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}bloqueo?accion=buscarPorFiltro&${paramCriterioBusqueda}`,
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
            success: (response) => {
                if (response.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaMantenimiento.rows.add(response).draw();
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
        const rangoFechasBloqueo = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasBloqueo);
        let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
        criterioBusqueda.fechaInicioProceso = new Date(rangoFechasProceso.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaFinProceso = new Date(rangoFechasProceso.fechaFin.replaceAll('-', '/'));
        if (rangoFechasBloqueo.fechaInicio !== "" && rangoFechasBloqueo.fechaFin !== "") {
            criterioBusqueda.fechaInicioBloqueo = new Date(rangoFechasBloqueo.fechaInicio.replaceAll('-', '/'));
            criterioBusqueda.fechaFinBloqueo = new Date(rangoFechasBloqueo.fechaFin.replaceAll('-', '/'));
        }
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionBloqueos.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaBloqueos.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresaBloqueos.find("option:selected").text();
        criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
        criterioBusqueda.descripcionRangoFechasBloqueo = $local.$rangoFechasBloqueo.val();
        criterioBusqueda.descripcionCliente = !!$local.$selectClienteBloqueos.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteBloqueos, "; ") : "TODOS";
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteBloqueos, "clientes");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}prepago/consulta/bloqueo?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterios&${paramCriterioBusqueda}`;
    });

    $local.$exportarPorTipoDocumento.on("click", function () {
        if (!$formBusquedaTipoDocumento.valid()) {
            return;
        }
        let criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find("option:selected").val() === "" ? "" : $local.$selectTipoDocumento.find("option:selected").text();
        const paramCriterioBusqueda = $.param(criterioBusqueda);
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}prepago/consulta/bloqueo?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorTipoDocumento&${paramCriterioBusqueda}`;
    });
});