package com.orbitx.service;
import com.orbitx.model.*;
import com.orbitx.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class MissionService {
 private final TelemetryRepository telemetryRepo; private final CommandRepository commandRepo; private final AlarmRepository alarmRepo;
 private final AtomicLong ticks=new AtomicLong(0);
 private volatile String mode="NOMINAL"; private volatile boolean payloadEnabled=true;
 private final Instant start=Instant.now();
 public MissionService(TelemetryRepository t,CommandRepository c,AlarmRepository a){telemetryRepo=t;commandRepo=c;alarmRepo=a;}
 @Scheduled(fixedRate=2000)
 @Transactional
 public void generateTelemetry(){
  long n=ticks.incrementAndGet(); double phase=n/15.0;
  Telemetry t=new Telemetry(Instant.now(),542.0+Math.sin(phase)*1.6,7.61+Math.cos(phase)*0.006,Math.max(5,94.0-n*0.0008),22.0+Math.sin(phase/2)*2.4,-68+Math.sin(phase)*2,78.0,18.52+Math.sin(phase)*0.01,73.85+Math.cos(phase)*0.01,mode);
  telemetryRepo.save(t);
  if(n%1800==0) telemetryRepo.deleteAll(telemetryRepo.findTop100ByOrderByTimestampDesc().stream().skip(80).toList());
 }
 public Telemetry latest(){return telemetryRepo.findTop100ByOrderByTimestampDesc().stream().findFirst().orElseGet(()->{generateTelemetry();return telemetryRepo.findTop100ByOrderByTimestampDesc().get(0);});}
 public List<Telemetry> history(int limit){List<Telemetry> rows=telemetryRepo.findTop100ByOrderByTimestampDesc();Collections.reverse(rows);return rows.stream().skip(Math.max(0,rows.size()-Math.max(1,Math.min(limit,100)))).toList();}
 public Map<String,Object> status(){long seconds=java.time.Duration.between(start,Instant.now()).getSeconds();return Map.of("missionName","ORBIT-X Demo Mission","orbit","LEO · 542 km","elapsed",String.format("T+ %02d:%02d:%02d",seconds/3600,(seconds%3600)/60,seconds%60),"connection","CONNECTED","mode",mode,"payloadEnabled",payloadEnabled);}
 public List<Map<String,Object>> subsystems(){return List.of(
  Map.of("name","Power subsystem","health",98,"status","NOMINAL","description","Solar array, battery and power distribution"),
  Map.of("name","Attitude control","health",100,"status","NOMINAL","description","Orientation sensors and reaction wheel simulation"),
  Map.of("name","Communications","health",94,"status","NOMINAL","description","RF transceiver and ground link"),
  Map.of("name","Thermal control","health",96,"status","NOMINAL","description","Internal temperature regulation"),
  Map.of("name","Payload","health",payloadEnabled?91:88,"status",payloadEnabled?"NOMINAL":"STANDBY","description","Scientific instrument package"));}
 public List<CommandRecord> commands(){return commandRepo.findTop50ByOrderByCreatedAtDesc();}
 @Transactional public CommandRecord sendCommand(String command){
  String c=command==null?"":command.trim().toUpperCase(Locale.ROOT);
  String message;
  switch(c){case "PAYLOAD_ON" -> {payloadEnabled=true;message="Payload enabled in simulator.";}case "PAYLOAD_OFF" -> {payloadEnabled=false;message="Payload placed in standby.";}case "SAFE_MODE" -> {mode="SAFE";message="Safe mode activated.";}case "NOMINAL_MODE" -> {mode="NOMINAL";message="Nominal flight mode restored.";}case "PING" -> message="Simulated communications ping successful.";default -> throw new IllegalArgumentException("Unsupported command. Use PAYLOAD_ON, PAYLOAD_OFF, SAFE_MODE, NOMINAL_MODE or PING.");}
  return commandRepo.save(new CommandRecord(c,"EXECUTED",message,Instant.now()));
 }
 public List<AlarmRecord> alarms(){if(alarmRepo.count()==0)alarmRepo.save(new AlarmRecord("INFO","Simulator ready","ORBIT-X mission simulator initialized successfully.",false,Instant.now()));return alarmRepo.findAllByOrderByCreatedAtDesc();}
 @Transactional public AlarmRecord acknowledge(long id){AlarmRecord a=alarmRepo.findById(id).orElseThrow(()->new NoSuchElementException("Alarm not found"));a.setAcknowledged(true);return alarmRepo.save(a);}
}
