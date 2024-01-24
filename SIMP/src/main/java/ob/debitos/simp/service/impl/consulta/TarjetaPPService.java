package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITarjetaPPMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.prepago.TarjetaPP;
import ob.debitos.simp.service.ITarjetaPPService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TarjetaPPService implements ITarjetaPPService
{

    private @Autowired ITarjetaPPMapper tarjetaPPMapper;

    private @Autowired IExportacionPOIService<TarjetaPP> exportTarjetaPPService;

    private static final String[][] cabeceraExportacion = {
            {"Fecha Registro", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "idInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descripcionEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descripcionCliente", "formatCadena", "-1"},
            {"Logo", "idLogo", "descLogoBin", "formatCadena", "-1"},
            {"Prodcutos", "codigoProducto", "descProducto", "formatCadena", "-1"},
            {"ID Tarjeta", "idTarjeta", "", "formatCadena", "-1"},
            {"Código Seguimiento", "codigoSeguimiento", "", "formatCadena", "-1"},
            {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
            {"Fecha Activación", "fechaActivacionCadena", "horaActivacion", "formatCadenaCentrada", "-1"},
            {"Estado", "estado", "descripcionEstado", "formatCadena", "-1"},
            {"Fecha Bloqueo", "fechaBloqueoCadena", "horaBloqueo", "formatCadenaCentrada", "-1"},
            {"Tipo Bloqueo", "tipoBloqueo", "descripcionTipoBloqueo", "formatCadena", "-1"},
            {"Posee Dueño", "poseeDuenio", "", "formatCadena", "-1"},
            {"ID Persona", "idPersona", "", "formatCadena", "-1"},
            {"Código UBA", "codigoUBA", "", "formatCadena", "-1"},
            {"Tipo Documento", "tipoDocumento", "descripcionTipoDocumento", "formatCadena", "-1"},
            {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
            {"Nombres", "nombres", "", "formatCadena", "-1"},
            {"Apellido Paterno", "apellidoPaterno", "", "formatCadena", "-1"},
            {"Apellido Materno", "apellidoMaterno", "", "formatCadena", "-1"},
            {"Fecha Orden", "fechaOrden", "", "formatFecha", "-1"},
            {"Tipo Tarjeta", "tipoTarjeta", "descripcionTipoTarjeta", "formatCadena", "-1"},
            {"ID Afinidad", "idAfinidad", "descripcionAfinidad", "formatCadena", "-1"},
            {"Física Nacida Virtual", "tarjetaFisicaNacidaDeVirtual", "", "formatSiNo", "-1"},
            {"Fecha Virtual A Física", "fechaTarjetaFisicaNacidaDeVirtual", "", "formatFecha", "-1"},
            {"Habilitación Virtual A Física", "habilitadaTarjetaFisicaNacidaDeVirtual", "", "formatSiNo", "-1"},
            {"Fecha Habilitación Virtual A Física", "fechaHabilitacionTarjetaFisicaNacidaVirtual", "", "formatFecha", "-1"},
            {"ID Cuenta", "idCuenta", "", "formatCadena", "-1"},
            {"Cuenta Defecto", "cuentaDefecto", "", "formatCadena", "-1"},
            {"Moneda Cuenta", "monedaCuenta", "descripcionMonedaCuenta", "formatCadena", "-1"},
            {"Tipo Cuenta", "tipoCuenta", "", "formatCadena", "-1"},
            {"Titularidad", "titularidad", "", "formatCadena", "-1"},
            {"Cta. Línea 1", "cuentaLinea1EnAtmBcoPlaza", "", "formatCadena", "-1"},
            {"Cta. Línea 2", "cuentaLinea2EnAtmTpoCodCta", "", "formatCadena", "-1"},
            {"Realiza Retiros", "permiteRealizarRetiros", "", "formatSiNo", "-1"},
            {"Realiza Depósito", "permiteRealizarDeposito", "", "formatSiNo", "-1"},
            {"Realiza Transferencia", "permiteRealizarTransferencia", "", "formatSiNo", "-1"},
            {"Recibe Transferencia", "permiteRecibirTransferencia", "", "formatSiNo", "-1"},
            {"Puede Consulta", "permiteConsulta", "", "formatSiNo", "-1"},
            {"ID Lote", "idLote", "", "formatCadena", "-1"},
            {"Trace Lote", "traceLote", "", "formatCadena", "-1"},
            {"Clave ID", "claveId", "", "formatCadena", "-1"}
    };

    @Override
    @Truncable(clase = TarjetaPP.class)
    public List<TarjetaPP> buscarTodos()
    {
        return tarjetaPPMapper.buscarTodos();
    }

    @Truncable(clase = TarjetaPP.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarjetaPP> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        return this.tarjetaPPMapper.buscarPorTipoDocumento(criterioBusqueda);
    }

    @Truncable(clase = TarjetaPP.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarjetaPP> buscarPorCriterios(CriterioBusquedaTarjeta criterioBusqueda)
    {
        return this.tarjetaPPMapper.buscarPorCriterios(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTarjetasPPPorCriterios(List<TarjetaPP> list, CriterioBusquedaTarjeta criterioBusquedaTarjeta, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
    	/*String auxiliarProducto[] = {""};
    	Integer obtenerTamaño[] = {0};
    	Integer auxiliarContador [] = {1};
    	
    	if(criterioBusquedaTarjeta.getProductos() != null) {
    		obtenerTamaño[0] = criterioBusquedaTarjeta.getProductos().size();
    		criterioBusquedaTarjeta.getProductos().forEach(item->
            {
                auxiliarProducto[0] = auxiliarContador[0] == obtenerTamaño[0] ? auxiliarProducto[0].concat(item) : auxiliarProducto[0].concat(item + ", ");
                auxiliarContador[0]++;
            });
    	}*/
    	
        String[][] filtros = {
                {"Fecha Registro", criterioBusquedaTarjeta.getDescripcionRangoFechasProceso()},
                {"Institución", criterioBusquedaTarjeta.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTarjeta.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaTarjeta.getDescripcionCliente()},
                {"Logo", criterioBusquedaTarjeta.getDescripcionLogo()},
                {"Producto", criterioBusquedaTarjeta.getDescripcionProducto()},
                {"Código Seguimiento", criterioBusquedaTarjeta.getCodigoSeguimiento()},
                {"Número Tarjeta", criterioBusquedaTarjeta.getNumeroTarjeta()},
                {"Nombre Completo", criterioBusquedaTarjeta.getNombreCompleto()}
        };
        
        this.exportTarjetaPPService.generarExportacionNormal("Consulta de Tarjetas - Criterios", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTarjetasPPPorTipoDocumento(List<TarjetaPP> list, CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaTipoDocumento.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaTipoDocumento.getNumeroDocumento()}
        };
        this.exportTarjetaPPService.generarExportacionNormal("Consulta de Tarjetas - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }
}
