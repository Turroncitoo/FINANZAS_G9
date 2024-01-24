package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ISubBinMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.SubBin;
import ob.debitos.simp.service.ISubBinService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class SubBinService extends MantenibleService<SubBin> implements ISubBinService
{
    @SuppressWarnings("unused")
    private ISubBinMapper subBinMapper;

    public SubBinService(@Qualifier("ISubBinMapper") IMantenibleMapper<SubBin> mapper)
    {
        super(mapper);
        this.subBinMapper = (ISubBinMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SubBin> buscarTodos()
    {
        return this.buscar(new SubBin(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SubBin> buscarPorCodigoBin(String codigoBIN)
    {
        SubBin subBin = SubBin.builder().codigoBIN(codigoBIN).build();
        return this.buscar(subBin, Verbo.GETS_BIN);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<SubBin> buscarPorCodigoBinCodigoSubBin(String codigoBIN, String codigoSubBIN)
    {
        SubBin subBin = SubBin.builder().codigoBIN(codigoBIN).codigoSubBIN(codigoSubBIN).build();
        return this.buscar(subBin, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeSubBin(String codigoBIN, String codigoSubBIN)
    {
        return !this.buscarPorCodigoBinCodigoSubBin(codigoBIN, codigoSubBIN).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarSubBin(SubBin subBin)
    {
        this.registrar(subBin);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarSubBin(SubBin subBin)
    {
        this.actualizar(subBin);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarSubBin(SubBin subBin)
    {
        this.eliminar(subBin);
    }
}