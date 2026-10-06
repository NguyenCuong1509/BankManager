package com.system.bank.mapper;

import com.system.bank.dto.RoleRequest;
import com.system.bank.dto.RoleResponse;
import com.system.bank.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponse toResponse(RoleEntity entity){
        if (entity == null) return null;
        RoleResponse response = new RoleResponse();
        response.setRoleName(entity.getNameRole());
        return response;
    }
}
