$(document).ready(function() {

    var $local = {
        $tablaConsultaAdministrativa: $("#tablaConsultaAdministrativa"),
        tablaConsultaAdministrativa: "",
        $exportarXlsx: $("#exportarXlsx"),

        $tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
        $tipoDocumento: $("#tipoDocumento"),
        $criterios: $("#criterios"),
        $btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
        $buscarCriterios: $("#buscarCriterios"),
        $selectTipoDocumento: $("#selectTipoDocumento"),
        filtrosSeleccionables: {},
        arregloSiNo: [1, 0],

        $modalAdministracionConsulta: $("#modalConsultaAdmin"),
        $txtLogo: $("#txtLogo"),
        $txtProducto: $("#txtProducto"),
        $txtAfinidad: $("#txtAfinidad"),
        $txtTipoTarjeta: $("#txtTipoTarjeta"),
        $txtEstadoTarjeta: $("#txtEstadoTarjeta"),
        $txtTipoBloqueo: $("#txtTipoBloqueo"),
        $txtNombreCompletoCliente: $('#txtNombreCompletoCliente'),
        $txtMonedaCuenta: $('#txtMonedaCuenta'),

        $filaSeleccionada: "",
        dataSeleccionada: {},

        numFiltrosMin: 1,
        mensajeFiltros: function(){
            return "Debe seleccionar al menos " + this.numFiltrosMin + " filtro(s) para porder realizar la B\u00FAsqueda";
        },

        $rangoFechasProceso: $('#fechaProcesoTarjetaPP'),

        //Selects form criterios
        $selectInstitucionTarjetaPP: $('#selectInstitucionTarjetaPP'),
        $selectEmpresaTarjetaPP: $('#selectEmpresaTarjetaPP'),
        $selectClienteTarjetaPP: $('#selectClienteTarjetaPP'),
        $selectLogoTarjetaPP: $('#selectLogoTarjetaPP'),
        $selectProductoTarjetaPP: $('#selectProductoTarjetaPP'),

        //Asociacion tarjeta-cliente-empresa
        $selectEmpresaAsociacion: $('#selectEmpresaAsociacion'),
        $msgAsociarCliente: $('#msgAsociarCliente'),
        $btnAsociarTarjetaCliente: $('#btnAsociarTarjetaCliente'),

        //Activacion tarjeta
        $msgActivar: $('#msgActivar'),
        $btnActivar: $('#btnActivar'),

        //Bloqueo tarjeta
        $btnBloqueo:  $('#btnBloqueo'),
        $selectMotivoBloqueo: $('#selectMotivoBloqueo'),

        //Reasignar tarjeta
        $btnReasignar: $('#btnReasignar'),
        $msgReasignarTarjeta: $('#msgReasignarTarjeta'),

        //Emitir virtual
        $selectTipoDocumentoEmitir: $('#selectTipoDocumentoEmitir'),
        $msg1EmitirTarjetaVirtual: $('#msg1EmitirTarjetaVirtual'),
        $msg2EmitirTarjetaVirtual: $('#msg2EmitirTarjetaVirtual'),
        $btnEmitirTarjetaVirtual: $('#btnEmitirTarjetaVirtual'),

        //Habilita tarjeta fisica
        $msgHabilita: $('#msgHabilita'),
        $btnHabilita: $('#btnHabilita'),

        //Regimen
        $selectRegimen: $('#selectRegimen'),
        $btnRegimen: $('#btnRegimen'),

        //Recarga
        $selectMonedaComisionRecarga: $('#selectMonedaComisionRecarga'),
        $selectMonedaMontoRecarga: $('#selectMonedaMontoRecarga'),
        $btnRecarga: $('#btnRecarga'),
        $msgRecarga: $('#msgRecarga'),

        //Debito
        $selectMonedaMontoDebito: $('#selectMonedaMontoDebito'),
        $btnDebito: $('#btnDebito'),
        $msgDebito: $('#msgDebito'),

        //Transferencia
        $selectMonedaMontoTransferencia: $('#selectMonedaMontoTransferencia'),
        $btnTransferencia: $('#btnTransferencia'),
        $msgTransferencia: $('#msgTransferencia'),

        //Consulta saldo
        $btnSaldos: $('#btnSaldos'),
        $msgSaldos: $('#msgSaldos'),
        $tablaSaldos: $('#tablaSaldos'),
        tablaSaldos: '',

        //Consulta mov
        $btnConsultaMov: $('#btnConsultaMov'),
        $msgConsultaMov: $('#msgConsultaMov'),
        $tablaMovimientos: $('#tablaMovimientos'),
        tablaMovimientos: '',

        //Exportacion
        $exportarPorCriterio: $('#exportarPorCriterio'),
        $exportarPorTipoDocumento: $('#exportarPorTipoDocumento')
    };

    $formBusquedaCriterios = $("#formBusquedaCriterios");
    $formBusquedaTipoDocumento = $("#formParamIniciales");
    $formModalConsultaAmin = $('#formModalConsultaAmin');

    //Emitir virtual
    $formEmitirTarjetaVirtual = $('#formEmitirTarjetaVirtual');

    //Habilita tarjeta fisica
    $formHabilita = $('#formHabilita');

    //Asociacion tarjeta-cliente-empresa
    $formAsociarCliente = $('#formAsociarCliente');

    //Activacion tarjeta
    $formActivar = $('#formActivar');

    //Bloqueo tarjeta
    $formBloqueo = $('#formBloqueo');

    //Reasignar tarjeta
    $formReasignar = $('#formReasignar');

    //Regimen
    $formRegimen = $('#formRegimen');

    //Recarga
    $formRecarga = $('#formRecarga');

    //Debito
    $formDebito= $('#formDebito');

    //Transferencia
    $formTransferencia = $('#formTransferencia');

    //Consulta saldos
    $formSaldos = $('#formSaldos');

    //Consulta saldos
    $formConsultaMov = $('#formConsultaMov');

    $.fn.dataTable.ext.errMode = "none";

    $funcionUtil.crearDateRangePickerSinValorPorDefecto($local.$rangoFechasProceso, "right");

    //Crear selects form criterios
    $funcionUtil.crearSelect2($local.$selectTipoDocumento);
    $funcionUtil.crearSelect2($local.$selectInstitucionTarjetaPP);
    $funcionUtil.crearSelect2($local.$selectEmpresaTarjetaPP);
    $funcionUtil.crearSelect2Multiple($local.$selectClienteTarjetaPP, "-1", "TODOS");
    $funcionUtil.crearSelect2($local.$selectLogoTarjetaPP);
    $funcionUtil.crearSelect2Multiple($local.$selectProductoTarjetaPP, "-1", "TODOS");


    //Asociacion tarjeta-cliente-empresa
    $funcionUtil.crearSelect2($local.$selectEmpresaAsociacion, "Seleccione...");

    //Bloqueo tarjeta
    $funcionUtil.crearSelect2($local.$selectMotivoBloqueo, "Seleccione...");

    //Emitir tarjeta virtual
    $funcionUtil.crearSelect2($local.$selectTipoDocumentoEmitir, "Seleccione...");

    //Regimen
    $funcionUtil.crearSelect2($local.$selectRegimen, "Seleccione...");

    //Recarga
    $funcionUtil.crearSelect2($local.$selectMonedaComisionRecarga, "Seleccione...");
    $funcionUtil.crearSelect2($local.$selectMonedaMontoRecarga, "Seleccione...");

    //Debito
    $funcionUtil.crearSelect2($local.$selectMonedaMontoDebito, "Seleccione...");

    //Transferencia
    $funcionUtil.crearSelect2($local.$selectMonedaMontoTransferencia, "Seleccione...");

    $local.$modalAdministracionConsulta.PopupWindow({
        title: "Gesti\u00F3n de la tarjeta",
        autoOpen: false,
        modal: false,
        height: 800,
        width: 600,
    });

    $local.$selectEmpresaTarjetaPP.on("change", () => {
        const opcionSeleccionada = $local.$selectEmpresaTarjetaPP.val();
        if (!opcionSeleccionada) {
            $local.$selectClienteTarjetaPP.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectClienteTarjetaPP.find("option").remove();
                $local.$selectClienteTarjetaPP.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectClienteTarjetaPP.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$selectClienteTarjetaPP.parent().find(".cargando").remove();
            }
        });
    });

    $local.$selectLogoTarjetaPP.on("change", () => {
        const opcionSeleccionada = $local.$selectLogoTarjetaPP.val();
        if (!opcionSeleccionada) {
            $local.$selectProductoTarjetaPP.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}producto/logo/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$selectProductoTarjetaPP.find("option").remove();
                $local.$selectProductoTarjetaPP.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Productos...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$selectProductoTarjetaPP.append($("<option/>").val(response[i].codigoProducto).text(response[i].codigoProducto + " - " + response[i].descProducto));
                });
            },
            complete: () => {
                $local.$selectProductoTarjetaPP.parent().find(".cargando").remove();
            }
        });
    });

    $local.$tablaConsultaAdministrativa.on("xhr.dt", function (e, settings, json, xhr) {
        switch (xhr.status) {
            case 500:
                $local.tablaConsultaAdministrativa.clear().draw();
                $funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
                break;
        }
    });

    $local.tablaConsultaAdministrativa = $local.$tablaConsultaAdministrativa.DataTable({
        "language": {
            "emptyTable": "No hay Tarjetas registradas."
        },
        "initComplete": function() {
            $local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");
            $local.filtrosSeleccionables["24"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["26"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["35"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["36"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["37"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["38"] = $local.arregloSiNo;
            $local.filtrosSeleccionables["39"] = $local.arregloSiNo;
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa, $local.filtrosSeleccionables);
        },
        "columnDefs": [{
            "targets": [1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 28, 29, 30, 31, 32, 33, 34, 40, 41, 42],
            "className": "all filtrable"
        }, {
            "targets": [0, 9, 11, 21, 25, 27],
            "className": "all filtrable dt-center",
        }, {
            "targets": 24,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.tarjetaFisicaNacidaDeVirtual);
            }
        }, {
            "targets": 26,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.habilitadaTarjetaFisicaNacidaDeVirtual);
            }
        }, {
            "targets": 35,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.permiteRealizarRetiros);
            }
        }, {
            "targets": 36,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.permiteRealizarDeposito);
            }
        }, {
            "targets": 37,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.permiteRealizarTransferencia);
            }
        }, {
            "targets": 38,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.permiteRecibirTransferencia);
            }
        }, {
            "targets": 39,
            "className": "all seleccionable data-no-definida dt-center",
            "render": (data, type, row) => {
                return $funcionUtil.insertarEtiquetaSiNo(row.permiteConsulta);
            }
        }, {
            "targets": 43,
            "className": "all dt-center",
            "render": () => {
                return $variableUtil.botonManageTarjeta;
            }
        }],
        "columns": [{
            "data": "fechaProceso",
            "title": "Fecha Registro" //0
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
            },
            "title": "Instituci\u00f3n" //1
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
            },
            "title": "Empresa" //2
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
            },
            "title": "Cliente" //3
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idLogo, row.descLogoBin);
            },
            "title": "Logo"  //4
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto);
            },
            "title": "Producto"  //5
        }, {
            "data": "idTarjeta",
            "title": "ID Tarjeta" //6
        }, {
            "data": "codigoSeguimiento",
            "title": "C\u00f3digo Seguimiento" //7
        }, {
            "data": "numeroTarjeta",
            "title": "N\u00famero Tarjeta" //8
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaActivacionCadena, row.horaActivacion);
            },
            "title": "Fecha Activaci\u00f3n" //9
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.estado, row.descripcionEstado);
            },
            "title": "Estado" //10
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.fechaBloqueoCadena, row.horaBloqueo);
            },
            "title": "Fecha Bloqueo"  //11
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoBloqueo, row.descripcionTipoBloqueo);
            },
            "title": "Tipo Bloqueo" //12
        }, {
            "data": "poseeDuenio",
            "title": "Posee Due\u00f1o" //13
        }, {
            "data": "idPersona",
            "title": "ID Persona" //14
        }, {
            "data": "codigoUBA",
            "title": "C\u00f3digo UBA" //15
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descripcionTipoDocumento);
            },
            "title": "Tipo Documento" //16
        }, {
            "data": "numeroDocumento",
            "title": "N\u00famero Documento"  //17
        }, {
            "data": "nombres",
            "title": "Nombres"  //18
        }, {
            "data": "apellidoPaterno",
            "title": "Apellido Paterno" //19
        }, {
            "data": "apellidoMaterno",
            "title": "Apellido Materno" //20
        }, {
            "data": "fechaOrden",
            "title": "Fecha Orden" //21
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.tipoTarjeta, row.descripcionTipoTarjeta);
            },
            "title": "Tipo Tarjeta" //22
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idAfinidad, row.descripcionAfinidad);
            },
            "title": "ID Afinidad" //23
        }, {
            "data": "tarjetaFisicaNacidaDeVirtual",
            "title": "F\u00edsica Nacida Virtual" //24
        }, {
            "data": "fechaTarjetaFisicaNacidaDeVirtual",
            "title": "Fecha Virtual A F\u00edsica" //25
        }, {
            "data": "habilitadaTarjetaFisicaNacidaDeVirtual",
            "title": "Habilitaci\u00f3n Virtual A F\u00edsica" //26
        }, {
            "data": "fechaHabilitacionTarjetaFisicaNacidaVirtual",
            "title": "Fecha Habilitaci\u00f3n Virtual A F\u00edsica" //27
        }, {
            "data": "idCuenta",
            "title": "ID Cuenta" //28
        }, {
            "data": "cuentaDefecto",
            "title": "Cuenta Defecto" //29
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaCuenta, row.descripcionMonedaCuenta);
            },
            "title": "Moneda Cuenta" //30
        }, {
            "data": "tipoCuenta",
            "title": "Tipo Cuenta" //31
        }, {
            "data": "titularidad",
            "title": "Titularidad" //32
        }, {
            "data": "cuentaLinea1EnAtmBcoPlaza",
            "title": "Cta. L\u00ednea 1" //33
        }, {
            "data": "cuentaLinea2EnAtmTpoCodCta",
            "title": "Cta. L\u00ednea 2" //34
        }, {
            "data": "permiteRealizarRetiros",
            "title": "Realiza Retiros" //35
        }, {
            "data": "permiteRealizarDeposito",
            "title": "Realiza Dep\u00f3sito" //36
        }, {
            "data": "permiteRealizarTransferencia",
            "title": "Realiza Transferencia" //37
        }, {
            "data": "permiteRecibirTransferencia",
            "title": "Recibe Transferencia" //38
        }, {
            "data": "permiteConsulta",
            "title": "Puede Consulta" //39
        }, {
            "data": "idLote",
            "title": "ID Lote" //40
        }, {
            "data": "traceLote",
            "title": "Trace Lote" //41
        }, {
            "data": "claveId",
            "title": "Clave ID" //42
        }, {
            "data": null,
            "title": "Acci\u00F3n" //43
        }],
        "order": []
    });

    $local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input.filtrable", function() {
        $local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
    });

    $local.$tablaConsultaAdministrativa.find("thead").on('change', 'select', function() {
        const val = $.fn.dataTable.util.escapeRegex($(this).val());
        $local.tablaConsultaAdministrativa.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
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
                $funcionUtil.notificarException("Seleccione un Tipo de B\u00FAsqueda v\u00E1lido", "fa-warning", "Aviso", "warning");
        }
    });

    $local.$btnBuscarPorDocumentoCliente.on("click", function() {
        if (!$formBusquedaTipoDocumento.valid()) {
            return;
        }
        var criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        $.ajax({
            type : "GET",
            url : `${$variableUtil.root}consulta/administrativa/tarjetaPP?accion=buscarTipoDocumento`,
            data : criterioBusqueda,
            statusCode : {
                400 : function(response) {
                    $funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
                    $funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
                    $funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
                }
            },
            beforeSend : () => {
                $local.tablaConsultaAdministrativa.clear().draw();
                $local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : (transaccionAjustes) => {
                if (transaccionAjustes.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
                    return;
                }
                $local.tablaConsultaAdministrativa.rows.add(transaccionAjustes).draw();
            },
            complete : () => {
                $local.$btnBuscarPorDocumentoCliente.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$buscarCriterios.on("click", function () {
        if (!$formBusquedaCriterios.valid()) {
            return;
        }
        let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
        if(criterioBusqueda.codigoSeguimiento.length == 0 && criterioBusqueda.numeroTarjeta.length == 0 && criterioBusqueda.nombreCompleto.length == 0){
            if(criterioBusqueda.fechaProceso.length == 0) {
                $funcionUtil.notificarException($variableUtil.camposRequeridos, "fa-exclamation-circle", "Informaci\u00F3n", "info");
                return;
            }
        }
        const rangoFechasProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasProceso);
        if(rangoFechasProceso.fechaInicio.length != 0 && rangoFechasProceso.fechaFin.length != 0){
            criterioBusqueda.fechaInicioProceso = new Date(rangoFechasProceso.fechaInicio.replaceAll('-', '/'));
            criterioBusqueda.fechaFinProceso = new Date(rangoFechasProceso.fechaFin.replaceAll('-', '/'));
        }
        let paramCriterioBusqueda = $.param(criterioBusqueda);
        paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTarjetaPP, "clientes");
        paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectProductoTarjetaPP, "productos");
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}consulta/administrativa/tarjetaPP?accion=buscarPorCriterios&${paramCriterioBusqueda}`,
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
                $local.tablaConsultaAdministrativa.clear().draw();
                $local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
            },
            success: (transaccionAjustes) => {
                if (transaccionAjustes.length === 0) {
                    $funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
                    return;
                }
                $local.tablaConsultaAdministrativa.rows.add(transaccionAjustes).draw();
            },
            complete: () => {
                $local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });


    $local.$tablaConsultaAdministrativa.children("tbody").on("click", ".ver-detalle", function() {
        $local.$filaSeleccionada = $(this).parents("tr");
        var compensacion = $local.tablaConsultaAdministrativa.row($local.$filaSeleccionada).data();
        $local.dataSeleccionada = compensacion;
        $funcionUtil.llenarFormulario(compensacion, $formModalConsultaAmin);
        $local.$txtLogo.val($funcionUtil.unirCodigoDescripcion(compensacion.idLogo, compensacion.descLogoBin));
        $local.$txtProducto.val($funcionUtil.unirCodigoDescripcion(compensacion.codigoProducto, compensacion.descProducto));
        $local.$txtAfinidad.val($funcionUtil.unirCodigoDescripcion(compensacion.idAfinidad, compensacion.descripcionAfinidad));
        $local.$txtTipoTarjeta.val($funcionUtil.unirCodigoDescripcion(compensacion.tipoTarjeta, compensacion.descripcionTipoTarjeta));
        $local.$txtEstadoTarjeta.val($funcionUtil.unirCodigoDescripcion(compensacion.estado, compensacion.descripcionEstado));
        $local.$txtTipoBloqueo.val($funcionUtil.unirCodigoDescripcion(compensacion.tipoBloqueo, compensacion.descripcionTipoBloqueo));
        $local.$txtMonedaCuenta.val($funcionUtil.unirCodigoDescripcion(compensacion.monedaCuenta, compensacion.descripcionMonedaCuenta));
        let nombres = compensacion.nombres == null ? "" :  compensacion.nombres;
        let apellidoPaterno = compensacion.apellidoPaterno == null ? "" :  compensacion.apellidoPaterno;
        let apellidoMaterno = compensacion.apellidoMaterno == null ? "" :  compensacion.apellidoMaterno;
        $local.$txtNombreCompletoCliente.val(nombres.trim() + " " + apellidoPaterno.trim() + " " + apellidoMaterno.trim());

        $funcionUtil.prepararFormularioRegistro($formAsociarCliente);
        $funcionUtil.prepararFormularioRegistro($formActivar);
        $funcionUtil.prepararFormularioRegistro($formBloqueo);
        $funcionUtil.prepararFormularioRegistro($formReasignar);
        $funcionUtil.prepararFormularioRegistro($formEmitirTarjetaVirtual);
        $funcionUtil.prepararFormularioRegistro($formHabilita);
        $funcionUtil.prepararFormularioRegistro($formRegimen);
        $funcionUtil.prepararFormularioRegistro($formRecarga);
        $funcionUtil.prepararFormularioRegistro($formDebito);
        $funcionUtil.prepararFormularioRegistro($formTransferencia);


        prepararFormularioAsociacionTarjetaCliente(compensacion);
        prepararFormularioActivacion(compensacion);
        prepararFormularioBloqueo(compensacion);
        prepararFormularioReasignar(compensacion);
        prepararFormularioEmitirTarjetaVirtual(compensacion);
        prepararFormularioHabilitarTarjetaFisica(compensacion);
        prepararFormularioRegimen(compensacion);
        prepararFormularioRecarga(compensacion);
        prepararFormularioDebito(compensacion);
        prepararFormularioTransferencia(compensacion);
        prepararFormularioConsultaSaldo(compensacion);
        prepararFormularioConsultaMov(compensacion);

        $local.tablaSaldos.clear().draw();
        $local.tablaMovimientos.clear().draw();

        $local.$modalAdministracionConsulta.PopupWindow("open");
        $local.$modalAdministracionConsulta.PopupWindow("maximize");
    });


    //Asociacion tarjeta-cliente-empresa
    function prepararFormularioAsociacionTarjetaCliente(data){
        $funcionUtil.llenarFormulario(data, $formAsociarCliente);
        if(data.poseeDuenio == 'TARJETA CON NOMBRE'){
            //Mostrar mensaje que la tarjeta ya tiene duenio
            $local.$msgAsociarCliente.removeClass('hidden')
            $local.$btnAsociarTarjetaCliente.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta ya tiene duenio
            $local.$msgAsociarCliente.addClass('hidden')
            $local.$btnAsociarTarjetaCliente.removeAttr("disabled");
        }
    }

    //Activacion tarjeta
    function prepararFormularioActivacion(data){
        let arrEstadosPermitidos = ['T','P','W','X']
        $funcionUtil.llenarFormulario(data, $formActivar);
        if(!arrEstadosPermitidos.includes(data.estado)){
            //Mostrar mensaje que la tarjeta no se puede activar
            $local.$msgActivar.removeClass('hidden');
            $local.$btnActivar.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta no se puede activar
            $local.$msgActivar.addClass('hidden');
            $local.$btnActivar.removeAttr("disabled");
        }
    }

    //Bloqueo tarjeta
    function prepararFormularioBloqueo(data){
        $funcionUtil.llenarFormulario(data, $formBloqueo);
    }

    //Reasignar tarjeta
    function prepararFormularioReasignar(data){
        let arrEstadosPermitidos = ['C']
        $funcionUtil.llenarFormulario(data, $formReasignar);
        if(!arrEstadosPermitidos.includes(data.estado)){
            //Mostrar mensaje que la tarjeta no se puede reasignar
            $local.$msgReasignarTarjeta.removeClass('hidden');
            $local.$btnReasignar.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta no se puede reasignar
            $local.$msgReasignarTarjeta.addClass('hidden');
            $local.$btnReasignar.removeAttr("disabled");
        }
    }

    //Emitir tarjeta virtual
    function prepararFormularioEmitirTarjetaVirtual(data){
        $funcionUtil.llenarFormulario(data, $formEmitirTarjetaVirtual);
        if(data.tipoTarjeta == '0200'){
            $local.$msg1EmitirTarjetaVirtual.removeClass('hidden');
            $local.$msg2EmitirTarjetaVirtual.addClass('hidden');
            $local.$btnEmitirTarjetaVirtual.attr("disabled",true);

        }else if(data.tipoTarjeta == '0240'){
            if(data.habilitadaTarjetaFisicaNacidaDeVirtual == 1){
                $local.$msg1EmitirTarjetaVirtual.addClass('hidden');
                $local.$msg2EmitirTarjetaVirtual.removeClass('hidden');
                $local.$btnEmitirTarjetaVirtual.attr("disabled",true);
            }else{
                $local.$msg1EmitirTarjetaVirtual.addClass('hidden');
                $local.$msg2EmitirTarjetaVirtual.addClass('hidden');
                $local.$btnEmitirTarjetaVirtual.removeAttr("disabled");
            }
        }
    }

    //Habilitar tarjeta fisica
    function prepararFormularioHabilitarTarjetaFisica(data){
        $funcionUtil.llenarFormulario(data, $formHabilita);
        if(data.tarjetaFisicaNacidaDeVirtual == 1){
            $local.$msgHabilita.addClass('hidden');
            $local.$btnHabilita.removeAttr("disabled");
        }else {
            $local.$msgHabilita.removeClass('hidden');
            $local.$btnHabilita.attr("disabled",true);
        }
    }

    //Regimen
    function prepararFormularioRegimen(data){
        $funcionUtil.llenarFormulario(data, $formRegimen);
    }

    //Recarga
    function prepararFormularioRecarga(data){
        let arrEstadosPermitidos = ['A']
        $funcionUtil.llenarFormulario(data, $formRecarga);
        if(!arrEstadosPermitidos.includes(data.estado)){
            //Mostrar mensaje que la tarjeta no se puede reasignar
            $local.$msgRecarga.removeClass('hidden');
            $local.$btnRecarga.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta no se puede reasignar
            $local.$msgRecarga.addClass('hidden');
            $local.$btnRecarga.removeAttr("disabled");
        }
    }

    //Debito
    function prepararFormularioDebito(data){
        let arrEstadosPermitidos = ['A']
        $funcionUtil.llenarFormulario(data, $formDebito);
        if(!arrEstadosPermitidos.includes(data.estado)){
            //Mostrar mensaje que la tarjeta no se puede reasignar
            $local.$msgDebito.removeClass('hidden');
            $local.$btnDebito.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta no se puede reasignar
            $local.$msgDebito.addClass('hidden');
            $local.$btnDebito.removeAttr("disabled");
        }
    }

    //Transferencia
    function prepararFormularioTransferencia(data){
        let arrEstadosPermitidos = ['A']
        $funcionUtil.llenarFormulario(data, $formTransferencia);
        if(!arrEstadosPermitidos.includes(data.estado)){
            //Mostrar mensaje que la tarjeta no se puede reasignar
            $local.$msgTransferencia.removeClass('hidden');
            $local.$btnTransferencia.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta no se puede reasignar
            $local.$msgTransferencia.addClass('hidden');
            $local.$btnTransferencia.removeAttr("disabled");
        }
    }

    //Consulta saldo
    function prepararFormularioConsultaSaldo(data){
        let arrEstadosPermitidos = ['A']
        $funcionUtil.llenarFormulario(data, $formSaldos);
        if(!arrEstadosPermitidos.includes(data.estado)){
            //Mostrar mensaje que la tarjeta no se puede reasignar
            $local.$msgSaldos.removeClass('hidden');
            $local.$btnSaldos.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta no se puede reasignar
            $local.$msgSaldos.addClass('hidden');
            $local.$btnSaldos.removeAttr("disabled");
        }
    }

    //Consulta Mov
    function prepararFormularioConsultaMov(data){
        let arrEstadosPermitidos = ['A']
        $funcionUtil.llenarFormulario(data, $formConsultaMov);
        if(!arrEstadosPermitidos.includes(data.estado)){
            //Mostrar mensaje que la tarjeta no se puede reasignar
            $local.$msgConsultaMov.removeClass('hidden');
            $local.$btnConsultaMov.attr("disabled",true);
        }else{
            //Ocutlar mensaje que la tarjeta no se puede reasignar
            $local.$msgConsultaMov.addClass('hidden');
            $local.$btnConsultaMov.removeAttr("disabled");
        }
    }


    $local.tablaMovimientos = $local.$tablaMovimientos.DataTable({
        "language": {
            "emptyTable": "No hay movimientos registrados"
        },
        "initComplete": function() {
            $local.$tablaMovimientos.wrap("<div class='table-responsive'></div>");
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMovimientos);
        },
        "columnDefs": [{
            "targets": [0, 1, 2, 3, 4, 5, 6, 9],
            "className": "all filtrable",
        }, {
            "targets": [7, 8],
            "className": "all filtrable dt-right",
        }],
        "columns": [{
            "data": 'secuencia', //0
            "title": "Trace"
        }, {
            "data": 'fecha', //1
            "title": "Fecha Transacci\u00F3n"
        }, {
            "data": 'hora',//2
            "title": "Hora Transacci\u00F3n"
        }, {
            "data": 'numeroTarjeta', //3
            "title": "Tarjeta"
        }, {
            "data": 'comercio', //4
            "title": "Comercio"
        }, {
            "data": 'categoriaComercio', //5
            "title": "Categor\u00EDa Comercio"
        }, {
            "data": 'operacion', //6
            "title": "Operaci\u00F3n"
        }, {
            "data": function (row) {
                return !row.costo ? "" : row.costo.toFixed(2);
            },
            "title": "Costo"
        }, {
            "data": function (row) {
                return !row.monto ? "" : row.monto.toFixed(2);
            },
            "title": "Monto"
        }, {
            "data": 'tipo', //9
            "title": "Tipo"
        }]
    });

    $local.$tablaMovimientos.find("thead").on('keyup', 'input', function() {
        $local.tablaMovimientos.column($(this).parent().index() + ':visible').search(this.value).draw();
    });


    $local.tablaSaldos = $local.$tablaSaldos.DataTable({
        "language": {
            "emptyTable": "No hay registros"
        },
        "initComplete": function() {
            $local.$tablaSaldos.wrap("<div class='table-responsive'></div>");
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaSaldos);
        },
        "columnDefs": [ {
            "targets": [0,1],
            "className": "all filtrable",
        }, {
            "targets": 2,
            "className": "all filtrable dt-right",
        } ],
        "columns": [ {
            "data": function(row) {
                return $funcionUtil.unirCodigoDescripcion(row.moneda, row.descripcion);
            },
            "title": "Moneda"
        },{
            "data": "signo",
            "title": "Signo"
        },{
            "data": function(row) {
                return !row.monto ? "0.00" : row.monto.toFixed(2);
            },
            "title": "Monto"
        }]
    });

    $local.$tablaSaldos.find("thead").on('keyup', 'input', function() {
        $local.tablaSaldos.column($(this).parent().index() + ':visible').search(this.value).draw();
    });

    $('.tab-op').on('click', function () {
        let idDiv = $(this).attr('key');
        let typeOp = $(this).attr('tp');
        $('.tab-op-det').each(function( index ) {
            let idOtrosDiv = $(this)[0].id;
            let typeOtrosDiv = $('#'+idOtrosDiv).attr('tp');
            if(idOtrosDiv == idDiv){
                $('#'+idOtrosDiv).removeClass('hidden');
            }else if(typeOtrosDiv == typeOp){
                $('#'+idOtrosDiv).addClass('hidden');
            }
        });
    });


    //Botones

    $local.$btnSaldos.on("click", function() {
        var btn = $(this);
        var criterio = $formSaldos.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type: "GET",
            url: $variableUtil.root + "txnsWebServices/api/consulta-saldo?"+paramCriterioBusqueda,
            contentType: "application/json",
            dataType: "json",
            beforeSend: function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
                $local.tablaSaldos.clear().draw();
            },
            success: function(response) {
                if(response == null){
                    $funcionUtil.notificarException("No se realizo la transacci\u00F3n, ocurrio un problema al enviar la operaci\u00F3n. Comuniquese con su administrador", "fa-warning", "Aviso", "warning");
                    return;
                }
                const respuesta = response;
                if(respuesta["RC"] == "0"){
                    let arr = [];
                    arr.push({
                        moneda: respuesta["MONEDA"],
                        signo: respuesta["SIGNO"],
                        descripcion: $funcionUtil.determinarMoneda(respuesta["MONEDA"]),
                        monto: $funcionUtil.formatMonedaFromConsultaSaldo(respuesta["MONTO_2"])
                    });
                    $local.tablaSaldos.rows.add(arr).draw();
                }else{
                    $funcionUtil.notificarException("Ocurrio un error, el mensajes es: "+response["RC_DESC"]+" Transacci\u00F3n: "+$funcionUtil.validarNull(response["TRANSACCION"]), "fa-warning", "Aviso", "warning");
                }
            },
            error: function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });


    $local.$btnConsultaMov.on("click", function() {
        var btn = $(this);
        var criterio = $formConsultaMov.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type: "GET",
            url: $variableUtil.root + "txnsWebServices/api/consulta-movimientos?"+paramCriterioBusqueda,
            contentType: "application/json",
            dataType: "json",
            beforeSend: function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
                $local.tablaMovimientos.clear().draw();
            },
            success: function(response) {
                if(response == null){
                    $funcionUtil.notificarException("No se realizo la transacci\u00F3n, ocurrio un problema al enviar la operaci\u00F3n. Comuniquese con su administrador", "fa-warning", "Aviso", "warning");
                    return;
                }
                const respuesta = response;
                let arrFrmt = [];
                if(respuesta["RC"] == "0"){
                    let arr = respuesta["MOVIMIENTOS"];
                    if(arr.length == 0){
                        $funcionUtil.notificarException("La tarjeta no contiene operaciones", "fa-exclamation-circle", "Informaci\u00F3n", "info");
                    }
                    arr.forEach(function(item){
                        let formatMonto = '0.0';
                        arrFrmt.push({
                            secuencia: item["SECUENCIA"],
                            fecha: $funcionUtil.convertirFechaConsultaMovimientos(item["FECHA"]),
                            numeroTarjeta: item["PAN_TRUNC"],
                            hora: $funcionUtil.convertirHoraConsultaMovimientos(item["HORA"]),
                            comercio: item["COMERCIO"],
                            categoriaComercio: item["CATEGORIA_COMERCIO"],
                            operacion: item["CODIGO DE OPERACION"],
                            costo: $funcionUtil.formatMonedaFromConsultaSaldo(item["COSTO"]),
                            tipo: item["TIPO"],
                            monto: $funcionUtil.formatMonedaFromConsultaSaldo(item["MONTO"])
                        });

                    });
                    $local.tablaMovimientos.rows.add(arrFrmt).draw();
                }else{
                    $funcionUtil.notificarException("Ocurrio un error, el mensajes es: "+response["RC_DESC"]+" Transacci\u00F3n: "+$funcionUtil.validarNull(response["TRANSACCION"]), "fa-warning", "Aviso", "warning");
                }
            },
            error: function(response) {
            },
            complete: function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });


    $local.$btnAsociarTarjetaCliente.on("click", function() {
        var btn = $(this);
        if (!$formAsociarCliente.valid()) {
            return;
        }
        var criterio = $formAsociarCliente.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type: "GET",
            url: $variableUtil.root + "txnsWebServices/api/asociar-tarjeta-cliente-empresa?"+paramCriterioBusqueda,
            contentType: "application/json",
            dataType: "json",
            beforeSend: function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-handshake-o").addClass("fa-spinner fa-pulse fa-fw");
                $local.tablaMovimientos.clear().draw();
            },
            success: function(response) {
                if(response == null){
                    $funcionUtil.notificarException("No se realizo la transacci\u00F3n, ocurrio un problema al enviar la operaci\u00F3n. Comuniquese con su administrador.", "fa-warning", "Aviso", "warning");
                    return;
                }
                if(response["RC"] == "0"){
                    $funcionUtil.notificarException("Asociaci\u00F3n tarjeta y cliente con \u00E9xito.", "fa-check", "Aviso", "success");
                }else{
                    $funcionUtil.notificarException("Ocurrio un error, el mensajes es: "+response["RC_DESC"]+" Transacci\u00F3n: "+$funcionUtil.validarNull(response["TRANSACCION"]), "fa-warning", "Aviso", "warning");
                }
            },
            error: function(response) {
            },
            complete: function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-handshake-o").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$btnRegimen.on("click", function() {
        var btn = $(this);
        if (!$formRegimen.valid()) {
            return;
        }
        var criterio = $formRegimen.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/actualiza-regimen?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-handshake-o").addClass("fa-spinner fa-pulse fa-fw");
                $local.tablaMovimientos.clear().draw();
            },
            success : function(response) {
                if(response == null){
                    $funcionUtil.notificarException("No se realizo la transacci\u00F3n, ocurrio un problema al enviar la operaci\u00F3n. Comuniquese con su administrador.", "fa-warning", "Aviso", "warning");
                    return;
                }
                if(response["RC"] == "0"){
                    $funcionUtil.notificarException("Actualizaci\u00F3n de r\u00E9gimen con \u00E9xito.", "fa-check", "Aviso", "success");
                }else{
                    $funcionUtil.notificarException("Ocurrio un error, el mensajes es: "+response["RC_DESC"]+" Transacci\u00F3n: "+$funcionUtil.validarNull(response["TRANSACCION"]), "fa-warning", "Aviso", "warning");
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-handshake-o").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });


    $local.$btnActivar.on("click", function() {
        var btn = $(this);
        var criterio = $formActivar.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/activacion-tarjeta?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-power-off").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-power-off").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });


    $local.$btnBloqueo.on("click", function() {
        var btn = $(this);
        if (!$formBloqueo.valid()) {
            return;
        }
        var criterio = $formBloqueo.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/bloqueo-tarjeta?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-ban").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-ban").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$btnReasignar.on("click", function() {
        var btn = $(this);
        if (!$formReasignar.valid()) {
            return;
        }
        var criterio = $formReasignar.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/reasignacion-tarjeta?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-random").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-random").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$btnEmitirTarjetaVirtual.on("click", function() {
        var btn = $(this);
        if (!$formEmitirTarjetaVirtual.valid()) {
            return;
        }
        var criterio = $formEmitirTarjetaVirtual.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/emitir-tarjeta-virtual?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-credit-card-alt").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-credit-card-alt").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$btnHabilita.on("click", function() {
        var btn = $(this);
        var criterio = $formHabilita.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/habilita-tarjeta-fisica?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-power-off").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-power-off").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$btnRecarga.on("click", function() {
        var btn = $(this);
        if (!$formRecarga.valid()) {
            return;
        }
        var criterio = $formRecarga.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        criterio.montoBD = criterio.montoRecarga;
        criterio.montoRecarga = $funcionUtil.convertirMontoATexto(criterio.montoRecarga);
        criterio.montoComision = $funcionUtil.convertirMontoATexto(criterio.montoComision);
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/recarga?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-credit-card-alt").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-credit-card-alt").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$btnDebito.on("click", function() {
        var btn = $(this);
        if (!$formDebito.valid()) {
            return;
        }
        var criterio = $formDebito.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        criterio.montoBD = criterio.montoDebito;
        criterio.montoDebito = $funcionUtil.convertirMontoATexto(criterio.montoDebito);
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/debito?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-credit-card-alt").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-credit-card-alt").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$btnTransferencia.on("click", function() {
        var btn = $(this);
        if (!$formTransferencia.valid()) {
            return;
        }
        var criterio = $formTransferencia.serializeJSON();
        criterio.codigoSeguimiento = $local.dataSeleccionada.codigoSeguimiento;
        criterio.montoBD = criterio.montoTransferencia;
        criterio.montoTransferencia = $funcionUtil.convertirMontoATexto(criterio.montoTransferencia);
        var paramCriterioBusqueda = $.param(criterio);
        $.ajax({
            type : "GET",
            url : $variableUtil.root + "txnsWebServices/api/transferencia?"+paramCriterioBusqueda,
            contentType : "application/json",
            dataType : "json",
            beforeSend : function(xhr) {
                btn.attr("disabled", true).find("i").removeClass("fa-credit-card-alt").addClass("fa-spinner fa-pulse fa-fw");
            },
            success : function(response) {
                if(response != null){
                    $funcionUtil.notificarException("Se genero una pre autorizaci\u00F3n, con el Id: <b> " + response + "</b>, consulte con su administrador para la aprobaci\u00F3n de la operaci\u00F3n.", "fa-lock", "Pre Autorizaci\u00F3n", "success");
                    //setTimeout({}, 1500);
                    $local.$modalAdministracionConsulta.PopupWindow("close");
                    return;
                }
            },
            error : function(response) {
            },
            complete : function(response) {
                btn.attr("disabled", false).find("i").addClass("fa-credit-card-alt").removeClass("fa-spinner fa-pulse fa-fw");
            }
        });
    });

    $local.$exportarPorCriterio.on("click", function() {
        const rangoFechasProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasProceso);
        let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
        if(rangoFechasProceso.fechaInicio.length != 0 && rangoFechasProceso.fechaFin.length != 0){
            criterioBusqueda.fechaInicioProceso = new Date(rangoFechasProceso.fechaInicio.replaceAll('-', '/'));
            criterioBusqueda.fechaFinProceso = new Date(rangoFechasProceso.fechaFin.replaceAll('-', '/'));
            criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
        }
        criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionTarjetaPP.find("option:selected").text();
        criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaTarjetaPP.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresaTarjetaPP.find("option:selected").text();
        criterioBusqueda.descripcionCliente = !!$local.$selectClienteTarjetaPP.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteTarjetaPP, "; ") : "TODOS";
        criterioBusqueda.descripcionLogo = $local.$selectLogoTarjetaPP.find("option:selected").val() === "-1" ? "" : $local.$selectLogoTarjetaPP.find("option:selected").text();
        criterioBusqueda.descripcionProducto = !!$local.$selectProductoTarjetaPP.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectProductoTarjetaPP, "; ") : "TODOS";
        if(criterioBusqueda.codigoSeguimiento.length == 0 && criterioBusqueda.numeroTarjeta.length == 0 && criterioBusqueda.nombreCompleto.length == 0){
            if(criterioBusqueda.fechaProceso.length == 0){
                criterioBusqueda.idInstitucion = 99999;
            }
        }
        let params = $.param(criterioBusqueda);
        params += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTarjetaPP, "clientes");
        params += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectProductoTarjetaPP, "productos");
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}consulta/administrativa/tarjetasPP?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterios&${params}`;
    });

    $local.$exportarPorTipoDocumento.on("click", function() {
        let criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
        criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find("option:selected").val() === "" ? "" : $local.$selectTipoDocumento.find("option:selected").text();
        const params = $.param(criterioBusqueda);
        $funcionUtil.webSocketMethod('exportacionPOI');
        window.location.href = `${$variableUtil.root}consulta/administrativa/tarjetasPP?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorTipoDocumento&${params}`;
    });

});