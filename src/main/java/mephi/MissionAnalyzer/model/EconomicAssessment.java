package model;

public class EconomicAssessment {
    private long totalDamageCost;
    private long infrastructureDamage;
    private long commercialDamage;
    private long transportDamage;
    private int recoveryEstimateDays;
    private boolean insuranceCovered;

    public EconomicAssessment() {}

    public Long getTotalDamageCost() { return totalDamageCost; }
    public Long getInfrastructureDamage() { return infrastructureDamage; }
    public Long getCommercialDamage() { return commercialDamage; }
    public Long getTransportDamage() { return transportDamage; }
    public Integer getRecoveryEstimateDays() { return recoveryEstimateDays; }
    public Boolean isInsuranceCovered() { return insuranceCovered; }

    public void setTotalDamageCost(long totalDamageCost) { this.totalDamageCost = totalDamageCost; }
    public void setInfrastructureDamage(long infrastructureDamage) { this.infrastructureDamage = infrastructureDamage; }
    public void setCommercialDamage(long commercialDamage) { this.commercialDamage = commercialDamage; }
    public void setTransportDamage(long transportDamage) { this.transportDamage = transportDamage; }
    public void setRecoveryEstimateDays(int recoveryEstimateDays) { this.recoveryEstimateDays = recoveryEstimateDays; }
    public void setInsuranceCovered(boolean insuranceCovered) { this.insuranceCovered = insuranceCovered; }
}