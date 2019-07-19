package org.smart4j.framework.security;

import java.util.Set;

import org.smart4j.framework.helper.DatabaseHelper;

public class AppSecurity implements SmartSecurity{

	@Override
	public String getPasswrod(String username) {
		String sql = "SELECT password from user where username = ?";
		return null;
	}

	@Override
	public Set<String> getRoleNameSet(String username) {
		String sql = "select r.role_name from user u. user_role ur, role r where u.id = ur.user_id and r.id = ur.role_id and u.username = ?";
		return null;
	}

	@Override
	public Set<String> getPermissonNameSet(String roleName) {
		String sql = "select p.permission__name from role r,role_permission rp,permission p where r.id = rp.role_id and p.id = rp.permission_id and r.role_name =?";
		return null;
	}

}
