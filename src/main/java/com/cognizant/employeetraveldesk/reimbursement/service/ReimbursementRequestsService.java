package com.cognizant.employeetraveldesk.reimbursement.service;

import java.util.List;

import com.cognizant.employeetraveldesk.reimbursement.exception.DuplicateResourceException;
import com.cognizant.employeetraveldesk.reimbursement.exception.InvalidResourceException;
import com.cognizant.employeetraveldesk.reimbursement.exception.ResourceNotFoundException;
import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementRequestsDTO;

public interface ReimbursementRequestsService {
	boolean createRequest(ReimbursementRequestsDTO request) throws DuplicateResourceException, ResourceNotFoundException, InvalidResourceException;
	List<ReimbursementRequestsDTO> readAllRequestsForTravelRequestId(Integer id);
	ReimbursementRequestsDTO readRequest(int id);
	boolean updateRequest(ReimbursementRequestsDTO request);
}
