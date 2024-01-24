package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.model.consulta.movimiento.TransaccionObservada;
import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IConsultaAjusteMapper;
import ob.debitos.simp.model.ajuste.TxnsAjuste;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAjuste;
import ob.debitos.simp.service.IConsultaAjusteService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ConsultaAjusteService implements IConsultaAjusteService
{

    private @Autowired IConsultaAjusteMapper consultaMapper;

    private @Autowired IExportacionPOIService<TxnsAjuste> exportTxnsAjustes;

    private static final String[][] cabeceraExportacion = {
            {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
            {"Rol Transacción", "rolTransaccion", "descripcionRolTransaccion", "formatCadena", "-1"},
            {"Membresía", "membresia", "descMembresia", "formatCadena", "-1"},
            {"Servicio", "claseServicio", "descClaseServicio", "formatCadena", "-1"},
            {"Origen", "origen", "descOrigen", "formatCadena", "-1"},
            {"Clase Transacción", "claseTransaccion", "descClaseTransaccion", "formatCadena", "-1"},
            {"Código Transacción", "codigoTransaccion", "descCodigoTransaccion", "formatCadena", "-1"},
            {"Código Proceso", "proceso", "descProceso", "formatCadena", "-1"},
            {"Código Respuesta", "codigoRespuesta", "descCodigoRespuesta", "formatCadena", "-1"},
            {"Canal", "canal", "descCanal", "formatCadena", "-1"},
            {"Tipo Documento", "tipoDocumento", "descTipoDocumento", "formatCadena", "-1"},
            {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
            {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
            {"Secuencia", "secuenciaTransaccion", "", "formatCadena", "-1"},
            {"Tipo Movimiento", "tipoMovimiento", "descTipoMovimiento", "formatCadena", "-1"},
            {"Fecha Transacción", "fechaTransaccionCadena", "horaTransaccion", "formatCadenaCentrada", "-1"},
            {"Moneda Compensación", "monedaCompensacion", "descMonedaCompensacion", "formatCadena", "-1"},
            {"Importe Autorización", "valorAutorizacion", "", "formatMonto", "-1"},
            {"Importe Compensación", "valorCompensacion", "", "formatMonto", "-1"},
            {"Importe OIF", "valorOIF", "", "formatMonto", "-1"},
            {"Importe Diferencia", "valorDiferencia", "", "formatMonto", "-1"},
            {"Registro Contable", "registroContable", "descRegistroContable", "formatCadena", "-1"},
            {"Trace", "numeroVoucher", "", "formatCadena", "-1"},
            {"Código Autorización", "autorizacion", "", "formatCadena", "-1"},
            {"Nombre Afiliado", "nombreAfiliado", "", "formatCadena", "-1"},
            {"Ind. Contabilización", "contabiliza", "descContabiliza", "formatCadena", "-1"},
            {"Cta. Cargo", "cuentaCargo", "", "formatCadena", "-1"},
            {"Cta. Abono", "cuentaAbono", "", "formatCadena", "-1"},
            {"Código Analítico", "codigoAnalitico", "", "formatCadena", "-1"}
    };

    @Truncable(clase = TxnsAjuste.class)
    @Override
    public List<TxnsAjuste> buscarTransaccionesAjustesPorCriterios(CriterioBusquedaTransaccionAjuste criterioBusqueda)
    {
        return consultaMapper.buscarTransaccionesAjustesPorCriterios(criterioBusqueda);
    }

    @Truncable(clase = TxnsAjuste.class)
    @Override
    public List<TxnsAjuste> buscarTransaccionesAjustesPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return consultaMapper.buscarTransaccionesAjustesPorTipoDocumento(criterioBusquedaTipoDocumento);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsAjustesPorCriterios(List<TxnsAjuste> list, CriterioBusquedaTransaccionAjuste criterioBusquedaTransaccionAjuste, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso", criterioBusquedaTransaccionAjuste.getDescripcionRangoFechasProceso()},
                {"Institución", criterioBusquedaTransaccionAjuste.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTransaccionAjuste.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaTransaccionAjuste.getDescripcionCliente()},
                {"Número Tarjeta", criterioBusquedaTransaccionAjuste.getNumeroTarjeta()},
                {"Trace", criterioBusquedaTransaccionAjuste.getNumeroVoucher()},
        };
        this.exportTxnsAjustes.generarExportacionNormal("Consulta de Ajustes - Criterios", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsAjustesPorTipoDocumento(List<TxnsAjuste> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionAjuste, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaTransaccionAjuste.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaTransaccionAjuste.getNumeroDocumento()}
        };
        this.exportTxnsAjustes.generarExportacionNormal("Consulta de Ajustes - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }
}
