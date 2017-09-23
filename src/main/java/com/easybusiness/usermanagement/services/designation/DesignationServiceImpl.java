package com.easybusiness.usermanagement.services.designation;

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

import com.easybusiness.modelmanagement.designation.DesignationDao;
import com.easybusiness.modelmanagement.entity.Designation;
import com.easybusiness.usermanagement.dto.DesignationDTO;

@RestController
@RequestMapping("/easybusiness/designation/")
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationDao desigDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getDesignationByName/{desigName}", method = RequestMethod.GET)
    @ResponseBody
    public DesignationDTO getDesignationByName(@PathVariable("desigName") String desigName) {

	Designation dept = desigDao.findByDesigName(desigName.toUpperCase());
	return prepareDesignationDetails(dept);
    }

    private DesignationDTO prepareDesignationDetails(Designation desig) {
	DesignationDTO DesignationDTO = new DesignationDTO();
	DesignationDTO.setId(desig.getId());
	DesignationDTO.setDesig(desig.getDesig());
	return DesignationDTO;
    }

    @Override
    public List<DesignationDTO> getDesignationAsPerCriteria(String whereClause) {

	return null;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addDesignation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DesignationDTO> addDesignation(@RequestBody DesignationDTO desigModel) {

	desigDao.addDesignation(prepareDesignationEntity(desigModel));
	return new ResponseEntity<DesignationDTO>(desigModel, HttpStatus.CREATED);

    }

    private Designation prepareDesignationEntity(DesignationDTO desigDTO) {
	Designation desigEntity = new Designation();
	desigEntity.setDesig(desigDTO.getDesig());
	return desigEntity;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllDesignations", method = RequestMethod.GET)
    @ResponseBody
    public List<DesignationDTO> getAllDesignations() throws Exception {
	List<Designation> deptList = desigDao.findAll();
	List<DesignationDTO> deptModelList = new ArrayList<DesignationDTO>();
	deptList.forEach(deptEntity -> {
	    DesignationDTO desigModel = new DesignationDTO();
	    desigModel = prepareDesignationDetails(deptEntity);
	    deptModelList.add(desigModel);

	});
	return deptModelList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getDesignationById/{desigId}", method = RequestMethod.GET)
    @ResponseBody
    public DesignationDTO getDesignationById(@PathVariable("desigId") Long desigId) {

	return prepareDesignationDetails(desigDao.findDesignationById(desigId));
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteDesignation/{desigId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<DesignationDTO> deleteDesignation(@PathVariable("desigId") Long desigId) {

	// deptMenuDao.deleteDesignationMenuByDesignationId(deptId);
	Designation desig = desigDao.findDesignationById(desigId);
	desigDao.deleteDesignation(desigId);
	return new ResponseEntity<DesignationDTO>(prepareDesignationDetails(desig), HttpStatus.OK);

    }

}
