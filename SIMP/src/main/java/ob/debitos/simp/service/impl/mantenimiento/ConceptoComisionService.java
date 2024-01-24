package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IConceptoComisionMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ConceptoComisionService extends MantenibleService<ConceptoComision>
        implements IConceptoComisionService
{

    @SuppressWarnings("unused")
    private IConceptoComisionMapper conceptoComisionMapper;

    public ConceptoComisionService(
            @Qualifier("IConceptoComisionMapper") IMantenibleMapper<ConceptoComision> mapper)
    {
        super(mapper);
        this.conceptoComisionMapper = (IConceptoComisionMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ConceptoComision> buscarTodos()
    {
        return this.buscar(new ConceptoComision(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ConceptoComision> buscarPorIdConcepto(int idConceptoComision)
    {
        ConceptoComision conceptoComision = ConceptoComision.builder()
                .idConceptoComision(idConceptoComision).build();
        return this.buscar(conceptoComision, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarConceptoComision(ConceptoComision conceptoComision)
    {
        this.validar(conceptoComision);
        this.registrar(conceptoComision);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarConceptoComision(ConceptoComision conceptoComision)
    {
        this.validar(conceptoComision);
        this.actualizar(conceptoComision);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeConceptoComision(int idConceptoComision)
    {
        return !this.buscarPorIdConcepto(idConceptoComision).isEmpty();
    }

    void validar(ConceptoComision conceptoComision)
    {
        if (!conceptoComision.getIdConceptoComision().equals(conceptoComision.getTipoComision()))
        {
            throw new SimpException("El Concepto Comision y el Tipo Comision no coinciden");
        }
        if (conceptoComision.getRolEmisor() == 0 && conceptoComision.getRolReceptor() == 0)
        {
            throw new SimpException("El Rol Emisor y el Rol Receptor no pueden estar desactivados");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ConceptoComision> buscarTodosRolEmisor()
    {
        return this.buscar(new ConceptoComision(), Verbo.GETS_EMI);
    }

}