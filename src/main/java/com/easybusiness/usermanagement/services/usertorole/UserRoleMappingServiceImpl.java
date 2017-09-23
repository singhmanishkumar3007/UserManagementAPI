package com.easybusiness.usermanagement.services.usertorole;

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

import com.easybusiness.modelmanagement.entity.Department;
import com.easybusiness.modelmanagement.entity.Designation;
import com.easybusiness.modelmanagement.entity.LocationMaster;
import com.easybusiness.modelmanagement.entity.Organization;
import com.easybusiness.modelmanagement.entity.Role;
import com.easybusiness.modelmanagement.entity.User;
import com.easybusiness.modelmanagement.entity.UserRoleMap;
import com.easybusiness.modelmanagement.role.RoleDao;
import com.easybusiness.modelmanagement.user.UserDao;
import com.easybusiness.modelmanagement.userrolemap.UserRoleMapDao;
import com.easybusiness.usermanagement.dto.DepartmentDTO;
import com.easybusiness.usermanagement.dto.DesignationDTO;
import com.easybusiness.usermanagement.dto.LocationMasterDTO;
import com.easybusiness.usermanagement.dto.OrganizationDTO;
import com.easybusiness.usermanagement.dto.RoleDTO;
import com.easybusiness.usermanagement.dto.UserDTO;
import com.easybusiness.usermanagement.dto.UserRoleMapDTO;

@RestController
@RequestMapping("/easybusiness/userrole/")
public class UserRoleMappingServiceImpl implements UserRoleMappingService {

    @Autowired
    UserRoleMapDao userRoleDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getRoleByUserId/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserRoleMapDTO> getUserRoleByUserId(@PathVariable("userid") Long userId) {

	User user = userDao.findUserById(userId);
	List<UserRoleMap> userRoleMapList = userRoleDao.findByUser(user);
	List<UserRoleMapDTO> userRoleMapDtoList = new ArrayList<UserRoleMapDTO>();
	userRoleMapList.forEach(userRoleMapEntity -> {

	    UserRoleMapDTO userRoleMapDTO = new UserRoleMapDTO();
	    userRoleMapDTO = prepareUserRoleMapDTO(userRoleMapEntity);
	    userRoleMapDtoList.add(userRoleMapDTO);
	});

	return userRoleMapDtoList;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getRoleByRoleId/{roleid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserRoleMapDTO> getUserRoleByRoleId(@PathVariable("roleid") Long roleId) {

	Role role = roleDao.findRoleById(roleId);
	List<UserRoleMap> userRoleMapList = userRoleDao.findByRole(role);
	List<UserRoleMapDTO> userRoleMapDtoList = new ArrayList<UserRoleMapDTO>();
	userRoleMapList.forEach(userRoleMapEntity -> {

	    UserRoleMapDTO userRoleMapDTO = new UserRoleMapDTO();
	    userRoleMapDTO = prepareUserRoleMapDTO(userRoleMapEntity);
	    userRoleMapDtoList.add(userRoleMapDTO);
	});

	return userRoleMapDtoList;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserRoleByMappingId/{mappingid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserRoleMapDTO> getUserRoleByMappingId(@PathVariable("mappingid") Long mappingId) {

	UserRoleMap userRoleMap = userRoleDao.findUserRoleMapById(mappingId);
	return new ResponseEntity<UserRoleMapDTO>(prepareUserRoleMapDTO(userRoleMap), HttpStatus.FOUND);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "mapUserRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserRoleMapDTO> persistUserRoleMapping(@RequestBody UserRoleMapDTO userRoleMapDTO) {

	UserRoleMap userRoleMapEntity = new UserRoleMap();
	userRoleMapEntity.setFromDate(userRoleMapDTO.getFromDate());
	userRoleMapEntity.setIsEnable(userRoleMapDTO.getIsEnable());
	userRoleMapEntity.setModifiedBy(userRoleMapDTO.getModifiedBy());
	userRoleMapEntity.setModifiedOn(userRoleMapDTO.getModifiedOn());
	userRoleMapEntity.setToDate(userRoleMapDTO.getToDate());
	User userEntity = prepareUserEntity(userRoleMapDTO);
	userRoleMapEntity.setUser(userEntity);
	Role role = prepareRoleEntity(userRoleMapDTO);
	userRoleMapEntity.setRole(role);
	userRoleDao.addUserRoleMap(userRoleMapEntity);
	return new ResponseEntity<UserRoleMapDTO>(userRoleMapDTO, HttpStatus.CREATED);

    }

    private Role prepareRoleEntity(UserRoleMapDTO userRoleMapDTO) {
	Role role = new Role();
	role.setFromDate(userRoleMapDTO.getRole().getFromDate());
	role.setId(userRoleMapDTO.getRole().getId());
	role.setIsEnable(userRoleMapDTO.getRole().getIsEnable());
	role.setModifiedBy(userRoleMapDTO.getRole().getModifiedBy());
	role.setModifiedOn(userRoleMapDTO.getRole().getModifiedOn());
	role.setRole(userRoleMapDTO.getRole().getRole());
	role.setToDate(userRoleMapDTO.getRole().getToDate());
	return role;
    }

    private User prepareUserEntity(UserRoleMapDTO userRoleMapDTO) {
	User userEntity = new User();
	userEntity.setAlternateEmail(userRoleMapDTO.getUser().getAlternateEmail());
	userEntity.setDateOfBirth(userRoleMapDTO.getUser().getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userRoleMapDTO.getUser().getDepartment().getDeptName());
	dept.setId(userRoleMapDTO.getUser().getDepartment().getId());
	Organization org = new Organization();
	org.setId(userRoleMapDTO.getUser().getDepartment().getOrganization().getId());
	org.setOrgLocation(userRoleMapDTO.getUser().getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userRoleMapDTO.getUser().getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userRoleMapDTO.getUser().getDesignation().getDesig());
	desg.setId(userRoleMapDTO.getUser().getDesignation().getId());
	userEntity.setDesignation(desg);
	userEntity.setEmail(userRoleMapDTO.getUser().getEmail());
	userEntity.setEndDate(userRoleMapDTO.getUser().getEndDate());
	userEntity.setFirstName(userRoleMapDTO.getUser().getFirstName());
	userEntity.setFromDate(userRoleMapDTO.getUser().getFromDate());
	userEntity.setGender(userRoleMapDTO.getUser().getGender());
	userEntity.setIsEnabled(userRoleMapDTO.getUser().getIsEnabled());
	userEntity.setLastName(userRoleMapDTO.getUser().getLastName());
	userEntity.setMobile(userRoleMapDTO.getUser().getMobile());
	userEntity.setModifiedBy(userRoleMapDTO.getUser().getModifiedBy());
	userEntity.setModifiedOn(userRoleMapDTO.getUser().getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userRoleMapDTO.getUser().getPassword());
	userEntity.setPermAddr(userRoleMapDTO.getUser().getPermAddr());
	userEntity.setState(userRoleMapDTO.getUser().getState());
	userEntity.setCity(userRoleMapDTO.getUser().getCity());
	userEntity.setCountry(userRoleMapDTO.getUser().getCountry());
	userEntity.setZip(userRoleMapDTO.getUser().getZip());
	userEntity.setFatherName(userRoleMapDTO.getUser().getFatherName());
	userEntity.setSpouseName(userRoleMapDTO.getUser().getSpouseName());
	userEntity.setPassport(userRoleMapDTO.getUser().getPassport());

	userEntity.setUserName(userRoleMapDTO.getUser().getUserName());
	userEntity.setId(userRoleMapDTO.getUser().getId());
	userEntity.setLocation(null == userRoleMapDTO.getUser().getLocation() ? null
		: prepareLocationEntity(userRoleMapDTO.getUser().getLocation()));
	userEntity.setUnitId(userRoleMapDTO.getUser().getUnitId());

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
    @RequestMapping(value = "deleteUserRoleMapping/{mappingid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void destroyUserRoleMapping(@PathVariable("mappingid") Long mappingId) {

	userRoleDao.deleteUserRoleMap(mappingId);
    }

    @Override
    public List<UserRoleMapDTO> getFieldEq(Class<UserRoleMapDTO> type, String propertyName, Object value, int offset,
	    int size) {
	return null;
    }

    private UserRoleMapDTO prepareUserRoleMapDTO(UserRoleMap userRoleMapEntity) {
	UserRoleMapDTO userRoleMapDTO = new UserRoleMapDTO();
	userRoleMapDTO.setFromDate(userRoleMapEntity.getFromDate());
	userRoleMapDTO.setId(userRoleMapEntity.getId());
	userRoleMapDTO.setIsEnable(userRoleMapEntity.getIsEnable());
	userRoleMapDTO.setModifiedOn(userRoleMapEntity.getModifiedOn());
	userRoleMapDTO.setModifiedBy(userRoleMapEntity.getModifiedBy());

	RoleDTO roleDTO = prepareRoleDTO(userRoleMapEntity);

	userRoleMapDTO.setRole(roleDTO);
	userRoleMapDTO.setToDate(userRoleMapEntity.getToDate());
	userRoleMapDTO.setUser(prepareUserDTO(userRoleMapEntity.getUser()));
	return userRoleMapDTO;
    }

    private RoleDTO prepareRoleDTO(UserRoleMap userRoleMapEntity) {
	RoleDTO roleDTO = new RoleDTO();
	roleDTO.setFromDate(userRoleMapEntity.getRole().getFromDate());
	roleDTO.setId(userRoleMapEntity.getRole().getId());
	roleDTO.setIsEnable(userRoleMapEntity.getRole().getIsEnable());
	roleDTO.setModifiedBy(userRoleMapEntity.getRole().getModifiedBy());
	roleDTO.setModifiedOn(userRoleMapEntity.getRole().getModifiedOn());
	roleDTO.setRole(userRoleMapEntity.getRole().getRole());
	roleDTO.setToDate(userRoleMapEntity.getRole().getToDate());
	return roleDTO;
    }

    private UserDTO prepareUserDTO(User userEntity) {
	UserDTO userDTO = new UserDTO();
	userDTO.setAlternateEmail(userEntity.getAlternateEmail());
	userDTO.setDateOfBirth(userEntity.getDateOfBirth());
	DepartmentDTO deptDO = new DepartmentDTO();
	deptDO.setDeptName(userEntity.getDepartment().getDeptName());
	deptDO.setId(userEntity.getDepartment().getId());
	OrganizationDTO orgDTO = new OrganizationDTO();
	orgDTO.setId(userEntity.getDepartment().getOrganization().getId());
	orgDTO.setOrgLocation(userEntity.getDepartment().getOrganization().getOrgLocation());
	orgDTO.setOrgName(userEntity.getDepartment().getOrganization().getOrgName());
	deptDO.setOrganization(orgDTO);
	userDTO.setDepartment(deptDO);
	DesignationDTO desigDTO = new DesignationDTO();
	desigDTO.setDesig(userEntity.getDesignation().getDesig());
	desigDTO.setId(userEntity.getDesignation().getId());
	userDTO.setDesignation(desigDTO);
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
	userDTO.setOrganization(orgDTO);
	userDTO.setPassword(userEntity.getPassword());
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

}