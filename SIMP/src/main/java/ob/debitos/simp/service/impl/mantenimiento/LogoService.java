package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ILogoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Logo;
import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class LogoService extends MantenibleService<Logo> implements ILogoService
{

    @SuppressWarnings("unused")
    private ILogoMapper logoMapper;

    public LogoService(@Qualifier("ILogoMapper") IMantenibleMapper<Logo> mapper)
    {
        super(mapper);
        this.logoMapper = (ILogoMapper) mapper;
    }

    @Override
    public List<Logo> buscarTodos()
    {
        return this.buscar(new Logo(), Verbo.GETS_T);
    }

    @Override
    public List<Logo> buscarTodosInstitucion()
    {
        return this.buscar(new Logo(), Verbo.GETS);
    }

    @Override
    public List<Logo> buscarPorIdLogo(String idLogo)
    {
        Logo logo = Logo.builder().idLogo(idLogo).build();
        return this.buscar(logo, Verbo.GET);
    }

    @Override
    public List<Logo> buscarPorCodigoInstitucion(Integer codigoInstitucion)
    {
        Logo logo = Logo.builder().idInstitucion(codigoInstitucion).build();
        return this.buscar(logo, Verbo.GET_INST);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Logo> buscarPorCodigoMembresiaCodigoClaseServicio(String idMembresia, String idClaseServicio, Integer idInstitucion)
    {
        Logo logo = Logo.builder().idMembresia(idMembresia).idClaseServicio(idClaseServicio).idInstitucion(idInstitucion).build();
        return this.buscar(logo, Verbo.GET_IMS);
    }

    @Override
    public void registrarLogo(Logo logo)
    {
        try
        {
            this.registrar(logo);

        } catch (Exception e)
        {
            throw new SimpException(e.getCause().getMessage());
        }
    }

    @Override
    public void actualizarLogo(Logo logo)
    {
        try
        {
            this.actualizar(logo);
        } catch (Exception e)
        {
            throw new SimpException(e.getCause().getMessage());
        }
    }

    @Override
    public void eliminarLogo(Logo logo)
    {
        this.eliminar(logo);
    }

    @Override
    public boolean existeLogo(String idLogo)
    {
        return !this.buscarPorIdLogo(idLogo).isEmpty();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Logo> buscarPorCodigoBIN(String codigoBIN)
    {
        Logo bin = Logo.builder().idBin(codigoBIN).build();
        return this.buscar(bin, Verbo.GET_BIN);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeBin(String codigoBIN)
    {
        return !this.buscarPorCodigoBIN(codigoBIN).isEmpty();
    }

}
