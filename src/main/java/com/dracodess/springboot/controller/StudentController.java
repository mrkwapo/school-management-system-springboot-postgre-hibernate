package com.dracodess.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dracodess.springboot.exception.ResourceNotFoundException;
import com.dracodess.springboot.model.Student;
import com.dracodess.springboot.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	//Get all students
	@GetMapping("students")
	public List<Student> getAllStudents(){
		return this.studentRepository.findAll();
	}
	
	//Get one student by id
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long studentId) 
			throws ResourceNotFoundException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found using this id: " + studentId));
		return ResponseEntity.ok().body(student);
	}
	
	//Create/save a new student
	@PostMapping("students")
	public Student createStudent(@RequestBody Student student) {
		return this.studentRepository.save(student);
	}
	
	//Update student
	@PutMapping("employees/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value ="id") Long studentId,
			@Valid @RequestBody Student studentDetails) throws ResourceNotFoundException {
		
		//Reusing this logic from getStudentById method to get the student from the Student database
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found using this id: " + studentId));
		
		//Update employee
		student.setEmail(studentDetails.getEmail());
		student.setFirstName(studentDetails.getFirstName());
		student.setLastName(studentDetails.getLastName());
		student.setCohort(studentDetails.getCohort());
		
		//Return the employee object
		return ResponseEntity.ok(this.studentRepository.save(student));
	}
	//Delete student
}
