package ob.debitos.simp.controller.consultas.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.DocumentoCliente;
import ob.debitos.simp.service.IPersonaPPService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.VALID_CLIENTES, accion = Accion.Consulta, comentario = Comentario.ConsultaClientes)
@RequestMapping("/clientes")
public @RestController class ClientesController
{

    private @Autowired IPersonaPPService personaPPService;
    private @Autowired Logger logger;

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    // @PreAuthorize("hasPermission('CON_ADMINCTE','ANY')")
    @PostMapping(value = "/validarPorDocumento")
    public List<DocumentoCliente> validarClientePorNumeroDocumento(@RequestBody List<DocumentoCliente> documentoCliente, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        CriterioBusquedaTipoDocumento criterio = new CriterioBusquedaTipoDocumento();
        List<String> listaTipoDocumento = documentoCliente.stream().map(DocumentoCliente::getTipoDocumento).collect(Collectors.toList());
        List<String> listaNumeroDocumento = documentoCliente.stream().map(DocumentoCliente::getNumeroDocumento).collect(Collectors.toList());

        criterio.setTiposDocumento(listaTipoDocumento);
        criterio.setNumerosDocumento(listaNumeroDocumento);

        documentoCliente = this.personaPPService.existePersonaPorDocumento(criterio);
        logger.info("{}", documentoCliente);
        return documentoCliente;
    }

}
