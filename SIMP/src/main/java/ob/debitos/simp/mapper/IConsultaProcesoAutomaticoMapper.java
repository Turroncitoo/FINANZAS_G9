package ob.debitos.simp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ob.debitos.simp.model.proceso.ConsultaProcesoAutomatico;

public interface IConsultaProcesoAutomaticoMapper
{
    public List<ConsultaProcesoAutomatico> buscarProcesoAutomaticos(@Param("verbo") String verbo,
            @Param("fechaProceso") Date fechaProceso);
}
