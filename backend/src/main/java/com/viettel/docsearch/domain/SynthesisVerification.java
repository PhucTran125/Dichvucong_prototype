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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author gpdn_huantv
 */
@Entity
@Table(name = "SYNTHESIS_VERIFICATION")
public class SynthesisVerification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "synthesisVerificationSeq")
    @SequenceGenerator(name = "synthesisVerificationSeq", sequenceName = "SYNTHESIS_VERIFICATION_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "SYNTHESIS_VERIFICATION_ID")
    private Long synthesisVerificationId;
    @Column(name = "SYNTHESIS_DATE")
    @Temporal(TemporalType.DATE)
    private Date synthesisDate;
    @Column(name = "ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    @Column(name = "ISSUE_PERSON")
    private String issuePerson;
    @Column(name = "STATUS_CRIME_ID")
    private Long statusCrimeId;
    @Column(name = "DOCUMENT_NO")
    private String documentNo;
    @Basic(optional = false)
    @Column(name = "MINISTRY_JUSTICE_ID")
    private long ministryJusticeId;
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @Column(name = "CREATED_BY")
    private Long createdBy;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "MODIFY_DATE")
    @Temporal(TemporalType.DATE)
    private Date modifyDate;
    @Column(name = "MODIFIED_BY")
    private Long modifiedBy;
    @JoinColumn(name = "DECLARATION_ID", referencedColumnName = "DECLARATION_ID")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Declaration declarationId;

    @Column(name = "CONTENT_SIGNED")
    private String contentSigned;
    @Column(name = "USER_SIGNED")
    private String userSigned;
    @Column(name = "BAN_POSSITION_STATUS_ID")
    private Long banPossitionStatusId;
    @Column(name = "NOTE")
    private String note;

    private transient String categoryName;

    public SynthesisVerification() {
    }

    public String getContentSigned() {
        return contentSigned;
    }

    public void setContentSigned(String contentSigned) {
        this.contentSigned = contentSigned;
    }

    public String getUserSigned() {
        return userSigned;
    }

    public void setUserSigned(String userSigned) {
        this.userSigned = userSigned;
    }

    public SynthesisVerification(Long synthesisVerificationId) {
        this.synthesisVerificationId = synthesisVerificationId;
    }

    public SynthesisVerification(Long synthesisVerificationId, long ministryJusticeId) {
        this.synthesisVerificationId = synthesisVerificationId;
        this.ministryJusticeId = ministryJusticeId;
    }

    public Long getSynthesisVerificationId() {
        return synthesisVerificationId;
    }

    public void setSynthesisVerificationId(Long synthesisVerificationId) {
        this.synthesisVerificationId = synthesisVerificationId;
    }

    public Date getSynthesisDate() {
        return synthesisDate;
    }

    public void setSynthesisDate(Date synthesisDate) {
        this.synthesisDate = synthesisDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Long getStatusCrimeId() {
        return statusCrimeId;
    }

    public void setStatusCrimeId(Long statusCrimeId) {
        this.statusCrimeId = statusCrimeId;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        //only code bca
        if( documentNo!=null && !documentNo.isEmpty() && !documentNo.startsWith("0")){
            documentNo = "0" + documentNo;
        }
        this.documentNo = documentNo;
    }

    public long getMinistryJusticeId() {
        return ministryJusticeId;
    }

    public void setMinistryJusticeId(long ministryJusticeId) {
        this.ministryJusticeId = ministryJusticeId;
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

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Declaration getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(Declaration declarationId) {
        this.declarationId = declarationId;
    }

    public String getIssuePerson() {
        return issuePerson;
    }

    public void setIssuePerson(String issuePerson) {
        this.issuePerson = issuePerson;
    }

    public Long getBanPossitionStatusId() {
        return banPossitionStatusId;
    }

    public void setBanPossitionStatusId(Long banPossitionStatusId) {
        this.banPossitionStatusId = banPossitionStatusId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (synthesisVerificationId != null ? synthesisVerificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SynthesisVerification)) {
            return false;
        }
        SynthesisVerification other = (SynthesisVerification) object;
        if ((this.synthesisVerificationId == null && other.synthesisVerificationId != null) || (this.synthesisVerificationId != null && !this.synthesisVerificationId.equals(other.synthesisVerificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.viettel.docsearch.domain.SynthesisVerification[synthesisVerificationId=" + synthesisVerificationId + "]";
    }
}
