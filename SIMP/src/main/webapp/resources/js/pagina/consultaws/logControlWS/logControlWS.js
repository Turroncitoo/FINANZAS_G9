$(document).ready(function () {
    let $local = {
        $buscar: $('#buscar'),
        $exportar: $('#exportar'),
        $tipoBusqueda: $("input[type='radio'][name='tipoBusqueda']"),
        $divHistorial: $('#divHistorial'),
        $divConsultaEnLinea: $('#divConsultaEnLinea'),

        $cargandoUltimaTxn: $('#cargandoUltimaTxn'),
        $spanUltimaTxn: $('#spanUltimaTxn'),

        $tablaEnLinea: $('#tablaEnLinea'),
        tablaEnLinea: '',

        $tablaHistorica: $('#tablaHistorica'),
        tablaHistorica: '',

        $rangoFechasTransaccion: $('#fechaTransaccion'),

        //Selects form criterios
        $selectInstitucion: $("#selectInstitucion"),
        $selectEmpresa: $("#selectEmpresa"),
        $selectCliente: $("#selectCliente"),
        $selectOperacion: $("#selectOperacion"),
        $selectExitoCliente: $("#selectExitoCliente"),
        $selectExitoUBA: $("#selectExitoUBA"),
        $selectExitoSIMP: $("#selectExitoSIMP"),

        $modalDatosExtra: $('#modalDatosExtra'),
        $filaSeleccionada: '',
        $txtJsonRequestClient: $('#txtJsonRequestClient'),
        $txtJsonResquestUBA: $('#txtJsonResquestUBA'),
        $txtJsonResponseUBA: $('#txtJsonResponseUBA'),
        $txtJsonResponseClient: $('#txtJsonResponseClient'),
        $txtRespuesta: $('#txtRespuesta'),
        $txtRespuestaSIMP: $('#txtRespuestaSIMP'),

        arregloSiNo: ["1", "0"],
        filtrosSeleccionables: {},
    }

    $formBusqueda = $("#formBusqueda");
    $formDatosExtra = $("#formDatosExtra");

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasTransaccion, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectInstitucion);
    $funcionUtil.crearSelect2($local.$selectEmpresa);
    $funcionUtil.crearSelect2($local.$selectExitoCliente);
    $funcionUtil.crearSelect2($local.$selectExitoUBA);
    $funcionUtil.crearSelect2($local.$selectExitoSIMP);
    $funcionUtil.crearSelect2Multiple($local.$selectCliente, "-1", "TODOS");
    $funcionUtil.crearSelect2Multiple($local.$selectOperacion, "-1", "TODOS");

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

    $local.$tipoBusqueda.on('change', function () {
        const val = $(this).val();
        switch (val) {
            case 'consultaEnLinea':
                $local.$divConsultaEnLinea.removeClass("hidden");
                $local.$divHistorial.addClass("hidden");
                break;
            case 'historial':
                $local.$divConsultaEnLinea.addClass("hidden");
                $local.$divHistorial.removeClass("hidden");
                break;
        }
    });

    $local.$modalDatosExtra.PopupWindow({
        title: "Detalle de mensajes en la transacci\u00F3n",
        autoOpen: false,
        modal: false,
        height: 500,
        width: 626,
    });

    $local.tablaEnLinea = $local.$tablaEnLinea.DataTable({
        "language": {
            "emptyTable": "No hay registros encontrados."
        },
        "initComplete": function () {
            $local.$tablaEnLinea.wrap("<div class='table-responsive'></div>");
            $local.filtrosSeleccionables["19"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["24"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["29"] = $local.arregloSiNo;
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaEnLinea, $local.filtrosSeleccionables);
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 20, 25, 30, 31, 32],
            "className": "all filtrable",
        }, {
            "targets": [0, 21, 22, 26, 27],
            "className": "all filtrable dt-center",
        }, {
            "targets": 12,
            "className": "all filtrable dt-right monto",
        }, {
            "targets": [23, 28, 33],
            "className": "all filtrable dt-right",
        }, {
            "targets": 19,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.exitoCliente);
            }
        }, {
            "targets": 24,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.exitoUBA);
            }
        }, {
            "targets": 29,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.exitoSIMP);
            }
        }, {
            "targets": 34,
            "className": "all dt-center",
            "width": "10%",
            "render": function () {
                return $variableUtil.botonVerJSONWS;
            }
        }],
        "columns": [{
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.hora);
            },
            "title": "Fecha Transacci\u00f3n"
        }, {
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
            "data": "secuencia",
            "title": "Secuencia"
        }, {
            "data": "transaccion",
            "title": "ID Transacci\u00f3n"
        }, {
            "data": "codigoSeguimiento",
            "title": "C\u00f3digo Seguimiento"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descripcionTipoDocumento);
            },
            "title": "Tipo Documento"
        }, {
            "data": "numeroDocumento",
            "title": "N\u00famero Documento"
        }, {
            "data": "nombreCliente",
            "title": "Nombre Cliente"
        }, {
            "data": "numeroTarjeta",
            "title": "N\u00famero Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaTransaccion, row.descripcionMonedaTransaccion);
            },
            "title": "Moneda Transacci\u00f3n"
        }, {
            "data": "montoTransaccion",
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Transacci\u00f3n"
        }, {
            "data": "idCanal",
            "title": "Canal"
        }, {
            "data": "usuario",
            "title": "Usuario"
        }, {
            "data": "operacion",
            "title": "Operaci\u00f3n"
        }, {
            "data": "numeroTrace",
            "title": "Trace"
        }, {
            "data": "direccionIP",
            "title": "Direcci\u00f3n IP"
        }, {
            "data": "tiempoUTC",
            "title": "Tipo UTC"
        }, {
            "data": "exitoCliente",
            "title": "\u00C9xito Cliente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaCliente, row.descripcionRespuestaCliente);
            },
            "title": "Respuesta Cliente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaRequestClientCadena, row.horaRequestClient);
            },
            "title": "Fecha Solicitud Cliente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaResponseClientCadena, row.horaResponseClient);
            },
            "title": "Fecha Respuesta Cliente"
        }, {
            "data": "tiempoSIMPHub",
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Tiempo SIMPhub (ms)"
        }, {
            "data": "exitoUBA",
            "title": "\u00C9xito UBA"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaUBA, row.descripcionRespuestaUBA);
            },
            "title": "Respuesta UBA"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaRequestUBACadena, row.horaRequestUBA);
            },
            "title": "Fecha Solicitud UBA"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaResponseUBACadena, row.horaResponseUBA);
            },
            "title": "Fecha Respuesta UBA"
        }, {
            "data": "tiempoRespuestaUBA",
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Tiempo UBA (ms)"
        }, {
            "data": "exitoSIMP",
            "title": "\u00C9xito SIMP"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaSP, row.descripcionRespuestaSP);
            },
            "title": "Respuesta SIMP"
        }, {
            "data": "nombreSP",
            "title": "Nombre SP"
        }, {
            "data": "lineaErrorSP",
            "title": "L\u00ednea Error"
        }, {
            "data": "tiempoTotal",
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Tiempo Total (ms)"
        }, {
            "data": null,
            "title": 'Acci\u00F3n'
        }],
        "createdRow": function (row) {
            $(row).find(".monto").filter(function () {
                let celda = $(this);
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

    $local.$tablaEnLinea.find("thead").on("keyup", "input.filtrable", function () {
        $local.tablaEnLinea.column($(this).parent().index() + ":visible").search(this.value).draw();
    });

    $local.$tablaEnLinea.find("thead").on('change', 'select', function () {
        var val = $.fn.dataTable.util.escapeRegex($(this).val());
        $local.tablaEnLinea.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
    });

    $local.tablaHistorica = $local.$tablaHistorica.DataTable({
        "language": {
            "emptyTable": "No hay registros encontrados."
        },
        "initComplete": function () {
            $local.$tablaHistorica.wrap("<div class='table-responsive'></div>");
            $local.filtrosSeleccionables["19"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["24"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["29"] = $local.arregloSiNo;
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaHistorica, $local.filtrosSeleccionables);
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 20, 25, 30, 31, 32],
            "className": "all filtrable",
        }, {
            "targets": [0, 21, 22, 26, 27],
            "className": "all filtrable dt-center",
        }, {
            "targets": 12,
            "className": "all filtrable dt-right monto",
        }, {
            "targets": [23, 28, 33],
            "className": "all filtrable dt-right",
        }, {
            "targets": 19,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.exitoCliente);
            }
        }, {
            "targets": 24,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.exitoUBA);
            }
        }, {
            "targets": 29,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.exitoSIMP);
            }
        }, {
            "targets": 34,
            "className": "all dt-center",
            "width": "10%",
            "render": function () {
                return $variableUtil.botonVerJSONWS;
            }
        }],
        "columns": [{
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.hora);
            },
            "title": "Fecha Transacci\u00f3n"
        }, {
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
            "data": "secuencia",
            "title": "Secuencia"
        }, {
            "data": "transaccion",
            "title": "ID Transacci\u00f3n"
        }, {
            "data": "codigoSeguimiento",
            "title": "C\u00f3digo Seguimiento"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descripcionTipoDocumento);
            },
            "title": "Tipo Documento"
        }, {
            "data": "numeroDocumento",
            "title": "N\u00famero Documento"
        }, {
            "data": "nombreCliente",
            "title": "Nombre Cliente"
        }, {
            "data": "numeroTarjeta",
            "title": "N\u00famero Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaTransaccion, row.descripcionMonedaTransaccion);
            },
            "title": "Moneda Transacci\u00f3n"
        }, {
            "data": "montoTransaccion",
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Transacci\u00f3n"
        }, {
            "data": "idCanal",
            "title": "Canal"
        }, {
            "data": "usuario",
            "title": "Usuario"
        }, {
            "data": "operacion",
            "title": "Operaci\u00f3n"
        }, {
            "data": "numeroTrace",
            "title": "Trace"
        }, {
            "data": "direccionIP",
            "title": "Direcci\u00f3n IP"
        }, {
            "data": "tiempoUTC",
            "title": "Tipo UTC"
        }, {
            "data": "exitoCliente",
            "title": "\u00C9xito Cliente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaCliente, row.descripcionRespuestaCliente);
            },
            "title": "Respuesta Cliente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaRequestClientCadena, row.horaRequestClient);
            },
            "title": "Fecha Solicitud Cliente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaResponseClientCadena, row.horaResponseClient);
            },
            "title": "Fecha Respuesta Cliente"
        }, {
            "data": "tiempoSIMPHub",
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Tiempo SIMPhub (ms)"
        }, {
            "data": "exitoUBA",
            "title": "\u00C9xito UBA"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaUBA, row.descripcionRespuestaUBA);
            },
            "title": "Respuesta UBA"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaRequestUBACadena, row.horaRequestUBA);
            },
            "title": "Fecha Solicitud UBA"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaResponseUBACadena, row.horaResponseUBA);
            },
            "title": "Fecha Respuesta UBA"
        }, {
            "data": "tiempoRespuestaUBA",
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Tiempo UBA (ms)"
        }, {
            "data": "exitoSIMP",
            "title": "\u00C9xito SIMP"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaSP, row.descripcionRespuestaSP);
            },
            "title": "Respuesta SIMP"
        }, {
            "data": "nombreSP",
            "title": "Nombre SP"
        }, {
            "data": "lineaErrorSP",
            "title": "L\u00ednea Error"
        }, {
            "data": "tiempoTotal",
            "render": $tablaFuncion.formatoMonto(0),
            "title": "Tiempo Total (ms)"
        }, {
            "data": null,
            "title": 'Acci\u00F3n'
        }],
        "createdRow": function (row) {
            $(row).find(".monto").filter(function () {
                let celda = $(this);
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

    $local.$tablaHistorica.find("thead").on("keyup", "input.filtrable", function () {
        $local.tablaHistorica.column($(this).parent().index() + ":visible").search(this.value).draw();
    });

    $local.$tablaHistorica.find("thead").on('change', 'select', function () {
        var val = $.fn.dataTable.util.escapeRegex($(this).val());
        $local.tablaHistorica.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
    });

    obtenerTransaccionesDeHoy(null);

    function obtenerTransaccionesDeHoy(ultimaSecuencia) {
        if ($local.error) {
            return;
        }
        let criterio = {};
        if (ultimaSecuencia) {
            criterio.numeroSecuencia = ultimaSecuencia;
            criterio.modo = 1;
        }
        $.ajax({
            type: "GET",
            url: $variableUtil.root + "prepago/consulta/logControl?accion=buscarEnLinea",
            data: criterio,
            beforeSend: function () {
                $local.$cargandoUltimaTxn.addClass("fa-spinner fa-pulse fa-fw");
                $local.$spanUltimaTxn.text('Buscando transacciones...');
            },
            success: function (response) {
                $local.$spanUltimaTxn.text('\u00DAltima actualizaci\u00F3n: ' + moment().format('DD/MM/YYYY, h:mm:ss a'));
                if (response.length === 0) {
                    return;
                }
                $local.tablaEnLinea.rows.add(response).draw();
                $local.ultimaSecuencia = response[response.length - 1].secuencia;
            },
            error: function () {
                $local.$spanUltimaTxn.text('Ocurri\u00f3 un error al realizar la consulta en l\u00EDnea');
                $local.error = true;
            },
            complete: function () {
                $local.$cargandoUltimaTxn.removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    }

    setInterval(function () {
        obtenerTransaccionesDeHoy($local.ultimaSecuencia, $local.error);
    }, 5000);

    $local.$buscar.on('click', function () {
        if (!$formBusqueda.valid()) {
            return;
        }
        const rangoFechas = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
        let criterioBusqueda = $formBusqueda.serializeJSON();
        criterioBusqueda.fechaTransaccionInicio = new Date(rangoFechas.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaTransaccionFin = new Date(rangoFechas.fechaFin.replaceAll('-', '/'));
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCliente, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectOperacion, "operaciones");
        $.ajax({
            type: "GET",
            url: $variableUtil.root + "prepago/consulta/logControl?accion=buscarHistorial&" + paramCriterioBusqueda,
            contentType: "application/json",
            dataType: "json",
            beforeSend: function () {
                $local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
                $local.tablaHistorica.clear().draw();
            },
            success: function (response) {
                if (response.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaHistorica.rows.add(response).draw();
            },
            error: function (response) {
            },
            complete: function () {
                $local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$exportar.on('click', function () {
        if (!$formBusqueda.valid()) {
            return;
        }
        const rangoFechas = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
        let criterioBusqueda = $formBusqueda.serializeJSON();
        criterioBusqueda.fechaTransaccionInicio = new Date(rangoFechas.fechaInicio.replaceAll('-', '/'));
        criterioBusqueda.fechaTransaccionFin = new Date(rangoFechas.fechaFin.replaceAll('-', '/'));
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucion.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresa.find("option:selected").val() === "-1" ? "TODOS" : $local.$selectEmpresa.find("option:selected").text();
        criterioBusqueda.descripcionRangoFechas = $local.$rangoFechasTransaccion.val();
        criterioBusqueda.descripcionCliente = !!$local.$selectCliente.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCliente, "; ") : "TODOS";
        criterioBusqueda.descripcionOperacion = !!$local.$selectOperacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectOperacion, "; ") : "TODOS";
        criterioBusqueda.descripcionExitoCliente = $local.$selectExitoCliente.find("option:selected").text();
        criterioBusqueda.descripcionExitoUBA = $local.$selectExitoUBA.find("option:selected").text();
        criterioBusqueda.descripcionExitoSIMP = $local.$selectExitoSIMP.find("option:selected").text();
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCliente, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectOperacion, "operaciones");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}prepago/consulta/logControl?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
    });

    $local.$tablaEnLinea.children("tbody").on("click", ".ver-detalle", function () {
        $local.$filaSeleccionada = $(this).parents("tr");
        const datosLog = $local.tablaEnLinea.row($local.$filaSeleccionada).data();
        $funcionUtil.llenarFormulario(datosLog, $formDatosExtra);
        $local.$txtJsonRequestClient.val(datosLog.jsonRequestClient);
        $local.$txtJsonResquestUBA.val(datosLog.jsonResquestUBA);
        $local.$txtJsonResponseUBA.val(datosLog.jsonResponseUBA);
        $local.$txtJsonResponseUBA.val(datosLog.jsonResponseUBA);
        $local.$txtJsonResponseClient.val(datosLog.jsonResponseClient);
        $local.$txtRespuesta.val($funcionUtil.unirCodigoDescripcion(datosLog.codigoRespuestaCliente, datosLog.descripcionRespuestaCliente));
        $local.$txtRespuestaSIMP.val($funcionUtil.unirCodigoDescripcion(datosLog.codigoRespuestaSP, datosLog.descripcionRespuestaSP));
        $local.$modalDatosExtra.PopupWindow("open");
    });

    $local.$tablaHistorica.children("tbody").on("click", ".ver-detalle", function () {
        $local.$filaSeleccionada = $(this).parents("tr");
        const datosLog = $local.tablaHistorica.row($local.$filaSeleccionada).data();
        $funcionUtil.llenarFormulario(datosLog, $formDatosExtra);
        $local.$txtJsonRequestClient.val(datosLog.jsonRequestClient);
        $local.$txtJsonResquestUBA.val(datosLog.jsonResquestUBA);
        $local.$txtJsonResponseUBA.val(datosLog.jsonResponseUBA);
        $local.$txtJsonResponseUBA.val(datosLog.jsonResponseUBA);
        $local.$txtJsonResponseClient.val(datosLog.jsonResponseClient);
        $local.$txtRespuesta.val($funcionUtil.unirCodigoDescripcion(datosLog.codigoRespuestaCliente, datosLog.descripcionRespuestaCliente));
        $local.$txtRespuestaSIMP.val($funcionUtil.unirCodigoDescripcion(datosLog.codigoRespuestaSP, datosLog.descripcionRespuestaSP));
        $local.$modalDatosExtra.PopupWindow("open");
    });
});
