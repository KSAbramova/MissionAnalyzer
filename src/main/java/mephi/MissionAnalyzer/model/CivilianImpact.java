package model;

public class CivilianImpact {
    private int evacuated;
    private int injured;
    private int missing;
    private String publicExposureRisk;

    public CivilianImpact() {}

    public Integer getEvacuated() { return evacuated; }
    public Integer getInjured() { return injured; }
    public Integer getMissing() { return missing; }
    public String getPublicExposureRisk() { return publicExposureRisk; }

    public void setEvacuated(int evacuated) { this.evacuated = evacuated; }
    public void setInjured(int injured) { this.injured = injured; }
    public void setMissing(int missing) { this.missing = missing; }
    public void setPublicExposureRisk(String publicExposureRisk) {
        this.publicExposureRisk = publicExposureRisk;
    }
}