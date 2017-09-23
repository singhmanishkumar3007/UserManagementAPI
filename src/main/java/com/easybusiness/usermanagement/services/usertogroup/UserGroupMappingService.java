package com.easybusiness.usermanagement.services.usertogroup;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easybusiness.usermanagement.dto.UserDTO;
import com.easybusiness.usermanagement.dto.UserGroupMapDTO;

public interface UserGroupMappingService {

    public List<UserGroupMapDTO> getUserGroupMapByGroupId(Long groupId);

    public ResponseEntity<UserGroupMapDTO> getUserGroupMapByMappingId(Long mappingId);

    public List<UserGroupMapDTO> getUserGroupMapByUserId(Long userId);

    public ResponseEntity<UserGroupMapDTO> persistUserGroupMapping(UserGroupMapDTO UserGroupMapDTO);

    public void destroyUserGroupMapping(Long userGroupMapId);

    public List<UserGroupMapDTO> getFieldEq(final Class<UserGroupMapDTO> type, final String propertyName,
	    final Object value, int offset, int size);

    public List<UserDTO> getAllUsersOfGroup(Long userGroupId);

    public List<UserDTO> getAllUsersOfGroupByGroupName(String userGroupName);

}
