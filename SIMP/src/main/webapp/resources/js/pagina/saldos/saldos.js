$(document).ready(function () {
    let $local = {
        $tablaMantenimiento: $("#tablaConsulta"),
        tablaMantenimiento: "",
        $tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
        $tipoDocumento: $("#tipoDocumento"),
        $btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
        $selectTipoDocumento: $("#selectTipoDocumento"),
        $txtNumDocumentoCliente: $("#txtNumDocumentoCliente"),
        $buscarCriterios: $("#buscarCriterios"),
        $criterios: $("#criterios"),

        $rangoFechasProceso: $('#fechaProcesoSaldos'),

        //Selects form criterios
        $selectInstitucionSaldos: $('#selectInstitucionSaldos'),
        $selectEmpresaSaldos: $('#selectEmpresaSaldos'),
        $selectClienteSaldos: $('#selectClienteSaldos'),
        $selectLogoSaldos: $('#selectLogoSaldos'),
        $selectProductoSaldos: $('#selectProductoSaldos'),
        $selectMonedaSaldos: $('#selectMonedaSaldos'),

        //Exportacion
        $exportarPorCriterios: $("#exportarPorCriterio"),
        $exportarPorTipoDocumento: $("#exportarPorTipoDocumento"),
    };

    $formBusquedaCriterios = $("#formBusquedaCriterios");
    $formBusquedaTipoDocumento = $("#formParamIniciales");

    $.fn.dataTable.ext.errMode = 'none';

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasProceso, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectTipoDocumento);
    $funcionUtil.crearSelect2($local.$selectInstitucionSaldos);
    $funcionUtil.crearSelect2($local.$selectEmpresaSaldos);
    $funcionUtil.crearSelect2Multiple($local.$selectClienteSaldos, "-1", "TODOS");
    $funcionUtil.crearSelect2($local.$selectLogoSaldos);
    $funcionUtil.crearSelect2Multiple($local.$selectProductoSaldos, "-1", "TODOS");
    $funcionUtil.crearSelect2($local.$selectMonedaSaldos);

    $local.$tablaMantenimiento.on('xhr.dt', function (e, settings, json, xhr) {
        switch (xhr.status) {
            case 500:
                $local.tablaMantenimiento.clear().draw();
                $funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
                break;
        }
    });

    $local.$selectEmpresaSaldos.on("change", () => {
        const opcionSeleccionada = $local.$selectEmpresaSaldos.val();
        if (!opcionSeleccionada) {
            $local.$selectClienteSaldos.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectClienteSaldos.find("option").remove();
                $local.$selectClienteSaldos.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectClienteSaldos.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$selectClienteSaldos.parent().find(".cargando").remove();
            }
        });
    });

    $local.$selectLogoSaldos.on("change", () => {
        const opcionSeleccionada = $local.$selectLogoSaldos.val();
        if (!opcionSeleccionada) {
            $local.$selectProductoSaldos.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}producto/logo/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectProductoSaldos.find("option").remove();
                $local.$selectProductoSaldos.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Productos...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectProductoSaldos.append($("<option/>").val(response[i].codigoProducto).text(response[i].codigoProducto + " - " + response[i].descProducto));
                });
            },
            complete: () => {
                $local.$selectProductoSaldos.parent().find(".cargando").remove();
            }
        });
    });

    $local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
        "language": {
            "emptyTable": "No se ha encontrado Saldos con los criterios definidos"
        },
        "initComplete": function () {
            $local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
            "className": "all filtrable",
        }, {
            "targets": 0,
            "className": "all filtrable dt-center",
        }, {
            "targets": 16,
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
                return $funcionUtil.unirCodigoDescripcion(row.idLogo, row.descLogoBin);
            },
            "title": "Logo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto);
            },
            "title": "Producto"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descripcionTipoDocumento);
            },
            "title": "Tipo Documento"
        }, {
            "data": 'numeroDocumento',
            "title": "N\u00famero Documento"
        }, {
            "data": 'idPersona',
            "title": "ID Persona"
        }, {
            "data": 'idCuenta',
            "title": "ID Cuenta"
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
            "data": 'numeroTarjeta',
            "title": "N\u00famero Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.estado, row.descEstado);
            },
            "title": "Estado Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.moneda, row.descMoneda);
            },
            "title": "Moneda"
        }, {
            "data": 'saldoDisponible',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Saldo Disponible"
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
            url: `${$variableUtil.root}saldos?accion=buscarPorTipoDocumento`,
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
            success: (saldos) => {
                if (saldos.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaMantenimiento.rows.add(saldos).draw();
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
        paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteSaldos, "clientes");
        paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectProductoSaldos, "productos");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}saldos?accion=buscarPorCriterios&${paramCriterioBusqueda}`,
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
            success: (saldos) => {
                if (saldos.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaMantenimiento.rows.add(saldos).draw();
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
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionSaldos.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaSaldos.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresaSaldos.find("option:selected").text();
        criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
        criterioBusqueda.descripcionCliente = !!$local.$selectClienteSaldos.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteSaldos, "; ") : "TODOS";
        criterioBusqueda.descripcionLogo = $local.$selectLogoSaldos.find("option:selected").val() === "-1" ? "" : $local.$selectLogoSaldos.find("option:selected").text();
        criterioBusqueda.descripcionProducto = !!$local.$selectProductoSaldos.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectProductoSaldos, "; ") : "TODOS";
        criterioBusqueda.descripcionMoneda = $local.$selectMonedaSaldos.find("option:selected").val() === "-1" ? "" : $local.$selectMonedaSaldos.find("option:selected").text();
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteSaldos, "clientes");
        paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectProductoSaldos, "productos");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}reporte/prepago/contabilidad/saldos?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterios&${paramCriterioBusqueda}`;
    });

    $local.$exportarPorTipoDocumento.on("click", function () {
        if (!$formBusquedaTipoDocumento.valid()) {
            return;
        }
        let criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find("option:selected").val() === "" ? "" : $local.$selectTipoDocumento.find("option:selected").text();
        const params = $.param(criterioBusqueda);
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}reporte/prepago/contabilidad/saldos?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorTipoDocumento&${params}`;
    });
});