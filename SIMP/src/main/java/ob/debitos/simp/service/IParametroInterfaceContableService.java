package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ParametrosInterfaceContable;


public interface IParametroInterfaceContableService  extends IMantenibleService<ParametrosInterfaceContable>{

		public List<ParametrosInterfaceContable> buscarTodos();
		public List<ParametrosInterfaceContable> buscarTodosParaMantenimiento();
		public void actualizarParametroInterfaceContable(ParametrosInterfaceContable parametroInterfaceContable);
		
}
