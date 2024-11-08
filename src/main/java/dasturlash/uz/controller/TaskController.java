package dasturlash.uz.controller;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.dto.TaskDTO;
import dasturlash.uz.service.TaskService;
import dasturlash.uz.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@EnableMethodSecurity
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto) {
        TaskDTO result = taskService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping({"","/"})
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/active/all")
    public ResponseEntity<List<TaskDTO>> active() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/finished/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<TaskDTO>> getFenished() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskDTO>> getMyTaskList() {
        List<TaskDTO> result = taskService.getCurrentProfileTasksList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable String id) {
        TaskDTO result = taskService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> update(@RequestBody TaskDTO student,
                                          @PathVariable("id") String id) {
        Boolean result = taskService.update(student, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/admin")
    public ResponseEntity<Void> deleteAsAdmin(@PathVariable("id") String id) {
        taskService.deleteAsAdmin(id);
        return ResponseEntity.ok().build();
    }
}
