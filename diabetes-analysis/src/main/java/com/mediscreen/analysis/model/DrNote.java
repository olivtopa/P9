package com.mediscreen.analysis.model;

public class DrNote {

    private String id;

    private Long patientId;

    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "DrNote{" +
                "id='" + id + '\'' +
                ", patientId=" + patientId +
                ", note='" + note + '\'' +
                '}';
    }
}
