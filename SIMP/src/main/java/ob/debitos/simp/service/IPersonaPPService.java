package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.DocumentoCliente;
import ob.debitos.simp.model.prepago.PersonaPP;
import ob.debitos.simp.model.prepago.wshub.ParametrosEntrada;

public interface IPersonaPPService
{

    public List<PersonaPP> buscarTodos();

    public List<PersonaPP> obtenerPersonasLote(int nIdLote);

    public String buscarCodigoUBAPorNumeroDocumento(ParametrosEntrada parametrosEntrada);

    public List<PersonaPP> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public Integer existePersonaPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<DocumentoCliente> existePersonaPorDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<PersonaPP> buscarPorCriterios(CriterioBusquedaClientePersona criterioBusqueda);

    public PersonaPP buscarPorCodigoUBA(Integer codigoUBA);

    public List<PersonaPP> buscarPorCriteriosPP(CriterioBusquedaClientePersona criterioBusqueda);

}