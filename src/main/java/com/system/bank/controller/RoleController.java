package com.system.bank.controller;

import com.system.bank.dto.ApiReponse;
import com.system.bank.dto.RoleRequest;
import com.system.bank.dto.RoleResponse;
import com.system.bank.entity.RoleEntity;
import com.system.bank.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiReponse<List<RoleResponse>>> getAllRole(){
        List<RoleResponse> role = roleService.getAll();
        return ResponseEntity.ok(ApiReponse.<List<RoleResponse>>builder()
                .code(1000)
                .message("Lấy danh sách tài khoản thành công.")
                .result(role)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiReponse<RoleResponse>> findByRole(String nameRole) {
        RoleResponse response = roleService.findByNameRole(nameRole);

        return ResponseEntity.ok(
                ApiReponse.<RoleResponse>builder()
                        .code(1000)
                        .message("Lấy thông tin quyền thành công.")
                        .result(response)
                        .build()
        );
    }
    @PostMapping("/upadate/{id}")
    public ResponseEntity<ApiReponse<RoleResponse>> upadateRole(Long roleId, RoleRequest request){
        RoleResponse response = roleService.update(roleId,request);
        return ResponseEntity.ok(
                ApiReponse.<RoleResponse>builder()
                        .code(1000)
                        .message("Sửa thành công.")
                        .result(response)
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiReponse<RoleResponse>> addRole(RoleRequest request){
        RoleResponse response = roleService.addRole(request);
        return ResponseEntity.ok(
                ApiReponse.<RoleResponse>builder()
                        .code(1000)
                        .message("Thêm thành công")
                        .result(response)
                        .build()
        );
    }
}
