package com.cognizant.employeetraveldesk.reimbursement.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementTypesDTO;
import com.cognizant.employeetraveldesk.reimbursement.service.implementation.ReimbursementTypesServiceImpl;

@WebMvcTest(controllers= ReimbursementTypesController.class)
class ReimbursementTypesControllerTest {

	@MockBean
	private ReimbursementTypesServiceImpl typesServiceImpl;

	@Autowired
	private MockMvc mockMvc;

	private static String SPECIFIC_TYPES_URL = "http://localhost:8084/api/reimbursements/types";

	@Test
	void getReimbursementRequests_basicScenario() {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_TYPES_URL)
				.accept(MediaType.APPLICATION_JSON);

		try {
			
			List<ReimbursementTypesDTO> typesDTO=new ArrayList<ReimbursementTypesDTO>();
			typesDTO.add(new ReimbursementTypesDTO(1,"Food"));
			typesDTO.add(new ReimbursementTypesDTO(2,"Water"));
			typesDTO.add(new ReimbursementTypesDTO(3,"Laundry"));
			typesDTO.add(new ReimbursementTypesDTO(4,"Local Travel"));
			
			when(typesServiceImpl.readAllTypes()).thenReturn(typesDTO);
			
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(mvcResult.getResponse().getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
