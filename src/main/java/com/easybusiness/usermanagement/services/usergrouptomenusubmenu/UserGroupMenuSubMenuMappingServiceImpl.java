package com.easybusiness.usermanagement.services.usergrouptomenusubmenu;

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
import com.easybusiness.modelmanagement.entity.SubMenu;
import com.easybusiness.modelmanagement.entity.UserGroup;
import com.easybusiness.modelmanagement.entity.UserGroupMenuSubMenu;
import com.easybusiness.modelmanagement.menu.MenuDao;
import com.easybusiness.modelmanagement.submenu.SubMenuDao;
import com.easybusiness.modelmanagement.usergroup.UserGroupDao;
import com.easybusiness.modelmanagement.usergroupmenusubmenu.UserGroupMenuSubMenuDao;
import com.easybusiness.usermanagement.dto.MenuDTO;
import com.easybusiness.usermanagement.dto.SubMenuDTO;
import com.easybusiness.usermanagement.dto.UserGroupDTO;
import com.easybusiness.usermanagement.dto.UserGroupMenuSubMenuDTO;

@RestController
@RequestMapping("/easybusiness/usergroupmenusubmenu/")
public class UserGroupMenuSubMenuMappingServiceImpl implements UserGroupMenuSubMenuMappingService {

    @Autowired
    UserGroupMenuSubMenuDao userGroupMenuSubMenuDao;

    @Autowired
    UserGroupDao userGroupDao;

    @Autowired
    MenuDao menuDao;

    @Autowired
    SubMenuDao subMenuDao;

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByMappingId/{mappingid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserGroupMenuSubMenuDTO> getUserGroupMenuBymappingId(
	    @PathVariable("mappingid") Long mappingId) {

	UserGroupMenuSubMenu userGroupMenuEntity = userGroupMenuSubMenuDao.findUserGroupMenuById(mappingId);
	UserGroupMenuSubMenuDTO userGroupMenuDto = prepareUserGroupMenuDTO(userGroupMenuEntity);
	return new ResponseEntity<UserGroupMenuSubMenuDTO>(userGroupMenuDto, HttpStatus.FOUND);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByGroupId/{groupid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMenuSubMenuDTO> getUserGroupMenuByGroupId(@PathVariable("groupid") Long groupId) {

	UserGroup userGroupEntity = userGroupDao.findUserGroupById(groupId);
	List<UserGroupMenuSubMenu> userGroupMenuEntityList = userGroupMenuSubMenuDao
		.findUserGroupMenuByUserGroup(userGroupEntity);
	List<UserGroupMenuSubMenuDTO> userGroupMenuList = new ArrayList<UserGroupMenuSubMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    System.out.println("group menu sub menu entity is " + userGroupMenuEntity.toString());
	    UserGroupMenuSubMenuDTO userGroupMenuDTO = new UserGroupMenuSubMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO);

	});
	return userGroupMenuList;
    }
    
    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllSubMenusAndMenusByGroupId/{groupid}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubMenuDTO> getAllSubMenusAndMenusByGroupId(@PathVariable("groupid") Long groupId) {

	UserGroup userGroupEntity = userGroupDao.findUserGroupById(groupId);
	List<UserGroupMenuSubMenu> userGroupMenuEntityList = userGroupMenuSubMenuDao
		.findUserGroupMenuByUserGroup(userGroupEntity);
	List<SubMenuDTO> userGroupMenuList = new ArrayList<SubMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    System.out.println("group menu sub menu entity is " + userGroupMenuEntity.toString());
	    UserGroupMenuSubMenuDTO userGroupMenuDTO = new UserGroupMenuSubMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO.getSubMenuItem());

	});
	return userGroupMenuList;
    }
    
    
    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllSubMenusAndMenusByGroupName/{groupname}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubMenuDTO> getAllSubMenusAndMenusByGroupName(@PathVariable("groupname") String groupName) {

	UserGroup userGroupEntity = userGroupDao.findByUserGroupName(groupName);
	List<UserGroupMenuSubMenu> userGroupMenuEntityList = userGroupMenuSubMenuDao
		.findUserGroupMenuByUserGroup(userGroupEntity);
	List<SubMenuDTO> userGroupMenuList = new ArrayList<SubMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    System.out.println("group menu sub menu entity is " + userGroupMenuEntity.toString());
	    UserGroupMenuSubMenuDTO userGroupMenuDTO = new UserGroupMenuSubMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO.getSubMenuItem());

	});
	return userGroupMenuList;
    }

    
    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByGroupMenu/{groupname}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMenuSubMenuDTO> getUserGroupMenuByGroupName(@PathVariable("groupname") String groupName) {

	UserGroup userGroupEntity = userGroupDao.findByUserGroupName(groupName);
	List<UserGroupMenuSubMenu> userGroupMenuEntityList = userGroupMenuSubMenuDao
		.findUserGroupMenuByUserGroup(userGroupEntity);
	List<UserGroupMenuSubMenuDTO> userGroupMenuList = new ArrayList<UserGroupMenuSubMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    System.out.println("group menu sub menu entity is " + userGroupMenuEntity.toString());
	    UserGroupMenuSubMenuDTO userGroupMenuDTO = new UserGroupMenuSubMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO);

	});
	return userGroupMenuList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUserGroupMenuByMenuId/{menuid}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserGroupMenuSubMenuDTO> getUserGroupMenuByMenuId(@PathVariable("menuid") Long menuId) {

	Menu menuEntity = menuDao.findMenuById(menuId);
	List<UserGroupMenuSubMenu> userGroupMenuEntityList = userGroupMenuSubMenuDao
		.findUserGroupMenuByMenu(menuEntity);
	List<UserGroupMenuSubMenuDTO> userGroupMenuList = new ArrayList<UserGroupMenuSubMenuDTO>();
	userGroupMenuEntityList.forEach(userGroupMenuEntity -> {
	    UserGroupMenuSubMenuDTO userGroupMenuDTO = new UserGroupMenuSubMenuDTO();
	    userGroupMenuDTO = prepareUserGroupMenuDTO(userGroupMenuEntity);
	    userGroupMenuList.add(userGroupMenuDTO);

	});
	return userGroupMenuList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "mapUserGroupMenuSubMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserGroupMenuSubMenuDTO> persistUserGroupMenu(
	    @RequestBody UserGroupMenuSubMenuDTO userGroupMenuSubMenuDTO) {

	UserGroupMenuSubMenu userGroupMenuSubMenu = new UserGroupMenuSubMenu();
	userGroupMenuSubMenu.setFromDate(userGroupMenuSubMenuDTO.getFromDate());
	userGroupMenuSubMenu.setIsEnable(userGroupMenuSubMenuDTO.getIsEnable());
	Menu menu = menuDao.findMenuById(userGroupMenuSubMenuDTO.getMenuItem().getId());
	userGroupMenuSubMenu.setMenuItem(menu);
	userGroupMenuSubMenu.setModifiedBy(userGroupMenuSubMenuDTO.getModifiedBy());
	userGroupMenuSubMenu.setModifiedOn(userGroupMenuSubMenuDTO.getModifiedOn());
	userGroupMenuSubMenu.setToDate(userGroupMenuSubMenuDTO.getToDate());
	UserGroup userGroup = userGroupDao.findUserGroupById(userGroupMenuSubMenuDTO.getUserGroup().getId());
	userGroupMenuSubMenu.setUserGroup(userGroup);

	SubMenu subMenu = subMenuDao.findSubMenuById(userGroupMenuSubMenuDTO.getSubMenuItem().getId());
	userGroupMenuSubMenu.setSubMenuItem(subMenu);

	userGroupMenuSubMenuDao.addUserGroupMenu(userGroupMenuSubMenu);
	return new ResponseEntity<UserGroupMenuSubMenuDTO>(userGroupMenuSubMenuDTO, HttpStatus.CREATED);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteUserGroupMenu/{mappingid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void destroyUserGroupMenu(@PathVariable("mappingid") Long mappingId) {

	userGroupMenuSubMenuDao.deleteUserGroupMenu(mappingId);
    }

    @Override
    public List<UserGroupMenuSubMenuDTO> getFieldEq(Class<UserGroupMenuSubMenuDTO> type, String propertyName,
	    Object value, int offset, int size) {
	return null;
    }

    private UserGroupMenuSubMenuDTO prepareUserGroupMenuDTO(UserGroupMenuSubMenu userGroupMenuEntity) {

	UserGroupMenuSubMenuDTO userGroupMenuDto = new UserGroupMenuSubMenuDTO();
	userGroupMenuDto.setFromDate(userGroupMenuEntity.getFromDate());
	userGroupMenuDto.setId(userGroupMenuEntity.getId());
	userGroupMenuDto.setIsEnable(userGroupMenuEntity.getIsEnable());
	MenuDTO menuDto = prepareMenuDTO(userGroupMenuEntity);
	userGroupMenuDto.setMenuItem(menuDto);
	userGroupMenuDto.setModifiedBy(userGroupMenuEntity.getModifiedBy());
	userGroupMenuDto.setModifiedOn(userGroupMenuEntity.getModifiedOn());
	userGroupMenuDto.setToDate(userGroupMenuEntity.getToDate());
	userGroupMenuDto.setUserGroup(prepareUserGroupDTO(userGroupMenuEntity.getUserGroup()));

	SubMenuDTO subMenuItem = new SubMenuDTO();
	subMenuItem.setId(userGroupMenuEntity.getSubMenuItem().getId());
	// subMenuItem.setMenu(userGroupMenuEntity.getSubMenuItem().getMenu());
	subMenuItem.setModifiedBy(userGroupMenuEntity.getSubMenuItem().getModifiedBy());
	subMenuItem.setModifiedTime(userGroupMenuEntity.getSubMenuItem().getModifiedTime());
	subMenuItem.setSubMenu(userGroupMenuEntity.getSubMenuItem().getSubMenu());
	subMenuItem.setMenu(menuDto);
	userGroupMenuDto.setSubMenuItem(subMenuItem);
	return userGroupMenuDto;
    }

    private MenuDTO prepareMenuDTO(UserGroupMenuSubMenu userGroupMenuEntity) {
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
