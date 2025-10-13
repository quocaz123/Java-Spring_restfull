package com.Quokka.Jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Quokka.Jobhunter.domain.Permission;
import com.Quokka.Jobhunter.domain.res.ResultPaginationResponse;
import com.Quokka.Jobhunter.util.annotation.ApiMessage;
import com.Quokka.service.PermissionService;

@RequestMapping(path = "${apiPrefix}/permissions")
@RestController
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping("")
    @ApiMessage("Create a permission")
    public ResponseEntity<Permission> create(@Valid @RequestBody Permission permission) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.create(permission));
    }

    @PutMapping("")
    @ApiMessage("Update a permission")
    public ResponseEntity<Permission> update(@Valid @RequestBody Permission permission) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(this.permissionService.update(permission));
    }

    @GetMapping("")
    @ApiMessage("fetch all permission")
    public ResponseEntity<ResultPaginationResponse> getAll(
            @Filter Specification<Permission> spec,
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.permissionService.fetchAll(spec, pageable)
        );
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a permission")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws Exception{
        this.permissionService.delete(id);
        return ResponseEntity.ok().body(null);
    }
}
