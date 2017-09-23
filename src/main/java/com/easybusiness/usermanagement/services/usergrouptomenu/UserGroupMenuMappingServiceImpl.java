package com.easybusiness.usermanagement.services.usergrouptomenu;

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

import com.easybusiness.modelmanagement.entity.Menu;
import com.easybusiness.modelmanagement.entity.UserGroup;
import com.easybusiness.modelmanagement.entity.UserGroupMenu;
import com.easybusiness.modelmanagement.menu.MenuDao;
import com.easybusiness.modelmanagement.usergroup.UserGroupDao;
import com.easybusiness.modelmanagement.usergroupmenu.UserGroupMenuDao;
import com.easybusiness.usermanagement.dto.MenuDTO;
import com.easybusiness.usermanagement.dto.UserGroupDTO;
import com.easybusiness.usermanagement.dto.UserGroupMenuDTO;

@RestController
@RequestMapping("/easybusiness/usergroupmenu/")
public class UserGroupMenuMappingServiceImpl implements UserGroupMenuMappingService {

    @Autowired
    UserGroupMenuDao userGroupMenuDao;

    @Autowired
    UserGroupDao userGroupDao;

    @Autowired
    MenuDao menuDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByMappingId/{mappingid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserGroupMenuDTO> getUserGroupMenuBymappingId(@PathVariable("mappingid") Long mappingId) {

	UserGroupMenu userGroupMenuEntity = userGroupMenuDao.findUserGroupMenuById(mappingId);
	UserGroupMenuDTO userGroupMenuDto = prepareUserGroupMenuDTO(userGroupMenuEntity);
	return new ResponseEntity<UserGroupMenuDTO>(userGroupMenuDto, HttpStatus.FOUND);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByGroupId/{groupid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMenuDTO> getUserGroupMenuByGroupId(@PathVariable("groupid") Long groupId) {

	UserGroup userGroupEntity = userGroupDao.findUserGroupById(groupId);
	List<UserGroupMenu> userGroupMenuEntityList = userGroupMenuDao.findUserGroupMenuByUserGroup(userGroupEntity);
	List<UserGroupMenuDTO> userGroupMenuList = new ArrayList<UserGroupMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    UserGroupMenuDTO userGroupMenuDTO = new UserGroupMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO);

	});
	return userGroupMenuList;
    }
    
    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByGroupName/{groupname}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMenuDTO> getUserGroupMenuByGroupName(@PathVariable("groupname") String groupName) {

	UserGroup userGroupEntity = userGroupDao.findByUserGroupName(groupName);
	List<UserGroupMenu> userGroupMenuEntityList = userGroupMenuDao.findUserGroupMenuByUserGroup(userGroupEntity);
	List<UserGroupMenuDTO> userGroupMenuList = new ArrayList<UserGroupMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    UserGroupMenuDTO userGroupMenuDTO = new UserGroupMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO);

	});
	return userGroupMenuList;
    }


    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByMenuId/{menuid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMenuDTO> getUserGroupMenuByMenuId(@PathVariable("menuid") Long menuId) {

	Menu menuEntity = menuDao.findMenuById(menuId);
	List<UserGroupMenu> userGroupMenuEntityList = userGroupMenuDao.findUserGroupMenuByMenu(menuEntity);
	List<UserGroupMenuDTO> userGroupMenuList = new ArrayList<UserGroupMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    UserGroupMenuDTO userGroupMenuDTO = new UserGroupMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO);

	});
	return userGroupMenuList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "mapUserGroupMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserGroupMenuDTO> persistUserGroupMenu(@RequestBody UserGroupMenuDTO userGroupMenuDTO) {

	UserGroupMenu userGroupMenu = new UserGroupMenu();
	userGroupMenu.setFromDate(userGroupMenuDTO.getFromDate());
	userGroupMenu.setIsEnable(userGroupMenuDTO.getIsEnable());
	Menu menu = menuDao.findMenuById(userGroupMenuDTO.getMenuItem().getId());
	userGroupMenu.setMenuItem(menu);
	userGroupMenu.setModifiedBy(userGroupMenuDTO.getModifiedBy());
	userGroupMenu.setModifiedOn(userGroupMenuDTO.getModifiedOn());
	userGroupMenu.setToDate(userGroupMenuDTO.getToDate());
	UserGroup userGroup = userGroupDao.findUserGroupById(userGroupMenuDTO.getUserGroup().getId());
	userGroupMenu.setUserGroup(userGroup);

	userGroupMenuDao.addUserGroupMenu(userGroupMenu);
	return new ResponseEntity<UserGroupMenuDTO>(userGroupMenuDTO, HttpStatus.CREATED);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteUserGroupMenuItem/{mappingid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void destroyUserGroupMenu(@PathVariable("mappingid") Long mappingId) {

	userGroupMenuDao.deleteUserGroupMenu(mappingId);
    }

    @Override
    public List<UserGroupMenuDTO> getFieldEq(Class<UserGroupMenuDTO> type, String propertyName, Object value,
	    int offset, int size) {
	return null;
    }

    private UserGroupMenuDTO prepareUserGroupMenuDTO(UserGroupMenu userGroupMenuEntity) {
	UserGroupMenuDTO userGroupMenuDto = new UserGroupMenuDTO();
	userGroupMenuDto.setFromDate(userGroupMenuEntity.getFromDate());
	userGroupMenuDto.setId(userGroupMenuEntity.getId());
	userGroupMenuDto.setIsEnable(userGroupMenuEntity.getIsEnable());
	MenuDTO menuDto = prepareMenuDTO(userGroupMenuEntity);
	userGroupMenuDto.setMenuItem(menuDto);
	userGroupMenuDto.setModifiedBy(userGroupMenuEntity.getModifiedBy());
	userGroupMenuDto.setModifiedOn(userGroupMenuEntity.getModifiedOn());
	userGroupMenuDto.setToDate(userGroupMenuEntity.getToDate());
	userGroupMenuDto.setUserGroup(prepareUserGroupDTO(userGroupMenuEntity.getUserGroup()));
	return userGroupMenuDto;
    }

    private MenuDTO prepareMenuDTO(UserGroupMenu userGroupMenuEntity) {
	MenuDTO menuDto = new MenuDTO();
	menuDto.setId(userGroupMenuEntity.getMenuItem().getId());
	menuDto.setMenuName(userGroupMenuEntity.getMenuItem().getMenuName());
	menuDto.setModifiedBy(userGroupMenuEntity.getMenuItem().getModifiedBy());
	menuDto.setModifiedTime(userGroupMenuEntity.getMenuItem().getModifiedTime());
	menuDto.setMenuIconName(userGroupMenuEntity.getMenuItem().getMenuIconName());
	return menuDto;
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

}
