package com.cognizant.employeetraveldesk.reimbursement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(controllers = ReimbursementRequestsController.class)
class ReimbursementRequestsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	ObjectWriter objectWriter = (ObjectWriter) objectMapper.writer();

	@MockBean
	private ReimbursementRequestsServiceImpl reimbursementRequestsServicesImpl;

	private EntityDTOMapper entityDTOMapper = new EntityDTOMapper();

	private LocalDate currentDate = LocalDate.now();

	protected String mapToJson(ReimbursementRequestsDTO obj) throws JsonProcessingException {
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.readValue(json, clazz);
	}

	ReimbursementRequestsDTO requestDTO_1 = new ReimbursementRequestsDTO(100, 1234567, 9876543, currentDate,
			new ReimbursementTypes(1, "Food"), "1928374", LocalDate.of(2023, 1, 21), 1500,
			"https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf", null, null, "New", "");
	ReimbursementRequestsDTO requestDTO_2 = new ReimbursementRequestsDTO(101, 1234567, 9876543, currentDate,
			new ReimbursementTypes(2, null), "1928398", LocalDate.of(2023, 1, 22), 1650,
			"https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf", null, null, "New", "");
	ReimbursementRequestsDTO requestDTO_3 = new ReimbursementRequestsDTO(102, 1234567, 9876543, currentDate,
			new ReimbursementTypes(3, null), "1925678", LocalDate.of(2023, 1, 22), 1050,
			"https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf", null, null, "New", "");

	private List<ReimbursementRequestsDTO> requestsDTO = new ArrayList<ReimbursementRequestsDTO>(
			Arrays.asList(requestDTO_1, requestDTO_2, requestDTO_3));

	/*
	 * Testing Methods for Success Outputs
	 */

	/*
	 * TEST - 1 : Testing the createRequest method for ReimbursementRequestService -
	 * SUCCESS
	 */
	@Test
	public void creatingRequestsSuccessTest() throws Exception {

		String inputJson = this.mapToJson(requestDTO_1);
		System.out.println(inputJson);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("http://localhost:8084/api/reimbursements/add")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}

	/*
	 * TEST - 2 : Testing the readAllRequestsForTravelRequestId method for
	 * ReimbursementRequestService - SUCCESS
	 */

	@Test
	public void readRequestByEmployeeIdTestSuccess() throws Exception {
		int travelRequestId = requestDTO_1.getTravelRequestId();

		when(reimbursementRequestsServicesImpl.readAllRequestsForTravelRequestId(travelRequestId))
				.thenReturn(requestsDTO);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("http://localhost:8084/api/reimbursements/" + travelRequestId + "/requests")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<ReimbursementRequestsDTO> expectedList = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<ReimbursementRequestsDTO>>() {
				});
		int count = 0;
		for (ReimbursementRequestsDTO expected : expectedList) {

			assertEquals(expected.getId(), requestsDTO.get(count++).getId());
		}
		// assertEquals(requestsDTO.toString(),mvcResult.getResponse().getContentAsString());
	}
	/*
	 * TEST - 3 : Testing the readRequestByEmployeeId method for
	 * ReimbursementRequestService - SUCCESS
	 */

	/*
	 * TEST - 4 : Testing the updateRequest method for ReimbursementRequestService -
	 * SUCCESS
	 */

}
