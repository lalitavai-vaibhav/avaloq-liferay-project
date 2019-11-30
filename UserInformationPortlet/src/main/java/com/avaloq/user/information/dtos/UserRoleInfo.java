package com.avaloq.user.information.dtos;
/**
 * @author Vaibhav Khopade
 */
public class UserRoleInfo {
	
	public String roleName;
	

	public UserRoleInfo() {
	}

	

	public UserRoleInfo(String roleName) {
		
		this.roleName = roleName;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

}
