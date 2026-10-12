package com.orbitx.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name="alarm_records")
public class AlarmRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String severity;
 private String title;
 @Column(length=1000) private String message;
 private boolean acknowledged;
 private Instant createdAt;
 public AlarmRecord() {}
 public AlarmRecord(String severity,String title,String message,boolean acknowledged,Instant createdAt){this.severity=severity;this.title=title;this.message=message;this.acknowledged=acknowledged;this.createdAt=createdAt;}
 public Long getId(){return id;} public String getSeverity(){return severity;} public String getTitle(){return title;} public String getMessage(){return message;} public boolean isAcknowledged(){return acknowledged;} public Instant getCreatedAt(){return createdAt;} public void setAcknowledged(boolean acknowledged){this.acknowledged=acknowledged;}
}
