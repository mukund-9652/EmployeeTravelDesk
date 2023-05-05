package com.cognizant.employeetraveldesk.reimbursement.service.mapper;

import com.cognizant.employeetraveldesk.reimbursement.entity.ReimbursementRequests;
import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementRequestsDTO;

public class EntityDTOMapper {
	public ReimbursementRequests mapDTOToEntity(ReimbursementRequestsDTO requestsDTO) {
		ReimbursementRequests reimbursementRequests = new ReimbursementRequests(requestsDTO.getId(),
				requestsDTO.getTravelRequestId(), requestsDTO.getRequestRaisedByEmployeeId(),
				requestsDTO.getRequestDate(), requestsDTO.getReimbursementTypes(), requestsDTO.getInvoiceNo(),
				requestsDTO.getInvoiceDate(), requestsDTO.getInvoiceAmount(), requestsDTO.getDocumentURL(),
				requestsDTO.getRequestProcessedOn(), requestsDTO.getRequestProcessedByEmployeeId(),
				requestsDTO.getStatus(), requestsDTO.getRemarks());
		return reimbursementRequests;
	}

	public ReimbursementRequestsDTO mapEntityToDTO(ReimbursementRequests requests) {
		ReimbursementRequestsDTO reimbursementRequestsDTO = new ReimbursementRequestsDTO(requests.getId(),
				requests.getTravelRequestId(), requests.getRequestRaisedByEmployeeId(), requests.getRequestDate(),
				requests.getReimbursementTypes(), requests.getInvoiceNo(), requests.getInvoiceDate(),
				requests.getInvoiceAmount(), requests.getDocumentURL(), requests.getRequestProcessedOn(),
				requests.getRequestProcessedByEmployeeId(), requests.getStatus(), requests.getRemarks());
		return reimbursementRequestsDTO;
	}
}
