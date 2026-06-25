/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.docsearch.domain;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author gpdn_huantv
 */
@Entity
@Table(name = "JUDGMENT")
public class Judgment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JUDGMENT_SEQ")
    @SequenceGenerator(name = "JUDGMENT_SEQ", sequenceName = "JUDGMENT_SEQ", allocationSize = 1)
    @Column(name = "JUDGMENT_ID")
    private Long judgmentId;
    @Column(name = "AGENCY_VERIFY_ID")
    private Long agencyVerifyId;
    @Column(name = "JUDGMENT_NO")
    private String judgmentNo;
    @Column(name = "JUDGMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date judgmentDate;
    @Column(name = "COURT_ID")
    private Long courtId;
    @Column(name = "PENALTY_MAIN")
    private String penaltyMain;
    @Column(name = "PENALTY_MAIN_NOTE")
    private String penaltyMainNote;
    @Column(name = "PENALTY_ADD")
    private String penaltyAdd;
    @Column(name = "PENALTY_ADD_NOTE")
    private String penaltyAddNote;
    @Column(name = "LEGAL_COST")
    private String legalCost;
    @Column(name = "CIVIL_OBLIGATION")
    private String civilObligation;
    @Column(name = "EXEC_JUDGMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date execJudgmentDate;
    @Column(name = "EXEC_JUDGMENT_STATUS_ID")
    private Long execJudgmentStatusId;
    @Column(name = "EXEC_JUDGMENT_STATUS")
    private String execJudgmentStatus;
    @Column(name = "IS_USED")
    private String isUsed;
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @Column(name = "CREATED_BY")
    private Long createdBy;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "MODIFIED_BY")
    private Long modifiedBy;
    @Column(name = "MODIFY_DATE")
    @Temporal(TemporalType.DATE)
    private Date modifyDate;
    @Column(name = "REMISSION_DATE")
    @Temporal(TemporalType.DATE)
    private Date remissionDate;
    @Column(name = "CRIME_ID")
    private Long crimeId;
    @Column(name = "CRIME_NAMES")
    private String crimeNames;
    @Column(name = "TERMS")
    private String terms;
    @Basic(optional = false)
    @Column(name = "MINISTRY_JUSTICE_ID")
    private long ministryJusticeId;
    @JoinColumn(name = "DECLARATION_ID", referencedColumnName = "DECLARATION_ID")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Declaration declarationId;
    @Column(name = "PENALTY_ID")
    private String penaltyId;
    @Column(name = "PENALTY_MAIN_ID")
    private String penaltyMainId;
    @Column(name = "SEND_REQUEST_STATUS")
    private String sendRequestStatus;
    @Column(name = "SEND_REQUEST_BY")
    private Long sendRequestBy;
    @Column(name = "SEND_REQUEST_DATE")
    @Temporal(TemporalType.DATE)
    private Date sendRequestDate;
    @Column(name = "IS_PRINT")
    String isPrint;
    @Column(name = "LIST_CRIME_NAME_ID")
    String listCrimeNameId;
    @Column(name = "JUSTICE_NO")
    private String justiceNo;
    @Column(name = "JP_PERSONAL_PROFILE_ID")
    private Long jpPersonalProfileId;
    @Column(name = "EXECUTION_DATE_STR")
    private String executionDateStr;
    @Column(name = "SENTENCING_DATE_STR")
    private String sentencingDateStr;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "DOCUMENT_NO")
    private String documentNo;
    @Column(name = "CRIME_NAMES_C06")
    private String crimeNamesC06;
    @Column(name = "LIST_CRIME_NAME_ID_C06")
    private String listCrimeNameIdC06;
    @Column(name = "COURT_ID_C06")
    private Long courtIdC06;
    @Column(name = "COURT_NAME_C06")
    private String courtNameC06;
    @Column(name = "PENALTY_MAIN_C06")
    private String penaltyMainC06;
    @Column(name = "PENALTY_ADD_ID_C06")
    private String penaltyAddIdC06;
    @Column(name = "REMISSION_INFO")
    private String remissionInfo;
    @Column(name = "PENALTY_MAIN_NAME_C06")
    private String penaltyMainNameC06;

    public Judgment() {
    }

    public String getListCrimeNameId() {
        return listCrimeNameId;
    }

    public void setListCrimeNameId(String listCrimeNameId) {
        this.listCrimeNameId = listCrimeNameId;
    }

    public String getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(String penaltyId) {
        this.penaltyId = penaltyId;
    }

    public String getPenaltyMainId() {
        return penaltyMainId;
    }

    public void setPenaltyMainId(String penaltyMainId) {
        this.penaltyMainId = penaltyMainId;
    }

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }

    public Judgment(Long judgmentId) {
        this.judgmentId = judgmentId;
    }

    public Judgment(Long judgmentId, long ministryJusticeId) {
        this.judgmentId = judgmentId;
        this.ministryJusticeId = ministryJusticeId;
    }

    public Long getJudgmentId() {
        return judgmentId;
    }

    public void setJudgmentId(Long judgmentId) {
        this.judgmentId = judgmentId;
    }

    public Long getAgencyVerifyId() {
        return agencyVerifyId;
    }

    public void setAgencyVerifyId(Long agencyVerifyId) {
        this.agencyVerifyId = agencyVerifyId;
    }

    public String getJudgmentNo() {
        return judgmentNo;
    }

    public void setJudgmentNo(String judgmentNo) {
        this.judgmentNo = judgmentNo;
    }

    public Date getJudgmentDate() {
        return judgmentDate;
    }

    public void setJudgmentDate(Date judgmentDate) {
        this.judgmentDate = judgmentDate;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getPenaltyAdd() {
        return penaltyAdd;
    }

    public void setPenaltyAdd(String penaltyAdd) {
        this.penaltyAdd = penaltyAdd;
    }

    public String getPenaltyMain() {
        return penaltyMain;
    }

    public void setPenaltyMain(String penaltyMain) {
        this.penaltyMain = penaltyMain;
    }

    public String getPenaltyMainNote() {
        return penaltyMainNote;
    }

    public void setPenaltyMainNote(String penaltyMainNote) {
        this.penaltyMainNote = penaltyMainNote;
    }

    public String getPenaltyAddNote() {
        return penaltyAddNote;
    }

    public void setPenaltyAddNote(String penaltyAddNote) {
        this.penaltyAddNote = penaltyAddNote;
    }

    public String getLegalCost() {
        return legalCost;
    }

    public void setLegalCost(String legalCost) {
        this.legalCost = legalCost;
    }

    public String getCivilObligation() {
        return civilObligation;
    }

    public void setCivilObligation(String civilObligation) {
        this.civilObligation = civilObligation;
    }

    public Date getExecJudgmentDate() {
        return execJudgmentDate;
    }

    public void setExecJudgmentDate(Date execJudgmentDate) {
        this.execJudgmentDate = execJudgmentDate;
    }

    public Long getExecJudgmentStatusId() {
        return execJudgmentStatusId;
    }

    public void setExecJudgmentStatusId(Long execJudgmentStatusId) {
        this.execJudgmentStatusId = execJudgmentStatusId;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public Date getRemissionDate() {
        return remissionDate;
    }

    public void setRemissionDate(Date remissionDate) {
        this.remissionDate = remissionDate;
    }

    public Long getCrimeId() {
        return crimeId;
    }

    public void setCrimeId(Long crimeId) {
        this.crimeId = crimeId;
    }

    public String getCrimeNames() {
        return crimeNames;
    }

    public void setCrimeNames(String crimeNames) {
        this.crimeNames = crimeNames;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public long getMinistryJusticeId() {
        return ministryJusticeId;
    }

    public void setMinistryJusticeId(long ministryJusticeId) {
        this.ministryJusticeId = ministryJusticeId;
    }

    public Declaration getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(Declaration declarationId) {
        this.declarationId = declarationId;
    }

    public Long getSendRequestBy() {
        return sendRequestBy;
    }

    public void setSendRequestBy(Long sendRequestBy) {
        this.sendRequestBy = sendRequestBy;
    }

    public Date getSendRequestDate() {
        return sendRequestDate;
    }

    public void setSendRequestDate(Date sendRequestDate) {
        this.sendRequestDate = sendRequestDate;
    }

    public String getSendRequestStatus() {
        return sendRequestStatus;
    }

    public void setSendRequestStatus(String sendRequestStatus) {
        this.sendRequestStatus = sendRequestStatus;
    }

    public String getExecJudgmentStatus() {
        return execJudgmentStatus;
    }

    public void setExecJudgmentStatus(String execJudgmentStatus) {
        this.execJudgmentStatus = execJudgmentStatus;
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

    public String getExecutionDateStr() {
        return executionDateStr;
    }

    public void setExecutionDateStr(String executionDateStr) {
        this.executionDateStr = executionDateStr;
    }

    public String getSentencingDateStr() {
        return sentencingDateStr;
    }

    public void setSentencingDateStr(String sentencingDateStr) {
        this.sentencingDateStr = sentencingDateStr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getCrimeNamesC06() {
        return crimeNamesC06;
    }

    public void setCrimeNamesC06(String crimeNamesC06) {
        this.crimeNamesC06 = crimeNamesC06;
    }

    public String getListCrimeNameIdC06() {
        return listCrimeNameIdC06;
    }

    public void setListCrimeNameIdC06(String listCrimeNameIdC06) {
        this.listCrimeNameIdC06 = listCrimeNameIdC06;
    }

    public Long getCourtIdC06() {
        return courtIdC06;
    }

    public void setCourtIdC06(Long courtIdC06) {
        this.courtIdC06 = courtIdC06;
    }

    public String getCourtNameC06() {
        return courtNameC06;
    }

    public void setCourtNameC06(String courtNameC06) {
        this.courtNameC06 = courtNameC06;
    }

    public String getPenaltyMainC06() {
        return penaltyMainC06;
    }

    public void setPenaltyMainC06(String penaltyMainC06) {
        this.penaltyMainC06 = penaltyMainC06;
    }

    public String getPenaltyAddIdC06() {
        return penaltyAddIdC06;
    }

    public void setPenaltyAddIdC06(String penaltyAddIdC06) {
        this.penaltyAddIdC06 = penaltyAddIdC06;
    }

    public String getRemissionInfo() {
        return remissionInfo;
    }

    public void setRemissionInfo(String remissionInfo) {
        this.remissionInfo = remissionInfo;
    }

    public String getPenaltyMainNameC06() {
        return penaltyMainNameC06;
    }

    public void setPenaltyMainNameC06(String penaltyMainNameC06) {
        this.penaltyMainNameC06 = penaltyMainNameC06;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (judgmentId != null ? judgmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Judgment)) {
            return false;
        }
        Judgment other = (Judgment) object;
        if ((this.judgmentId == null && other.judgmentId != null) || (this.judgmentId != null && !this.judgmentId.equals(other.judgmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.viettel.docsearch.domain.Judgment[judgmentId=" + judgmentId + "]";
    }
}
