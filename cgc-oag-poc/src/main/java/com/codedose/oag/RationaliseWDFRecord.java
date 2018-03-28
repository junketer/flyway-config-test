package com.codedose.oag;

import java.time.LocalDate;
import com.oag.wdf.utils.DateUtil;

public class RationaliseWDFRecord {

    private Long legSegId;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
    private String operDaysOfWeek;
    private boolean oxoDated;

    @Override
    public String toString() {
        return "RationaliseWDFRecord [legSegId=" + legSegId + ", effectiveStartDate=" + effectiveStartDate
                + ", effectiveEndDate=" + effectiveEndDate + ", operDaysOfWeek=" + operDaysOfWeek + ", oxoDated="
                + oxoDated + "]";
    }

    public RationaliseWDFRecord() {
    }

    public RationaliseWDFRecord(Long legSegId) {
        this.legSegId = legSegId;
        this.oxoDated = false;
    }

    public RationaliseWDFRecord(Long legSegId, String operDaysOfWeek, LocalDate startDate, LocalDate endDate) {
        this.legSegId = legSegId;
        this.operDaysOfWeek = operDaysOfWeek;
        this.setEffectiveStartDate(startDate);
        this.setEffectiveEndDate(endDate);

    }

    public RationaliseWDFRecord(RationaliseWDFRecord copyFromRecord) {
        this.legSegId = copyFromRecord.getLegSegId();
        this.effectiveStartDate = copyFromRecord.getEffectiveStartDate();
        this.effectiveEndDate = copyFromRecord.getEffectiveEndDate();
        this.operDaysOfWeek = copyFromRecord.getOperDaysOfWeek();
        this.oxoDated = copyFromRecord.isOxoDated();
    }

    public Long getLegSegId() {
        return legSegId;
    }

    public void setLegSegId(Long legSegId) {
        this.legSegId = legSegId;
    }

    public LocalDate getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(LocalDate effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public LocalDate getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(LocalDate effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getOperDaysOfWeek() {
        return operDaysOfWeek;
    }

    public void setOperDaysOfWeek(String operDaysOfWeek) {
        this.operDaysOfWeek = operDaysOfWeek;
    }

    public boolean isOxoDated() {
        return oxoDated;
    }

    public void setOxoDated(boolean oxoDated) {
        this.oxoDated = oxoDated;
    }

}
