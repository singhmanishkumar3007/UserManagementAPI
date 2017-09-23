package com.easybusiness.usermanagement.services.designation;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easybusiness.usermanagement.dto.DesignationDTO;

public interface DesignationService {

    public DesignationDTO getDesignationByName(String desigName);

    public List<DesignationDTO> getDesignationAsPerCriteria(String whereClause);

    public ResponseEntity<DesignationDTO> addDesignation(DesignationDTO designation);

    public List<DesignationDTO> getAllDesignations() throws Exception;

    public DesignationDTO getDesignationById(Long designationId);

    public ResponseEntity<DesignationDTO> deleteDesignation(Long designationId);


}
