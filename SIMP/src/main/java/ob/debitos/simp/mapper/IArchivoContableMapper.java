package ob.debitos.simp.mapper;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.model.proceso.ArchivoContable;

public interface IArchivoContableMapper
{
    public List<ArchivoContable> buscarResumenTransacciones(Date fechaProceso);
}