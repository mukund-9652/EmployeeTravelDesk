package com.cognizant.employeetraveldesk.reimbursement.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.employeetraveldesk.reimbursement.entity.ReimbursementTypes;
import com.cognizant.employeetraveldesk.reimbursement.repository.ReimbursementTypesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReimbursementTypesServiceImplTest {
	
	@Autowired
	private ReimbursementTypesServiceImpl typesService;

	@MockBean
	private ReimbursementTypesRepository typesRepository;
	
	@Test
	public void retrieveListOfTypesTest() {
		List<ReimbursementTypes> types=new ArrayList<>(Arrays.asList(
				new ReimbursementTypes(1,"Food"),
				new ReimbursementTypes(2,"Water"),
				new ReimbursementTypes(3,"Laundry"),
				new ReimbursementTypes(4,"Local Travel")
				));
		when(typesRepository.findAll()).thenReturn(types);
		assertEquals(4,typesService.readAllTypes().size());
	}

}
