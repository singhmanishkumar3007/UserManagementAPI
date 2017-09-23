package com.easybusiness.usermanagement.services.usergroup;

import static com.easybusiness.usermanagement.constant.UserManagementConstant.USER_HOST_SERVER;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.easybusiness.modelmanagement.entity.UserGroup;
import com.easybusiness.modelmanagement.usergroup.UserGroupDao;
import com.easybusiness.usermanagement.dto.UserGroupDTO;

@RestController
@RequestMapping("/easybusiness/usergroup/")
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupDao userGroupDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getByUsergroupName/{usergroupname}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserGroupDTO> getUserGroupByName(@PathVariable("usergroupname") String userGroupName) {
	UserGroup userEntity = userGroupDao.findByUserGroupName(userGroupName);
	return new ResponseEntity<UserGroupDTO>(prepareUserGroupDTO(userEntity), HttpStatus.OK);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getByUsergroupId/{usergroupid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserGroupDTO> getUserGroupById(@PathVariable("usergroupid") Long userGroupId) {
	UserGroup userEntity = userGroupDao.findUserGroupById(userGroupId);
	return new ResponseEntity<UserGroupDTO>(prepareUserGroupDTO(userEntity), HttpStatus.OK);
    }

    private UserGroupDTO prepareUserGroupDTO(UserGroup userGroupEntity) {
	UserGroupDTO userGroupDTO = new UserGroupDTO();
	userGroupDTO.setFromDate(userGroupEntity.getFromDate());
	userGroupDTO.setId(userGroupEntity.getId());
	userGroupDTO.setIsEnable(userGroupEntity.getIsEnable());
	userGroupDTO.setModifiedBy(userGroupEntity.getModifiedBy());
	userGroupDTO.setModifiedOn(userGroupEntity.getModifiedOn());
	userGroupDTO.setToDate(userGroupEntity.getToDate());
	userGroupDTO.setUserGroupName(userGroupEntity.getUserGroupName());
	return userGroupDTO;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addUserGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserGroupDTO> persistUserGroup(@RequestBody UserGroupDTO userGroupDTO) {

	UserGroup userGroupEntity = new UserGroup();
	userGroupEntity.setFromDate(userGroupDTO.getFromDate());
	userGroupEntity.setIsEnable(userGroupDTO.getIsEnable());
	userGroupEntity.setModifiedBy(userGroupDTO.getModifiedBy());
	userGroupEntity.setModifiedOn(userGroupDTO.getModifiedOn());
	userGroupEntity.setToDate(userGroupDTO.getToDate());
	userGroupEntity.setUserGroupName(userGroupDTO.getUserGroupName());

	userGroupDao.addUserGroup(userGroupEntity);
	return new ResponseEntity<UserGroupDTO>(userGroupDTO, HttpStatus.CREATED);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllUserGroup", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupDTO> populateUserGroupList() throws Exception {
	List<UserGroup> userGroupList=userGroupDao.findAll();
	List<UserGroupDTO> userGroupDTOList=new ArrayList<UserGroupDTO>();
	userGroupList.forEach(userGroup->{
	    UserGroupDTO userGroupDTO=new UserGroupDTO();
	    userGroupDTO.setFromDate(userGroup.getFromDate());
	    userGroupDTO.setId(userGroup.getId());
	    userGroupDTO.setIsEnable(userGroup.getIsEnable());
	    userGroupDTO.setModifiedBy(userGroup.getModifiedBy());
	    userGroupDTO.setModifiedOn(userGroup.getModifiedOn());
	    userGroupDTO.setToDate(userGroup.getToDate());
	    userGroupDTO.setUserGroupName(userGroup.getUserGroupName());
	    userGroupDTOList.add(userGroupDTO);
	});
	return userGroupDTOList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteUserGroup/{userGroupId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void destroyUserGroup(@PathVariable("userGroupId") Long userGroupId) {

	userGroupDao.deleteUserGroup(userGroupId);
    }

    @Override
    public List<UserGroupDTO> getFieldEq(Class<UserGroupDTO> type, String propertyName, Object value, int offset,
	    int size) {
	return null;
    }

}
