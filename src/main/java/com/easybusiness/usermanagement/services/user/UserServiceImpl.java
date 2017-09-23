package com.easybusiness.usermanagement.services.user;

import static com.easybusiness.usermanagement.constant.UserManagementConstant.EMAIL_DOMAIN;
import static com.easybusiness.usermanagement.constant.UserManagementConstant.USER_HOST_SERVER;

import java.sql.Blob;
import java.sql.SQLException;
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
import com.easybusiness.modelmanagement.entity.UserAcademics;
import com.easybusiness.modelmanagement.entity.UserImage;
import com.easybusiness.modelmanagement.entity.UserProfession;
import com.easybusiness.modelmanagement.user.UserAcademicsDao;
import com.easybusiness.modelmanagement.user.UserDao;
import com.easybusiness.modelmanagement.user.UserImageDao;
import com.easybusiness.modelmanagement.user.UserProfessionDao;
import com.easybusiness.usermanagement.dto.DepartmentDTO;
import com.easybusiness.usermanagement.dto.DesignationDTO;
import com.easybusiness.usermanagement.dto.LocationMasterDTO;
import com.easybusiness.usermanagement.dto.OrganizationDTO;
import com.easybusiness.usermanagement.dto.UserAcademicsDTO;
import com.easybusiness.usermanagement.dto.UserDTO;
import com.easybusiness.usermanagement.dto.UserImageDTO;
import com.easybusiness.usermanagement.dto.UserProfessionDTO;

@RestController
@RequestMapping("/easybusiness/user/")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserImageDao userImageDao;

    @Autowired
    UserProfessionDao userProfessionDao;

    @Autowired
    UserAcademicsDao userAcademicsDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getByUserName/{username}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") String userName) {
	System.out.println("in get user");
	User userEntity = userDao.findByUserName(userName);
	return new ResponseEntity<UserDTO>(prepareUserDTO(userEntity), HttpStatus.OK);
    }

    private UserDTO prepareUserDTO(User userEntity) {
	UserDTO userDTO = new UserDTO();
	userDTO.setAlternateEmail(userEntity.getAlternateEmail());
	userDTO.setDateOfBirth(userEntity.getDateOfBirth());
	DepartmentDTO deptDO = new DepartmentDTO();
	try {
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
	    LOGGER.error("error in getting organization/department of user {} {}", userEntity.getUserName(),
		    e.getMessage());
	}
	try {
	    DesignationDTO desigDTO = new DesignationDTO();

	    desigDTO.setDesig(userEntity.getDesignation().getDesig());
	    desigDTO.setId(userEntity.getDesignation().getId());

	    userDTO.setDesignation(desigDTO);
	} catch (Exception e) {
	    LOGGER.error("error in getting designation of user {} {}", userEntity.getUserName(), e.getMessage());
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
	userDTO.setUserImg(null);

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

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserDTO> persistUser(@RequestBody UserDTO userDTO) {

	System.out.println(
		"user dto in persistence layer to be set is " + userDTO.toString() + "`" + userDTO.getUserImg());

	User userEntity = new User();
	userEntity.setAlternateEmail(userDTO.getAlternateEmail());
	userEntity.setDateOfBirth(userDTO.getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userDTO.getDepartment().getDeptName());
	dept.setId(userDTO.getDepartment().getId());
	Organization org = new Organization();
	org.setId(userDTO.getDepartment().getOrganization().getId());
	org.setOrgLocation(userDTO.getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userDTO.getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userDTO.getDesignation().getDesig());
	desg.setId(userDTO.getDesignation().getId());
	userEntity.setDesignation(desg);
	// userEntity.setEmail(userDTO.getEmail());
	userEntity.setEndDate(userDTO.getEndDate());
	userEntity.setFirstName(userDTO.getFirstName());
	userEntity.setFromDate(userDTO.getFromDate());
	userEntity.setGender(userDTO.getGender());
	userEntity.setIsEnabled(userDTO.getIsEnabled());
	userEntity.setLastName(userDTO.getLastName());
	userEntity.setMobile(userDTO.getMobile());
	userEntity.setModifiedBy(userDTO.getModifiedBy());
	userEntity.setModifiedOn(userDTO.getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userDTO.getPassword());
	userEntity.setTypeOfEmployment(userDTO.getTypeOfEmployment());
	// userEntity.setUserName(userDTO.getUserName());

	String uniqueUserName = prepareUniqueUserName((userDTO.getFirstName().toLowerCase()).charAt(0),
		userDTO.getLastName().toLowerCase());
	userEntity.setUserName(uniqueUserName);
	userEntity.setEmail(uniqueUserName + EMAIL_DOMAIN);

	userEntity.setPermAddr(userDTO.getPermAddr());
	userEntity.setState(userDTO.getState());
	userEntity.setCity(userDTO.getCity());
	userEntity.setCountry(userDTO.getCountry());
	userEntity.setZip(userDTO.getZip());
	userEntity.setFatherName(userDTO.getFatherName());
	userEntity.setSpouseName(userDTO.getSpouseName());
	userEntity.setPassport(userDTO.getPassport());
	userEntity.setLocation(null == userDTO.getLocation() ? null : prepareLocationEntity(userDTO.getLocation()));
	userEntity.setUnitId(userDTO.getUnitId());
	userDao.addUser(userEntity);
	userDTO.setUserName(userEntity.getUserName());
	userDTO.setEmail(userEntity.getEmail());
	return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);

    }

    private String prepareUniqueUserName(char firstCharOfFirstName, String lastName) {
	int countOfAlreadyPresentSimilarUserName = userDao.findCountOfUserName(firstCharOfFirstName + lastName);
	return countOfAlreadyPresentSimilarUserName == 0 ? (firstCharOfFirstName + lastName)
		: (firstCharOfFirstName + lastName + (countOfAlreadyPresentSimilarUserName + 1));
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
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

	System.out.println(
		"user dto in persistence layer to be updated is " + userDTO.toString() + "`" + userDTO.getUserImg());
	User userGetEntity = userDao.findByUserName(userDTO.getUserName());
	User userEntity = new User();
	userEntity.setAlternateEmail(userDTO.getAlternateEmail());
	userEntity.setDateOfBirth(userDTO.getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userDTO.getDepartment().getDeptName());
	dept.setId(userDTO.getDepartment().getId());
	Organization org = new Organization();
	org.setId(userDTO.getDepartment().getOrganization().getId());
	org.setOrgLocation(userDTO.getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userDTO.getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userDTO.getDesignation().getDesig());
	desg.setId(userDTO.getDesignation().getId());
	userEntity.setDesignation(desg);
	userEntity.setEmail(userDTO.getEmail());
	userEntity.setEndDate(userDTO.getEndDate());
	userEntity.setFirstName(userDTO.getFirstName());
	userEntity.setFromDate(userDTO.getFromDate());
	userEntity.setGender(userDTO.getGender());
	userEntity.setIsEnabled(userDTO.getIsEnabled());
	userEntity.setLastName(userDTO.getLastName());
	userEntity.setMobile(userDTO.getMobile());
	userEntity.setModifiedBy(userDTO.getModifiedBy());
	userEntity.setModifiedOn(userDTO.getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userDTO.getPassword());
	userEntity.setTypeOfEmployment(userDTO.getTypeOfEmployment());
	userEntity.setUserName(userDTO.getUserName());

	userEntity.setPermAddr(userDTO.getPermAddr());
	userEntity.setState(userDTO.getState());
	userEntity.setCity(userDTO.getCity());
	userEntity.setCountry(userDTO.getCountry());
	userEntity.setZip(userDTO.getZip());
	userEntity.setFatherName(userDTO.getFatherName());
	userEntity.setSpouseName(userDTO.getSpouseName());
	userEntity.setPassport(userDTO.getPassport());
	userEntity.setId(userGetEntity.getId());

	userEntity.setLocation(null == userDTO.getLocation() ? null : prepareLocationEntity(userDTO.getLocation()));
	userEntity.setUnitId(userDTO.getUnitId());

	System.out.println("user Entity to send to dao is " + userEntity);
	userDao.updateUser(userEntity);
	return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> populateUserList() {
	List<UserDTO> userDTOList = new ArrayList<UserDTO>();
	try {
	    List<User> userEntityList = userDao.findAll();

	    userEntityList.forEach(userEntity -> {
		if (userEntity.getIsEnabled() == 1) {
		    userDTOList.add(prepareUserDTO(userEntity));
		}
	    });

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return userDTOList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getByUserId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO populateOneUserDetails(@PathVariable("userId") Long userId) {
	User userEntity = userDao.findUserById(userId);
	return prepareUserDTO(userEntity);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteUser/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void destroyUser(@PathVariable("userId") Long userId) {

	userDao.deleteUser(userId);
    }

    @Override
    public List<UserDTO> getFieldEq(Class<UserDTO> type, String propertyName, Object value, int offset, int size) {
	return null;
    }

    @Override
    public void persistUser(UserDTO loggedUser, boolean changePassword) {

    }

    @Override
    public void activateUser(UserDTO user) {

    }

    @Override
    public void deActivateUser(UserDTO user) {

    }

    @Override
    public UserDTO getActiveUser(String email) {
	return null;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addUserImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserImageDTO> persistUserImage(@RequestBody UserImageDTO userImage) {
	// TODO Auto-generated method stub

	UserImage userImageValue = new UserImage();
	System.out.println("user image DTO to be sent to Dao is " + userImage.toString());
	userImageValue.setUser(prepareUserEntity(userImage));

	try {
	    Blob blob = new javax.sql.rowset.serial.SerialBlob(userImage.getUserImg());
	    userImageValue.setUserImg(userImage.getUserImg());
	} catch (SQLException e) {
	    e.printStackTrace();
	    userImageValue.setUserImg(null);
	}
	userImageDao.addUserImage(userImageValue);
	return new ResponseEntity<UserImageDTO>(userImage, HttpStatus.CREATED);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserImage/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserImageDTO> getUserImage(@PathVariable("userid") String userId) {
	System.out.println("in get user");
	UserImage userImageEntity = userImageDao.getUserImage(userDao.findUserById(Long.parseLong(userId)));
	UserImageDTO userImageDTO = new UserImageDTO();
	if (null != userImageEntity) {

	    userImageDTO.setId(userImageEntity.getId());
	    userImageDTO.setUser(prepareUserDTO(userImageEntity.getUser()));
	    byte data[];
	    // data = userImageEntity.getUserImg().getBytes(1, (int)
	    // userImageEntity.getUserImg().length());

	    data = userImageEntity.getUserImg();
	    userImageDTO.setUserImg(data);
	}
	return new ResponseEntity<UserImageDTO>(userImageDTO, HttpStatus.OK);

    }

    private User prepareUserEntity(UserImageDTO userImageDTO) {
	User userEntity = new User();
	userEntity.setAlternateEmail(userImageDTO.getUser().getAlternateEmail());
	userEntity.setDateOfBirth(userImageDTO.getUser().getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userImageDTO.getUser().getDepartment().getDeptName());
	dept.setId(userImageDTO.getUser().getDepartment().getId());
	Organization org = new Organization();
	org.setId(userImageDTO.getUser().getDepartment().getOrganization().getId());
	org.setOrgLocation(userImageDTO.getUser().getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userImageDTO.getUser().getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userImageDTO.getUser().getDesignation().getDesig());
	desg.setId(userImageDTO.getUser().getDesignation().getId());
	userEntity.setDesignation(desg);
	userEntity.setEmail(userImageDTO.getUser().getEmail());
	userEntity.setEndDate(userImageDTO.getUser().getEndDate());
	userEntity.setFirstName(userImageDTO.getUser().getFirstName());
	userEntity.setFromDate(userImageDTO.getUser().getFromDate());
	userEntity.setGender(userImageDTO.getUser().getGender());
	userEntity.setIsEnabled(userImageDTO.getUser().getIsEnabled());
	userEntity.setLastName(userImageDTO.getUser().getLastName());
	userEntity.setMobile(userImageDTO.getUser().getMobile());
	userEntity.setModifiedBy(userImageDTO.getUser().getModifiedBy());
	userEntity.setModifiedOn(userImageDTO.getUser().getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userImageDTO.getUser().getPassword());
	userEntity.setTypeOfEmployment(userImageDTO.getUser().getTypeOfEmployment());
	userEntity.setUserName(userImageDTO.getUser().getUserName());
	userEntity.setId(userImageDTO.getUser().getId());

	userEntity.setPermAddr(userImageDTO.getUser().getPermAddr());
	userEntity.setState(userImageDTO.getUser().getState());
	userEntity.setCity(userImageDTO.getUser().getCity());
	userEntity.setCountry(userImageDTO.getUser().getCountry());
	userEntity.setZip(userImageDTO.getUser().getZip());
	userEntity.setFatherName(userImageDTO.getUser().getFatherName());
	userEntity.setSpouseName(userImageDTO.getUser().getSpouseName());
	userEntity.setPassport(userImageDTO.getUser().getPassport());

	userEntity.setLocation(null == userImageDTO.getUser().getLocation() ? null
		: prepareLocationEntity(userImageDTO.getUser().getLocation()));
	userEntity.setUnitId(userImageDTO.getUser().getUnitId());

	return userEntity;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserProfession/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserProfessionDTO> getUserProfession(@PathVariable("userid") String userId) {
	System.out.println("in get user Profession");
	UserProfession userProfessionEntity = userProfessionDao
		.getUserProfession(userDao.findUserById(Long.parseLong(userId)));
	UserProfessionDTO userProfessionDTO = new UserProfessionDTO();
	if (null != userProfessionEntity) {

	    userProfessionDTO.setId(userProfessionEntity.getId());
	    userProfessionDTO.setUser(prepareUserDTO(userProfessionEntity.getUser()));
	    userProfessionDTO.setPrevAchievement(userProfessionEntity.getPrevAchievement());
	    userProfessionDTO.setPrevDesignation(userProfessionEntity.getPrevDesignation());
	    userProfessionDTO.setPrevFromDate(userProfessionEntity.getPrevFromDate());
	    userProfessionDTO.setPrevOnsiteDuration(userProfessionEntity.getPrevOnsiteDuration());
	    userProfessionDTO.setPrevOrganization(userProfessionEntity.getPrevOrganization());
	    userProfessionDTO.setPrevTechnology(userProfessionEntity.getPrevTechnology());
	    userProfessionDTO.setPrevToDate(userProfessionEntity.getPrevToDate());
	    userProfessionDTO.setPrimarySkill(userProfessionEntity.getPrimarySkill());
	    userProfessionDTO.setPrimarySkillDuration(userProfessionEntity.getPrimarySkillDuration());
	    userProfessionDTO.setRelevantExp(userProfessionEntity.getRelevantExp());
	    userProfessionDTO.setTotalExp(userProfessionEntity.getTotalExp());

	}
	return new ResponseEntity<UserProfessionDTO>(userProfessionDTO, HttpStatus.OK);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addUserProfession", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserProfessionDTO> persistUserProfession(@RequestBody UserProfessionDTO userProfession) {
	// TODO Auto-generated method stub

	UserProfession userProfessionValue = new UserProfession();
	System.out.println("user profession DTO to be sent to Dao is " + userProfession.toString());
	userProfessionValue.setUser(prepareUserProfessionEntity(userProfession));
	userProfessionValue.setPrevAchievement(userProfession.getPrevAchievement());
	userProfessionValue.setPrevDesignation(userProfession.getPrevDesignation());
	userProfessionValue.setPrevFromDate(userProfession.getPrevFromDate());
	userProfessionValue.setPrevOnsiteDuration(userProfession.getPrevOnsiteDuration());
	userProfessionValue.setPrevOrganization(userProfession.getPrevOrganization());
	userProfessionValue.setPrevTechnology(userProfession.getPrevTechnology());
	userProfessionValue.setPrevToDate(userProfession.getPrevToDate());
	userProfessionValue.setPrimarySkill(userProfession.getPrimarySkill());
	userProfessionValue.setPrimarySkillDuration(userProfession.getPrimarySkillDuration());
	userProfessionValue.setRelevantExp(userProfession.getRelevantExp());
	userProfessionValue.setTotalExp(userProfession.getTotalExp());

	userProfessionDao.addUserProfession(userProfessionValue);
	return new ResponseEntity<UserProfessionDTO>(userProfession, HttpStatus.CREATED);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "updateUserProfession", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserProfessionDTO> updateUserProfession(@RequestBody UserProfessionDTO userProfession) {
	// TODO Auto-generated method stub

	UserProfession userProfessionValue = new UserProfession();
	System.out.println("user profession DTO to be sent to Dao is " + userProfession.toString());
	userProfessionValue.setUser(prepareUserProfessionEntity(userProfession));
	userProfessionValue.setPrevAchievement(userProfession.getPrevAchievement());
	userProfessionValue.setPrevDesignation(userProfession.getPrevDesignation());
	userProfessionValue.setPrevFromDate(userProfession.getPrevFromDate());
	userProfessionValue.setPrevOnsiteDuration(userProfession.getPrevOnsiteDuration());
	userProfessionValue.setPrevOrganization(userProfession.getPrevOrganization());
	userProfessionValue.setPrevTechnology(userProfession.getPrevTechnology());
	userProfessionValue.setPrevToDate(userProfession.getPrevToDate());
	userProfessionValue.setPrimarySkill(userProfession.getPrimarySkill());
	userProfessionValue.setPrimarySkillDuration(userProfession.getPrimarySkillDuration());
	userProfessionValue.setRelevantExp(userProfession.getRelevantExp());
	userProfessionValue.setTotalExp(userProfession.getTotalExp());
	userProfessionValue.setId(userProfession.getId());

	userProfessionDao.updateUserProfession(userProfessionValue);
	return new ResponseEntity<UserProfessionDTO>(userProfession, HttpStatus.CREATED);
    }

    private User prepareUserProfessionEntity(UserProfessionDTO userProfessionDTO) {
	User userEntity = new User();
	userEntity.setAlternateEmail(userProfessionDTO.getUser().getAlternateEmail());
	userEntity.setDateOfBirth(userProfessionDTO.getUser().getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userProfessionDTO.getUser().getDepartment().getDeptName());
	dept.setId(userProfessionDTO.getUser().getDepartment().getId());
	Organization org = new Organization();
	org.setId(userProfessionDTO.getUser().getDepartment().getOrganization().getId());
	org.setOrgLocation(userProfessionDTO.getUser().getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userProfessionDTO.getUser().getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userProfessionDTO.getUser().getDesignation().getDesig());
	desg.setId(userProfessionDTO.getUser().getDesignation().getId());
	userEntity.setDesignation(desg);
	userEntity.setEmail(userProfessionDTO.getUser().getEmail());
	userEntity.setEndDate(userProfessionDTO.getUser().getEndDate());
	userEntity.setFirstName(userProfessionDTO.getUser().getFirstName());
	userEntity.setFromDate(userProfessionDTO.getUser().getFromDate());
	userEntity.setGender(userProfessionDTO.getUser().getGender());
	userEntity.setIsEnabled(userProfessionDTO.getUser().getIsEnabled());
	userEntity.setLastName(userProfessionDTO.getUser().getLastName());
	userEntity.setMobile(userProfessionDTO.getUser().getMobile());
	userEntity.setModifiedBy(userProfessionDTO.getUser().getModifiedBy());
	userEntity.setModifiedOn(userProfessionDTO.getUser().getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userProfessionDTO.getUser().getPassword());
	userEntity.setTypeOfEmployment(userProfessionDTO.getUser().getTypeOfEmployment());
	userEntity.setUserName(userProfessionDTO.getUser().getUserName());
	userEntity.setId(userProfessionDTO.getUser().getId());

	userEntity.setPermAddr(userProfessionDTO.getUser().getPermAddr());
	userEntity.setState(userProfessionDTO.getUser().getState());
	userEntity.setCity(userProfessionDTO.getUser().getCity());
	userEntity.setCountry(userProfessionDTO.getUser().getCountry());
	userEntity.setZip(userProfessionDTO.getUser().getZip());
	userEntity.setFatherName(userProfessionDTO.getUser().getFatherName());
	userEntity.setSpouseName(userProfessionDTO.getUser().getSpouseName());
	userEntity.setPassport(userProfessionDTO.getUser().getPassport());
	userEntity.setLocation(null == userProfessionDTO.getUser().getLocation() ? null
		: prepareLocationEntity(userProfessionDTO.getUser().getLocation()));
	userEntity.setUnitId(userProfessionDTO.getUser().getUnitId());

	return userEntity;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserAcademics/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserAcademicsDTO> getUserAcademics(@PathVariable("userid") String userId) {
	System.out.println("in get user Academics");
	UserAcademics userAcademicsEntity = userAcademicsDao
		.getUserAcademics(userDao.findUserById(Long.parseLong(userId)));
	UserAcademicsDTO userAcademicsDTO = new UserAcademicsDTO();
	if (null != userAcademicsEntity) {

	    userAcademicsDTO.setId(userAcademicsEntity.getId());
	    userAcademicsDTO.setUser(prepareUserDTO(userAcademicsEntity.getUser()));
	    userAcademicsDTO.setCollege(userAcademicsEntity.getCollege());
	    userAcademicsDTO.setHighestDegree(userAcademicsEntity.getHighestDegree());
	    userAcademicsDTO.setMarks(userAcademicsEntity.getMarks());
	    userAcademicsDTO.setPassOutYear(userAcademicsEntity.getPassOutYear());
	    userAcademicsDTO.setUniversity(userAcademicsEntity.getUniversity());
	    userAcademicsDTO.setCertificateName(userAcademicsEntity.getCertificateName());
	    userAcademicsDTO.setCertificationYear(userAcademicsEntity.getCertificationYear());
	    userAcademicsDTO.setCertifiedBy(userAcademicsEntity.getCertifiedBy());

	}
	return new ResponseEntity<UserAcademicsDTO>(userAcademicsDTO, HttpStatus.OK);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addUserAcademics", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserAcademicsDTO> persistUserAcademics(@RequestBody UserAcademicsDTO userAcademics) {

	UserAcademics userAcademicsValue = new UserAcademics();
	System.out.println("user Academics DTO to be sent to Dao is " + userAcademics.toString());
	userAcademicsValue.setUser(prepareUserAcademicsEntity(userAcademics));
	userAcademicsValue.setCollege(userAcademics.getCollege());
	userAcademicsValue.setHighestDegree(userAcademics.getHighestDegree());
	userAcademicsValue.setMarks(userAcademics.getMarks());
	userAcademicsValue.setPassOutYear(userAcademics.getPassOutYear());
	userAcademicsValue.setUniversity(userAcademics.getUniversity());
	userAcademicsValue.setCertificateName(userAcademics.getCertificateName());
	userAcademicsValue.setCertificationYear(userAcademics.getCertificationYear());
	userAcademicsValue.setCertifiedBy(userAcademics.getCertifiedBy());

	userAcademicsDao.addUserAcademics(userAcademicsValue);
	return new ResponseEntity<UserAcademicsDTO>(userAcademics, HttpStatus.CREATED);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "updateUserAcademics", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserAcademicsDTO> updateUserAcademics(@RequestBody UserAcademicsDTO userAcademics) {

	UserAcademics userAcademicsValue = new UserAcademics();
	System.out.println("user Academics DTO to be sent to Dao is " + userAcademics.toString());
	userAcademicsValue.setUser(prepareUserAcademicsEntity(userAcademics));
	userAcademicsValue.setCollege(userAcademics.getCollege());
	userAcademicsValue.setHighestDegree(userAcademics.getHighestDegree());
	userAcademicsValue.setMarks(userAcademics.getMarks());
	userAcademicsValue.setPassOutYear(userAcademics.getPassOutYear());
	userAcademicsValue.setUniversity(userAcademics.getUniversity());
	userAcademicsValue.setId(userAcademics.getId());
	userAcademicsValue.setCertificateName(userAcademics.getCertificateName());
	userAcademicsValue.setCertificationYear(userAcademics.getCertificationYear());
	userAcademicsValue.setCertifiedBy(userAcademics.getCertifiedBy());

	userAcademicsDao.updateUserAcademics(userAcademicsValue);
	return new ResponseEntity<UserAcademicsDTO>(userAcademics, HttpStatus.CREATED);
    }

    private User prepareUserAcademicsEntity(UserAcademicsDTO userAcademicsDTO) {
	User userEntity = new User();
	userEntity.setAlternateEmail(userAcademicsDTO.getUser().getAlternateEmail());
	userEntity.setDateOfBirth(userAcademicsDTO.getUser().getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userAcademicsDTO.getUser().getDepartment().getDeptName());
	dept.setId(userAcademicsDTO.getUser().getDepartment().getId());
	Organization org = new Organization();
	org.setId(userAcademicsDTO.getUser().getDepartment().getOrganization().getId());
	org.setOrgLocation(userAcademicsDTO.getUser().getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userAcademicsDTO.getUser().getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userAcademicsDTO.getUser().getDesignation().getDesig());
	desg.setId(userAcademicsDTO.getUser().getDesignation().getId());
	userEntity.setDesignation(desg);
	userEntity.setEmail(userAcademicsDTO.getUser().getEmail());
	userEntity.setEndDate(userAcademicsDTO.getUser().getEndDate());
	userEntity.setFirstName(userAcademicsDTO.getUser().getFirstName());
	userEntity.setFromDate(userAcademicsDTO.getUser().getFromDate());
	userEntity.setGender(userAcademicsDTO.getUser().getGender());
	userEntity.setIsEnabled(userAcademicsDTO.getUser().getIsEnabled());
	userEntity.setLastName(userAcademicsDTO.getUser().getLastName());
	userEntity.setMobile(userAcademicsDTO.getUser().getMobile());
	userEntity.setModifiedBy(userAcademicsDTO.getUser().getModifiedBy());
	userEntity.setModifiedOn(userAcademicsDTO.getUser().getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userAcademicsDTO.getUser().getPassword());
	userEntity.setTypeOfEmployment(userAcademicsDTO.getUser().getTypeOfEmployment());
	userEntity.setUserName(userAcademicsDTO.getUser().getUserName());
	userEntity.setId(userAcademicsDTO.getUser().getId());

	userEntity.setPermAddr(userAcademicsDTO.getUser().getPermAddr());
	userEntity.setState(userAcademicsDTO.getUser().getState());
	userEntity.setCity(userAcademicsDTO.getUser().getCity());
	userEntity.setCountry(userAcademicsDTO.getUser().getCountry());
	userEntity.setZip(userAcademicsDTO.getUser().getZip());
	userEntity.setFatherName(userAcademicsDTO.getUser().getFatherName());
	userEntity.setSpouseName(userAcademicsDTO.getUser().getSpouseName());
	userEntity.setPassport(userAcademicsDTO.getUser().getPassport());
	userEntity.setLocation(null == userAcademicsDTO.getUser().getLocation() ? null
		: prepareLocationEntity(userAcademicsDTO.getUser().getLocation()));
	userEntity.setUnitId(userAcademicsDTO.getUser().getUnitId());
	return userEntity;
    }

}
