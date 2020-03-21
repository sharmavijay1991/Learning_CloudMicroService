/*
package com.glarimy.cmad;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeRespositoryImpl.class)
public class EmployeeRepositoryImplTest {
	
	@Autowired
	private EmployeeRepositoryImpl repo;

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setId(123);
		employee.setName("ABC");
		employee.setPhoneNumber(123455L);
		
		Employee result = repo.create(employee);
		
		assertTrue(result.getId() == employee.getId());

}
*/
