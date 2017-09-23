package com.easybusiness.usermanagement.services.department;

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

import com.easybusiness.modelmanagement.department.DepartmentDao;
import com.easybusiness.modelmanagement.entity.Department;
import com.easybusiness.modelmanagement.entity.Organization;
import com.easybusiness.usermanagement.dto.DepartmentDTO;
import com.easybusiness.usermanagement.dto.OrganizationDTO;

@RestController
@RequestMapping("/easybusiness/department/")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getDepartmentByName/{deptName}", method = RequestMethod.GET)
    @ResponseBody
    public DepartmentDTO getDepartmentByName(@PathVariable("deptName") String deptName) {

	Department dept = departmentDao.findByDepartmentName(deptName.toUpperCase());
	return prepareDepartmentDetails(dept);
    }

    private DepartmentDTO prepareDepartmentDetails(Department dept) {
	DepartmentDTO DepartmentDTO = new DepartmentDTO();
	DepartmentDTO.setId(dept.getId());
	DepartmentDTO.setDeptName(dept.getDeptName());
	OrganizationDTO organizationDTO = new OrganizationDTO();
	organizationDTO.setId(dept.getOrganization().getId());
	organizationDTO.setOrgLocation(dept.getOrganization().getOrgLocation());
	organizationDTO.setOrgName(dept.getOrganization().getOrgName());
	DepartmentDTO.setOrganization(organizationDTO);
	return DepartmentDTO;
    }

    @Override
    public List<DepartmentDTO> getDepartmentAsPerCriteria(String whereClause) {

	return null;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addDepartment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO deptModel) {

	departmentDao.addDepartment(prepareDepartmentEntity(deptModel));
	return new ResponseEntity<DepartmentDTO>(deptModel, HttpStatus.CREATED);

    }

    private Department prepareDepartmentEntity(DepartmentDTO deptDTO) {
	Department deptEntity = new Department();
	deptEntity.setDeptName(deptDTO.getDeptName());
	Organization organization = new Organization();
	organization.setId(deptDTO.getOrganization().getId());
	organization.setOrgLocation(deptDTO.getOrganization().getOrgLocation());
	organization.setOrgName(deptDTO.getOrganization().getOrgName());
	deptEntity.setOrganization(organization);
	return deptEntity;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllDepartments", method = RequestMethod.GET)
    @ResponseBody
    public List<DepartmentDTO> getAllDepartments() throws Exception {
	List<Department> deptList = departmentDao.findAll();
	List<DepartmentDTO> deptModelList = new ArrayList<DepartmentDTO>();
	deptList.forEach(deptEntity -> {
	    DepartmentDTO deptModel = new DepartmentDTO();
	    deptModel = prepareDepartmentDetails(deptEntity);
	    deptModelList.add(deptModel);

	});
	return deptModelList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getDepartmentById/{deptId}", method = RequestMethod.GET)
    @ResponseBody
    public DepartmentDTO getDepartmentById(@PathVariable("deptId") Long deptId) {

	return prepareDepartmentDetails(departmentDao.findDepartmentById(deptId));
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllDepartmentsByOrganization/{orgId}", method = RequestMethod.GET)
    @ResponseBody
    public List<DepartmentDTO> getAllDepartmentsByOrganization(@PathVariable("orgId") Long orgId) throws Exception {
	List<Department> deptList = departmentDao.findDepartmentByOrgId(orgId);
	List<DepartmentDTO> deptModelList = new ArrayList<DepartmentDTO>();
	deptList.forEach(deptEntity -> {
	    DepartmentDTO deptModel = new DepartmentDTO();
	    deptModel = prepareDepartmentDetails(deptEntity);
	    deptModelList.add(deptModel);

	});
	return deptModelList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteDepartment/{deptId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable("deptId") Long deptId) {

	// deptMenuDao.deleteDepartmentMenuByDepartmentId(deptId);
	Department dept = departmentDao.findDepartmentById(deptId);
	departmentDao.deleteDepartment(deptId);
	return new ResponseEntity<DepartmentDTO>(prepareDepartmentDetails(dept), HttpStatus.OK);

    }

}
