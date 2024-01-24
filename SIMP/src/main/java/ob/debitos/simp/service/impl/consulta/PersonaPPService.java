package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IPersonaPPMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.DocumentoCliente;
import ob.debitos.simp.model.prepago.PersonaPP;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.prepago.wshub.ParametrosEntrada;
import ob.debitos.simp.service.IPersonaPPService;

@Service
public class PersonaPPService implements IPersonaPPService
{

    private @Autowired IPersonaPPMapper personaPPMapper;

    @Override
    public List<PersonaPP> buscarTodos()
    {
        return personaPPMapper.buscarTodos();
    }

    @Override
    public List<PersonaPP> obtenerPersonasLote(int nIdLote)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String buscarCodigoUBAPorNumeroDocumento(ParametrosEntrada parametrosEntrada)
    {
        return personaPPMapper.buscarCodigoUBAPorNumeroDocumento(CriterioBusquedaTipoDocumentoPrepago.builder().tipoDocumento(parametrosEntrada.getTipoDocumento()).numeroDocumento(parametrosEntrada.getNumeroDocumento()).build());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PersonaPP> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        return this.personaPPMapper.buscarPorTipoDocumento(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer existePersonaPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        return this.personaPPMapper.existePersonaPorTipoDocumento(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PersonaPP> buscarPorCriterios(CriterioBusquedaClientePersona criterioBusqueda)
    {
        return this.personaPPMapper.buscarPorCriterios(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PersonaPP buscarPorCodigoUBA(Integer codigoUBA)
    {
        return this.personaPPMapper.buscarPorCodigoUBA(codigoUBA);
    }

    @Override
    public List<DocumentoCliente> existePersonaPorDocumento(CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<personas>");
        int index = 0;
        for (String tipoDoc : criterioBusqueda.getTiposDocumento())
        {
            xmlString.append("<persona ");
            xmlString.append("tipoDoc='");
            xmlString.append(tipoDoc);
            xmlString.append("' ");
            xmlString.append("numDoc='");
            xmlString.append(criterioBusqueda.getNumerosDocumento().get(index));
            xmlString.append("' ");
            xmlString.append("/>");
            index++;
        }
        xmlString.append("</personas>");
        criterioBusqueda.setXmlString(xmlString.toString());
        return this.personaPPMapper.existePersonaPorDocumento(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PersonaPP> buscarPorCriteriosPP(CriterioBusquedaClientePersona criterioBusqueda)
    {
        return this.personaPPMapper.buscarPorCriteriosPP(criterioBusqueda);
    }

}