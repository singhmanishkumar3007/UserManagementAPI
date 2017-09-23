package com.easybusiness.usermanagement.services.menu;

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

import com.easybusiness.modelmanagement.entity.Menu;
import com.easybusiness.modelmanagement.entity.SubMenu;
import com.easybusiness.modelmanagement.entity.SubMenuUrl;
import com.easybusiness.modelmanagement.menu.MenuDao;
import com.easybusiness.modelmanagement.submenu.SubMenuDao;
import com.easybusiness.modelmanagement.submenuurl.SubMenuUrlDao;
import com.easybusiness.usermanagement.dto.MenuDTO;
import com.easybusiness.usermanagement.dto.SubMenuDTO;
import com.easybusiness.usermanagement.dto.SubMenuUrlDTO;

@RestController
@RequestMapping("easybusiness/menu/")
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Autowired
    SubMenuDao subMenuDao;

    @Autowired
    SubMenuUrlDao subMenuUrlDao;

    private static Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getMenuByName/{menuName}", method = RequestMethod.GET)
    @ResponseBody
    public MenuDTO getMenuByName(@PathVariable("menuName") String menuName) {

	Menu menu = menuDao.findByMenuName(menuName);
	return prepareMenuResponse(menu);
    }

    private MenuDTO prepareMenuResponse(Menu menu) {
	MenuDTO menuItem = new MenuDTO();
	menuItem.setId(menu.getId());
	menuItem.setMenuName(menu.getMenuName());
	menuItem.setModifiedBy(menu.getModifiedBy());
	menuItem.setModifiedTime(menu.getModifiedTime());
	menuItem.setMenuIconName(menu.getMenuIconName());
	return menuItem;
    }

    @Override
    public List<MenuDTO> getMenuAsPerCriteria(String whereClause) {
	return null;
    }

    @Override
    @RequestMapping(value = "addMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<MenuDTO> addMenu(@RequestBody MenuDTO menuDTO) {
	Menu menuItem = new Menu();
	menuItem.setMenuName(menuDTO.getMenuName());
	menuItem.setModifiedBy(menuDTO.getModifiedBy());
	menuItem.setModifiedTime(menuDTO.getModifiedTime());
	menuItem.setMenuIconName(menuDTO.getMenuIconName());

	menuDao.addMenu(menuItem);
	return new ResponseEntity<MenuDTO>(menuDTO, HttpStatus.CREATED);

    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllMenu", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuDTO> getAllMenuItems() throws Exception {
	List<Menu> menuList = menuDao.findAll();
	List<MenuDTO> menuModelList = new ArrayList<MenuDTO>();
	menuList.forEach(menu -> {

	    MenuDTO menuItem = prepareMenuResponse(menu);
	    menuModelList.add(menuItem);

	});
	return menuModelList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getMenuById/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public MenuDTO getMenuById(@PathVariable("menuId") Long menuId) {
	LOGGER.info("inside get Menu By Id");
	Menu menu = menuDao.findMenuById(menuId);
	MenuDTO menuItem = prepareMenuResponse(menu);

	return menuItem;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteMenu/{menuId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<MenuDTO> deleteMenu(@PathVariable("menuId") Long menuId) {
	Menu menu = menuDao.findMenuById(menuId);
	menuDao.deleteMenu(menuId);
	return new ResponseEntity<MenuDTO>(prepareMenuResponse(menu), HttpStatus.OK);
    }

    @Override
    public List<MenuDTO> getFieldEq(Class<MenuDTO> type, String propertyName, Object value, int offset, int size) {
	return null;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getSubMenuByParentMenuId/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubMenuDTO> getSubMenuById(@PathVariable("menuId") Long parentMenuId) {
	List<SubMenu> subMenuList = subMenuDao.findByMenu(menuDao.findMenuById(parentMenuId));
	List<SubMenuDTO> subMenuDTOList = new ArrayList<SubMenuDTO>();
	subMenuList.forEach(subMenu -> {
	    SubMenuDTO subMenuDTO = new SubMenuDTO();
	    subMenuDTO.setId(subMenu.getId());
	    subMenuDTO.setSubMenu(subMenu.getSubMenu());
	    MenuDTO menuDTOvalue = new MenuDTO();
	    menuDTOvalue.setId(subMenu.getMenu().getId());
	    menuDTOvalue.setMenuName(subMenu.getMenu().getMenuName());
	    menuDTOvalue.setModifiedBy(subMenu.getMenu().getModifiedBy());
	    menuDTOvalue.setModifiedTime(subMenu.getMenu().getModifiedTime());
	    menuDTOvalue.setMenuIconName(subMenu.getMenu().getMenuIconName());
	    subMenuDTO.setMenu(menuDTOvalue);
	    subMenuDTOList.add(subMenuDTO);
	});
	return subMenuDTOList;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getSubMenuBySubMenuId/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public SubMenuDTO getSubMenuBySubMenuId(@PathVariable("menuId") Long subMenuId) {
	SubMenu subMenu = subMenuDao.findSubMenuById(subMenuId);

	SubMenuDTO subMenuDTO = new SubMenuDTO();
	subMenuDTO.setId(subMenu.getId());
	subMenuDTO.setSubMenu(subMenu.getSubMenu());
	MenuDTO menuDTOvalue = new MenuDTO();
	menuDTOvalue.setId(subMenu.getMenu().getId());
	menuDTOvalue.setMenuName(subMenu.getMenu().getMenuName());
	menuDTOvalue.setModifiedBy(subMenu.getMenu().getModifiedBy());
	menuDTOvalue.setModifiedTime(subMenu.getMenu().getModifiedTime());
	menuDTOvalue.setMenuIconName(subMenu.getMenu().getMenuIconName());
	subMenuDTO.setMenu(menuDTOvalue);

	return subMenuDTO;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getAllSubMenu", method = RequestMethod.GET)
    @ResponseBody
    public List<SubMenuDTO> getAllSubMenuItems() throws Exception {
	List<SubMenu> subMenuList = subMenuDao.findAll();
	List<SubMenuDTO> subMenuModelList = new ArrayList<SubMenuDTO>();
	subMenuList.forEach(subMenu -> {

	    SubMenuDTO subMenuItem = prepareSubMenuResponse(subMenu);
	    subMenuModelList.add(subMenuItem);

	});
	return subMenuModelList;
    }

    private SubMenuDTO prepareSubMenuResponse(SubMenu subMenu) {

	SubMenuDTO subMenuDTO = new SubMenuDTO();
	subMenuDTO.setId(subMenu.getId());
	subMenuDTO.setMenu(prepareMenuResponse(subMenu.getMenu()));
	subMenuDTO.setModifiedBy(subMenu.getModifiedBy());
	subMenuDTO.setModifiedTime(subMenu.getModifiedTime());
	subMenuDTO.setSubMenu(subMenu.getSubMenu());
	return subMenuDTO;
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "addSubMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SubMenuDTO> addSubMenu(@RequestBody SubMenuDTO subMenuDTO) {
	SubMenu subMenu = new SubMenu();
	Menu menuItem = new Menu();
	menuItem.setMenuName(subMenuDTO.getMenu().getMenuName());
	menuItem.setModifiedBy(subMenuDTO.getMenu().getModifiedBy());
	menuItem.setModifiedTime(subMenuDTO.getMenu().getModifiedTime());
	menuItem.setId(subMenuDTO.getMenu().getId());
	menuItem.setMenuIconName(subMenuDTO.getMenu().getMenuIconName());
	subMenu.setMenu(menuItem);
	subMenu.setModifiedBy(subMenuDTO.getModifiedBy());
	subMenu.setModifiedTime(subMenuDTO.getModifiedTime());
	subMenu.setSubMenu(subMenuDTO.getSubMenu());
	subMenuDao.addSubMenu(subMenu);
	return new ResponseEntity<SubMenuDTO>(subMenuDTO, HttpStatus.CREATED);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "deleteSubMenu/{submenuId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<SubMenuDTO> deleteSubMenu(@PathVariable("submenuId") Long subMenuId) {
	SubMenu subMenu = subMenuDao.findSubMenuById(subMenuId);
	SubMenuDTO subMenuDTO = new SubMenuDTO();
	subMenuDTO.setId(subMenu.getId());
	subMenuDTO.setSubMenu(subMenu.getSubMenu());

	subMenuDao.deleteSubMenu(subMenuId);
	return new ResponseEntity<SubMenuDTO>(subMenuDTO, HttpStatus.OK);
    }

    @Override
    @CrossOrigin(origins = USER_HOST_SERVER)
    @RequestMapping(value = "getUrlBySubMenuId/{subMenuId}", method = RequestMethod.GET)
    @ResponseBody
    public SubMenuUrlDTO getUrlBySubMenuId(@PathVariable("subMenuId") Long subMenuId) {
	SubMenuUrl subMenuUrlEntity = subMenuUrlDao.findBySubMenu(subMenuDao.findSubMenuById(subMenuId));
	SubMenuUrlDTO subMenuUrlDTO = new SubMenuUrlDTO();
	subMenuUrlDTO.setCreatedBy(subMenuUrlEntity.getCreatedBy());
	subMenuUrlDTO.setCreatedOn(subMenuUrlEntity.getCreatedOn());
	subMenuUrlDTO.setId(subMenuUrlEntity.getId());
	SubMenuDTO subMenuDTO = new SubMenuDTO();
	subMenuDTO.setId(subMenuUrlEntity.getSubMenu().getId());
	subMenuDTO.setSubMenu(subMenuUrlEntity.getSubMenu().getSubMenu());
	subMenuUrlDTO.setSubMenu(subMenuDTO);
	subMenuUrlDTO.setUrl(subMenuUrlEntity.getUrl());
	return subMenuUrlDTO;
    }

}
