package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsLiberadas;
import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.ISaldoMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaSaldo;
import ob.debitos.simp.model.prepago.Saldo;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.service.ISaldoService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Truncable(clase = Saldo.class)
public class SaldoService implements ISaldoService
{

    private @Autowired ISaldoMapper saldoMapper;

    private @Autowired IExportacionPOIService<Saldo> exportSaldo;

    private static final String[][] cabeceraExportacion = {
            {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
            {"Logo", "idLogo", "descLogoBin", "formatCadena", "-1"},
            {"Producto", "codigoProducto", "descProducto", "formatCadena", "-1"},
            {"Tipo Documento", "tipoDocumento", "descripcionTipoDocumento", "formatCadena", "-1"},
            {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
            {"ID Persona", "idPersona", "", "formatCadena", "-1"},
            {"ID Cuenta", "idCuenta", "", "formatCadena", "-1"},
            {"Nombres", "nombres", "", "formatCadena", "-1"},
            {"Apellido Paterno", "apellidoPaterno", "", "formatCadena", "-1"},
            {"Apellido Materno", "apellidoMaterno", "", "formatCadena", "-1"},
            {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
            {"Estado Tarjeta", "estado", "descEstado", "formatCadena", "-1"},
            {"Moneda", "moneda", "descMoneda", "formatCadena", "-1"},
            {"Saldo Disponible", "saldoDisponible", "", "formatMonto", "-1"}
    };

    @Override
    @Truncable
    public List<Saldo> buscarTodos()
    {
        return saldoMapper.buscarTodos();
    }

    @Override
    @Truncable
    public List<Saldo> buscarPorCriterio(CriterioBusquedaSaldo criterioBusquedaSaldo)
    {
        return this.saldoMapper.buscarPorCriterio(criterioBusquedaSaldo);
    }

    @Override
    @Truncable
    public List<Saldo> buscarPorTipoDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumentoPrepago)
    {
        return this.saldoMapper.buscarPorTipoDocumento(criterioBusquedaTipoDocumentoPrepago);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarSaldoPorCriterios(List<Saldo> list, CriterioBusquedaSaldo criterioBusquedaSaldo, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso", criterioBusquedaSaldo.getDescripcionRangoFechasProceso()},
                {"Institución", criterioBusquedaSaldo.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaSaldo.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaSaldo.getDescripcionCliente()},
                {"Logo", criterioBusquedaSaldo.getDescripcionLogo()},
                {"Producto", criterioBusquedaSaldo.getDescripcionProducto()},
                {"Moneda", criterioBusquedaSaldo.getDescripcionMoneda()},
                {"Número Tarjeta", criterioBusquedaSaldo.getNumeroTarjeta()}
        };
        this.exportSaldo.generarExportacionNormal("Consulta de Saldos - Criterios", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarSaldoPorTipoDocumento(List<Saldo> list, CriterioBusquedaTipoDocumentoPrepago criterioBusquedaSaldo, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaSaldo.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaSaldo.getNumeroDocumento()}
        };
        this.exportSaldo.generarExportacionNormal("Consulta de Saldos - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

}
