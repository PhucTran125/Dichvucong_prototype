/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.docsearch.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author gpdn_huantv
 */
@Entity
@Table(name = "DECLARATION")
public class Declaration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "declarationSeq")
    @SequenceGenerator(name = "declarationSeq", sequenceName = "DECLARATION_SEQ", allocationSize = 1)
    @Column(name = "DECLARATION_ID")
    private Long declarationId;
    @Column(name = "DECLARATION_PORTAL_ID")
    private Long declarationPortalId;
    @Column(name = "OLD_DECLARATION_ID")
    private Long oldDeclarationId;
    @Column(name = "OBJECT_REQUEST_ID")
    private Long objectRequestId;
    @Basic(optional = false)
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "OTHER_NAME")
    private String otherName;
    @Column(name = "GENDER_ID")
    private Long genderId;
    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "BIRTH_PLACE_ID")
    private BigInteger birthPlaceId;
    @Column(name = "NATIONALITY_ID")
    private Long nationalityId;
    @Column(name = "ETHNIC_ID")
    private Long ethnicId;
    @Column(name = "RESIDENCE")
    private String residence;
    @Column(name = "RE_REGION_ID")
    private Long reRegionId;
    @Column(name = "RESIDENCE_TEMPORARY")
    private String residenceTemporary;
    @Column(name = "RT_REGION_ID")
    private Long rtRegionId;
    @Column(name = "ID_TYPE_ID")
    private Long idTypeId;
    @Column(name = "IDENTIFY_NO")
    private String identifyNo;
    @Column(name = "ID_ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date idIssueDate;
    @Column(name = "ID_ISSUE_PLACE")
    private String idIssuePlace;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DAD_NAME")
    private String dadName;
    @Column(name = "DAD_DOB")
    private String dadDob;
    @Column(name = "MOM_NAME")
    private String momName;
    @Column(name = "MOM_DOB")
    private String momDob;
    @Column(name = "PARTNER_NAME")
    private String partnerName;
    @Column(name = "PARTNER_DOB")
    private String partnerDob;
    @Column(name = "APPOINTMENT_NO")
    private String appointmentNo;
    @Column(name = "DECLARE_TYPE_ID")
    private Long declareTypeId;
    @Column(name = "AGENCY_REQUEST_ID")
    private Long agencyRequestId;
    @Column(name = "REGION_REQUEST_ID")
    private Long regionRequestId;
    @Column(name = "FORM_TYPE")
    private String formType;
    @Column(name = "REQUEST_QTY")
    private Long requestQty;
    @Column(name = "CONTENT_REQUEST")
    private String contentRequest;
    @Column(name = "APPOINTMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDate;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "DECLARE_JUDGMENT")
    private String declareJudgment;
    @Column(name = "IS_BAN_POSITION")
    private String isBanPosition;
    @Column(name = "CREATED_BY")
    private Long createdBy;
    @Column(name = "APPROVE_BY")
    private Long approveBy;
    @Column(name = "APPROVE_DATE")
    @Temporal(TemporalType.DATE)
    private Date approveDate;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "MODIFIED_BY")
    private Long modifiedBy;
    @Column(name = "MODIFY_DATE")
    @Temporal(TemporalType.DATE)
    private Date modifyDate;
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @Basic(optional = false)
    @Column(name = "MINISTRY_JUSTICE_ID")
    private long ministryJusticeId;
    @Column(name = "DECLARE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date declareDate;
    @Column(name = "SEND_BY")
    private Long sendBy;
    @Column(name = "SEND_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "RECEIVE_BY")
    private Long receiveBy;
    @Column(name = "RECEIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiveDate;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "BIRTH_PLACE")
    private String birthPlace;
    @Column(name = "BIRTH_DATE_STR")
    private String birthDateStr;
    @Column(name = "AGE")
    private Long age;
    @Column(name = "ANALYST")
    private String analyst;
    @Column(name = "DATE_INVITATION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInvitation;
    @Column(name = "CONTENT_INVITATION")
    private String contentInvitation;
    @Column(name = "PURPOSE")
    private String purpose;
    @Column(name = "FEE")
    private Long fee;
    @Column(name = "DELIVERY")
    private Long delivery;
    @Column(name = "DELIVERY_FEE")
    private Long deliveryFee;
    @Column(name = "DELIVERY_NO")
    private Long deliveryNo;
    @Column(name = "DELIVERY_DATE")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;
    @Column(name = "DELIVERY_DISTRICT")
    private String deliveryDistrict;
    @Column(name = "TRANSLATE")
    private Long translate;
    @Column(name = "TRANSLATE_NO")
    private Long translateNo;
    @Column(name = "TRANSLATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date translateDate;
    @Column(name = "TRANSLATE_FEE")
    private Long translateFee;
    @Column(name = "TRANSLATE_AMOUNT")
    private Long translateAmount;
    @Column(name = "TOTAL_FEE")
    private Long totalFee;
    @Column(name = "TRANSLATE_NOTE")
    private String translateNote;
    @Column(name = "DELIVERY_NOTE")
    private String deliveryNote;
    @Column(name = "RECEIPT_NO")
    private Long receiptNo;
    @Column(name = "REQUEST_QTY_ADD")
    private Long requestQtyAdd;
    @Column(name = "FEE_ADD")
    private Long feeAdd;
    @Column(name = "POST_DATE")
    @Temporal(TemporalType.DATE)
    private Date postDate;
    @Column(name = "LANGUAGE_ID")
    private Long languageId;
    @Column(name = "SEND_TTTT_DATE")
    @Temporal(TemporalType.DATE)
    private Date sendTtttDate;
    @Column(name = "SEND_TTTT_NO")
    private Long sendTtttNo;
    @Column(name = "POST_NO")
    private Long postNo;
    @Column(name = "RECEIVE_LLTP_DATE")
    @Temporal(TemporalType.DATE)
    private Date receiveLltpDate;
    @Column(name = "RECEIVE_LLTP_NO")
    private Long receiveLltpNo;
    @Column(name = "REFERENCE_ID")
    private Long referenceId;
    @Column(name = "GIVE_PROFILE_TYPE")
    private Long giveProfileType;
    @Column(name = "VTP_CODE_BACK")
    private String vtpCodeBack;
    @Column(name = "POST_TYPE")
    private Long postType;
    @Column(name = "IS_SEND_VTP")
    private Long isSendVtp;
    @Column(name = "IS_SEND_BLDT")
    private Long isSendBldt;
    @Column(name = "RECEIPT_NO_CIRCLE")
    private Long receiptNoCircle;
    @Column(name = "DAILY_NO")
    private Long dailyNo;
    @Column(name = "PORTAL_GATE_SEND")
    private String portalGateSend;
    @Column(name = "IS_DEC_FORM")
    private String isDecForm;
    @Column(name = "IS_IDENTIFY")
    private String isIdentify;
    @Column(name = "IS_FAMILY_REGISTER")
    private String isFamilyRegister;
    @Column(name = "IS_OTHERS")
    private String isOthers;
    @Column(name = "EXECUTION_TIME")
    private String executionTime;
    @Column(name = "PROFILE_AMOUNT")
    private String profileAmount;
    @Column(name = "IS_GET_STATUS")
    private String isGetStatus;
    @Column(name = "IS_GET_INFO")
    private String isGetInfo;
    @Column(name = "NUMBER_SORRY")
    private Long numberSorry;
    @Column(name = "IS_SYNC_DEC_STP")
    private Long isSyncDecStp;
    //ky so 14/10/2023
    @Column(name = "SIGN_STATUS")
    private Long signStatus;
    @Column(name = "REASON_FOR_REFUSAL")
    private String reasonForRefusal;
    @Column(name = "SIGNER_USB")
    private Long signerUsb;
    @Column(name = "DAD_IDENTIFY_TYPE")
    private Long dadIdentifyType;
    @Column(name = "DAD_IDENTIFY_NO")
    private String dadIdentifyNo;
    @Column(name = "MOM_IDENTIFY_TYPE")
    private Long momIdentifyType;
    @Column(name = "MOM_IDENTIFY_NO")
    private String momIdentifyNo;
    @Column(name = "COUPLE_IDENTIFY_TYPE")
    private Long coupleIdentifyType;
    @Column(name = "COUPLE_IDENTIFY_NO")
    private String coupleIdentifyNo;
    @Column(name = "DATE_SIGN_USB")
    private Date dateSignUsb;
    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;
    @Column(name = "NATIONALYTY_NAME")
    private String nationalName;
    @Column(name = "ETHNIC_NAME")
    private String ethnicName;
    @Column(name = "CITY_NAME")
    private String cityName;
    @Column(name = "SOURCE_RECEIVE")
    private String sourceReceive;
    @Column(name = "RECEIVE_NO")
    private String receiveNo;
    @Column(name = "SP_REGIS_BIRTH_PLACE_NAME")
    private String spRegisBirthPlaceName;
    @Column(name = "SP_REGIS_BIRTH_PLACE")
    private Long spRegisBirthPlace;
    @Column(name = "PURPOSE_CODE")
    private String purposeCode;
    @Column(name = "CITY_ID")
    private Long cityId;
    @Column(name = "RT_WARD_NAME")
    private String rtWardName;
    @Column(name = "RE_WARD_NAME")
    private String reWardName;
    @Column(name = "REGIS_PLACE_WARD_NAME")
    private String regisPlaceWardName;
    @Column(name = "RT_CITY_ID")
    private Long rtCityId;
    @Column(name = "RE_CITY_ID")
    private Long reCityId;
    @Column(name = "REGIS_PLACE_CITY_ID")
    private Long regisPlaceCityId;
    @Column(name = "REGIS_BIRTH_PLACE_NATIONAL")
    private String regisBirthPlaceNational;
    @Column(name = "SIGNATURE_XML")
    private String signatureXml;
    @Column(name = "IS_SIGN_STATUS_XML")
    private Long isSignStatusXml;
    @Column(name = "RE_WARD_ID")
    private Long reWardId;
    @Column(name = "RT_WARD_ID")
    private Long rtWardId;
    @Column(name = "BIRTH_PLACE_WARD_ID")
    private Long birthPlaceWardId;
    @Column(name = "RESIDENCE_DETAIL")
    private String residenceDetail;
    @Column(name = "RESIDENCE_TEMPORARY_DETAIL")
    private String residenceTemporaryDetail;
    @Column(name = "RESIGN_CONTENT")
    private String resignContent;
    @Column(name = "IS_GET_BCA")
    private Long isGetBCA;
    @Column(name = "IS_SYNC_BCA")
    private Long isSyncBca;

    @Column(name = "FORM_OF_RESULT")
    private Long formOfResult;

    @Column(name = "ADDRESS_RECEIVE_RESULT")
    private String addressReceiveResult;

    @Column(name = "FORM_OF_RECEIVE")
    private Long formOfReceive;

//    @Column(name = "ADDRESS_RECEIVE")
//    private String addressReceive;


    @Column(name = "IS_PENDING")
    private Long isPending;
    @Column(name = "REASON_PENDING")
    private String reasonPending;
    @Column(name = "DATE_PENDING")
    private Date datePending;
    @Column(name = "REASON_CONTINUE")
    private String reasonContinue;

    @Column(name = "ADDITIONAL_NOTE")
    private String additionalNote;

    @Column(name = "ADDITIONAL_REASON")
    private String additionalReason;

    @Column(name = "REASON_REJECT")
    private String reasonReject;

    @Column(name = "REASON_EXTEND")
    private String reasonExtend;


    @Column(name = "FEE_PAYER")
    private Long freePayer;


    @Column(name = "ADDITIONAL_DATE")
    @Temporal(TemporalType.DATE)
    private Date additionalDate;

    @Column(name = "IS_UPDATE_STATUS")
    private Long isUpdateStatus;

    @Column(name = "EXTEND_BY")
    private Long extendBy;

    @Column(name = "EXTEND_DATE")
    @Temporal(TemporalType.DATE)
    private Date extendDate;

    @Column(name = "LAST_SEND_STATUS")
    private Long lastSendStatus;

    @Column(name = "ID_DVCQG")
    private String idDvcqg;

    @Column(name = "RECEIPIENT_ADDRESS")
    private String receipientAddress;

    @Column(name = "RECIPIENT_WARD_CODE")
    private String recipientWardCode;

    @Column(name = "MATTHC")
    private String maTthc;

    @Column(name = "LOAIYEUCAUBOSUNG")
    private Long loaiYeuCaubosung;

    public String getMaTthc() {
        return maTthc;
    }

    public void setMaTthc(String maTthc) {
        this.maTthc = maTthc;
    }

    public Long getLoaiYeuCaubosung() {
        return loaiYeuCaubosung;
    }

    public void setLoaiYeuCaubosung(Long loaiYeuCaubosung) {
        this.loaiYeuCaubosung = loaiYeuCaubosung;
    }

    public String getReceipientAddress() {
        return receipientAddress;
    }

    public void setReceipientAddress(String receipientAddress) {
        this.receipientAddress = receipientAddress;
    }

    public String getRecipientWardCode() {
        return recipientWardCode;
    }

    public void setRecipientWardCode(String recipientWardCode) {
        this.recipientWardCode = recipientWardCode;
    }

    public String getIdDvcqg() {
        return idDvcqg;
    }

    public void setIdDvcqg(String idDvcqg) {
        this.idDvcqg = idDvcqg;
    }

    public Long getIsUpdateStatus() {
        return isUpdateStatus;
    }

    public void setIsUpdateStatus(Long isUpdateStatus) {
        this.isUpdateStatus = isUpdateStatus;
    }

    public Long getExtendBy() {
        return extendBy;
    }

    public void setExtendBy(Long extendBy) {
        this.extendBy = extendBy;
    }

    public Date getExtendDate() {
        return extendDate;
    }

    public void setExtendDate(Date extendDate) {
        this.extendDate = extendDate;
    }

    public Long getLastSendStatus() {
        return lastSendStatus;
    }

    public void setLastSendStatus(Long lastSendStatus) {
        this.lastSendStatus = lastSendStatus;
    }

    public Date getAdditionalDate() {
        return additionalDate;
    }

    public void setAdditionalDate(Date additionalDate) {
        this.additionalDate = additionalDate;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public String getAdditionalReason() {
        return additionalReason;
    }

    public void setAdditionalReason(String additionalReason) {
        this.additionalReason = additionalReason;
    }

    public String getReasonReject() {
        return reasonReject;
    }

    public void setReasonReject(String reasonReject) {
        this.reasonReject = reasonReject;
    }

    public String getReasonExtend() {
        return reasonExtend;
    }

    public void setReasonExtend(String reasonExtend) {
        this.reasonExtend = reasonExtend;
    }

    public Long getFreePayer() {
        return freePayer;
    }

    public void setFreePayer(Long freePayer) {
        this.freePayer = freePayer;
    }

    public Long getIsPending() {
        return isPending;
    }

    public void setIsPending(Long isPending) {
        this.isPending = isPending;
    }

    public String getReasonPending() {
        return reasonPending;
    }

    public void setReasonPending(String reasonPending) {
        this.reasonPending = reasonPending;
    }

    public Date getDatePending() {
        return datePending;
    }

    public void setDatePending(Date datePending) {
        this.datePending = datePending;
    }

    public String getReasonContinue() {
        return reasonContinue;
    }

    public void setReasonContinue(String reasonContinue) {
        this.reasonContinue = reasonContinue;
    }

    public String getAddressReceiveResult() {
        return addressReceiveResult;
    }

    public void setAddressReceiveResult(String addressReceiveResult) {
        this.addressReceiveResult = addressReceiveResult;
    }

    public Long getFormOfResult() {
        return formOfResult;
    }

    public void setFormOfResult(Long formOfResult) {
        this.formOfResult = formOfResult;
    }

    public Long getFormOfReceive() {
        return formOfReceive;
    }

    public void setFormOfReceive(Long formOfReceive) {
        this.formOfReceive = formOfReceive;
    }

//    public String getAddressReceive() {
//        return addressReceive;
//    }
//
//    public void setAddressReceive(String addressReceive) {
//        this.addressReceive = addressReceive;
//    }

    public Long getIsSyncBca() {
        return isSyncBca;
    }

    public void setIsSyncBca(Long isSyncBca) {
        this.isSyncBca = isSyncBca;
    }

    public Long getIsGetBCA() {
        return isGetBCA;
    }

    public void setIsGetBCA(Long isGetBCA) {
        this.isGetBCA = isGetBCA;
    }
    public Long getIsSignStatusXml() {
        return isSignStatusXml;
    }

    public void setIsSignStatusXml(Long isSignStatusXml) {
        this.isSignStatusXml = isSignStatusXml;
    }

    public String getSignatureXml() {
        return signatureXml;
    }

    public void setSignatureXml(String signatureXml) {
        this.signatureXml = signatureXml;
    }

    public String getRtWardName() {
        return rtWardName;
    }

    public void setRtWardName(String rtWardName) {
        this.rtWardName = rtWardName;
    }

    public String getReWardName() {
        return reWardName;
    }

    public void setReWardName(String reWardName) {
        this.reWardName = reWardName;
    }

    public String getRegisPlaceWardName() {
        return regisPlaceWardName;
    }

    public void setRegisPlaceWardName(String regisPlaceWardName) {
        this.regisPlaceWardName = regisPlaceWardName;
    }

    public Long getRtCityId() {
        return rtCityId;
    }

    public void setRtCityId(Long rtCityId) {
        this.rtCityId = rtCityId;
    }

    public Long getReCityId() {
        return reCityId;
    }

    public void setReCityId(Long reCityId) {
        this.reCityId = reCityId;
    }

    public Long getRegisPlaceCityId() {
        return regisPlaceCityId;
    }

    public void setRegisPlaceCityId(Long regisPlaceCityId) {
        this.regisPlaceCityId = regisPlaceCityId;
    }

    public String getRegisBirthPlaceNational() {
        return regisBirthPlaceNational;
    }

    public void setRegisBirthPlaceNational(String regisBirthPlaceNational) {
        this.regisBirthPlaceNational = regisBirthPlaceNational;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public Long getDadIdentifyType() {
        return dadIdentifyType;
    }

    public void setDadIdentifyType(Long dadIdentifyType) {
        this.dadIdentifyType = dadIdentifyType;
    }

    public String getDadIdentifyNo() {
        return dadIdentifyNo;
    }

    public void setDadIdentifyNo(String dadIdentifyNo) {
        this.dadIdentifyNo = dadIdentifyNo;
    }

    public Long getMomIdentifyType() {
        return momIdentifyType;
    }

    public void setMomIdentifyType(Long momIdentifyType) {
        this.momIdentifyType = momIdentifyType;
    }

    public String getMomIdentifyNo() {
        return momIdentifyNo;
    }

    public void setMomIdentifyNo(String momIdentifyNo) {
        this.momIdentifyNo = momIdentifyNo;
    }

    public Long getCoupleIdentifyType() {
        return coupleIdentifyType;
    }

    public void setCoupleIdentifyType(Long coupleIdentifyType) {
        this.coupleIdentifyType = coupleIdentifyType;
    }

    public String getCoupleIdentifyNo() {
        return coupleIdentifyNo;
    }

    public void setCoupleIdentifyNo(String coupleIdentifyNo) {
        this.coupleIdentifyNo = coupleIdentifyNo;
    }

    public String getSpRegisBirthPlaceName() {
        return spRegisBirthPlaceName;
    }

    public void setSpRegisBirthPlaceName(String spRegisBirthPlaceName) {
        this.spRegisBirthPlaceName = spRegisBirthPlaceName;
    }

    public Long getSpRegisBirthPlace() {
        return spRegisBirthPlace;
    }

    public void setSpRegisBirthPlace(Long spRegisBirthPlace) {
        this.spRegisBirthPlace = spRegisBirthPlace;
    }

    private transient String language;
    private transient String Address;
    private transient String appointmentNoPost;
    private transient String languageIdStr;
    private transient String deliveryDistrictStr;


    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getNationalName() {
        return nationalName;
    }

    public void setNationalName(String nationalName) {
        this.nationalName = nationalName;
    }

    public String getEthnicName() {
        return ethnicName;
    }

    public void setEthnicName(String ethnicName) {
        this.ethnicName = ethnicName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getReWardId() {
        return reWardId;
    }

    public void setReWardId(Long reWardId) {
        this.reWardId = reWardId;
    }

    public Long getRtWardId() {
        return rtWardId;
    }

    public void setRtWardId(Long rtWardId) {
        this.rtWardId = rtWardId;
    }

    public Long getBirthPlaceWardId() {
        return birthPlaceWardId;
    }

    public void setBirthPlaceWardId(Long birthPlaceWardId) {
        this.birthPlaceWardId = birthPlaceWardId;
    }

    public String getSourceReceive() {
        return sourceReceive;
    }

    public void setSourceReceive(String sourceReceive) {
        this.sourceReceive = sourceReceive;
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public Long getRequestQtyAdd() {
        return requestQtyAdd;
    }

    public void setRequestQtyAdd(Long requestQtyAdd) {
        this.requestQtyAdd = requestQtyAdd;
    }

    public Long getFeeAdd() {
        return feeAdd;
    }

    public void setFeeAdd(Long feeAdd) {
        this.feeAdd = feeAdd;
    }

    public String getContentInvitation() {
        return contentInvitation;
    }

    public void setContentInvitation(String contentInvitation) {
        this.contentInvitation = contentInvitation;
    }

    public Date getDateInvitation() {
        return dateInvitation;
    }

    public void setDateInvitation(Date dateInvitation) {
        this.dateInvitation = dateInvitation;
    }
    @Column(name = "JUSTICE_NO")
    private String justiceNo;
    @Column(name = "JP_PERSONAL_PROFILE_ID")
    private Long jpPersonalProfileId;

    public Declaration() {
    }

    public Declaration(Long declarationId) {
        this.declarationId = declarationId;
    }

    public Declaration(Long declarationId, String fullName, long ministryJusticeId) {
        this.declarationId = declarationId;
        this.fullName = fullName;
        this.ministryJusticeId = ministryJusticeId;
    }

    public Long getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(Long declarationId) {
        this.declarationId = declarationId;
    }

    public Long getObjectRequestId() {
        return objectRequestId;
    }

    public void setObjectRequestId(Long objectRequestId) {
        this.objectRequestId = objectRequestId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public BigInteger getBirthPlaceId() {
        return birthPlaceId;
    }

    public void setBirthPlaceId(BigInteger birthPlaceId) {
        this.birthPlaceId = birthPlaceId;
    }

    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public Long getEthnicId() {
        return ethnicId;
    }

    public void setEthnicId(Long ethnicId) {
        this.ethnicId = ethnicId;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public Long getReRegionId() {
        return reRegionId;
    }

    public void setReRegionId(Long reRegionId) {
        this.reRegionId = reRegionId;
    }

    public String getResidenceTemporary() {
        return residenceTemporary;
    }

    public void setResidenceTemporary(String residenceTemporary) {
        this.residenceTemporary = residenceTemporary;
    }

    public Long getRtRegionId() {
        return rtRegionId;
    }

    public void setRtRegionId(Long rtRegionId) {
        this.rtRegionId = rtRegionId;
    }

    public Long getIdTypeId() {
        return idTypeId;
    }

    public void setIdTypeId(Long idTypeId) {
        this.idTypeId = idTypeId;
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdIssuePlace() {
        return idIssuePlace;
    }

    public void setIdIssuePlace(String idIssuePlace) {
        this.idIssuePlace = idIssuePlace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDadName() {
        return dadName;
    }

    public void setDadName(String dadName) {
        this.dadName = dadName;
    }

    public String getDadDob() {
        return dadDob;
    }

    public void setDadDob(String dadDob) {
        this.dadDob = dadDob;
    }

    public String getMomName() {
        return momName;
    }

    public void setMomName(String momName) {
        this.momName = momName;
    }

    public String getMomDob() {
        return momDob;
    }

    public void setMomDob(String momDob) {
        this.momDob = momDob;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerDob() {
        return partnerDob;
    }

    public void setPartnerDob(String partnerDob) {
        this.partnerDob = partnerDob;
    }

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public Long getDeclareTypeId() {
        return declareTypeId;
    }

    public void setDeclareTypeId(Long declareTypeId) {
        this.declareTypeId = declareTypeId;
    }

    public Long getAgencyRequestId() {
        return agencyRequestId;
    }

    public void setAgencyRequestId(Long agencyRequestId) {
        this.agencyRequestId = agencyRequestId;
    }

    public Long getRegionRequestId() {
        return regionRequestId;
    }

    public void setRegionRequestId(Long regionRequestId) {
        this.regionRequestId = regionRequestId;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public Long getRequestQty() {
        return requestQty;
    }

    public void setRequestQty(Long requestQty) {
        this.requestQty = requestQty;
    }

    public String getContentRequest() {
        return contentRequest;
    }

    public void setContentRequest(String contentRequest) {
        this.contentRequest = contentRequest;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDeclareJudgment() {
        return declareJudgment;
    }

    public void setDeclareJudgment(String declareJudgment) {
        this.declareJudgment = declareJudgment;
    }

    public String getIsBanPosition() {
        return isBanPosition;
    }

    public void setIsBanPosition(String isBanPosition) {
        this.isBanPosition = isBanPosition;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(Long approveBy) {
        this.approveBy = approveBy;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public long getMinistryJusticeId() {
        return ministryJusticeId;
    }

    public void setMinistryJusticeId(long ministryJusticeId) {
        this.ministryJusticeId = ministryJusticeId;
    }

    public Date getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(Date declareDate) {
        this.declareDate = declareDate;
    }

    public Long getSendBy() {
        return sendBy;
    }

    public void setSendBy(Long sendBy) {
        this.sendBy = sendBy;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getReceiveBy() {
        return receiveBy;
    }

    public void setReceiveBy(Long receiveBy) {
        this.receiveBy = receiveBy;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getPostType() {
        return postType;
    }

    public void setPostType(Long postType) {
        this.postType = postType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (declarationId != null ? declarationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Declaration)) {
            return false;
        }
        Declaration other = (Declaration) object;
        if ((this.declarationId == null && other.declarationId != null) || (this.declarationId != null && !this.declarationId.equals(other.declarationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.viettel.docsearch.domain.Declaration[declarationId=" + declarationId + "]";
    }

    @Transient
    private String objectRequest;
//    @Transient
//    private String birthPlace;
    @Transient
    private String nation;
    @Transient
    private String ethnic;
    @Transient
    private String agencyRequest;
    @Transient
    private String regionRequest;
    @Transient
    private String ministryJustice;
    @Transient
    private String sendByPerson;
    @Transient
    private String receiveByPerson;
    @Transient
    private String createdPerson;
    @Transient
    private String modifiedPerson;

    /**
     * Ham danh cho danh sach y/c xac minh cua phong XL nhan duoc tu phong HC
     *
     * @param dec
     * @param objectRequest
     * @param birthPlace
     * @param nation
     * @param ethnic
     * @param agencyRequest
     * @param regionRequest
     * @param ministryJustice
     * @param sendByPerson
     * @param receiveByPerson
     */
    public Declaration(Declaration dec, String objectRequest, String nation, String ethnic, String agencyRequest,
            String regionRequest, String ministryJustice,
            String sendByPerson, String receiveByPerson,
            String createdPerson, String modifiedPerson,String additionalNote) {

        this.declarationId = dec.declarationId;
        this.objectRequestId = dec.objectRequestId;
        this.fullName = dec.fullName;
        this.otherName = dec.otherName;
        this.genderId = dec.genderId;
        this.birthDate = dec.birthDate;
        this.birthPlaceId = dec.birthPlaceId;
        this.nationalityId = dec.nationalityId;
        this.ethnicId = dec.ethnicId;
        this.residence = dec.residence;
        this.reRegionId = dec.reRegionId;
        this.residenceTemporary = dec.residenceTemporary;
        this.rtRegionId = dec.rtRegionId;
        this.idTypeId = dec.idTypeId;
        this.identifyNo = dec.identifyNo;
        this.idIssueDate = dec.idIssueDate;
        this.idIssuePlace = dec.idIssuePlace;
        this.phone = dec.phone;
        this.email = dec.email;
        this.dadName = dec.dadName;
        this.dadDob = dec.dadDob;
        this.momName = dec.momName;
        this.momDob = dec.momDob;
        this.partnerName = dec.partnerName;
        this.partnerDob = dec.partnerDob;
        this.appointmentNo = dec.appointmentNo;
        this.declareTypeId = dec.declareTypeId;
        this.agencyRequestId = dec.agencyRequestId;
        this.regionRequestId = dec.regionRequestId;
        this.formType = dec.formType;
        this.requestQty = dec.requestQty;
        this.contentRequest = dec.contentRequest;
        this.appointmentDate = dec.appointmentDate;
        this.note = dec.note;
        this.declareJudgment = dec.declareJudgment;
        this.isBanPosition = dec.isBanPosition;
        this.createdBy = dec.createdBy;
        this.approveBy = dec.approveBy;
        this.approveDate = dec.approveDate;
        this.createDate = dec.createDate;
        this.modifiedBy = dec.modifiedBy;
        this.modifyDate = dec.modifyDate;
        this.isActive = dec.isActive;
        this.ministryJusticeId = dec.ministryJusticeId;
        this.declareDate = dec.declareDate;
        this.sendBy = dec.sendBy;
        this.sendDate = dec.sendDate;
        this.status = dec.status;
        this.receiveBy = dec.receiveBy;
        this.receiveDate = dec.receiveDate;
        this.comments = dec.comments;
        this.birthPlace = dec.birthPlace;
        this.birthDateStr = dec.birthDateStr;

        this.objectRequest = objectRequest;
        this.nation = nation;
        this.ethnic = ethnic;
        this.agencyRequest = agencyRequest;
        this.regionRequest = regionRequest;
        this.ministryJustice = ministryJustice;
        this.sendByPerson = sendByPerson;
        this.receiveByPerson = receiveByPerson;
        this.createdPerson = createdPerson;
        this.modifiedPerson = modifiedPerson;
        this.additionalNote = additionalNote;
    }

    public String getAgencyRequest() {
        return agencyRequest;
    }

    public void setAgencyRequest(String agencyRequest) {
        this.agencyRequest = agencyRequest;
    }

    public String getBirthPlace() {
        return this.birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getMinistryJustice() {
        return ministryJustice;
    }

    public void setMinistryJustice(String ministryJustice) {
        this.ministryJustice = ministryJustice;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getReceiveByPerson() {
        return receiveByPerson;
    }

    public void setReceiveByPerson(String receiveByPerson) {
        this.receiveByPerson = receiveByPerson;
    }

    public String getRegionRequest() {
        return regionRequest;
    }

    public void setRegionRequest(String regionRequest) {
        this.regionRequest = regionRequest;
    }

    public String getSendByPerson() {
        return sendByPerson;
    }

    public void setSendByPerson(String sendByPerson) {
        this.sendByPerson = sendByPerson;
    }

    public String getObjectRequest() {
        return objectRequest;
    }

    public void setObjectRequest(String objectRequest) {
        this.objectRequest = objectRequest;
    }

    public String getCreatedPerson() {
        return createdPerson;
    }

    public void setCreatedPerson(String createdPerson) {
        this.createdPerson = createdPerson;
    }

    public String getModifiedPerson() {
        return modifiedPerson;
    }

    public void setModifiedPerson(String modifiedPerson) {
        this.modifiedPerson = modifiedPerson;
    }

    public String getBirthDateStr() {
        return birthDateStr;
    }

    public void setBirthDateStr(String birthDateStr) {
        this.birthDateStr = birthDateStr;
    }

    public Long getOldDeclarationId() {
        return oldDeclarationId;
    }

    public void setOldDeclarationId(Long oldDeclarationId) {
        this.oldDeclarationId = oldDeclarationId;
    }

    public String getAnalyst() {
        return analyst;
    }

    public void setAnalyst(String analyst) {
        this.analyst = analyst;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getJpPersonalProfileId() {
        return jpPersonalProfileId;
    }

    public void setJpPersonalProfileId(Long jpPersonalProfileId) {
        this.jpPersonalProfileId = jpPersonalProfileId;
    }

    public String getJusticeNo() {
        return justiceNo;
    }

    public void setJusticeNo(String justiceNo) {
        this.justiceNo = justiceNo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getDelivery() {
        return delivery;
    }

    public void setDelivery(Long delivery) {
        this.delivery = delivery;
    }

    public Long getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Long getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(Long deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }

    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict = deliveryDistrict;
    }

    public Long getTranslate() {
        return translate;
    }

    public void setTranslate(Long translate) {
        this.translate = translate;
    }

    public Long getTranslateNo() {
        return translateNo;
    }

    public void setTranslateNo(Long translateNo) {
        this.translateNo = translateNo;
    }

    public Date getTranslateDate() {
        return translateDate;
    }

    public void setTranslateDate(Date translateDate) {
        this.translateDate = translateDate;
    }

    public Long getTranslateFee() {
        return translateFee;
    }

    public void setTranslateFee(Long translateFee) {
        this.translateFee = translateFee;
    }

    public Long getTranslateAmount() {
        return translateAmount;
    }

    public void setTranslateAmount(Long translateAmount) {
        this.translateAmount = translateAmount;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getTranslateNote() {
        return translateNote;
    }

    public void setTranslateNote(String translateNote) {
        this.translateNote = translateNote;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Date getSendTtttDate() {
        return sendTtttDate;
    }

    public void setSendTtttDate(Date sendTtttDate) {
        this.sendTtttDate = sendTtttDate;
    }

    public Long getSendTtttNo() {
        return sendTtttNo;
    }

    public void setSendTtttNo(Long sendTtttNo) {
        this.sendTtttNo = sendTtttNo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public Long getPostNo() {
        return postNo;
    }

    public void setPostNo(Long postNo) {
        this.postNo = postNo;
    }

    public String getAppointmentNoPost() {
        return appointmentNoPost;
    }

    public void setAppointmentNoPost(String appointmentNoPost) {
        this.appointmentNoPost = appointmentNoPost;
    }

    public Date getReceiveLltpDate() {
        return receiveLltpDate;
    }

    public void setReceiveLltpDate(Date receiveLltpDate) {
        this.receiveLltpDate = receiveLltpDate;
    }

    public Long getReceiveLltpNo() {
        return receiveLltpNo;
    }

    public void setReceiveLltpNo(Long receiveLltpNo) {
        this.receiveLltpNo = receiveLltpNo;
    }

    public Long getDeclarationPortalId() {
        return declarationPortalId;
    }

    public void setDeclarationPortalId(Long declarationPortalId) {
        this.declarationPortalId = declarationPortalId;
    }

    public Long getGiveProfileType() {
        return giveProfileType;
    }

    public void setGiveProfileType(Long giveProfileType) {
        this.giveProfileType = giveProfileType;
    }

    public String getVtpCodeBack() {
        return vtpCodeBack;
    }

    public void setVtpCodeBack(String vtpCodeBack) {
        this.vtpCodeBack = vtpCodeBack;
    }

    public Long getIsSendVtp() {
        return isSendVtp;
    }

    public void setIsSendVtp(Long isSendVtp) {
        this.isSendVtp = isSendVtp;
    }

    public Long getIsSendBldt() {
        return isSendBldt;
    }

    public void setIsSendBldt(Long isSendBldt) {
        this.isSendBldt = isSendBldt;
    }

    public String getLanguageIdStr() {
        return languageIdStr;
    }

    public void setLanguageIdStr(String languageIdStr) {
        this.languageIdStr = languageIdStr;
    }

    public String getDeliveryDistrictStr() {
        return deliveryDistrictStr;
    }

    public void setDeliveryDistrictStr(String deliveryDistrictStr) {
        this.deliveryDistrictStr = deliveryDistrictStr;
    }

    public Long getReceiptNoCircle() {
        return receiptNoCircle;
    }

    public void setReceiptNoCircle(Long receiptNoCircle) {
        this.receiptNoCircle = receiptNoCircle;
    }

    public Long getDailyNo() {
        return dailyNo;
    }

    public void setDailyNo(Long dailyNo) {
        this.dailyNo = dailyNo;
    }

    public String getPortalGateSend() {
        return portalGateSend;
    }

    public void setPortalGateSend(String portalGateSend) {
        this.portalGateSend = portalGateSend;
    }

    public String getIsDecForm() {
        return isDecForm;
    }

    public void setIsDecForm(String isDecForm) {
        this.isDecForm = isDecForm;
    }

    public String getIsIdentify() {
        return isIdentify;
    }

    public void setIsIdentify(String isIdentify) {
        this.isIdentify = isIdentify;
    }

    public String getIsFamilyRegister() {
        return isFamilyRegister;
    }

    public void setIsFamilyRegister(String isFamilyRegister) {
        this.isFamilyRegister = isFamilyRegister;
    }

    public String getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(String isOthers) {
        this.isOthers = isOthers;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getProfileAmount() {
        return profileAmount;
    }

    public void setProfileAmount(String profileAmount) {
        this.profileAmount = profileAmount;
    }

    public String getIsGetStatus() {
        return isGetStatus;
    }

    public void setIsGetStatus(String isGetStatus) {
        this.isGetStatus = isGetStatus;
    }

    public String getIsGetInfo() {
        return isGetInfo;
    }

    public void setIsGetInfo(String isGetInfo) {
        this.isGetInfo = isGetInfo;
    }

    public Long getNumberSorry() {
        return numberSorry;
    }

    public void setNumberSorry(Long numberSorry) {
        this.numberSorry = numberSorry;
    }

    public Long getIsSyncDecStp() {
        return isSyncDecStp;
    }

    public void setIsSyncDecStp(Long isSyncDecStp) {
        this.isSyncDecStp = isSyncDecStp;
    }

    public Long getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Long signStatus) {
        this.signStatus = signStatus;
    }

    public String getReasonForRefusal() {
        return reasonForRefusal;
    }

    public void setReasonForRefusal(String reasonForRefusal) {
        this.reasonForRefusal = reasonForRefusal;
    }

    public Long getSignerUsb() {
        return signerUsb;
    }

    public void setSignerUsb(Long signerUsb) {
        this.signerUsb = signerUsb;
    }

    public Date getDateSignUsb() {
        return dateSignUsb;
    }

    public void setDateSignUsb(Date dateSignUsb) {
        this.dateSignUsb = dateSignUsb;
    }

    public String getResidenceDetail() {
        return residenceDetail;
    }

    public void setResidenceDetail(String residenceDetail) {
        this.residenceDetail = residenceDetail;
    }

    public String getResidenceTemporaryDetail() {
        return residenceTemporaryDetail;
    }

    public void setResidenceTemporaryDetail(String residenceTemporaryDetail) {
        this.residenceTemporaryDetail = residenceTemporaryDetail;
    }

    public String getResignContent() {
        return resignContent;
    }

    public void setResignContent(String resignContent) {
        this.resignContent = resignContent;
    }

    // ----- Child collections (active rows only). Mapped by BanPosition/Judgment.declarationId.
    //       @SQLRestriction is the Hibernate 6 replacement for the legacy @Where("is_Active=1").
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "declarationId")
    @SQLRestriction("is_Active=1")
    @JsonIgnore
    private Collection<BanPosition> banPositionCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "declarationId")
    @SQLRestriction("is_Active=1")
    @JsonIgnore
    private Collection<Judgment> judgmentCollection;

    public Collection<BanPosition> getBanPositionCollection() {
        return banPositionCollection;
    }

    public void setBanPositionCollection(Collection<BanPosition> banPositionCollection) {
        this.banPositionCollection = banPositionCollection;
    }

    public Collection<Judgment> getJudgmentCollection() {
        return judgmentCollection;
    }

    public void setJudgmentCollection(Collection<Judgment> judgmentCollection) {
        this.judgmentCollection = judgmentCollection;
    }
}
