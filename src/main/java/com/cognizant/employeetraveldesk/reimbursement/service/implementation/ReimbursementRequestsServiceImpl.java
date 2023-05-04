package com.cognizant.employeetraveldesk.reimbursement.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employeetraveldesk.reimbursement.entity.ReimbursementRequests;
import com.cognizant.employeetraveldesk.reimbursement.exception.DuplicateResourceException;
import com.cognizant.employeetraveldesk.reimbursement.exception.ResourceNotFoundException;
import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementRequestsDTO;
import com.cognizant.employeetraveldesk.reimbursement.repository.ReimbursementRequestsRepository;
import com.cognizant.employeetraveldesk.reimbursement.service.ReimbursementRequestsService;

@Service
public class ReimbursementRequestsServiceImpl implements ReimbursementRequestsService {

	@Autowired
	ReimbursementRequestsRepository reimbursementRequestsRepository;

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

	@Override
	public boolean createRequest(ReimbursementRequestsDTO requestDTO)
			throws DuplicateResourceException, ResourceNotFoundException {
		// TODO Auto-generated method stub

		Optional<ReimbursementRequests> result;
		ReimbursementRequests reimbursementRequests = mapDTOToEntity(requestDTO);
		result = reimbursementRequestsRepository.findById(reimbursementRequests.getId());

		if (result.isPresent()) {
			throw new DuplicateResourceException(
					"Resource is already available for Id : " + reimbursementRequests.getId());
		}
		reimbursementRequestsRepository.save(reimbursementRequests);
		return true;

	}

	@Override
	public List<ReimbursementRequestsDTO> readAllRequestsForTravelRequestId(Integer travelRequestId)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub

		List<ReimbursementRequests> reimbursementRequestsEntity;
		List<ReimbursementRequestsDTO> reimbursementRequestsDTO = new ArrayList<ReimbursementRequestsDTO>();
		reimbursementRequestsEntity = reimbursementRequestsRepository.findByTravelRequestId(travelRequestId);

		if (reimbursementRequestsEntity.isEmpty()) {
			throw new ResourceNotFoundException("Travel Requests for the given id " + travelRequestId + " Not Found");
		}

		for (ReimbursementRequests requests : reimbursementRequestsEntity) {
			ReimbursementRequestsDTO requestDTO = mapEntityToDTO(requests);
			reimbursementRequestsDTO.add(requestDTO);
		}

		return reimbursementRequestsDTO;
	}

	@Override
	public ReimbursementRequestsDTO read(int id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		ReimbursementRequests resultEntity;
		ReimbursementRequestsDTO resultDTO;

		Optional<ReimbursementRequests> result;
		result = reimbursementRequestsRepository.findById(id);

		if (result.isPresent()) {
			resultEntity = result.get();
			resultDTO = mapEntityToDTO(resultEntity);
			return resultDTO;
		} else {
			throw new ResourceNotFoundException("Request for Id " + id + " was not found");
		}
	}

	@Override
	public boolean updateRequest(ReimbursementRequestsDTO requestDTO) throws ResourceNotFoundException {
		// TODO Auto-generated method stub

		ReimbursementRequests request = mapDTOToEntity(requestDTO);

		Optional<ReimbursementRequests> result = reimbursementRequestsRepository.findById(request.getId());
		if (result.isPresent()) {
			String updatedStatus = request.getStatus(), currentStatus = result.get().getStatus();
			if (currentStatus.equalsIgnoreCase(updatedStatus) && updatedStatus.equalsIgnoreCase("rejected")
					&& request.getRemarks().isEmpty()) {
				return false;
			}
			reimbursementRequestsRepository.save(request);
			return true;
		} else {
			throw new ResourceNotFoundException("Request for Id " + requestDTO.getId() + " was not found");
		}
	}

}
