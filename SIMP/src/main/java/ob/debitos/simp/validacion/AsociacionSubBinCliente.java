package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.AsociacionSubBinClienteValidator;

@Documented
@Constraint(validatedBy = AsociacionSubBinClienteValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AsociacionSubBinCliente.List.class)
public @interface AsociacionSubBinCliente
{

    String message() default "{Existe.SubBin.codigoSubBIN}";

    boolean existe();

    String campoCodigoBin() default "codigoBIN";

    String campoCodigoSubBin() default "codigoSubBIN";

    String campoIdCliente() default "idCliente";
    
    String campoIdEmpresa() default "idEmpresa";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        AsociacionSubBinCliente[] value();
    }

}