package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ob.debitos.simp.model.consulta.administrativa.Atm;

public interface IAtmMapper
{
    public List<Atm> buscarTodos();

    public List<Atm> buscarPorCodigoInstitucion(Integer codigoInstitucion);

    public List<Atm> buscarPorIdATM(Integer idATM);

    public List<Atm> buscarPorIdATMCodigoInstitucion(@Param("idATM") Integer idATM,
            @Param("codigoInstitucion") Integer codigoInstitucion);
}
