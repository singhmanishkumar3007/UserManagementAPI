package com.easybusiness.usermanagement.services.department;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easybusiness.usermanagement.dto.DepartmentDTO;

public interface DepartmentService {

    public DepartmentDTO getDepartmentByName(String deptName);

    public List<DepartmentDTO> getDepartmentAsPerCriteria(String whereClause);

    public ResponseEntity<DepartmentDTO> addDepartment(DepartmentDTO department);

    public List<DepartmentDTO> getAllDepartments() throws Exception;

    public DepartmentDTO getDepartmentById(Long departmentId);

    public ResponseEntity<DepartmentDTO> deleteDepartment(Long departmentId);

    List<DepartmentDTO> getAllDepartmentsByOrganization(Long orgId) throws Exception;


}
