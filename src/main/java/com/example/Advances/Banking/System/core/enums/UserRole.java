package com.example.Advances.Banking.System.core.enums;

import lombok.Getter;
import java.util.Arrays;
import java.util.List;

@Getter
public enum UserRole {  // ✅ أو Role إذا غيرت اسم الملف

    CUSTOMER(
            "ROLE_CUSTOMER",
            "Customer",
            "Bank customer with basic account access",
            Arrays.asList(
                    Permission.VIEW_OWN_ACCOUNTS,
                    Permission.MAKE_TRANSFERS,
                    Permission.VIEW_TRANSACTIONS,
                    Permission.CREATE_SUPPORT_TICKET,
                    Permission.VIEW_STATEMENTS
            )
    ),

    TELLER(
            "ROLE_TELLER",
            "Teller",
            "Bank teller with customer service capabilities",
            Arrays.asList(
                    Permission.VIEW_CUSTOMER_ACCOUNTS,
                    Permission.PROCESS_DEPOSITS,
                    Permission.PROCESS_WITHDRAWALS,
                    Permission.VIEW_ALL_TRANSACTIONS,
                    Permission.CREATE_ACCOUNTS,
                    Permission.UPDATE_CUSTOMER_INFO
            )
    ),

    MANAGER(
            "ROLE_MANAGER",
            "Manager",
            "Branch manager with oversight capabilities",
            Arrays.asList(
                    Permission.APPROVE_LARGE_TRANSACTIONS,
                    Permission.MANAGE_TELLERS,
                    Permission.VIEW_BRANCH_REPORTS,
                    Permission.FREEZE_ACCOUNTS,
                    Permission.VIEW_AUDIT_LOGS,
                    Permission.GENERATE_REPORTS
            )
    ),

    ADMIN(
            "ROLE_ADMIN",
            "Administrator",
            "System administrator with full access",
            Arrays.asList(
                    Permission.MANAGE_SYSTEM_CONFIG,
                    Permission.MANAGE_ALL_ACCOUNTS,
                    Permission.VIEW_SYSTEM_LOGS,
                    Permission.USER_MANAGEMENT,
                    Permission.DATABASE_ACCESS,
                    Permission.BACKUP_RESTORE
            )
    );

    private final String authority;
    private final String displayName;
    private final String description;
    private final List<Permission> permissions;

    UserRole(String authority, String displayName, String description, List<Permission> permissions) {
        this.authority = authority;
        this.displayName = displayName;
        this.description = description;
        this.permissions = permissions;
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public List<String> getPermissionStrings() {
        return permissions.stream()
                .map(Permission::name)
                .toList();
    }

    public boolean isHigherThan(UserRole other) {
        return this.ordinal() > other.ordinal();
    }

    public boolean canAccessRole(UserRole targetRole) {
        return this.ordinal() >= targetRole.ordinal();
    }

    public List<UserRole> getManageableRoles() {
        return Arrays.stream(UserRole.values())
                .filter(role -> this.ordinal() > role.ordinal())
                .toList();
    }
}