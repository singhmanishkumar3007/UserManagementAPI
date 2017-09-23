package com.easybusiness.usermanagement.services.role;

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

import com.easybusiness.modelmanagement.entity.Role;
import com.easybusiness.modelmanagement.role.RoleDao;
import com.easybusiness.usermanagement.dto.RoleDTO;

@RestController
@RequestMapping("/easybusiness/role/")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getRoleByName/{roleName}", method = RequestMethod.GET)
    @ResponseBody
    public RoleDTO getRoleByName(@PathVariable("roleName") String roleName) {

	Role role = roleDao.findByRoleName(roleName.toUpperCase());
	return prepareRoleDetails(role);
    }

    private RoleDTO prepareRoleDetails(Role role) {
	RoleDTO roleDTO = new RoleDTO();
	roleDTO.setId(role.getId());
	roleDTO.setRole(role.getRole());
	roleDTO.setFromDate(role.getFromDate());
	roleDTO.setIsEnable(role.getIsEnable());
	roleDTO.setModifiedBy(role.getModifiedBy());
	roleDTO.setModifiedOn(role.getModifiedOn());
	roleDTO.setToDate(role.getToDate());
	return roleDTO;
    }

    @Override
    public List<RoleDTO> getRolesAsPerCriteria(String whereClause) {

	return null;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO roleModel) {

	roleDao.addRole(prepareRoleEntity(roleModel));
	return new ResponseEntity<RoleDTO>(roleModel, HttpStatus.CREATED);

    }

    private Role prepareRoleEntity(RoleDTO roleDTO) {
	Role roleEntity = new Role();
	roleEntity.setRole(roleDTO.getRole());
	roleEntity.setFromDate(roleDTO.getFromDate());
	roleEntity.setIsEnable(roleDTO.getIsEnable());
	roleEntity.setModifiedBy(roleDTO.getModifiedBy());
	roleEntity.setModifiedOn(roleDTO.getModifiedOn());
	roleEntity.setToDate(roleDTO.getToDate());
	return roleEntity;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllRoles", method = RequestMethod.GET)
    @ResponseBody
    public List<RoleDTO> getAllRoles() throws Exception {
	List<Role> roleList = roleDao.findAll();
	List<RoleDTO> roleModelList = new ArrayList<RoleDTO>();
	roleList.forEach(roleEntity -> {
	    RoleDTO roleModel = new RoleDTO();
	    roleModel = prepareRoleDetails(roleEntity);
	    roleModelList.add(roleModel);

	});
	return roleModelList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getRoleById/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public RoleDTO getRoleById(@PathVariable("roleId") Long roleId) {

	return prepareRoleDetails(roleDao.findRoleById(roleId));
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteRole/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable("roleId") Long roleId) {

	// roleMenuDao.deleteRoleMenuByRoleId(roleId);
	Role role = roleDao.findRoleById(roleId);
	roleDao.deleteRole(roleId);
	return new ResponseEntity<RoleDTO>(prepareRoleDetails(role), HttpStatus.OK);

    }

    @Override
    public List<RoleDTO> getFieldEq(Class<RoleDTO> type, String propertyName, Object value, int offset, int size) {

	return null;
    }

}
