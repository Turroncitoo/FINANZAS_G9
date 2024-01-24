package ob.debitos.simp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.service.IMantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class MantenibleService<T> implements IMantenibleService<T>
{
    protected IMantenibleMapper<T> mantenimientoMapper;

    public MantenibleService(IMantenibleMapper<T> mapper)
    {
        this.mantenimientoMapper = mapper;
    }

    public MantenibleService()
    {
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void registrar(T dto)
    {
        mantenimientoMapper.mantener(
                new Parametro(Verbo.INSERT, dto, SecurityContextFacade.obtenerNombreUsuario()));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void registrar(T dto, String verbo)
    {
        mantenimientoMapper
                .mantener(new Parametro(verbo, dto, SecurityContextFacade.obtenerNombreUsuario()));
    }
    
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<T> registrarAutoIncrementable(T dto)
    {
        return mantenimientoMapper.mantener(
                new Parametro(Verbo.INSERT, dto, SecurityContextFacade.obtenerNombreUsuario()));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void actualizar(T dto)
    {
        mantenimientoMapper.mantener(
                new Parametro(Verbo.UPDATE, dto, SecurityContextFacade.obtenerNombreUsuario()));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void actualizar(T dto, String verbo)
    {
        mantenimientoMapper
                .mantener(new Parametro(verbo, dto, SecurityContextFacade.obtenerNombreUsuario()));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void eliminar(T dto)
    {
        mantenimientoMapper.mantener(new Parametro(Verbo.REMOVE, dto));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void eliminar(T dto, String verbo)
    {
        mantenimientoMapper.mantener(new Parametro(verbo, dto));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<T> buscar(T dto, String verbo)
    {
        return mantenimientoMapper.mantener(new Parametro(verbo, dto));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public boolean existe(T dto)
    {
        return !this.buscar(dto, Verbo.EXIST).isEmpty();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mantener(T dto, String verbo)
    {
        mantenimientoMapper
                .mantener(new Parametro(verbo, dto, SecurityContextFacade.obtenerNombreUsuario()));
    }
}