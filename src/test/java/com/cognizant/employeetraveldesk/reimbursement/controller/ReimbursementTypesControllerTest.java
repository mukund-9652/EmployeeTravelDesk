package com.cognizant.employeetraveldesk.reimbursement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ReimbursementTypesController.class)
class ReimbursementTypesControllerTest {

	@MockBean
	private ReimbursementTypesServiceImpl typesServiceImpl;

	@Autowired
	private MockMvc mockMvc;

	private static String SPECIFIC_TYPES_URL = "http://localhost:8084/api/reimbursements/types";

	private List<ReimbursementTypesDTO> expectedTypesDTO = new ArrayList<ReimbursementTypesDTO>();
	private HashMap<Integer, String> expectedMapDTO = new HashMap<Integer, String>();

	@BeforeClass
	void setUp() {
		expectedTypesDTO.add(new ReimbursementTypesDTO(1, "Food"));
		expectedTypesDTO.add(new ReimbursementTypesDTO(2, "Water"));
		expectedTypesDTO.add(new ReimbursementTypesDTO(3, "Laundry"));
		expectedTypesDTO.add(new ReimbursementTypesDTO(4, "Local Travel"));

		for (ReimbursementTypesDTO item : expectedTypesDTO) {
			expectedMapDTO.put(item.getId(), item.getType());
		}
	}

	@Test
	void getReimbursementRequestsTest() {
		try {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_TYPES_URL)
					.accept(MediaType.APPLICATION_JSON);

			when(typesServiceImpl.readAllTypes()).thenReturn(expectedTypesDTO);

			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

			String json = mvcResult.getResponse().getContentAsString();
			ObjectMapper objectMapper = new ObjectMapper();

			List<Map<String, Object>> list = objectMapper.readValue(json,
					new TypeReference<List<Map<String, Object>>>() {
					});
			HashMap<Integer, String> actualMapDTO = new HashMap<Integer, String>();

			for (Map<String, Object> item : list) {
				actualMapDTO.put((Integer) item.get("id"), item.get("type").toString());
			}

			assertEquals(expectedMapDTO, actualMapDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
