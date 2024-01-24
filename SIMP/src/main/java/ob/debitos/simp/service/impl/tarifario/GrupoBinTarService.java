package ob.debitos.simp.service.impl.tarifario;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IGrupoBinTarMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.tarifario.GrupoBinTar;
import ob.debitos.simp.service.IGrupoBinTarService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class GrupoBinTarService extends MantenibleService<GrupoBinTar>
        implements IGrupoBinTarService
{
    @SuppressWarnings("unused")
    private IGrupoBinTarMapper grupoBinTarMapper;

    public GrupoBinTarService(
            @Qualifier("IGrupoBinTarMapper") IMantenibleMapper<GrupoBinTar> mapper)
    {
        super(mapper);
        this.grupoBinTarMapper = (IGrupoBinTarMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<GrupoBinTar> buscarTodos()
    {
        return this.buscar(new GrupoBinTar(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<GrupoBinTar> buscarPorGrupoBin(int grupoBin)
    {
        GrupoBinTar grupoBinTar = GrupoBinTar.builder().grupoBin(grupoBin).build();
        return this.buscar(grupoBinTar, Verbo.GET);
    }
}