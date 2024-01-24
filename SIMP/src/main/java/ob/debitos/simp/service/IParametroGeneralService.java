package ob.debitos.simp.service;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.model.mantenimiento.ParametroGeneral;

public interface IParametroGeneralService extends IMantenibleService<ParametroGeneral>
{

    public int buscarCodigoInstitucion();

    public Date buscarFechaProceso();

    public String buscarIdEmpresa();
    
    public List<ParametroGeneral> buscarinstitucionParametroGeneral();

    public List<ParametroGeneral> buscarTodos();

    public void registrarParametroGeneral(ParametroGeneral parametroGeneral);

    public void actualizarParametroGeneral(ParametroGeneral parametroGeneral);

    public String buscarRepositorioArchivoContable();

    public int buscarProcesoObservadasManual();

    public String buscarBIN();

    public Byte[] buscarLogo();

}