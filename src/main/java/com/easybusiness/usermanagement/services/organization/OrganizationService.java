package com.easybusiness.usermanagement.services.organization;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easybusiness.usermanagement.dto.OrganizationDTO;

public interface OrganizationService {

    public OrganizationDTO getOrganizationByName(String orgName);

    public List<OrganizationDTO> getOrganizationAsPerCriteria(String whereClause);

    public ResponseEntity<OrganizationDTO> addOrganization(OrganizationDTO organization);

    public List<OrganizationDTO> getAllOrganizations() throws Exception;

    public OrganizationDTO getOrganizationById(Long organizationId);

    public ResponseEntity<OrganizationDTO> deleteOrganization(Long organizationId);


}
