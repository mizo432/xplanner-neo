package com.github.mizo432.xplanner.buisiness.model.virtual;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Timesheet implements Serializable {

    private List<TimesheetEntry> entries = new ArrayList<>();
    private TreeMap<Date, DailyTimesheetEntry> dailyEntries = new TreeMap<>();
    private String personName;
    private BigDecimal total = new BigDecimal(0.0);
    private Map<String, BigDecimal> projectData = new HashMap<>();
    private Map<String, BigDecimal> iterationData = new HashMap<>();
    private Map<String, BigDecimal> storyData = new HashMap<>();

    public Timesheet() {
    }

    public Timesheet(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);

        for (; cal.getTimeInMillis() <= endDate.getTime(); cal.add(Calendar.DATE, 1)) {
            dailyEntries.put(cal.getTime(), new DailyTimesheetEntry(cal.getTime(),
                    new BigDecimal(0.0)));
        }
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Collection<TimesheetEntry> getEntries() {
        return entries;
    }

    public Collection<DailyTimesheetEntry> getDailyEntries() {
        return dailyEntries.values();
    }

    public void addEntry(TimesheetEntry entry) {
        this.total = this.total.add(entry.getTotalDuration());
        this.personName = entry.getPersonName();
        this.entries.add(entry);
        this.updateGroupedData(entry);
    }

    public void addDailyEntry(DailyTimesheetEntry entry) {
        DailyTimesheetEntry dailyEntry = this.dailyEntries.get(entry.getEntryDate());
        dailyEntry.setTotalDuration(dailyEntry.getTotalDuration().
                add(entry.getTotalDuration()));

        this.dailyEntries.put(dailyEntry.getEntryDate(), dailyEntry);
    }

    public BigDecimal getTotal() {
        return this.total.setScale(1, RoundingMode.HALF_EVEN);
    }

    private void updateGroupedData(TimesheetEntry entry) {

        // Project Data
        BigDecimal projectTotal = this.projectData.get(entry.getProjectName());
        if (projectTotal == null) {
            projectTotal = new BigDecimal(0.0);
        }
        projectTotal = projectTotal.add(entry.getTotalDuration())
                .setScale(1, RoundingMode.HALF_EVEN);
        this.projectData.put(entry.getProjectName(), projectTotal);

        // Iteration Data
        BigDecimal iterationTotal = this.iterationData.get(entry.getIterationName());
        if (iterationTotal == null) {
            iterationTotal = new BigDecimal(0.0);
        }
        iterationTotal = iterationTotal.add(entry.getTotalDuration())
                .setScale(1, RoundingMode.HALF_EVEN);
        this.iterationData.put(entry.getIterationName(), iterationTotal);

        // Story Data
        BigDecimal storyTotal = this.storyData.get(entry.getStoryName());
        if (storyTotal == null) {
            storyTotal = new BigDecimal(0.0);
        }
        storyTotal = storyTotal.add(entry.getTotalDuration())
                .setScale(1, RoundingMode.HALF_EVEN);
        this.storyData.put(entry.getStoryName(), storyTotal);
    }

    public Map<String, BigDecimal> getIterationData() {
        return iterationData;
    }

    public Map<String, BigDecimal> getProjectData() {
        return projectData;
    }

    public Map<String, BigDecimal> getStoryData() {
        return storyData;
    }
}