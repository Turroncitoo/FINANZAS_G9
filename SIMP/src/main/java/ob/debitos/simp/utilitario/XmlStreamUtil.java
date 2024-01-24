package ob.debitos.simp.utilitario;

import java.util.List;

import com.thoughtworks.xstream.XStream;

import ob.debitos.simp.model.mantenimiento.Cliente;
import ob.debitos.simp.model.mantenimiento.SubBin;
import ob.debitos.simp.model.prepago.ControlLotePP;
import ob.debitos.simp.model.prepago.LotePP;
import ob.debitos.simp.model.prepago.PersonaPP;
import ob.debitos.simp.model.prepago.RecargaPP;
import ob.debitos.simp.model.prepago.TarjetaPP;


public class XmlStreamUtil {
	private XStream xStream;

	public XmlStreamUtil() {
		super();
		this.xStream = new XStream();
		xStream.alias("clSubBin", SubBin.class);
		xStream.useAttributeFor(SubBin.class, "codigoBIN");
		xStream.useAttributeFor(SubBin.class, "codigoSubBIN");
		xStream.useAttributeFor(SubBin.class, "codigoCliente");
		xStream.useAttributeFor(SubBin.class, "descripcion");

		xStream.alias("lote", LotePP.class);
		xStream.useAttributeFor(LotePP.class, "nIdLote");
		xStream.useAttributeFor(LotePP.class, "nEstadoLote");
		xStream.useAttributeFor(LotePP.class, "dFechaRegistro");
		xStream.useAttributeFor(LotePP.class, "dFechaModificacion");
		xStream.useAttributeFor(LotePP.class, "nInstancia");
		xStream.useAttributeFor(LotePP.class, "nIdInstitucion");
		xStream.useAttributeFor(LotePP.class, "sNombreArchivo");
		xStream.useAttributeFor(LotePP.class, "nSecuencia");
		xStream.useAttributeFor(LotePP.class, "nTipoAfiliacion");
		xStream.addImplicitCollection(LotePP.class, "lstControlLote");

		xStream.alias("controlLote", ControlLotePP.class);
		xStream.useAttributeFor(ControlLotePP.class, "idControlLote");
		xStream.useAttributeFor(ControlLotePP.class, "idLote");
		xStream.useAttributeFor(ControlLotePP.class, "idSecuencial");
		xStream.useAttributeFor(ControlLotePP.class, "fecHora");
		xStream.useAttributeFor(ControlLotePP.class, "datos");
		xStream.useAttributeFor(ControlLotePP.class, "respCode");

		xStream.alias("persona", PersonaPP.class);
		xStream.useAttributeFor(PersonaPP.class, "idPersona");
		xStream.useAttributeFor(PersonaPP.class, "nombres");
		xStream.useAttributeFor(PersonaPP.class, "apePaterno");
		xStream.useAttributeFor(PersonaPP.class, "apeMaterno");
		xStream.useAttributeFor(PersonaPP.class, "alias");
		xStream.useAttributeFor(PersonaPP.class, "tipoDocumento");
		xStream.useAttributeFor(PersonaPP.class, "numDocumento");
		xStream.useAttributeFor(PersonaPP.class, "telFijo");
		xStream.useAttributeFor(PersonaPP.class, "telMovil");
		xStream.useAttributeFor(PersonaPP.class, "fechaRegistro");
		xStream.useAttributeFor(PersonaPP.class, "fechaNacimiento");
		xStream.useAttributeFor(PersonaPP.class, "codCliente");
		xStream.useAttributeFor(PersonaPP.class, "nomCliente");
		xStream.useAttributeFor(PersonaPP.class, "correoElectronico");
		xStream.useAttributeFor(PersonaPP.class, "nacionalidad");
		xStream.useAttributeFor(PersonaPP.class, "estadoBD");

		xStream.alias("cliente", Cliente.class);
		xStream.useAttributeFor(Cliente.class, "idCliente");
		xStream.useAttributeFor(Cliente.class, "idEmpresa");
		xStream.useAttributeFor(Cliente.class, "descripcion");

		xStream.alias("recarga", RecargaPP.class);
		xStream.useAttributeFor(RecargaPP.class, "idRecarga");
		xStream.useAttributeFor(RecargaPP.class, "idLote");
		xStream.useAttributeFor(RecargaPP.class, "idSecuencial");
		xStream.useAttributeFor(RecargaPP.class, "montoEnviadoo");
		xStream.useAttributeFor(RecargaPP.class, "montoRecibido");
		xStream.useAttributeFor(RecargaPP.class, "moneda");
		xStream.useAttributeFor(RecargaPP.class, "datos");
		xStream.useAttributeFor(RecargaPP.class, "respCode");

		xStream.alias("tarjeta", TarjetaPP.class);
		xStream.useAttributeFor(TarjetaPP.class, "numTarjeta");
		xStream.useAttributeFor(TarjetaPP.class, "codSeguimiento");
		xStream.useAttributeFor(TarjetaPP.class, "estado");
		xStream.useAttributeFor(TarjetaPP.class, "codCuenta");
		xStream.useAttributeFor(TarjetaPP.class, "titularidad");
	}

	public String serializarLote(LotePP piLote) {
		return xStream.toXML(piLote);
	}

	public String serializarControlLote(ControlLotePP piControlLote) {
		return xStream.toXML(piControlLote);
	}

	public String serializarPersona(PersonaPP piPersona) {
		return xStream.toXML(piPersona);
	}

	public String serializarControlLotes(List<ControlLotePP> piControlLotes) {
		return xStream.toXML(piControlLotes);
	}

	public String serializarPersonas(List<PersonaPP> piPersonas) {
		return xStream.toXML(piPersonas);
	}

	public String serializarRecargas(List<RecargaPP> piRecargas) {
		return xStream.toXML(piRecargas);
	}
}
