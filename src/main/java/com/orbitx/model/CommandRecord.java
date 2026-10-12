package com.orbitx.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name="command_records")
public class CommandRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String command;
 private String status;
 private String message;
 private Instant createdAt;
 public CommandRecord() {}
 public CommandRecord(String command,String status,String message,Instant createdAt){this.command=command;this.status=status;this.message=message;this.createdAt=createdAt;}
 public Long getId(){return id;} public String getCommand(){return command;} public String getStatus(){return status;} public String getMessage(){return message;} public Instant getCreatedAt(){return createdAt;}
}
