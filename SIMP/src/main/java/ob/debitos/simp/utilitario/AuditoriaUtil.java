package ob.debitos.simp.utilitario;

public class AuditoriaUtil
{
    public static final String DATOS_BIN = "Código_de_BIN = #bin.codigoBIN, Descripción = #bin.descripcion";
    public static final String DATOS_EMPRESA = "Código_de_Empresa = #empresa.idEmpresa, Descripción = #empresa.descripcion";
    public static final String DATOS_INSTITUCION = "Código_de_Institución = #institucion.codigoInstitucion, Descripción = #institucion.descripcion";
    public static final String DATOS_CLIENTE = "Código_de_Cliente = #cliente.idCliente, Código_de_Empresa = #cliente.idEmpresa, Descripción = #cliente.descripcion";
    public static final String DATOS_SUBBIN = "Código_de_SubBIN = #subBin.codigoSubBIN, Código_de_BIN = #subBin.codigoBIN, Descripción = #subBin.descripcion";
    public static final String DATOS_SUBBINCLIENTE = "Código_de_SubBIN = #subBin.codigoSubBIN, Código_de_BIN = #subBin.codigoBIN, Código_de_Cliente = #subBin.idCliente";
    public static final String DATOS_MEMBRESIA = "Código_de_Membresía = #membresia.codigoMembresia, Descripción = #membresia.descripcion";
    public static final String DATOS_CLASE_SERVICIO = "Código_de_Clase_de_Servicio = #claseServicio.codigoClaseServicio, Código_de_Membresía=#claseServicio.codigoMembresia,Descripción = #claseServicio.descripcion";
}