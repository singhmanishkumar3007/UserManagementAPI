package com.easybusiness.usermanagement.services.organization;

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

import com.easybusiness.modelmanagement.entity.Organization;
import com.easybusiness.modelmanagement.organisation.OrganizationDao;
import com.easybusiness.usermanagement.dto.OrganizationDTO;

@RestController
@RequestMapping("/easybusiness/organization/")
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao orgDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getOrganizationByName/{orgName}", method = RequestMethod.GET)
    @ResponseBody
    public OrganizationDTO getOrganizationByName(@PathVariable("orgName") String orgName) {

	Organization org = orgDao.findByOrgName(orgName.toUpperCase());
	return prepareOrganizationDetails(org);
    }

    private OrganizationDTO prepareOrganizationDetails(Organization org) {
	OrganizationDTO OrganizationDTO = new OrganizationDTO();
	OrganizationDTO.setId(org.getId());
	OrganizationDTO.setOrgName(org.getOrgName());
	OrganizationDTO.setOrgLocation(org.getOrgLocation());
	return OrganizationDTO;
    }

    @Override
    public List<OrganizationDTO> getOrganizationAsPerCriteria(String whereClause) {

	return null;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addOrganization", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<OrganizationDTO> addOrganization(@RequestBody OrganizationDTO orgModel) {

	orgDao.addOrganization(prepareOrganizationEntity(orgModel));
	return new ResponseEntity<OrganizationDTO>(orgModel, HttpStatus.CREATED);

    }

    private Organization prepareOrganizationEntity(OrganizationDTO organizationDTO) {
	Organization orgEntity = new Organization();
	orgEntity.setOrgLocation(organizationDTO.getOrgLocation());
	orgEntity.setOrgName(organizationDTO.getOrgName());
	return orgEntity;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllOrganizations", method = RequestMethod.GET)
    @ResponseBody
    public List<OrganizationDTO> getAllOrganizations() throws Exception {
	List<Organization> orgList = orgDao.findAll();
	List<OrganizationDTO> orgModelList = new ArrayList<OrganizationDTO>();
	orgList.forEach(orgEntity -> {
	    OrganizationDTO orgModel = new OrganizationDTO();
	    orgModel = prepareOrganizationDetails(orgEntity);
	    orgModelList.add(orgModel);

	});
	return orgModelList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getOrganizationById/{orgId}", method = RequestMethod.GET)
    @ResponseBody
    public OrganizationDTO getOrganizationById(@PathVariable("orgId") Long orgId) {

	return prepareOrganizationDetails(orgDao.findOrganizationById(orgId));
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteOrganization/{orgId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<OrganizationDTO> deleteOrganization(@PathVariable("orgId") Long orgId) {

	// orgMenuDao.deleteOrganizationMenuByOrganizationId(orgId);
	Organization org = orgDao.findOrganizationById(orgId);
	orgDao.deleteOrganization(orgId);
	return new ResponseEntity<OrganizationDTO>(prepareOrganizationDetails(org), HttpStatus.OK);

    }


}
