package com.orbitx.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name="telemetry")
public class Telemetry {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Instant timestamp;
 private double altitudeKm, velocityKms, batteryPct, temperatureC, signalDbm, fuelPct, latitude, longitude;
 private String mode;
 public Telemetry() {}
 public Telemetry(Instant timestamp,double altitudeKm,double velocityKms,double batteryPct,double temperatureC,double signalDbm,double fuelPct,double latitude,double longitude,String mode){this.timestamp=timestamp;this.altitudeKm=altitudeKm;this.velocityKms=velocityKms;this.batteryPct=batteryPct;this.temperatureC=temperatureC;this.signalDbm=signalDbm;this.fuelPct=fuelPct;this.latitude=latitude;this.longitude=longitude;this.mode=mode;}
 public Long getId(){return id;} public Instant getTimestamp(){return timestamp;}
 public double getAltitudeKm(){return altitudeKm;} public double getVelocityKms(){return velocityKms;} public double getBatteryPct(){return batteryPct;} public double getTemperatureC(){return temperatureC;} public double getSignalDbm(){return signalDbm;} public double getFuelPct(){return fuelPct;} public double getLatitude(){return latitude;} public double getLongitude(){return longitude;} public String getMode(){return mode;}
}
