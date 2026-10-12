package com.orbitx.controller;
import com.orbitx.model.*;
import com.orbitx.service.MissionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"http://localhost:5173","http://127.0.0.1:5173"})
public class MissionController {
 private final MissionService service;
 public MissionController(MissionService service){this.service=service;}
 @GetMapping("/telemetry/latest") public Telemetry latest(){return service.latest();}
 @GetMapping("/telemetry/history") public List<Telemetry> history(@RequestParam(defaultValue="30") int limit){return service.history(limit);}
 @GetMapping("/mission/status") public Map<String,Object> status(){return service.status();}
 @GetMapping("/subsystems") public List<Map<String,Object>> subsystems(){return service.subsystems();}
 @GetMapping("/commands") public List<CommandRecord> commands(){return service.commands();}
 public record CommandRequest(@NotBlank String command) {}
 @PostMapping("/commands") public CommandRecord send(@Valid @RequestBody CommandRequest request){try{return service.sendCommand(request.command());}catch(IllegalArgumentException e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());}}
 @GetMapping("/alarms") public List<AlarmRecord> alarms(){return service.alarms();}
 @PutMapping("/alarms/{id}/acknowledge") public AlarmRecord acknowledge(@PathVariable long id){try{return service.acknowledge(id);}catch(java.util.NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());}}
}
