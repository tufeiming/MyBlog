package com.kafka.service.impl;

import com.kafka.util.SecurityUtils;
import org.springframework.stereotype.Service;

@Service("ps")
public class PermissionService {
    public boolean hasPermission(String permission) {
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        return SecurityUtils.getLoginUser().getPermissions().contains(permission);
    }
}
