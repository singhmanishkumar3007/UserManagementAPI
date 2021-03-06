package com.easybusiness.usermanagement.services.usertogroup;

import static com.easybusiness.usermanagement.constant.UserManagementConstant.USER_HOST_SERVER;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.easybusiness.modelmanagement.entity.Department;
import com.easybusiness.modelmanagement.entity.Designation;
import com.easybusiness.modelmanagement.entity.LocationMaster;
import com.easybusiness.modelmanagement.entity.Organization;
import com.easybusiness.modelmanagement.entity.User;
import com.easybusiness.modelmanagement.entity.UserGroup;
import com.easybusiness.modelmanagement.entity.UserGroupMap;
import com.easybusiness.modelmanagement.user.UserDao;
import com.easybusiness.modelmanagement.usergroup.UserGroupDao;
import com.easybusiness.modelmanagement.usergroupmap.UserGroupMapDao;
import com.easybusiness.usermanagement.dto.DepartmentDTO;
import com.easybusiness.usermanagement.dto.DesignationDTO;
import com.easybusiness.usermanagement.dto.LocationMasterDTO;
import com.easybusiness.usermanagement.dto.OrganizationDTO;
import com.easybusiness.usermanagement.dto.UserDTO;
import com.easybusiness.usermanagement.dto.UserGroupDTO;
import com.easybusiness.usermanagement.dto.UserGroupMapDTO;

@RestController
@RequestMapping("/easybusiness/usergroup/")
public class UserGroupMappingServiceImpl implements UserGroupMappingService {

    @Autowired
    UserGroupMapDao userGroupMapDao;

    @Autowired
    UserGroupDao userGroupDao;

    @Autowired
    UserDao userDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupMappingServiceImpl.class);

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getByUserGroupId/{usergroupid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMapDTO> getUserGroupMapByGroupId(@PathVariable("usergroupid") Long userGroupId) {

	UserGroup userGroupEntity = userGroupDao.findUserGroupById(userGroupId);
	List<UserGroupMap> userGroupMapEntityList = userGroupMapDao.findUserGroupMapByGroupId(userGroupEntity);
	List<UserGroupMapDTO> userGroupMapList = new ArrayList<UserGroupMapDTO>();
	userGroupMapEntityList.forEach(userGroupMapEntity -> {
	    UserGroupMapDTO userGroupMapDTO = prepareUserGroupMapDTO(userGroupMapEntity);
	    userGroupMapList.add(userGroupMapDTO);
	});

	return userGroupMapList;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllUsersOfGroup/{usergroupid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> getAllUsersOfGroup(@PathVariable("usergroupid") Long userGroupId) {

	UserGroup userGroupEntity = userGroupDao.findUserGroupById(userGroupId);
	List<UserGroupMap> userGroupMapEntityList = userGroupMapDao.findUserGroupMapByGroupId(userGroupEntity);
	List<UserDTO> userList = new ArrayList<UserDTO>();
	userGroupMapEntityList.forEach(userGroupMapEntity -> {
	    UserGroupMapDTO userGroupMapDTO = prepareUserGroupMapDTO(userGroupMapEntity);
	    if(userGroupMapDTO.getUser().getIsEnabled()==1)
	    {
	    userList.add(userGroupMapDTO.getUser());
	    }
	});

	return userList;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllUsersOfGroupByGroupName/{usergroupname}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> getAllUsersOfGroupByGroupName(@PathVariable("usergroupname") String userGroupName) {

	UserGroup userGroupEntity = userGroupDao.findByUserGroupName(userGroupName);
	List<UserGroupMap> userGroupMapEntityList = userGroupMapDao.findUserGroupMapByGroupId(userGroupEntity);
	List<UserDTO> userList = new ArrayList<UserDTO>();
	userGroupMapEntityList.forEach(userGroupMapEntity -> {
	    UserGroupMapDTO userGroupMapDTO = prepareUserGroupMapDTO(userGroupMapEntity);
	    if(userGroupMapDTO.getUser().getIsEnabled()==1)
	    {
	    userList.add(userGroupMapDTO.getUser());
	    }
	});

	return userList;

    }

    private UserGroupMapDTO prepareUserGroupMapDTO(UserGroupMap userGroupMapEntity) {
	UserGroupMapDTO userGroupMapDTO = new UserGroupMapDTO();
	userGroupMapDTO.setFromDate(userGroupMapEntity.getFromDate());
	userGroupMapDTO.setId(userGroupMapEntity.getId());
	userGroupMapDTO.setIsEnable(userGroupMapEntity.getIsEnable());
	userGroupMapDTO.setModifiedBy(userGroupMapEntity.getModifiedBy());
	userGroupMapDTO.setModifiedOn(userGroupMapEntity.getModifiedOn());
	userGroupMapDTO.setToDate(userGroupMapEntity.getToDate());
	userGroupMapDTO.setUser(prepareUserDTO(userGroupMapEntity.getUser()));
	userGroupMapDTO.setUserGroup(prepareUserGroupDTO(userGroupMapEntity.getUserGroup()));
	return userGroupMapDTO;
    }

    private UserDTO prepareUserDTO(User userEntity) {
	UserDTO userDTO = new UserDTO();
	userDTO.setAlternateEmail(userEntity.getAlternateEmail());
	userDTO.setDateOfBirth(userEntity.getDateOfBirth());
	try {
	    DepartmentDTO deptDO = new DepartmentDTO();
	    deptDO.setDeptName(userEntity.getDepartment().getDeptName());
	    deptDO.setId(userEntity.getDepartment().getId());
	    OrganizationDTO orgDTO = new OrganizationDTO();
	    orgDTO.setId(userEntity.getDepartment().getOrganization().getId());
	    orgDTO.setOrgLocation(userEntity.getDepartment().getOrganization().getOrgLocation());
	    orgDTO.setOrgName(userEntity.getDepartment().getOrganization().getOrgName());
	    deptDO.setOrganization(orgDTO);
	    userDTO.setDepartment(deptDO);
	    userDTO.setOrganization(orgDTO);
	} catch (Exception e) {
	    LOGGER.error("exception while getting organization/department details for user {} {}",
		    userEntity.getUserName(), e.getMessage());
	}
	try {
	    DesignationDTO desigDTO = new DesignationDTO();
	    desigDTO.setDesig(userEntity.getDesignation().getDesig());
	    desigDTO.setId(userEntity.getDesignation().getId());
	    userDTO.setDesignation(desigDTO);
	} catch (Exception e) {
	    LOGGER.error("exception while getting designation details for user {} {}", userEntity.getUserName(),
		    e.getMessage());
	}
	userDTO.setEmail(userEntity.getEmail());
	userDTO.setEndDate(userEntity.getEndDate());
	userDTO.setFirstName(userEntity.getFirstName());
	userDTO.setFromDate(userEntity.getFromDate());
	userDTO.setGender(userEntity.getGender());
	userDTO.setId(userEntity.getId());
	userDTO.setIsEnabled(userEntity.getIsEnabled());
	userDTO.setLastName(userEntity.getLastName());
	userDTO.setMobile(userEntity.getMobile());
	userDTO.setModifiedBy(userEntity.getModifiedBy());
	userDTO.setModifiedOn(userEntity.getModifiedOn());

	userDTO.setPassword(userEntity.getPassword());
	userDTO.setTypeOfEmployment(userEntity.getTypeOfEmployment());
	userDTO.setUserName(userEntity.getUserName());

	userDTO.setPermAddr(userEntity.getPermAddr());
	userDTO.setState(userEntity.getState());
	userDTO.setCity(userEntity.getCity());
	userDTO.setCountry(userEntity.getCountry());
	userDTO.setZip(userEntity.getZip());
	userDTO.setFatherName(userEntity.getFatherName());
	userDTO.setSpouseName(userEntity.getSpouseName());
	userDTO.setPassport(userEntity.getPassport());
	userDTO.setLocation(null == userEntity.getLocation() ? null : prepareLocationDTO(userEntity.getLocation()));
	userDTO.setUnitId(userEntity.getUnitId());

	return userDTO;
    }

    private LocationMasterDTO prepareLocationDTO(LocationMaster location) {
	LocationMasterDTO locationMaster = new LocationMasterDTO();
	locationMaster.setCreatedBy(location.getCreatedBy());
	locationMaster.setCreatedOn(location.getCreatedOn());
	locationMaster.setId(location.getId());
	locationMaster.setLocationArea(location.getLocationArea());
	locationMaster.setLocationCity(location.getLocationCity());
	locationMaster.setLocationCountry(location.getLocationCountry());
	locationMaster.setLocationPin(location.getLocationPin());
	locationMaster.setLocationState(location.getLocationState());
	locationMaster.setModifiedBy(location.getModifiedBy());
	locationMaster.setModifiedOn(location.getModifiedOn());
	return locationMaster;
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
    @RequestMapping(value = "getByUserId/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMapDTO> getUserGroupMapByUserId(@PathVariable("userid") Long userId) {
	User userEntity = userDao.findUserById(userId);
	List<UserGroupMap> userGroupMapEntityList = userGroupMapDao.findByUser(userEntity);
	List<UserGroupMapDTO> userGroupMapList = new ArrayList<UserGroupMapDTO>();
	userGroupMapEntityList.forEach(userGroupMapEntity -> {
	    UserGroupMapDTO userGroupMapDTO = prepareUserGroupMapDTO(userGroupMapEntity);
	    userGroupMapList.add(userGroupMapDTO);
	});

	return userGroupMapList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getByMappingId/{mappingid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserGroupMapDTO> getUserGroupMapByMappingId(@PathVariable("mappingid") Long mappingId) {

	UserGroupMap userGroupMap = userGroupMapDao.findUserGroupMapById(mappingId);
	return new ResponseEntity<UserGroupMapDTO>(prepareUserGroupMapDTO(userGroupMap), HttpStatus.FOUND);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "mapUserGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserGroupMapDTO> persistUserGroupMapping(@RequestBody UserGroupMapDTO userGroupMapDTO) {

	UserGroupMap userGroupMapEntity = new UserGroupMap();
	userGroupMapEntity.setFromDate(userGroupMapDTO.getFromDate());
	userGroupMapEntity.setIsEnable(userGroupMapDTO.getIsEnable());
	userGroupMapEntity.setModifiedBy(userGroupMapDTO.getModifiedBy());
	userGroupMapEntity.setModifiedOn(userGroupMapDTO.getModifiedOn());
	userGroupMapEntity.setToDate(userGroupMapDTO.getToDate());
	User userEntity = prepareUserEntity(userGroupMapDTO);
	userGroupMapEntity.setUser(userEntity);
	UserGroup userGroupEntity = prepareUserGroupEntity(userGroupMapDTO);
	userGroupMapEntity.setUserGroup(userGroupEntity);
	userGroupMapDao.addUserGroupMap(userGroupMapEntity);
	return new ResponseEntity<UserGroupMapDTO>(userGroupMapDTO, HttpStatus.CREATED);

    }

    private UserGroup prepareUserGroupEntity(UserGroupMapDTO userGroupMapDTO) {
	UserGroup userGroupEntity = new UserGroup();
	userGroupEntity.setFromDate(userGroupMapDTO.getUserGroup().getFromDate());
	userGroupEntity.setIsEnable(userGroupMapDTO.getUserGroup().getIsEnable());
	userGroupEntity.setModifiedBy(userGroupMapDTO.getUserGroup().getModifiedBy());
	userGroupEntity.setModifiedOn(userGroupMapDTO.getUserGroup().getModifiedOn());
	userGroupEntity.setToDate(userGroupMapDTO.getUserGroup().getToDate());
	userGroupEntity.setUserGroupName(userGroupMapDTO.getUserGroup().getUserGroupName());
	userGroupEntity.setId(userGroupMapDTO.getUserGroup().getId());
	return userGroupEntity;
    }

    private User prepareUserEntity(UserGroupMapDTO userGroupMapDTO) {
	User userEntity = new User();
	userEntity.setAlternateEmail(userGroupMapDTO.getUser().getAlternateEmail());
	userEntity.setDateOfBirth(userGroupMapDTO.getUser().getDateOfBirth());
	try {
	    Department dept = new Department();
	    dept.setDeptName(userGroupMapDTO.getUser().getDepartment().getDeptName());
	    dept.setId(userGroupMapDTO.getUser().getDepartment().getId());
	    Organization org = new Organization();
	    org.setId(userGroupMapDTO.getUser().getDepartment().getOrganization().getId());
	    org.setOrgLocation(userGroupMapDTO.getUser().getDepartment().getOrganization().getOrgLocation());
	    org.setOrgName(userGroupMapDTO.getUser().getDepartment().getOrganization().getOrgName());
	    dept.setOrganization(org);
	    userEntity.setDepartment(dept);
	    userEntity.setOrganization(org);
	} catch (Exception e) {
	    LOGGER.error("exception in getting department/organization details for user {}{}",
		    userGroupMapDTO.getUser().getUserName(), e.getMessage());
	}
	try {
	    Designation desg = new Designation();
	    desg.setDesig(userGroupMapDTO.getUser().getDesignation().getDesig());
	    desg.setId(userGroupMapDTO.getUser().getDesignation().getId());
	    userEntity.setDesignation(desg);
	} catch (Exception e) {
	    LOGGER.error("exception in getting designation details for user {}{}",
		    userGroupMapDTO.getUser().getUserName(), e.getMessage());
	}
	userEntity.setEmail(userGroupMapDTO.getUser().getEmail());
	userEntity.setEndDate(userGroupMapDTO.getUser().getEndDate());
	userEntity.setFirstName(userGroupMapDTO.getUser().getFirstName());
	userEntity.setFromDate(userGroupMapDTO.getUser().getFromDate());
	userEntity.setGender(userGroupMapDTO.getUser().getGender());
	userEntity.setIsEnabled(userGroupMapDTO.getUser().getIsEnabled());
	userEntity.setLastName(userGroupMapDTO.getUser().getLastName());
	userEntity.setMobile(userGroupMapDTO.getUser().getMobile());
	userEntity.setModifiedBy(userGroupMapDTO.getUser().getModifiedBy());
	userEntity.setModifiedOn(userGroupMapDTO.getUser().getModifiedOn());

	userEntity.setPassword(userGroupMapDTO.getUser().getPassword());
	userEntity.setTypeOfEmployment(userGroupMapDTO.getUser().getTypeOfEmployment());
	userEntity.setPermAddr(userGroupMapDTO.getUser().getPermAddr());
	userEntity.setState(userGroupMapDTO.getUser().getState());
	userEntity.setCity(userGroupMapDTO.getUser().getCity());
	userEntity.setCountry(userGroupMapDTO.getUser().getCountry());
	userEntity.setZip(userGroupMapDTO.getUser().getZip());
	userEntity.setFatherName(userGroupMapDTO.getUser().getFatherName());
	userEntity.setSpouseName(userGroupMapDTO.getUser().getSpouseName());
	userEntity.setPassport(userGroupMapDTO.getUser().getPassport());
	userEntity.setUserName(userGroupMapDTO.getUser().getUserName());
	userEntity.setId(userGroupMapDTO.getUser().getId());
	userEntity.setLocation(null == userGroupMapDTO.getUser().getLocation() ? null
		: prepareLocationEntity(userGroupMapDTO.getUser().getLocation()));
	userEntity.setUnitId(userGroupMapDTO.getUser().getUnitId());
	return userEntity;
    }

    private LocationMaster prepareLocationEntity(LocationMasterDTO location) {
	LocationMaster locationMaster = new LocationMaster();
	locationMaster.setCreatedBy(location.getCreatedBy());
	locationMaster.setCreatedOn(location.getCreatedOn());
	locationMaster.setId(location.getId());
	locationMaster.setLocationArea(location.getLocationArea());
	locationMaster.setLocationCity(location.getLocationCity());
	locationMaster.setLocationCountry(location.getLocationCountry());
	locationMaster.setLocationPin(location.getLocationPin());
	locationMaster.setLocationState(location.getLocationState());
	locationMaster.setModifiedBy(location.getModifiedBy());
	locationMaster.setModifiedOn(location.getModifiedOn());
	return locationMaster;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteUserGroupMapping/{mappingid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void destroyUserGroupMapping(@PathVariable("mappingid") Long mappingId) {

	userGroupMapDao.deleteUserGroupMap(mappingId);
    }

    @Override
    public List<UserGroupMapDTO> getFieldEq(Class<UserGroupMapDTO> type, String propertyName, Object value, int offset,
	    int size) {
	return null;
    }

}
