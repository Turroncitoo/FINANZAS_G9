package ob.debitos.simp.utilitario;

import java.util.ArrayList;
import java.util.List;
import ob.debitos.simp.model.mantenimiento.CodigoRespuestaSwitch;


public class FiltrarDatos {
	public static final String ERROR_UBA = "UBAErroresCode";
	public static final String ERROR_LOCAL = "localErroresCode";
	
	public static List<CodigoRespuestaSwitch> obtenerPorTipoError(List<CodigoRespuestaSwitch> lista,String tipoError)
	    {	List<CodigoRespuestaSwitch> listaPorTipoError = new ArrayList<CodigoRespuestaSwitch>();
	    	
		 
	    	
	    	
	    	switch(tipoError){
		    	case ERROR_UBA:{
		    		 for (CodigoRespuestaSwitch codigoRpta  : lista){
		 	        	
		    			 if((("0".compareTo(codigoRpta.getCodigoRespuestaSwitch()) < 0 || "0".compareTo(codigoRpta.getCodigoRespuestaSwitch()) == 0) && 
		    			     (codigoRpta.getCodigoRespuestaSwitch().compareTo("99") < 0 || codigoRpta.getCodigoRespuestaSwitch().compareTo("99") == 0))
		    			   |(("200".compareTo(codigoRpta.getCodigoRespuestaSwitch()) < 0 || "200".compareTo(codigoRpta.getCodigoRespuestaSwitch()) == 0) && 
		                     (codigoRpta.getCodigoRespuestaSwitch().compareTo("299") < 0 || codigoRpta.getCodigoRespuestaSwitch().compareTo("299") == 0)))
		    				 listaPorTipoError.add(codigoRpta);
		 	        	
		 	         }
		    	}break;
		    	case ERROR_LOCAL:{
			    	for (CodigoRespuestaSwitch codigoRpta  : lista){
			    	    if(("300".compareTo(codigoRpta.getCodigoRespuestaSwitch()) < 0 || "300".compareTo(codigoRpta.getCodigoRespuestaSwitch()) == 0) && 
	                       (codigoRpta.getCodigoRespuestaSwitch().compareTo("399") < 0 || codigoRpta.getCodigoRespuestaSwitch().compareTo("399") == 0))
			        		listaPorTipoError.add(codigoRpta);
			        	
			        }
		    	}break;
	    	}
	        return listaPorTipoError;
	       
	    }
}
