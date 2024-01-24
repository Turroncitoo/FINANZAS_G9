package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametroGeneral;
import ob.debitos.simp.model.parametro.Parametro;

public interface IParametroGeneralMapper extends IMantenibleMapper<ParametroGeneral>
{

    @Select("{call MANT_PARAMETROS_GENERALES ( " 
            + "#{verbo,                             jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.fechaProceso,               jdbcType = DATE,    mode = IN}," 
            + "#{objeto.binRuteoSwitch,             jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion,          jdbcType = INTEGER, mode = IN}," 
            + "#{objeto.surchargeSoles,             jdbcType = NUMERIC, mode = IN}," 
            + "#{objeto.surchargeDolares,           jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.idEmpresa,                  jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.porcentajeIgv,              jdbcType = NUMERIC, mode = IN}," 
            + "#{objeto.tiempoReprogramacion,       jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.activoSimpBatch,            jdbcType = BIT,     mode = IN}," 
            + "#{objeto.procesaObservadasManual,    jdbcType = BIT,     mode = IN}," 
            + "#{objeto.repositorioArchivoContable, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit,                         jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<ParametroGeneral> mantener(Parametro parametro);

}