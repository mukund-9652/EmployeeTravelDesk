package com.cognizant.employeetraveldesk.reimbursement.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.employeetraveldesk.reimbursement.entity.ReimbursementRequests;
import com.cognizant.employeetraveldesk.reimbursement.entity.ReimbursementTypes;
import com.cognizant.employeetraveldesk.reimbursement.model.ReimbursementRequestsDTO;
import com.cognizant.employeetraveldesk.reimbursement.repository.ReimbursementRequestsRepository;
import com.cognizant.employeetraveldesk.reimbursement.service.mapper.EntityDTOMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReimbursementRequestsServiceImplTest {

	@InjectMocks
	private ReimbursementRequestsServiceImpl reimbursementRequestsServiceImpl;

	@Mock
	private ReimbursementRequestsRepository reimbursementRequestsRepository;

	private EntityDTOMapper entityDTOMapper = new EntityDTOMapper();

	private LocalDate currentDate = LocalDate.now();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void creatingRequestsSuccessTest() {
		ReimbursementRequestsDTO requestDTOTest = new ReimbursementRequestsDTO(100, 1234567, 9876543, currentDate,
				new ReimbursementTypes(1, "Food"), "1928374", LocalDate.of(2023, 1, 21), 1500, "www.google.com", null,
				null, "New", "");

		ReimbursementRequests reimbursementRequest = entityDTOMapper.mapDTOToEntity(requestDTOTest);

		when(reimbursementRequestsRepository.save(reimbursementRequest)).thenReturn(reimbursementRequest);

		reimbursementRequestsServiceImpl.createRequest(requestDTOTest);

		ReimbursementRequests savedRequest = entityDTOMapper
				.mapDTOToEntity(reimbursementRequestsServiceImpl.read(reimbursementRequest.getId()));

		verify(reimbursementRequestsRepository, times(1)).save(eq(reimbursementRequest));

		assertEquals(reimbursementRequest, savedRequest);

	}

	@Test
	public void readAllRequestsForTravelRequestIdTest() {
		List<ReimbursementRequests> requestListTest = new ArrayList<>(Arrays.asList(
				(new ReimbursementRequests(100, 1234567, 9876543, currentDate, new ReimbursementTypes(1, "Food"),
						"1928374", LocalDate.of(2023, 1, 21), 1500, "www.google.com", null, null, "New", "")),
				new ReimbursementRequests(101, 1234567, 9876543, currentDate, new ReimbursementTypes(2, null),
						"1928398", LocalDate.of(2023, 1, 22), 1650, "www.yahoo.com", null, null, "New", ""),
				new ReimbursementRequests(102, 1234567, 9876543, currentDate, new ReimbursementTypes(3, null),
						"1925678", LocalDate.of(2023, 1, 22), 1050, "www.outlook.com", null, null, "New", "")));

		when(reimbursementRequestsRepository.saveAll(requestListTest)).thenReturn(requestListTest);

		List<ReimbursementRequests> actualList = reimbursementRequestsRepository.findAll();

		verify(reimbursementRequestsRepository).findAll();
		assertEquals(reimbursementRequestsServiceImpl.readAllRequestsForTravelRequestId(1234567), actualList);
	}

}
