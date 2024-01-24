package ob.debitos.simp.model.parametro;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Parametro
{
    private String verbo;
    private Object objeto;
    private String userAudit;

    public Parametro(String verbo, Object objeto)
    {
        this.verbo = verbo;
        this.objeto = objeto;
    }

    public Parametro(String verbo, Object objeto, String userAudit)
    {
        this.verbo = verbo;
        this.objeto = objeto;
        this.userAudit = userAudit;
    }
}
