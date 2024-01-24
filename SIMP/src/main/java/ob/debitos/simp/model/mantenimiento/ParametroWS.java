package ob.debitos.simp.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ParametroWS
{

    private String pathHostWS;

    private String llaveInstalacion;

    private String llaveTransporteZMK;

    private String llaveTrabajoZPK;

    private String tokenParaHabilitarRestWS;

    private int tiempoExpiracionTokenEnMinutos;

    private int tiempoTimeOutUbaEnSegundos;

    private String pathBaseParaConsultaDesdeSIMPWeb;

    private String servidorLogWS;

    private String ipsPermitidas;

    private Integer filtrarIps;

}
