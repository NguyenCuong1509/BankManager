package com.system.bank.service;

import com.system.bank.dto.RoleRequest;
import com.system.bank.dto.RoleResponse;
import com.system.bank.entity.RoleEntity;
import com.system.bank.exception.AppException;
import com.system.bank.exception.ErrorCode;
import com.system.bank.mapper.RoleMapper;
import com.system.bank.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse addRole(RoleRequest request){
        RoleEntity entity = new RoleEntity();
        entity.setNameRole(request.getRoleName());
        RoleEntity saved = roleRepository.save(entity);
        return roleMapper.toResponse(saved);
    }

    public List<RoleResponse> getAll(){
        List<RoleEntity> role = roleRepository.findAll();
        if (role.isEmpty()){
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        return role.stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    public boolean existByName(String name){
        return roleRepository.existsByNameRole(name);
    }

    public RoleResponse findByNameRole(String nameRole){
        RoleEntity entity = roleRepository.findByNameRole(nameRole).orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_FOUND));
        return roleMapper.toResponse(entity);
    }

    public RoleResponse update(Long roleId, RoleRequest request){
        if (request==null) return null;
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
        if (existByName(request.getRoleName())
                && !role.getNameRole().equals(request.getRoleName())) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        role.setNameRole(request.getRoleName());
        RoleEntity saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

}
