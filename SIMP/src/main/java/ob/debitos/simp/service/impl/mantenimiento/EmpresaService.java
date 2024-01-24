package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IEmpresaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Empresa;
import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class EmpresaService extends MantenibleService<Empresa> implements IEmpresaService
{
    @SuppressWarnings("unused")
    private IEmpresaMapper empresaMapper;

    public EmpresaService(@Qualifier("IEmpresaMapper") IMantenibleMapper<Empresa> mapper)
    {
        super(mapper);
        this.empresaMapper = (IEmpresaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Empresa> buscarTodos()
    {
        return this.buscar(new Empresa(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Empresa> buscarPorIdEmpresa(String idEmpresa)
    {
        Empresa empresa = Empresa.builder().idEmpresa(idEmpresa).build();
        return this.buscar(empresa, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeEmpresa(String idEmpresa)
    {
        return !this.buscarPorIdEmpresa(idEmpresa).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarEmpresa(Empresa empresa)
    {
        this.registrar(empresa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarEmpresa(Empresa empresa)
    {
        this.actualizar(empresa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarEmpresa(Empresa empresa)
    {
        this.eliminar(empresa);
    }
}