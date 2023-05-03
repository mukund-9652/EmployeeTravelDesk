package com.cognizant.employeetraveldesk.reimbursement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.employeetraveldesk.reimbursement.entity.ReimbursementRequests;

public interface ReimbursementRequestsRepository extends JpaRepository<ReimbursementRequests, Integer> {

	List<ReimbursementRequests> findByTravelRequestId(int id);

}
