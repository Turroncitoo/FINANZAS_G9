package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.model.prepago.DescripcionDTO;

public interface IDescripcionMapper {
	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 3 And [nIdSubTabla] = 4 And [nIdElemento] <> 0" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerNacionalidades();

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 3 And [nIdSubTabla] = 4 And [nIdElemento] = #{codigoPais}" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerNacionalidad(@Param("codigoPais") Integer codigoPais);

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 3 And [nIdSubTabla] = 4 And [vValor] = #{valor}" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerCodigoPorNacionalidad(@Param("valor") String valor);

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 3 And [nIdSubTabla] = 3 And [nIdElemento] <> 0" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerMonedaDenominaciones();

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 3 And [nIdSubTabla] = 3 And [nIdElemento] = #{codigoPais}" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerDenominacion(@Param("codigoPais") Integer codigoPais);

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 3 And [nIdSubTabla] = 3 And [vValor] = #{valor}" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerMonedaPorDenominacion(@Param("valor") String valor);

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 3 And [nIdSubTabla] = 2 And [nIdElemento] <> 0" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerMonedaSimbolos();

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 2 And [nIdSubTabla] = 1 And [nIdElemento] <> 0" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerTiposDocumento();

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 2 And [nIdSubTabla] = 1 And [nIdElemento] = #{codigoDoc}" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerTipoDocumentoPorCodigo(@Param("codigoDoc") Integer codigoDocumento);

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 2 And [nIdSubTabla] = 1 And [vDescripcion] = #{descripcion}" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerDocumentoPorDescripcion(@Param("descripcion") String descripcion);

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 2 And [nIdSubTabla] = 1 And [vValor] = #{valor}" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerDocumentoPorValor(@Param("valor") String valor);

	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 4 And [nIdSubTabla] = 1 And [nIdElemento] = 3" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerUrlWebServices();

	@Select(value = { "Select * from MaeParametros "
			+ "where [nIdTabla] = 5 and [nIdSubTabla] = 1 And [nIdElemento] <> 0" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerEstadosLotes();
	
	@Select(value = { "Select * From MaeParametros "
			+ "Where [nIdTabla] = 5 And [nIdSubTabla] = 2 And [nIdElemento] <> 0" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerInstanciasLotes();
	
	@Select(value = { "Select * from MaeParametros "
			+ "where [nIdTabla] = 6 and [nIdSubTabla] = 1 And [nIdElemento] <> 0" })
	@ResultMap("mapDescripciones")
	@Options(statementType = StatementType.CALLABLE)
	public List<DescripcionDTO> obtenerInstitucionesActivas();

}
