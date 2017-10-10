package com.easybusiness.usermanagement.services.usertobank;

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

import com.easybusiness.modelmanagement.bank.BankDao;
import com.easybusiness.modelmanagement.branch.BranchDao;
import com.easybusiness.modelmanagement.entity.Bank;
import com.easybusiness.modelmanagement.entity.Branch;
import com.easybusiness.modelmanagement.entity.LocationMaster;
import com.easybusiness.modelmanagement.entity.User;
import com.easybusiness.modelmanagement.entity.UserBankMap;
import com.easybusiness.modelmanagement.user.UserDao;
import com.easybusiness.modelmanagement.userbankmap.UserBankMapDao;
import com.easybusiness.usermanagement.dto.BankDTO;
import com.easybusiness.usermanagement.dto.BranchDTO;
import com.easybusiness.usermanagement.dto.DepartmentDTO;
import com.easybusiness.usermanagement.dto.DesignationDTO;
import com.easybusiness.usermanagement.dto.LocationMasterDTO;
import com.easybusiness.usermanagement.dto.OrganizationDTO;
import com.easybusiness.usermanagement.dto.UserBankMapDTO;
import com.easybusiness.usermanagement.dto.UserDTO;

@RestController
@RequestMapping("/easybusiness/userbank/")
public class UserBankMappingServiceImpl implements UserBankMappingService {

    @Autowired
    UserBankMapDao userBankDao;

    @Autowired
    UserDao userDao;

    @Autowired
    BankDao bankDao;

    @Autowired
    BranchDao branchDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getbankByUserId/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserBankMapDTO> getBankByUserId(@PathVariable("userid") Long userId) {

	User user = userDao.findUserById(userId);
	List<UserBankMap> userBankEntityList = userBankDao.findByUser(user);
	List<UserBankMapDTO> userBankMapList = new ArrayList<UserBankMapDTO>();
	userBankEntityList.forEach(userBankEntity -> {
	    UserBankMapDTO userBankMapDTO = prepareUserbankMapDTO(userBankEntity);
	    userBankMapList.add(userBankMapDTO);
	});

	return userBankMapList;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getbankById/{bankid}", method = RequestMethod.GET)
    @ResponseBody
    public BankDTO getBankById(@PathVariable("bankid") Long bankId) {

	Bank bank = bankDao.findBankById(bankId);
	BankDTO bankDTO = new BankDTO();
	bankDTO.setAddress(bank.getAddress());
	bankDTO.setBankName(bank.getBankName());
	bankDTO.setId(bank.getId());
	bankDTO.setLocation(bank.getLocation());
	return bankDTO;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getbranchById/{branchid}", method = RequestMethod.GET)
    @ResponseBody
    public BranchDTO getBranchById(@PathVariable("branchid") Long branchId) {

	Branch branch = branchDao.findBranchById(branchId);
	BranchDTO branchDTO = new BranchDTO();
	branchDTO.setAddress(branch.getAddress());
	Bank bank = branch.getBank();
	BankDTO bankDTO = new BankDTO();
	bankDTO.setAddress(bank.getAddress());
	bankDTO.setBankName(bank.getBankName());
	bankDTO.setId(bank.getId());
	bankDTO.setLocation(bank.getLocation());
	branchDTO.setBank(bankDTO);
	branchDTO.setBranchName(branch.getBranchName());
	branchDTO.setId(branch.getId());
	branchDTO.setIfscCode(branch.getIfscCode());
	return branchDTO;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getbranchByBankId/{bankid}", method = RequestMethod.GET)
    @ResponseBody
    public List<BranchDTO> getBranchByBankId(@PathVariable("bankid") Long bankId) {

	Bank bank = bankDao.findBankById(bankId);

	List<Branch> branch = branchDao.findByBank(bank);
	List<BranchDTO> branchDTOList = new ArrayList<BranchDTO>();
	branch.forEach(branchEntity -> {
	    BranchDTO branchDTO = new BranchDTO();
	    branchDTO.setAddress(branchEntity.getAddress());
	    BankDTO bankDTO = new BankDTO();
	    bankDTO.setAddress(branchEntity.getBank().getAddress());
	    bankDTO.setBankName(branchEntity.getBank().getBankName());
	    bankDTO.setId(branchEntity.getBank().getId());
	    bankDTO.setLocation(branchEntity.getBank().getLocation());
	    bankDTO.setModifiedBy(branchEntity.getBank().getModifiedBy());
	    bankDTO.setModifiedOn(branchEntity.getBank().getModifiedOn());
	    branchDTO.setBank(bankDTO);
	    branchDTO.setBranchName(branchEntity.getBranchName());
	    branchDTO.setId(branchEntity.getId());
	    branchDTO.setModifiedBy(branchEntity.getModifiedBy());
	    branchDTO.setModifiedOn(branchEntity.getModifiedOn());
	    branchDTO.setIfscCode(branchEntity.getIfscCode());
	    branchDTOList.add(branchDTO);
	});
	return branchDTOList;

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllBanksAndBranches", method = RequestMethod.GET)
    @ResponseBody
    public List<BranchDTO> getAllBanksAndBranches() throws Exception {

	List<Branch> branchList = branchDao.findAll();
	List<BranchDTO> branchDTOList = new ArrayList<BranchDTO>();
	branchList.forEach(branchEntity -> {
	    BranchDTO branchDTO = new BranchDTO();
	    branchDTO.setAddress(branchEntity.getAddress());
	    BankDTO bankDTO = new BankDTO();
	    bankDTO.setAddress(branchEntity.getBank().getAddress());
	    bankDTO.setBankName(branchEntity.getBank().getBankName());
	    bankDTO.setId(branchEntity.getBank().getId());
	    bankDTO.setLocation(branchEntity.getBank().getLocation());
	    bankDTO.setModifiedBy(branchEntity.getBank().getModifiedBy());
	    bankDTO.setModifiedOn(branchEntity.getBank().getModifiedOn());
	    branchDTO.setBank(bankDTO);
	    branchDTO.setBranchName(branchEntity.getBranchName());
	    branchDTO.setId(branchEntity.getId());
	    branchDTO.setModifiedBy(branchEntity.getModifiedBy());
	    branchDTO.setModifiedOn(branchEntity.getModifiedOn());
	    branchDTO.setIfscCode(branchEntity.getIfscCode());
	    branchDTOList.add(branchDTO);
	});
	return branchDTOList;

    }

    private UserBankMapDTO prepareUserbankMapDTO(UserBankMap userBankEntity) {
	UserBankMapDTO userBankMapDTO = new UserBankMapDTO();
	userBankMapDTO.setAccountNum(userBankEntity.getAccountNum());
	BankDTO bankDTO = prepareBankDTO(userBankEntity);
	userBankMapDTO.setBank(bankDTO);
	BranchDTO branchDTO = prepareBranchDTO(userBankEntity, bankDTO);

	userBankMapDTO.setBranch(branchDTO);
	userBankMapDTO.setId(userBankEntity.getId());
	userBankMapDTO.setIfscCode(userBankEntity.getIfscCode());
	userBankMapDTO.setModifiedBy(userBankEntity.getModifiedBy());
	userBankMapDTO.setModifiedOn(userBankEntity.getModifiedOn());
	userBankMapDTO.setUser(prepareUserDTO(userBankEntity.getUser()));
	userBankMapDTO.setAccountType(userBankEntity.getAccountType());
	return userBankMapDTO;
    }

    private BranchDTO prepareBranchDTO(UserBankMap userBankEntity, BankDTO bankDTO) {
	BranchDTO branchDTO = new BranchDTO();
	branchDTO.setAddress(userBankEntity.getBranch().getAddress());
	branchDTO.setBank(bankDTO);
	branchDTO.setBranchName(userBankEntity.getBranch().getBranchName());
	branchDTO.setId(userBankEntity.getBranch().getId());
	branchDTO.setModifiedBy(userBankEntity.getBranch().getModifiedBy());
	branchDTO.setModifiedOn(userBankEntity.getBranch().getModifiedOn());
	branchDTO.setIfscCode(userBankEntity.getBranch().getIfscCode());
	return branchDTO;
    }

    private BankDTO prepareBankDTO(UserBankMap userBankEntity) {
	BankDTO bankDTO = new BankDTO();
	bankDTO.setAddress(userBankEntity.getBank().getAddress());
	bankDTO.setBankName(userBankEntity.getBank().getBankName());
	bankDTO.setId(userBankEntity.getBank().getId());
	bankDTO.setLocation(userBankEntity.getBank().getLocation());
	bankDTO.setModifiedBy(userBankEntity.getBank().getModifiedBy());
	bankDTO.setModifiedOn(userBankEntity.getBank().getModifiedOn());
	return bankDTO;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "mapUserBank", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserBankMapDTO> persistUserBankDetails(@RequestBody UserBankMapDTO userBankMapDTO) {

	UserBankMap userBankMapEntity = new UserBankMap();
	userBankMapEntity.setAccountNum(userBankMapDTO.getAccountNum());
	userBankMapEntity.setBank(bankDao.findBankById(userBankMapDTO.getBank().getId()));
	userBankMapEntity.setBranch(branchDao.findBranchById(userBankMapDTO.getBranch().getId()));
	userBankMapEntity.setIfscCode(userBankMapDTO.getIfscCode());
	userBankMapEntity.setModifiedBy(userBankMapDTO.getModifiedBy());
	userBankMapEntity.setModifiedOn(userBankMapDTO.getModifiedOn());
	userBankMapEntity.setAccountType(userBankMapDTO.getAccountType());
	User user = userDao.findUserById(userBankMapDTO.getUser().getId());
	userBankMapEntity.setUser(user);
	userBankDao.addUserBankMap(userBankMapEntity);
	return new ResponseEntity<UserBankMapDTO>(userBankMapDTO, HttpStatus.CREATED);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "destroyUserBankDetails/{mappingid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void destroyUserBankDetails(@PathVariable("mappingid") Long mappingId) {

	userBankDao.deleteUserBankMap(mappingId);
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
	userEntity.setLocation(null == userDTO.getLocation() ? null : prepareLocationEntity(userDTO.getLocation()));
	userEntity.setUnitId(userDTO.getUnitId());
	return userDTO;
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

}
