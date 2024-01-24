$(document).ready(function () {

    var $local = {
        $tablaTransaccionObservadas: $("#tablaConsulta"),
        tablaTransaccionObservadas: "",
        $filaSeleccionada: "",
        filtrosSeleccionables: {},
        $tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
        $tipoDocumento: $("#tipoDocumento"),
        $criterios: $("#criterios"),
        $btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
        $buscarCriterios: $("#buscarCriterios"),

        $rangoFechasProceso: $("#fechaProcesoTxnsObservadas"),

        $modalTxnsObservadasTrace: $("#modal-esquemaAjuste"),
        $btnActualizarTrace: $("#btnActualizarTrace"),
        $btnActualizarExtDev: $("#btnActualizarExtDev"),

        $modalTxnsObservadasExtDev: $("#modal-indExtDev"),
        $btnActualizarExtDev: $("#btnActualizarIndExtDev"),
        $indicadorDevolucionCampo: $("#indicadorDevolucionCampo"),
        $indicadorExtornoCampo: $("#indicadorExtornoCampo"),

        $modalDetalleConsulta: $("#modalDetalleConsulta"),
        $selectTipoDocumento: $("#selectTipoDocumento"),

        accionExtornoDevolucion: "",
        registroTxnsObservadaSeleccionado: "",
        $fechaActualizacion: $("#fechaActualizacion"),

        procesaObservadasManual: -1,
        $exportarPorCriterios: $("#exportarPorCriterio"),
        $exportarPorTipoDocumento: $("#exportarPorTipoDocumento"),

        // Permiso
        permisoActualizacionTrace: false,
        permisoActualizacionExtornoDevolucion: false,

        tnxObservadaSeleccionada: {},

        arregloSiNo: [1, 0],

        //Selects form criterios
        $selectInstitucionTxnsObservadas: $('#selectInstitucionTxnsObservadas'),
        $selectEmpresaTxnsObservadas: $('#selectEmpresaTxnsObservadas'),
        $selectClienteTxnsObservadas: $('#selectClienteTxnsObservadas'),
        $selectIndicadorDevolucionTxnsObservadas: $('#selectIndicadorDevolucionTxnsObservadas'),
        $selectIndicadorExtornoTxnsObservadas: $('#selectIndicadorExtornoTxnsObservadas'),
        $selectIndicadorConciliacionTxnsObservadas: $('#selectIndicadorConciliacionTxnsObservadas'),

        $arregloDevolucion: {},
    };

    $formBusquedaCriterios = $("#formBusquedaCriterios");
    $formBusquedaTipoDocumento = $("#formParamIniciales");

    $funcionUtil.crearDatePickerSimple($local.$fechaActualizacion);

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasProceso, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectTipoDocumento);
    $funcionUtil.crearSelect2($local.$selectInstitucionTxnsObservadas);
    $funcionUtil.crearSelect2($local.$selectEmpresaTxnsObservadas);
    $funcionUtil.crearSelect2Multiple($local.$selectClienteTxnsObservadas, "-1", "TODOS");
    $funcionUtil.crearSelect2Multiple($local.$selectIndicadorDevolucionTxnsObservadas, "-1", "TODOS");
    $funcionUtil.crearSelect2Multiple($local.$selectIndicadorExtornoTxnsObservadas, "-1", "TODOS");
    $funcionUtil.crearSelect2Multiple($local.$selectIndicadorConciliacionTxnsObservadas, "-1", "TODOS");

    $.when($funcionUtil.obtenerProcesaObservadasManual()).done(function (procesaObservadasManual) {
        $local.procesaObservadasManual = procesaObservadasManual;
    });

    $formTxnsTraceObservadas = $("#formTxnsTraceObservadas");
    $formTxnsObservadasExtDev = $("#formTxnsObservadasExtDev");

    $local.$modalTxnsObservadasTrace.PopupWindow({
        title: "Esquema de Ajuste",
        autoOpen: false,
        modal: false,
        height: 230,
        width: 450,
    });

    $local.$modalTxnsObservadasExtDev.PopupWindow({
        title: "Ajuste de Extorno / Devoluci\u00f3n",
        autoOpen: false,
        modal: false,
        height: 378,
        width: 590,
    });

    $local.$modalTxnsObservadasExtDev.on("close.popupwindow", function () {
        $local.accionExtornoDevolucion = "";
        $local.$filaSeleccionada = "";
    });

    $local.$modalDetalleConsulta.PopupWindow({
        title: "Detalle de Transacciones Observadas",
        autoOpen: false,
        modal: false,
        height: 400,
        width: 600,
    });

    $.fn.dataTable.ext.errMode = 'none';

    $local.$tablaTransaccionObservadas.on('xhr.dt', function (e, settings, json, xhr) {
    });

    $local.$selectEmpresaTxnsObservadas.on("change", () => {
        const opcionSeleccionada = $local.$selectEmpresaTxnsObservadas.val();
        if (!opcionSeleccionada) {
            $local.$selectClienteTxnsObservadas.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectClienteTxnsObservadas.find("option").remove();
                $local.$selectClienteTxnsObservadas.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectClienteTxnsObservadas.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$selectClienteTxnsObservadas.parent().find(".cargando").remove();
            }
        });
    });

    $local.permisoActualizacionTrace = $local.$tablaTransaccionObservadas.attr("data-permiso-actualizacion-trace") || false;
    $local.permisoActualizacionExtornoDevolucion = $local.$tablaTransaccionObservadas.attr("data-permiso-actualizacion-extdev") || false;

    $local.tablaTransaccionObservadas = $local.$tablaTransaccionObservadas.DataTable({
        "language": {
            "emptyTable": "No se han encontrado Transacciones Observadas con los criterios definidos."
        },
        "initComplete": function () {
            $local.$tablaTransaccionObservadas.wrap("<div class='table-responsive'></div>");
            $local.filtrosSeleccionables["23"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["28"] = $local.arregloSiNo;
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTransaccionObservadas, $local.filtrosSeleccionables);
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 22, 24, 25, 26, 30, 33, 34, 35, 38],
            "className": "all filtrable"
        }, {
            "targets": [0, 20, 21, 32, 36, 37],
            "className": "all filtrable dt-center",
        }, {
            "targets": [19, 27, 31],
            "className": "all filtrable dt-right monto"
        }, {
            "targets": 23,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaNoSi(row.sinAutorizacion);
            }
        }, {
            "targets": 28,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaNoSi(row.pendienteCompensar);
            }
        }, {
            "targets": 29,
            "className": "all filtrable dt-right"
        }, {
            "targets": 39,
            "className": "all dt-center",
            "render": function (data, type, row) {
                let botones = "";
                if ($local.permisoActualizacionTrace == "true") {
                    if (row.idIndicadorConciliacion == 3 && (row.numeroTrace == '' || row.numeroTrace == null)) {
                        botones += "" + $variableUtil.botonActualizarTrace;
                    }
                }
                if ($local.permisoActualizacionExtornoDevolucion == "true" && $local.procesaObservadasManual == 1) {
                    if (row.idIndicadorExtorno == 1 || row.idIndicadorDevolucion == 1) {
                        botones += "" + $variableUtil.botonActualizarExtDev;
                    }
                }
                if (botones == "" && ($local.permisoActualizacionTrace == "true" || $local.permisoActualizacionExtornoDevolucion == "true")) {
                    botones = "";
                }
                return botones;
            }
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
                return $funcionUtil.unirCodigoDescripcion(row.idRolTransaccion, row.descripcionRolTransaccion);
            },
            "title": "Rol Transacci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idOrigenArchivo, row.descripcionOrigenArchivo);
            },
            "title": "Origen Archivo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoProcesoSwitch, row.descripcionProcesoSwitch);
            },
            "title": "C\u00f3digo Proceso"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaSwitch, row.descripcionCodigoRespuestaSwitch);
            },
            "title": "C\u00f3digo Respuesta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descripcionCanal);
            },
            "title": "Canal"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoTipoDocumento, row.descripcionTipoDocumento);
            },
            "title": "Tipo Documento"
        }, {
            "data": "numeroDocumento",
            "title": "N\u00famero Documento"
        }, {
            "data": "idPersona",
            "title": "ID Persona"
        }, {
            "data": "nombres",
            "title": "Nombres"
        }, {
            "data": "apellidoPaterno",
            "title": "Apellido Paterno"
        }, {
            "data": "apellidoMaterno",
            "title": "Apellido Materno"
        }, {
            "data": "numeroCuenta",
            "title": "N\u00famero Cuenta"
        }, {
            "data": "numeroTarjeta",
            "title": "N\u00famero Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.estado, row.descEstado);
            },
            "title": "Estado"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaCargoThb, row.descMonedaCargoThb);
            },
            "title": "Moneda Cargo"
        }, {
            "data": "montoCargoThb",
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Cargo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.horaTransaccion);
            },
            "title": "Fecha Transacci\u00f3n"
        }, {
            "data": "fechaSwitch",
            "title": "Fecha Switch"
        }, {
            "data": "numeroTrace",
            "title": "Trace"
        }, {
            "data": "sinAutorizacion",
            "title": "Sin Autorizaci\u00f3n"
        }, {
            "data": "autorizacion",
            "title": "C\u00f3digo Autorizaci\u00f3n"
        }, {
            "data": "nombreAdquirente",
            "title": "Nombre Adquirente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoMonedaTransaccion, row.descripcionMonedaTransaccion);
            },
            "title": "Moneda Autorizaci\u00f3n"
        }, {
            "data": "valorTransaccion",
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Autorizaci\u00f3n"
        }, {
            "data": "pendienteCompensar",
            "title": "Pendiente Compensar"
        }, {
            "data": "diasPendiente",
            "render": $tablaFuncion.formatoMonto(0),
            "title": "D\u00edas Pendiente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.txnCurrencyComp, row.descTxnCurrencyComp);
            },
            "title": "Moneda Compensaci\u00f3n"
        }, {
            "data": "txnAmountComp",
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe Compensaci\u00f3n"
        }, {
            "data": "fechaCompensacion",
            "title": "Fecha Compensaci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idIndicadorDevolucion, row.descripcionIndicadorDevolucion);
            },
            "title": "Indicador Devoluci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idIndicadorExtorno, row.descripcionIndicadorExtorno);
            },
            "title": "Indicador Extorno"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idIndicadorConciliacion, row.descripcionIndicadorConciliacion);
            },
            "title": "Indicador Conciliaci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaRegistroCadena, row.horaRegistro);
            },
            "title": "Fecha Registro"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaRegulCadena, row.horaRegul);
            },
            "title": "Fecha Regularizaci\u00f3n"
        }, {
            "data": "idSecuencia",
            "title": "Secuencia"
        }, {
            "data": null,
            "title": "Acci\u00f3n"
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

    $local.$tablaTransaccionObservadas.children("tbody").on("click", ".actualizar", function () {
        $local.$modalTxnsObservadasTrace.PopupWindow("open");
        $local.$modalTxnsObservadasTrace.find("input").focus();
        $local.$modalTxnsObservadasTrace.find("input").val('');
        $local.$filaSeleccionada = $(this).parents("tr");
        $local.tnxObservadaSeleccionada = $local.tablaTransaccionObservadas.row($local.$filaSeleccionada).data();
    });

    $local.$btnActualizarTrace.on("click", function () {
        if (!$formTxnsTraceObservadas.valid()) {
			return;
		}
        var traceObservada = $formTxnsTraceObservadas.serializeJSON();
        traceObservada.trace = traceObservada.numeroTrace;
        traceObservada.idSecuencia = $local.tnxObservadaSeleccionada.idSecuencia;
        traceObservada.idInstitucion = $local.tnxObservadaSeleccionada.codigoInstitucion;
        $.ajax({
            type: "PUT",
            url: $variableUtil.root + "txnsObservadas?accion=actualizarTrace",
            data: JSON.stringify(traceObservada),
            beforeSend: function (xhr) {
                $local.$btnActualizarTrace.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
            },
            statusCode: {
                400: function (response) {
                    $funcionUtil.limpiarMensajesDeError($formTxnsTraceObservadas);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formTxnsTraceObservadas);
                }
            },
            success: function (trace) {
                $funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
                $local.tablaTransaccionObservadas.row($local.$filaSeleccionada).remove().draw(false);
                $local.tnxObservadaSeleccionada.numeroTrace = traceObservada.trace;
				$local.tnxObservadaSeleccionada.idSecuencia = traceObservada.idSecuencia;
                var row = $local.tablaTransaccionObservadas.row.add($local.tnxObservadaSeleccionada).draw();
                if (row != null) {
                    row.show().draw(false);
                    $(row.node()).animateHighlight();
                }
                $funcionUtil.prepararFormularioRegistro($formTxnsTraceObservadas)
                $local.$modalTxnsObservadasTrace.PopupWindow("close");
            },
            error: function (response) {
                $local.$btnActualizarTrace.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
                $local.$modalTxnsObservadasTrace.PopupWindow("close");
            },
            complete: function (response) {
                $local.$modalTxnsObservadasTrace.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$tablaTransaccionObservadas.children("tbody").on("click", ".actualizar-indExtDev", function () {
        if ($local.procesaObservadasManual != 1) {
            return;
        }
        $local.$filaSeleccionada = $(this).parents("tr");
        $local.registroTxnsObservadaSeleccionado = $local.tablaTransaccionObservadas.row($local.$filaSeleccionada).data();
        $local.$indicadorDevolucionCampo.addClass("hidden");
        $local.$indicadorExtornoCampo.addClass("hidden");
        if ($local.registroTxnsObservadaSeleccionado.idIndicadorDevolucion == 1) {
            $local.$indicadorDevolucionCampo.removeClass("hidden");
            $local.accionExtornoDevolucion = "actualizarIndicadorDevolucion";
            $local.$arregloDevolucion = {
				'2': 'DEVUELTO'
			}
        } else if ($local.registroTxnsObservadaSeleccionado.idIndicadorExtorno == 1) {
            $local.$indicadorExtornoCampo.removeClass("hidden");
            $local.$arregloDevolucion = {
				'2': 'EXTORNADO',
				'3': 'NO REQUIERE EXTORNO'
			}
            $local.accionExtornoDevolucion = "actualizarIndicadorExtorno";
        }
        $funcionUtil.prepararFormularioRegistro($formTxnsObservadasExtDev);
        $local.$modalTxnsObservadasExtDev.PopupWindow("open");
    });

    $local.$btnActualizarExtDev.on("click", function () {
        if ($local.procesaObservadasManual != 1) {
            return;
        }
        if (!$formTxnsObservadasExtDev.valid()) {
            return;
        }
        let currentDate = new Date();
        const currenTime = String(currentDate).split(' ')[4]
        var actualizacionExtornoDevolucion = $formTxnsObservadasExtDev.serializeJSON();
        actualizacionExtornoDevolucion.numeroTarjeta = $local.registroTxnsObservadaSeleccionado.numeroTarjeta;
        actualizacionExtornoDevolucion.numeroTrace = $local.registroTxnsObservadaSeleccionado.numeroTrace;
        actualizacionExtornoDevolucion.fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato($local.registroTxnsObservadaSeleccionado.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
        actualizacionExtornoDevolucion.horaActualizacion = currenTime
        actualizacionExtornoDevolucion.horaTransaccion = $local.registroTxnsObservadaSeleccionado.horaTransaccion;
        actualizacionExtornoDevolucion.fechaActualizacion = $funcionUtil.obtenerFechaDatePicker($local.$fechaActualizacion);
        actualizacionExtornoDevolucion.idInstitucion = $local.registroTxnsObservadaSeleccionado.idInstitucion;
        actualizacionExtornoDevolucion.idSecuencia = $local.registroTxnsObservadaSeleccionado.idSecuencia;
        actualizacionExtornoDevolucion.idIndicadorDevolucion = actualizacionExtornoDevolucion.idIndicadorDevolucion == '' ? 0 :
            actualizacionExtornoDevolucion.idIndicadorDevolucion;
        $.ajax({
            type: "PUT",
            url: $variableUtil.root + "txnsObservadas?accion=" + $local.accionExtornoDevolucion,
            data: JSON.stringify(actualizacionExtornoDevolucion),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
                $local.$btnActualizarExtDev.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
            },
            statusCode: {
                400: function (response) {
                    $funcionUtil.limpiarMensajesDeError($formTxnsObservadasExtDev);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formTxnsObservadasExtDev);
                }
            },
            success: function (response) {
                $funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
                $local.tablaTransaccionObservadas.row($local.$filaSeleccionada).remove().draw(false);
                $local.registroTxnsObservadaSeleccionado.horaRegul = currenTime;
				if ($local.accionExtornoDevolucion === 'actualizarIndicadorDevolucion') {
					$local.registroTxnsObservadaSeleccionado.idIndicadorDevolucion = actualizacionExtornoDevolucion.idIndicadorDevolucion;
					$local.registroTxnsObservadaSeleccionado.descripcionIndicadorDevolucion = $local.$arregloDevolucion[actualizacionExtornoDevolucion.idIndicadorDevolucion] || 'NO APLICA';
				} else {
					$local.registroTxnsObservadaSeleccionado.idIndicadorExtorno = actualizacionExtornoDevolucion.idIndicadorExtorno;
					$local.registroTxnsObservadaSeleccionado.descripcionIndicadorExtorno = $local.$arregloDevolucion[actualizacionExtornoDevolucion.idIndicadorExtorno] || 'NO APLICA';
				}

				let row = $local.tablaTransaccionObservadas.row.add($local.registroTxnsObservadaSeleccionado).draw();
				if (row != null) {
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
				$funcionUtil.prepararFormularioRegistro($formTxnsObservadasExtDev)
                $local.$modalTxnsObservadasExtDev.PopupWindow("close");
            },
            error: function (response) {
            },
            complete: function (response) {
                $local.$btnActualizarExtDev.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$tablaTransaccionObservadas.find("thead").on('keyup', 'input.filtrable', function () {
        $local.tablaTransaccionObservadas.column($(this).parent().index() + ':visible').search(this.value).draw();
    });

    $local.$tablaTransaccionObservadas.find("thead").on('change', 'select', function () {
        const val = $.fn.dataTable.util.escapeRegex($(this).val());
        $local.tablaTransaccionObservadas.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
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
            url: `${$variableUtil.root}txnsObservadas?accion=buscarPorTipoDocumento`,
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
                $local.tablaTransaccionObservadas.clear().draw();
                $local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: function (transaccionObservadas) {
                if (transaccionObservadas.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaTransaccionObservadas.rows.add(transaccionObservadas).draw();
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
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsObservadas, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectIndicadorDevolucionTxnsObservadas, "indicadoresDevolucion");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectIndicadorExtornoTxnsObservadas, "indicadoresExtorno");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectIndicadorConciliacionTxnsObservadas, "indicadoresConciliacion");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}txnsObservadas?accion=buscarPorCriterios&${paramCriterioBusqueda}`,
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
                $local.tablaTransaccionObservadas.clear().draw();
                $local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: function (transaccionObservadas) {
                if (transaccionObservadas.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
                    return;
                }
                $local.tablaTransaccionObservadas.rows.add(transaccionObservadas).draw();
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
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionTxnsObservadas.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaTxnsObservadas.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresaTxnsObservadas.find("option:selected").text();
        criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
        criterioBusqueda.descripcionCliente = !!$local.$selectClienteTxnsObservadas.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteTxnsObservadas, "; ") : "TODOS";
        criterioBusqueda.descripcionIndicadorDevolucion = !!$local.$selectIndicadorDevolucionTxnsObservadas.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectIndicadorDevolucionTxnsObservadas, "; ") : "TODOS";
        criterioBusqueda.descripcionIndicadorExtorno = !!$local.$selectIndicadorExtornoTxnsObservadas.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectIndicadorExtornoTxnsObservadas, "; ") : "TODOS";
        criterioBusqueda.descripcionIndicadorConciliacion = !!$local.$selectIndicadorConciliacionTxnsObservadas.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectIndicadorConciliacionTxnsObservadas, "; ") : "TODOS";
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsObservadas, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectIndicadorDevolucionTxnsObservadas, "indicadoresDevolucion");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectIndicadorExtornoTxnsObservadas, "indicadoresExtorno");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectIndicadorConciliacionTxnsObservadas, "indicadoresConciliacion");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}reporte/consulta/txnsObservadas?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=buscarPorCriterios&${paramCriterioBusqueda}`;
    });

    $local.$exportarPorTipoDocumento.on("click", function () {
        if (!$formBusquedaTipoDocumento.valid()) {
            return;
        }
        let criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find("option:selected").val() === "" ? "" : $local.$selectTipoDocumento.find("option:selected").text();
        const params = $.param(criterioBusqueda);
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}reporte/consulta/txnsObservadas?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=buscarPorTipoDocumento&${params}`;
    });
});