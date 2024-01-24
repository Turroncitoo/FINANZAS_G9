$(document).ready(function () {
    let $local = {
        $tablaConsultaWS: $("#tablaConsultaWS"),
        tablaConsultaWS: "",
        $filaSeleccionada: "",
        filtrosSeleccionables: [],
        $buscarPorCriterio: $("#buscarPorCriterio"),
        arregloSiNo: [1, 0],
        $filaSeleccionadaConsulta: {},

        //Selects form criterios
        $selectInstitucion: $("#selectInstitucion"),
        $selectEmpresa: $("#selectEmpresa"),
        $selectCliente: $("#selectCliente"),
        $selectLogo: $("#selectLogo"),
        $selectOperacion: $("#selectOperacion"),

        $rangoFechas: $("#fechaTransaccion"),

        // Exportacion
        $exportarPorCriterio: $("#exportarPorCriterio"),
    };

    $formCriterioBusquedaConsulta = $("#formCriterioBusquedaConsulta");

    $funcionUtil.crearDateRangePickerConsulta($local.$rangoFechas, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectInstitucion);
    $funcionUtil.crearSelect2($local.$selectEmpresa);
    $funcionUtil.crearSelect2Multiple($local.$selectCliente, "-1", "TODOS");
    $funcionUtil.crearSelect2($local.$selectLogo);
    $funcionUtil.crearSelect2Multiple($local.$selectOperacion, "-1", "TODOS");

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
            $local.filtrosSeleccionables["24"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["30"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["34"] = $local.arregloSiNo;
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaWS, $local.filtrosSeleccionables);
        },
        "columnDefs": [{
            "targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20, 22, 23, 25, 26, 28, 31, 32],
            "className": "all filtrable",
        }, {
            "targets": [16, 21, 33],
            "className": "all filtrable dt-center",
        }, {
            "targets": 24,
            "className": "all seleccionable data-no-definida dt-center",
            "render": function (data, type, row) {
                return $funcionUtil.insertarEtiquetaSiNo(row.exito);
            }
        }, {
            "targets": [27, 29],
            "className": "all filtrable dt-right monto",
        }, {
            "targets": 30,
            "className": "all seleccionable data-no-definida dt-center",
            "render": function (data, type, row) {
                return $funcionUtil.insertarEtiquetaNoSi(row.indExtorno);
            }
        }, {
            "targets": 34,
            "className": "all seleccionable data-no-definida dt-center",
            "render": function (data, type, row) {
                return $funcionUtil.insertarEtiquetaSiNo(row.solicitoExtorno);
            }
        }, {
            "targets": 35,
            "className": "all dt-center",
            "width": "10%",
            "render": function (data, type, row) {
                if ((row.solicitoExtorno == 0 || row.solicitoExtorno == null) && row.indExtorno == 0 && row.exito == 1) {
                    return $variableUtil.botonExtornoWS;
                }
                return "";
            }
        }],
        "columns": [{
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
            },
            "title": "Instituci\u00f3n"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
            },
            "title": "Empresa"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
            },
            "title": "Cliente"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descripcionMembresia);
            },
            "title": "Membres\u00eda"
        }, {
            "data": (row) => {
                return `${$funcionUtil.unirCodigoDescripcion(row.idLogo, row.descripcionLogoBin)}`
            },
            "title": "Logo"
        }, {
            "data": 'messageType',
            "title": "Message Type"
        }, {
            "data": 'operacion',
            "title": "Operaci\u00f3n"
        }, {
            "data": 'idTransaccion',
            "title": "ID Transacci\u00f3n"
        }, {
            "data": 'idPersona',
            "title": "ID Persona"
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
            "data": 'codigoSeguimiento',
            "title": "C\u00f3digo Seguimiento"
        }, {
            "data": 'numeroTarjeta',
            "title": "N\u00famero Tarjeta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.horaTransaccion);
            },
            "title": "Fecha Transacci\u00f3n"
        }, {
            "data": 'numeroTrace',
            "title": "Trace"
        }, {
            "data": 'idComercio',
            "title": "ID Comercio"
        }, {
            "data": 'dirComercio',
            "title": "Direcci\u00f3n Comercio"
        }, {
            "data": 'idTerminal',
            "title": "ID Terminal"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.localDateCadena, row.localTime);
            },
            "title": "Local Time"
        }, {
            "data": 'codigoSeguimientoOrigen',
            "title": "Cod. Seg. Origen"
        }, {
            "data": 'codigoSeguimientoDestino',
            "title": "Cod. Seg. Destino"
        }, {
            "data": 'exito',
            "title": "\u00C9xito"
        }, {
            "data": 'codigoRespuesta',
            "title": "Respuesta"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaRecarga, row.descMonedaRecarga);
            },
            "title": "Moneda"
        }, {
            "data": 'montoRecarga',
            "render": $tablaFuncion.formatoMonto(),
            "title": "Importe"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaComisionExtra, row.descMonedaComisionExtra);
            },
            "title": "Moneda Comisi\u00f3n"
        }, {
            "data": 'montoComisionExtra',
            "render": $tablaFuncion.formatoMonto(4),
            "title": "Importe Comisi\u00f3n"
        }, {
            "data": 'indExtorno',
            "title": "Extorno"
        }, {
            "data": 'traceExtorno',
            "title": "Trace Extorno"
        }, {
            "data": 'usuarioSolitudExtorno',
            "title": "Usuario Extorno"
        }, {
            "data": 'fechaSolicitudExtorno',
            "title": "Fecha Solicitud Extorno"
        }, {
            "data": 'solicitoExtorno',
            "title": "Solicit\u00f3 Extorno"
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
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectOperacion, "operaciones");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}txnsWebServices?accion=buscarPorCriterio&${paramCriterioBusqueda}`,
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
        criterioBusqueda.descripcionLogoBin = $local.$selectLogo.find("option:selected").val() === "-1" ? "" : $local.$selectLogo.find("option:selected").text();
        criterioBusqueda.descripcionOperacion = !!$local.$selectOperacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectOperacion, "; ") : "TODOS";
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCliente, "clientes");
        paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectOperacion, "operaciones");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}txnsWebServices?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterio&${paramCriterioBusqueda}`;
    });

    $local.$tablaConsultaWS.children("tbody").on("click", ".realizar-extorno", function () {
        var btn = $(this);
        $local.$filaSeleccionadaConsulta = $(this).parents("tr");
        var txn = $local.tablaConsultaWS.row($local.$filaSeleccionadaConsulta).data();
        let path = $funcionUtil.pathExtorno(txn.operacion);
        let criterio = $funcionUtil.obtenerCriteriosExtorno(txn);
        var paramCriterioBusqueda = $.param(criterio);
        $.confirm({
            icon: "fa fa-info-circle",
            title: "Pre-autorizaci\u00F3n",
            content: "Se realizar\u00E1 la solicitud de extorno para la siguiente transacci\u00F3n.</br><b>Cliente: </b> " + $funcionUtil.aplicarTrim(txn.nombres) + ' ' + $funcionUtil.aplicarTrim(txn.apellidoPaterno) + ' ' + $funcionUtil.aplicarTrim(txn.apellidoMaterno) + "</br><b>Documento: </b>" + $funcionUtil.aplicarTrim(txn.numeroDocumento) + "</br><b>Tarjeta: </b>" + txn.numeroTarjeta + "</br><b>Trace: </b>" + txn.numeroTrace + "</br><b>Operaci\u00F3n a extornar: </b>" + txn.operacion + "</br><b>Moneda: </b>" + txn.monedaRecarga + "</br><b>Monto: </b>" + txn.montoRecarga,
            buttons: {
                Aceptar: {
                    action: function () {
                        var confirmar = $.confirm({
                            icon: 'fa fa-spinner fa-pulse fa-fw',
                            title: "Solicitando extorno...",
                            theme: "bootstrap",
                            content: function () {
                                var self = this;
                                self.buttons.close.hide();
                                $.ajax({
                                    type: "GET",
                                    url: $variableUtil.root + 'txnsWebServices/api/' + path + '?' + paramCriterioBusqueda,
                                    contentType: "application/json",
                                    dataType: "json",
                                    beforeSend: function (xhr) {
                                        btn.attr("disabled", true).find("i").removeClass("fa-retweet").addClass("fa-spinner fa-pulse fa-fw");
                                        xhr.setRequestHeader('Content-Type', 'application/json');
                                        xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
                                    },
                                    success: function (data) {
                                        $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, consultar con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
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