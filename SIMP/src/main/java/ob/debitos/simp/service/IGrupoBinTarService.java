package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.tarifario.GrupoBinTar;

public interface IGrupoBinTarService extends IMantenibleService<GrupoBinTar>
{
    public List<GrupoBinTar> buscarTodos();

    public List<GrupoBinTar> buscarPorGrupoBin(int grupoBin);
}