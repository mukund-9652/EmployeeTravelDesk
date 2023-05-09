package com.cognizant.employeetraveldesk.reimbursement.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.cognizant.employeetraveldesk.reimbursement.exception.DuplicateResourceException;
import com.cognizant.employeetraveldesk.reimbursement.exception.InvalidResourceException;
import com.cognizant.employeetraveldesk.reimbursement.exception.ResourceNotFoundException;
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
	public void creatingRequestsSuccessTest() throws DuplicateResourceException, ResourceNotFoundException, InvalidResourceException {
		ReimbursementRequestsDTO reimbursementRequestDTO = new ReimbursementRequestsDTO(100, 1234567, 9876543,
				currentDate, new ReimbursementTypes(1, "Food"), "1928374", LocalDate.of(2023, 1, 21), 1500,
				"www.google.com", null, null, "New", "");

		ReimbursementRequests reimbursementRequest = entityDTOMapper.mapDTOToEntity(reimbursementRequestDTO);
		when(reimbursementRequestsRepository.save(any(ReimbursementRequests.class))).thenReturn(reimbursementRequest);

		boolean createRequest = reimbursementRequestsServiceImpl.createRequest(reimbursementRequestDTO);

		verify(reimbursementRequestsRepository).save(any(ReimbursementRequests.class));

		assertTrue(createRequest);
	}

	@Test
	public void readAllRequestsForTravelRequestIdTest() {
		List<ReimbursementRequestsDTO> requestListTestDTO = new ArrayList<>(Arrays.asList(
				(new ReimbursementRequestsDTO(100, 1234567, 9876543, currentDate, new ReimbursementTypes(1, "Food"),
						"1928374", LocalDate.of(2023, 1, 21), 1500, "www.google.com", null, null, "New", "")),
				new ReimbursementRequestsDTO(101, 1234567, 9876543, currentDate, new ReimbursementTypes(2, "Water"),
						"1928398", LocalDate.of(2023, 1, 22), 1650, "www.yahoo.com", null, null, "New", ""),
				new ReimbursementRequestsDTO(102, 1234567, 9876543, currentDate, new ReimbursementTypes(3, "Laundry"),
						"1925678", LocalDate.of(2023, 1, 22), 1050, "www.outlook.com", null, null, "New", "")));

		when(reimbursementRequestsRepository.findByTravelRequestId(1234567))
				.thenReturn(entityDTOMapper.mapDTOToEntity(requestListTestDTO));

		List<ReimbursementRequestsDTO> actualListDTO = reimbursementRequestsServiceImpl
				.readAllRequestsForTravelRequestId(1234567);

		assertEquals(requestListTestDTO.get(0).toString(), actualListDTO.get(0).toString());

		assertEquals(requestListTestDTO.get(1).toString(), actualListDTO.get(1).toString());

		assertEquals(requestListTestDTO.get(2).toString(), actualListDTO.get(2).toString());
	}

	@Test
	public void readRequestTestSuccess() {
		ReimbursementRequestsDTO reimbursementRequestDTO = new ReimbursementRequestsDTO(100, 1234567, 9876543,
				currentDate, new ReimbursementTypes(1, "Food"), "1928374", LocalDate.of(2023, 1, 21), 1500,
				"www.google.com", null, null, "New", "");

		Optional<ReimbursementRequestsDTO> reimbursementRequest = Optional.of(reimbursementRequestDTO);

		when(reimbursementRequestsRepository.findById(100))
				.thenReturn(Optional.of(entityDTOMapper.mapDTOToEntity(reimbursementRequest.get())));

		ReimbursementRequestsDTO findRequest = reimbursementRequestsServiceImpl.readRequest(100);

		verify(reimbursementRequestsRepository).findById(100);

		assertEquals(reimbursementRequest.get().toString(), findRequest.toString());
	}

	@Test
	public void updateRequestTestSuccess() throws DuplicateResourceException, ResourceNotFoundException, InvalidResourceException {
		ReimbursementRequestsDTO reimbursementRequestDTO = new ReimbursementRequestsDTO(100, 1234567, 9876543,
				currentDate, new ReimbursementTypes(1, "Food"), "1928374", LocalDate.of(2023, 1, 21), 1500,
				"www.google.com", null, null, "New", "");

		ReimbursementRequests reimbursementRequest = entityDTOMapper.mapDTOToEntity(reimbursementRequestDTO);
		when(reimbursementRequestsRepository.save(any(ReimbursementRequests.class))).thenReturn(reimbursementRequest);

		boolean createRequest = reimbursementRequestsServiceImpl.createRequest(reimbursementRequestDTO);

		verify(reimbursementRequestsRepository).save(any(ReimbursementRequests.class));
		
		reimbursementRequest.setStatus("Approved");
		
		when(reimbursementRequestsRepository.findById(100)).thenReturn(Optional.of(reimbursementRequest));

		when(reimbursementRequestsRepository.save(any(ReimbursementRequests.class))).thenReturn(reimbursementRequest);

		boolean updatedRequest = reimbursementRequestsServiceImpl.updateRequest(reimbursementRequestDTO);
		
		assertTrue(updatedRequest);
	}
}
