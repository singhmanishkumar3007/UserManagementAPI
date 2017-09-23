package com.easybusiness.usermanagement.services.user;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easybusiness.usermanagement.dto.UserAcademicsDTO;
import com.easybusiness.usermanagement.dto.UserDTO;
import com.easybusiness.usermanagement.dto.UserImageDTO;
import com.easybusiness.usermanagement.dto.UserProfessionDTO;

public interface UserService {

    public ResponseEntity<UserDTO> getUser(String username);

    public ResponseEntity<UserDTO> persistUser(UserDTO user);

    public List<UserDTO> populateUserList();

    public UserDTO populateOneUserDetails(Long userId);

    public void destroyUser(Long userId);

    public List<UserDTO> getFieldEq(final Class<UserDTO> type, final String propertyName, final Object value, int offset,
	    int size);

    public void persistUser(UserDTO loggedUser, boolean changePassword);

    public void activateUser(UserDTO user);

    public void deActivateUser(UserDTO user);

    public UserDTO getActiveUser(String email);
    
    public ResponseEntity<UserImageDTO> persistUserImage(UserImageDTO userImage);

    ResponseEntity<UserImageDTO> getUserImage(String userId);

    ResponseEntity<UserDTO> updateUser(UserDTO userDTO);

    ResponseEntity<UserProfessionDTO> getUserProfession(String userId);

    ResponseEntity<UserProfessionDTO> persistUserProfession(UserProfessionDTO userProfession);

    ResponseEntity<UserProfessionDTO> updateUserProfession(UserProfessionDTO userProfession);

    ResponseEntity<UserAcademicsDTO> getUserAcademics(String userId);

    ResponseEntity<UserAcademicsDTO> persistUserAcademics(UserAcademicsDTO userAcademics);

    ResponseEntity<UserAcademicsDTO> updateUserAcademics(UserAcademicsDTO userAcademics);
    
    

}
