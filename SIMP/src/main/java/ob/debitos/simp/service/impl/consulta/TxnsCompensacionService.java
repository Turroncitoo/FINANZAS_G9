package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.consulta.movimiento.*;
import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.ITxnsCompensacionMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionCompensacion;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsCompensacion;
import ob.debitos.simp.service.ITxnsCompensacionService;
import ob.debitos.simp.utilitario.PaginacionUtil;

@Service
public class TxnsCompensacionService implements ITxnsCompensacionService
{

    private @Autowired ITxnsCompensacionMapper consultaMapper;

    private @Autowired IExportacionPOIService<TxnsCompensacion> exportTxnsCompensacion;

    private static final String[][] cabeceraExportacion = {
            {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
            {"Logo", "descripcionLogoCompleto", "", "formatCadena", "-1"},
            {"Producto", "codigoProducto", "descProducto", "formatCadena", "-1"},
            {"Membresía", "codigoMembresia", "descMembresia", "formatCadena", "-1"},
            {"Servicio", "codigoClaseServicio", "descClaseServicio", "formatCadena", "-1"},
            {"Origen", "codigoOrigen", "descOrigen", "formatCadena", "-1"},
            {"Clase Transacción", "idClaseTransaccion", "descripcionClaseTransaccion", "formatCadena", "-1"},
            {"Código Transacción", "idCodigoTransaccion", "descripcionCodigoTransaccion", "formatCadena", "-1"},
            {"Rol Transacción", "rolTransaccion", "descripcionRol", "formatCadena", "-1"},
            {"Inst. Emisor", "codigoInstitucionEmisor", "descripcionInstitucionEmisor", "formatCadena", "-1"},
            {"Inst. Receptor", "codigoInstitucionReceptor", "descripcionInstitucionReceptor", "formatCadena", "-1"},
            {"Código Respuesta", "codigoRespuestaSwitch", "descripcionCodigoRespuesta", "formatCadena", "-1"},
            {"Canal", "idCanal", "descripcionCanal", "formatCadena", "-1"},
            {"Tipo Documento", "tipoDocumento", "descTipoDocumento", "formatCadena", "-1"},
            {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
            {"Secuencia", "secuenciaTransaccion", "", "formatCadena", "-1"},
            {"Código Seguimiento", "codigoSeguimiento", "", "formatCadena", "-1"},
            {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
            {"Número Cuenta", "numeroCuenta", "", "formatCadena", "-1"},
            {"Estado Tarjeta", "estadoTarjeta", "", "formatCadena", "-1"},
            {"Fecha Transacción", "fechaTransaccionCadena", "horaTransaccion", "formatCadenaCentrada", "-1"},
            {"Trace", "numeroVoucher", "", "formatCadena", "-1"},
            {"Código Autorización", "codigoAutorizacion", "", "formatCadena", "-1"},
            {"Moneda Transacción", "monedaTransaccion", "descMonedaTransaccion", "formatCadena", "-1"},
            {"Importe Transacción", "valorTransaccion", "", "formatMonto", "-1"},
            {"Moneda Autorización", "monedaAutorizacion", "descMonedaAutorizacion", "formatCadena", "-1"},
            {"Importe Autorización", "valorAutorizacion", "", "formatMonto", "-1"},
            {"Moneda Compensación", "monedaCompensacion", "descMonedaCompensacion", "formatCadena", "-1"},
            {"Importe Compensación", "valorCompensacion", "", "formatMonto", "-1"},
            {"Moneda THB", "monedaCargadaThb", "descripcionCodigoMonedaCargadaThb", "formatCadena", "-1"},
            {"Importe THB", "valorCargadoThb", "", "formatMonto", "-1"},
            {"Tipo Cambio", "tipoDeCambio", "", "formatComision", "-1"},
            {"Afiliado", "nombreAfiliado", "", "formatCadena", "-1"},
            {"Número Comprobante", "numeroDocumentoCompensacion", "", "formatCadena", "-1"},
            {"Comp. Fondos", "compensaFondos", "", "formatSiNo", "-1"},
            {"Comp. Comisiones", "compensaComisiones", "", "formatSiNo", "-1"}
    };

    @Override
    @Truncable(clase = TxnsCompensacion.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsCompensacion> buscarTxnsCompensacion(CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsCompensacion> criterioPaginacion)
    {
        criterioPaginacion.setFiltrosDatatable(PaginacionUtil.armarFiltroDatatableLogContable(criterioPaginacion));
        criterioPaginacion.setOrderExpression(PaginacionUtil.obtenerExpresionOrdenamiento(criterioPaginacion));
        return consultaMapper.buscarTxnsCompensacion(criterioPaginacion);
    }

    @Override
    @Truncable(clase = TxnsCompensacion.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsCompensacion> filtrarTxnsCompensacion(CriterioPaginacion<CriterioBusquedaTransaccionCompensacion, FiltroDtTxnsCompensacion> criterioPaginacion)
    {
        criterioPaginacion.setFiltrosDatatable(PaginacionUtil.armarFiltroDatatableLogContable(criterioPaginacion));
        criterioPaginacion.setOrderExpression(PaginacionUtil.obtenerExpresionOrdenamiento(criterioPaginacion));
        return consultaMapper.filtrarTxnsCompensacion(criterioPaginacion);
    }

    @Override
    @Truncable(clase = TxnsCompensacionDetalle.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsCompensacionDetalle> buscarDetalleTxnCompensacion(CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion)
    {
        return consultaMapper.buscarDetalleTxnCompensacion(criterioBusquedaDetalleTransaccion);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnCompensacionComisiones> buscarComisionesPorCompensacion(TxnCompensacionComisiones txnCompensacionComisiones)
    {
        return consultaMapper.buscarComisionesPorCompensacion(txnCompensacionComisiones);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsCompensacionPorCriterios(List<TxnsCompensacion> list, CriterioBusquedaTransaccionCompensacion criterioBusquedaTransaccionCompensacion, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso", criterioBusquedaTransaccionCompensacion.getDescripcionRangoFechasProceso()},
                {"Fecha Transacción", criterioBusquedaTransaccionCompensacion.getDescripcionRangoFechasTransaccion()},
                {"Institución", criterioBusquedaTransaccionCompensacion.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTransaccionCompensacion.getDescripcionEmpresa()},
                {"Clientes", criterioBusquedaTransaccionCompensacion.getDescripcionCliente()},
                {"Roles Transacción", criterioBusquedaTransaccionCompensacion.getDescripcionRolTransaccion()},
                {"Membresía", criterioBusquedaTransaccionCompensacion.getDescripcionMembresia()},
                {"Servicios", criterioBusquedaTransaccionCompensacion.getDescripcionServicio()},
                {"Origenes", criterioBusquedaTransaccionCompensacion.getDescripcionOrigen()},
                {"Clase Transacción", criterioBusquedaTransaccionCompensacion.getDescripcionClaseTransaccion()},
                {"Códigos Transacción", criterioBusquedaTransaccionCompensacion.getDescripcionCodigoTransaccion()},
                {"Logo", criterioBusquedaTransaccionCompensacion.getDescripcionLogo()},
                {"Productos", criterioBusquedaTransaccionCompensacion.getDescripcionProducto()},
                {"Inst. Emisor", criterioBusquedaTransaccionCompensacion.getDescripcionInstitucionEmisor()},
                {"Inst. Receptor", criterioBusquedaTransaccionCompensacion.getDescripcionInstitucionReceptor()},
                {"Código Respuesta", criterioBusquedaTransaccionCompensacion.getDescripcionCodigoRespuesta()},
                {"Canales", criterioBusquedaTransaccionCompensacion.getDescripcionCanal()},
                {"Moneda Transacción", criterioBusquedaTransaccionCompensacion.getDescripcionMonedaTransaccion()},
                {"Número Tarjeta", criterioBusquedaTransaccionCompensacion.getNumeroTarjeta()},
                {"Trace", criterioBusquedaTransaccionCompensacion.getNumeroVoucher()},
                {"Código Autorización", criterioBusquedaTransaccionCompensacion.getCodigoAutorizacion()}
        };
        this.exportTxnsCompensacion.generarExportacionNormal("Consulta de Log Contable - Criterios", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsCompensacionPorTipoDocumento(List<TxnsCompensacion> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionCompensacion, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaTransaccionCompensacion.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaTransaccionCompensacion.getNumeroDocumento()}
        };
        this.exportTxnsCompensacion.generarExportacionNormal("Consulta de Log Contable - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

}