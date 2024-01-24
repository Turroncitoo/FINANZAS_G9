package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.prepago.DescripcionDTO;

public interface IDescripcionService {
	public List<DescripcionDTO> obtenerNacionalidades();

	public List<DescripcionDTO> obtenerNacionalidad(Integer codigoPais);

	public List<DescripcionDTO> obtenerCodigoPorNacionalidad(String nacionalidad);

	public List<DescripcionDTO> obtenerMonedaDenominaciones();

	public List<DescripcionDTO> obtenerDenominacion(Integer codigoPais);

	public List<DescripcionDTO> obtenerMonedaPorDenominacion(String moneda);

	public List<DescripcionDTO> obtenerMonedaSimbolos();

	public List<DescripcionDTO> obtenerTiposDocumento();

	public List<DescripcionDTO> obtenerTipoDocumentoPorCodigo(Integer codigoDocumento);

	public List<DescripcionDTO> obtenerDocumentoPorDescripcion(String documento);

	public List<DescripcionDTO> obtenerDocumentoPorValor(String valor);

	public List<DescripcionDTO> obtenerUrlWebServices();
	
	public List<DescripcionDTO> obtenerEstadosLotes();
	
	public List<DescripcionDTO> obtenerInstanciasLotes();
	
	public List<DescripcionDTO> obtenerInstitucionesActivas();

}
