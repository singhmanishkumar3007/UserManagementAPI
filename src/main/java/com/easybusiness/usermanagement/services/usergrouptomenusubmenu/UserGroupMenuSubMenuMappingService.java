package com.easybusiness.usermanagement.services.usergrouptomenusubmenu;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easybusiness.usermanagement.dto.SubMenuDTO;
import com.easybusiness.usermanagement.dto.UserGroupMenuSubMenuDTO;

public interface UserGroupMenuSubMenuMappingService {

    public ResponseEntity<UserGroupMenuSubMenuDTO> getUserGroupMenuBymappingId(Long mappingId);

    public List<UserGroupMenuSubMenuDTO> getUserGroupMenuByGroupId(Long groupId);

    public List<UserGroupMenuSubMenuDTO> getUserGroupMenuByMenuId(Long menuId);

    public ResponseEntity<UserGroupMenuSubMenuDTO> persistUserGroupMenu(UserGroupMenuSubMenuDTO UserGroupMenuDTO);

    public void destroyUserGroupMenu(Long userGroupMenuId);

    public List<UserGroupMenuSubMenuDTO> getFieldEq(final Class<UserGroupMenuSubMenuDTO> type,
	    final String propertyName, final Object value, int offset, int size);

    public List<UserGroupMenuSubMenuDTO> getUserGroupMenuByGroupName(String groupName);

    public List<SubMenuDTO> getAllSubMenusAndMenusByGroupId(Long groupId);

    public List<SubMenuDTO> getAllSubMenusAndMenusByGroupName(String groupName);


}
