package ob.debitos.simp.model.consulta.movimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsSwDmpLogDetallado
{
    private String vMessageType;
    private String vIndStoreForward;
    @NoIdentificable(campo="vAccountNumber")
    private String vAccountNumber;
    private String vProcessingCode;
    private Double nTransactionAmount;
    private Double nSettlementAmount;
    private Double nCardIssuerAmount;
    private String dTransmissionDate;
    private String vTransmissionTime;
    private Integer nConversionRateSettlement;
    private Integer nConversionRateBilling;
    private String vTraceNumber;
    private String vLocalTransactionTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private String dLocalTransactionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private String dExpiryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date dSetllementDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date dConversionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private String dCaptureDate;
    private String vMerchantType;
    private String vPosEntryMode;
    private String vNetworkIdentifier;
    private String vPosConditionCode;
    private Double nSettAmountTransactionFee;
    private Double nSettAmountProcessingFee;
    private String vAcquiringInstitution;
    private String vForwardingInstitution;
    private String vTrack2Data;
    private String vReferenceNumber;
    private String vAuthorizationCode;
    private String nResponseCode;
    private String vCardAcceptorTermId;
    private String vCardAcceptorLocation;
    private Integer nTransactionCurrency;
    private Integer nSettlementCurrency;
    private Integer nBillingCurrency;
    private String vOriginalMessageType;
    private String vOriginalTraceNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date dOriginalDate;
    private String vOriginalTime;
    private String vOriginalAdquiringInst;
    private String vOriginalForwardingInst;
    private Double nReplaTransactionAmount;
    private Double nReplaSettlementAmount;
    private Double nReplaTransactionFee;
    private Double nReplaSettlementFee;
    private String vMerchant;
    private String vRequestingInstitution;
    private String vAccountIdentification1;
    private String vAccountIdentification2;
    private String vCardCategory;
    private String vTierII;
    private String vCardAcceptorId;
    private Integer nConciliaAutorizacion;
    private Integer nConciliaLogContable;
    private String nRolTransaccion;
    private String cIdClaseServicio;
    private String cIdMembresia;
    private String nIdOrigen;
    private String nIdCodigoTransaccion;
    private String nIdClaseTransaccion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date dFechaProceso;
    private String vIdCliente;
    private String vIdEmpresa;
    private String vIdProceso;
    private String nIdCanal;
    private String vIdBIN;
    private String vCuentaFrom;
    private String vCuentaTo;
    private String vNumeroDocumento;
    private String vCuentaCargo;
    private String vCuentaAbono;
    private String vCodigoAnalitico;
    private Integer nContabFondos;
    private String nIdATM;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private String dFechaConciliaLog;
    private String vIdSubBIN;
    private String nIdInstitucion;
    private String vPANEntryMode;
    private String vPINEntryCapability;
    private String nInstitucionEmisora;
    private String nInstitucionReceptora;
    private Integer nSecuenciaSIMP;
    private String codigoSeguimiento;
}