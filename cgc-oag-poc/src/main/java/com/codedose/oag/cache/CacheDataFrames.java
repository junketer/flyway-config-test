package com.codedose.oag.cache;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class CacheDataFrames {
    private Dataset<Row> airportTerminalDF;
    private Dataset<Row> airportToStateDF;
    private Dataset<Row> allianceLookupDF;
    private Dataset<Row> cargoDuplicateIndicatorDF;
    private Dataset<Row> carrierCodeICAODF;
    private Dataset<Row> carrierCountryDF;
    private Dataset<Row> carrierDefaultDEIValueDF;
    private Dataset<Row> carrierNameDF;
    private Dataset<Row> carrierServiceZeroPaddingDF;
    private Dataset<Row> cityToCountryDF;
    private Dataset<Row> combinedCarrierDF;
    private Dataset<Row> countryDotCodeDF;
    private Dataset<Row> equipmentCodeIATADF;
    private Dataset<Row> equipmentCodeICAODF;
    private Dataset<Row> equipmentDefaultCargoClassDF;
    private Dataset<Row> IVIdDF;
    private Dataset<Row> legSegDefaultDEIValueDF;
    private Dataset<Row> locationCodeIATADF;
    private Dataset<Row> locationCodeICAODF;
    private Dataset<Row> ontimePerformance501DF;
    private Dataset<Row> ontimePerformance502DF;
    private Dataset<Row> partyRoleNameDF;
    private Dataset<Row> passengerClassDF;
    private Dataset<Row> portCoordinatesDF;
    private Dataset<Row> portsDF;
    private Dataset<Row> portTimezoneIATADF;
    private Dataset<Row> portTimezoneOAGDF;
    private Dataset<Row> portToRegionDF;
    private Dataset<Row> seatsDF;
    private Dataset<Row> sharedAirlineDesignatorDF;


    public Dataset<Row> getAirportTerminalDF() {
        return airportTerminalDF;
    }

    public void setAirportTerminalDF(Dataset<Row> airportTerminalDF) {
        this.airportTerminalDF = airportTerminalDF;
    }

    public Dataset<Row> getAirportToStateDF() {
        return airportToStateDF;
    }

    public void setAirportToStateDF(Dataset<Row> airportToStateDF) {
        this.airportToStateDF = airportToStateDF;
    }

    public Dataset<Row> getAllianceLookupDF() {
        return allianceLookupDF;
    }

    public void setAllianceLookupDF(Dataset<Row> allianceLookupDF) {
        this.allianceLookupDF = allianceLookupDF;
    }

    public Dataset<Row> getCargoDuplicateIndicatorDF() {
        return cargoDuplicateIndicatorDF;
    }

    public void setCargoDuplicateIndicatorDF(Dataset<Row> cargoDuplicateIndicatorDF) {
        this.cargoDuplicateIndicatorDF = cargoDuplicateIndicatorDF;
    }

    public Dataset<Row> getCarrierCodeICAODF() {
        return carrierCodeICAODF;
    }

    public void setCarrierCodeICAODF(Dataset<Row> carrierCodeICAODF) {
        this.carrierCodeICAODF = carrierCodeICAODF;
    }

    public Dataset<Row> getCarrierCountryDF() {
        return carrierCountryDF;
    }

    public void setCarrierCountryDF(Dataset<Row> carrierCountryDF) {
        this.carrierCountryDF = carrierCountryDF;
    }

    public Dataset<Row> getCarrierDefaultDEIValueDF() {
        return carrierDefaultDEIValueDF;
    }

    public void setCarrierDefaultDEIValueDF(Dataset<Row> carrierDefaultDEIValueDF) {
        this.carrierDefaultDEIValueDF = carrierDefaultDEIValueDF;
    }

    public Dataset<Row> getCarrierNameDF() {
        return carrierNameDF;
    }

    public void setCarrierNameDF(Dataset<Row> carrierNameDF) {
        this.carrierNameDF = carrierNameDF;
    }

    public Dataset<Row> getCarrierServiceZeroPaddingDF() {
        return carrierServiceZeroPaddingDF;
    }

    public void setCarrierServiceZeroPaddingDF(Dataset<Row> carrierServiceZeroPaddingDF) {
        this.carrierServiceZeroPaddingDF = carrierServiceZeroPaddingDF;
    }

    public Dataset<Row> getCityToCountryDF() {
        return cityToCountryDF;
    }

    public void setCityToCountryDF(Dataset<Row> cityToCountryDF) {
        this.cityToCountryDF = cityToCountryDF;
    }

    public Dataset<Row> getCombinedCarrierDF() {
        return combinedCarrierDF;
    }

    public void setCombinedCarrierDF(Dataset<Row> combinedCarrierDF) {
        this.combinedCarrierDF = combinedCarrierDF;
    }

    public Dataset<Row> getCountryDotCodeDF() {
        return countryDotCodeDF;
    }

    public void setCountryDotCodeDF(Dataset<Row> countryDotCodeDF) {
        this.countryDotCodeDF = countryDotCodeDF;
    }

    public Dataset<Row> getEquipmentCodeIATADF() {
        return equipmentCodeIATADF;
    }

    public void setEquipmentCodeIATADF(Dataset<Row> equipmentCodeIATADF) {
        this.equipmentCodeIATADF = equipmentCodeIATADF;
    }

    public Dataset<Row> getEquipmentCodeICAODF() {
        return equipmentCodeICAODF;
    }

    public void setEquipmentCodeICAODF(Dataset<Row> equipmentCodeICAODF) {
        this.equipmentCodeICAODF = equipmentCodeICAODF;
    }

    public Dataset<Row> getEquipmentDefaultCargoClassDF() {
        return equipmentDefaultCargoClassDF;
    }

    public void setEquipmentDefaultCargoClassDF(Dataset<Row> equipmentDefaultCargoClassDF) {
        this.equipmentDefaultCargoClassDF = equipmentDefaultCargoClassDF;
    }

    public Dataset<Row> getIVIdDF() {
        return IVIdDF;
    }

    public void setIVIdDF(Dataset<Row> IVIdDF) {
        this.IVIdDF = IVIdDF;
    }

    public Dataset<Row> getLegSegDefaultDEIValueDF() {
        return legSegDefaultDEIValueDF;
    }

    public void setLegSegDefaultDEIValueDF(Dataset<Row> legSegDefaultDEIValueDF) {
        this.legSegDefaultDEIValueDF = legSegDefaultDEIValueDF;
    }

    public Dataset<Row> getLocationCodeIATADF() {
        return locationCodeIATADF;
    }

    public void setLocationCodeIATADF(Dataset<Row> locationCodeIATADF) {
        this.locationCodeIATADF = locationCodeIATADF;
    }

    public Dataset<Row> getLocationCodeICAODF() {
        return locationCodeICAODF;
    }

    public void setLocationCodeICAODF(Dataset<Row> locationCodeICAODF) {
        this.locationCodeICAODF = locationCodeICAODF;
    }

    public Dataset<Row> getOntimePerformance501DF() {
        return ontimePerformance501DF;
    }

    public void setOntimePerformance501DF(Dataset<Row> ontimePerformance501DF) {
        this.ontimePerformance501DF = ontimePerformance501DF;
    }

    public Dataset<Row> getOntimePerformance502DF() {
        return ontimePerformance502DF;
    }

    public void setOntimePerformance502DF(Dataset<Row> ontimePerformance502DF) {
        this.ontimePerformance502DF = ontimePerformance502DF;
    }

    public Dataset<Row> getPartyRoleNameDF() {
        return partyRoleNameDF;
    }

    public void setPartyRoleNameDF(Dataset<Row> partyRoleNameDF) {
        this.partyRoleNameDF = partyRoleNameDF;
    }

    public Dataset<Row> getPassengerClassDF() {
        return passengerClassDF;
    }

    public void setPassengerClassDF(Dataset<Row> passengerClassDF) {
        this.passengerClassDF = passengerClassDF;
    }

    public Dataset<Row> getPortCoordinatesDF() {
        return portCoordinatesDF;
    }

    public void setPortCoordinatesDF(Dataset<Row> portCoordinatesDF) {
        this.portCoordinatesDF = portCoordinatesDF;
    }

    public Dataset<Row> getPortsDF() {
        return portsDF;
    }

    public void setPortsDF(Dataset<Row> portsDF) {
        this.portsDF = portsDF;
    }

    public Dataset<Row> getPortTimezoneIATADF() {
        return portTimezoneIATADF;
    }

    public void setPortTimezoneIATADF(Dataset<Row> portTimezoneIATADF) {
        this.portTimezoneIATADF = portTimezoneIATADF;
    }

    public Dataset<Row> getPortTimezoneOAGDF() {
        return portTimezoneOAGDF;
    }

    public void setPortTimezoneOAGDF(Dataset<Row> portTimezoneOAGDF) {
        this.portTimezoneOAGDF = portTimezoneOAGDF;
    }

    public Dataset<Row> getPortToRegionDF() {
        return portToRegionDF;
    }

    public void setPortToRegionDF(Dataset<Row> portToRegionDF) {
        this.portToRegionDF = portToRegionDF;
    }

    public Dataset<Row> getSeatsDF() {
        return seatsDF;
    }

    public void setSeatsDF(Dataset<Row> seatsDF) {
        this.seatsDF = seatsDF;
    }

    public Dataset<Row> getSharedAirlineDesignatorDF() {
        return sharedAirlineDesignatorDF;
    }

    public void setSharedAirlineDesignatorDF(Dataset<Row> sharedAirlineDesignatorDF) {
        this.sharedAirlineDesignatorDF = sharedAirlineDesignatorDF;
    }
}
