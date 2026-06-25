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
@Table(name = "BAN_POSITION")
public class BanPosition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAN_POSITION_SEQ")
    @SequenceGenerator(name = "BAN_POSITION_SEQ", sequenceName = "BAN_POSITION_SEQ", allocationSize = 1)
    @Column(name = "BAN_POSITION_ID")
    private Long banPositionId;
    @Column(name = "POSITION_ID")
    private Long positionId;
    @Column(name = "DECISION_DATE")
    @Temporal(TemporalType.DATE)
    private Date decisionDate;
    @Column(name = "COURT_ID")
    private Long courtId;
    @Column(name = "DECISION_NO")
    private String decisionNo;
    @Column(name = "BAN_POSITION")
    private String banPosition;
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "BAN_TIME_ID")
    private String banTimeId;
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

    public BanPosition() {
    }

    public BanPosition(Long banPositionId) {
        this.banPositionId = banPositionId;
    }

    public BanPosition(Long banPositionId, long ministryJusticeId) {
        this.banPositionId = banPositionId;
        this.ministryJusticeId = ministryJusticeId;
    }

    public Long getBanPositionId() {
        return banPositionId;
    }

    public void setBanPositionId(Long banPositionId) {
        this.banPositionId = banPositionId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Date getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(Date decisionDate) {
        this.decisionDate = decisionDate;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getDecisionNo() {
        return decisionNo;
    }

    public void setDecisionNo(String decisionNo) {
        this.decisionNo = decisionNo;
    }

    public String getBanPosition() {
        return banPosition;
    }

    public void setBanPosition(String banPosition) {
        this.banPosition = banPosition;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getBanTimeId() {
        return banTimeId;
    }

    public void setBanTimeId(String banTimeId) {
        this.banTimeId = banTimeId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (banPositionId != null ? banPositionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BanPosition)) {
            return false;
        }
        BanPosition other = (BanPosition) object;
        if ((this.banPositionId == null && other.banPositionId != null) || (this.banPositionId != null && !this.banPositionId.equals(other.banPositionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.viettel.docsearch.domain.BanPosition[banPositionId=" + banPositionId + "]";
    }
}
