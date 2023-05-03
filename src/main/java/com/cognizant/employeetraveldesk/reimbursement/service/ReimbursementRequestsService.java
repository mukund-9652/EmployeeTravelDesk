package com.cognizant.employeetraveldesk.reimbursement.service;

import java.util.List;

import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementRequestsDTO;

public interface ReimbursementRequestsService {
	boolean createRequest(ReimbursementRequestsDTO request);
	List<ReimbursementRequestsDTO> readAllRequestsForTravelRequestId(Integer id);
	ReimbursementRequestsDTO read(int id);
	boolean updateRequest(ReimbursementRequestsDTO request);
}
