package com.cognizant.employeetraveldesk.reimbursement.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.employeetraveldesk.reimbursement.entity.ReimbursementTypes;
import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementRequestsDTO;
import com.cognizant.employeetraveldesk.reimbursement.service.implementation.ReimbursementRequestsServiceImpl;
import com.cognizant.employeetraveldesk.reimbursement.service.mapper.EntityDTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@WebMvcTest(controllers= ReimbursementRequestsController.class)
class ReimbursementRequestsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = (ObjectWriter) objectMapper.writer();

	@MockBean
	private ReimbursementRequestsServiceImpl reimbursementRequestsServicesImpl;

	private EntityDTOMapper entityDTOMapper = new EntityDTOMapper();

	private LocalDate currentDate = LocalDate.now();
	
	ReimbursementRequestsDTO requestDTO_1 = new ReimbursementRequestsDTO(100, 1234567, 9876543, currentDate,
			new ReimbursementTypes(1, "Food"), "1928374", LocalDate.of(2023, 1, 21), 1500, "www.google.com", null, null,
			"New", "");
	ReimbursementRequestsDTO requestDTO_2 = new ReimbursementRequestsDTO(101, 1234567, 9876543, currentDate,
			new ReimbursementTypes(2, null), "1928398", LocalDate.of(2023, 1, 22), 1650, "www.yahoo.com", null, null,
			"New", "");
	ReimbursementRequestsDTO requestDTO_3 = new ReimbursementRequestsDTO(102, 1234567, 9876543, currentDate,
			new ReimbursementTypes(3, null), "1925678", LocalDate.of(2023, 1, 22), 1050, "www.outlook.com", null, null,
			"New", "");

	private List<ReimbursementRequestsDTO> requests = new ArrayList<ReimbursementRequestsDTO>(Arrays.asList(requestDTO_1,requestDTO_2,requestDTO_3));

	@Test
	public void getRequestsForTravelRequestIdSuccess() throws Exception {
			int travelRequestId = requestDTO_1.getTravelRequestId();
			
			when(reimbursementRequestsServicesImpl.readAllRequestsForTravelRequestId(travelRequestId)).thenReturn(requests);
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
					.get("http://localhost:8084/api/reimbursements/"+travelRequestId+"/requests")
					.accept(MediaType.APPLICATION_JSON)).andReturn();
			System.out.println(mvcResult.getResponse().getContentAsString()); 
	}
}
