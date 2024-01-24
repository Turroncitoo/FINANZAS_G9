package ob.debitos.simp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IDescripcionMapper;
import ob.debitos.simp.model.prepago.DescripcionDTO;
import ob.debitos.simp.service.IDescripcionService;

@Service
public class DescripcionService implements IDescripcionService {

	@Autowired
	private IDescripcionMapper descripcionMapper;

	@Override
	public List<DescripcionDTO> obtenerNacionalidades() {
		return descripcionMapper.obtenerNacionalidades();
	}

	@Override
	public List<DescripcionDTO> obtenerNacionalidad(Integer codigoPais) {
		return descripcionMapper.obtenerNacionalidad(codigoPais);
	}

	@Override
	public List<DescripcionDTO> obtenerCodigoPorNacionalidad(String nacionalidad) {
		return descripcionMapper.obtenerCodigoPorNacionalidad(nacionalidad);
	}

	@Override
	public List<DescripcionDTO> obtenerMonedaDenominaciones() {
		return descripcionMapper.obtenerMonedaDenominaciones();
	}

	@Override
	public List<DescripcionDTO> obtenerDenominacion(Integer codigoPais) {
		return descripcionMapper.obtenerDenominacion(codigoPais);
	}

	@Override
	public List<DescripcionDTO> obtenerMonedaPorDenominacion(String moneda) {
		return descripcionMapper.obtenerMonedaPorDenominacion(moneda);
	}

	@Override
	public List<DescripcionDTO> obtenerMonedaSimbolos() {
		return descripcionMapper.obtenerMonedaSimbolos();
	}

	public List<DescripcionDTO> obtenerTiposDocumento() {
		return descripcionMapper.obtenerTiposDocumento();
	}

	@Override
	public List<DescripcionDTO> obtenerTipoDocumentoPorCodigo(Integer codigoDocumento) {
		return descripcionMapper.obtenerTipoDocumentoPorCodigo(codigoDocumento);
	}

	@Override
	public List<DescripcionDTO> obtenerDocumentoPorDescripcion(String documento) {
		return descripcionMapper.obtenerDocumentoPorDescripcion(documento);
	}

	@Override
	public List<DescripcionDTO> obtenerDocumentoPorValor(String valor) {
		return descripcionMapper.obtenerDocumentoPorValor(valor);
	}

	@Override
	public List<DescripcionDTO> obtenerUrlWebServices() {
		return descripcionMapper.obtenerUrlWebServices();
	}

	@Override
	public List<DescripcionDTO> obtenerEstadosLotes() {
		return descripcionMapper.obtenerEstadosLotes();
	}

	@Override
	public List<DescripcionDTO> obtenerInstanciasLotes() {
		return descripcionMapper.obtenerInstitucionesActivas();
	}

	@Override
	public List<DescripcionDTO> obtenerInstitucionesActivas() {
		return descripcionMapper.obtenerInstitucionesActivas();
	}
}
