package com.cognizant.employeetraveldesk.reimbursement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementRequestsDTO;
import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementTypesDTO;
import com.cognizant.employeetraveldesk.reimbursement.service.implementation.ReimbursementRequestsServiceImpl;
import com.cognizant.employeetraveldesk.reimbursement.service.implementation.ReimbursementTypesServiceImpl;

@Validated
@RestController
@RequestMapping("/api/reimbursements")
public class ReimbursementRequestsController {
	@Autowired
	ReimbursementRequestsServiceImpl reimbursementRequestsServiceImpl;
	@Autowired
	ReimbursementTypesServiceImpl reimbursementTypesServiceImpl;

	@GetMapping("/types")
	public List<ReimbursementTypesDTO> getReimbursementTypes() {
		// This Returns a list of ReimbursementTypes
		return reimbursementTypesServiceImpl.readAllTypes();
	}

	@GetMapping("/{travelrequestid}/requests")
	public List<ReimbursementRequestsDTO> getReimbursementRequests(@PathVariable Integer travelrequestid) {
		// This Returns the list of reimbursement requests DTO for the requested travel id
		return reimbursementRequestsServiceImpl.readAllRequestsForTravelRequestId(travelrequestid);
	}

	@GetMapping("/{reimbursementid}")
	public ReimbursementRequestsDTO getReimbursement(@PathVariable Integer reimbursementid) {
		// This Returns the reimbursement request DTO for the given reimbursement id
		return reimbursementRequestsServiceImpl.read(reimbursementid);
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createReimbursement(@Valid @RequestBody ReimbursementRequestsDTO request) {
		// This returns the status code if the reimbursement request is created or not
		boolean requestStatusCheck = reimbursementRequestsServiceImpl.createRequest(request);
		if (requestStatusCheck) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{reimbursementid}/process")
	public ResponseEntity<Void> putReimbursement(@PathVariable Integer reimbursementid,
			@RequestBody ReimbursementRequestsDTO request) {
		// This
		// This returns the status code if the reimbursement request is updated or not

		boolean requestStatusCheck = reimbursementRequestsServiceImpl.updateRequest(request);
		if (requestStatusCheck) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}
