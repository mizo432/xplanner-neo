package com.github.mizo432.xplanner.buisiness.model.virtual;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Getters return mutable objects, acceptable for this use case")
public class DailyTimesheetEntry implements Serializable {
    private java.util.Date entryDate;
    private java.math.BigDecimal totalDuration = new BigDecimal(0.0);
    private static final String SHORT_DATE_PATTERN = "EEE d-MMM-yy";

    public DailyTimesheetEntry() {
    }

    public DailyTimesheetEntry(Date entryDate, BigDecimal duration) {
        this.setEntryDate(entryDate);
        this.setTotalDuration(duration);
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public String getEntryDateShort() {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_PATTERN);
        return sdf.format(this.getEntryDate());
    }

    public void setEntryDate(Date date) {
        this.entryDate = date;
    }

    public java.math.BigDecimal getTotalDuration() {
        return totalDuration.setScale(1, RoundingMode.HALF_EVEN);
    }

    public void setTotalDuration(java.math.BigDecimal totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = this.totalDuration.add(new BigDecimal(totalDuration));
    }
}