package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ConceptoComision;

public interface IConceptoComisionService extends IMantenibleService<ConceptoComision>
{

    public List<ConceptoComision> buscarTodos();

    public List<ConceptoComision> buscarPorIdConcepto(int idConceptoComision);

    public void registrarConceptoComision(ConceptoComision conceptoComision);

    public void actualizarConceptoComision(ConceptoComision conceptoComision);

    public boolean existeConceptoComision(int idConceptoComision);

    public List<ConceptoComision> buscarTodosRolEmisor();

}
