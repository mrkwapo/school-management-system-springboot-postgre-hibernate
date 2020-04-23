# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)


CRUD RESTful APIs for Simple School Management System
S.r No.
API Name
HTTP Method
Path
Status Code
Description
(1)
GET Students
GET
/api/v1/students
200(OK)
All Students are fetched
(2)
POST Student
POST
/api/v1/students
201(Created)
A new Student resource is created
(3)
GET Student
GET
/api/v1/students/{id}
200(OK)
One Student resource is fetched
(4)
PUT Student
PUT
/api/v1/students/{id}
200(OK)
Student resource is updated
(5)
DELETE Student
DELETE
/api/v1/students/{id}
204 (No Content)
Student  resource is deleted

Tools and Technologies
Spring Boot
JDK
Spring Framework
Hibernate
JPA
Maven
Spring Tool Suite
PostgreSQL JDBC Driver

Development Steps
Create Spring Boot project in IDE
Create project structure
Configure PostgreSQL
Define domain entity - Student.java
Create Spring Data Repository - StudentRepository.java
Create Spring Rest Controller - StudentController.java
Exception(Error) Handling for RESTful Services
Run Application
Test REST API via Postman Client

1.Create Spring Boot project in IDE
>File > new > spring starter project
Name: school-springboot-postgresql-hibernate
Type: Maven
Packaging: Jar
Java Version: 8
Language: Java
Group: com.dracodess
Artifact: school-springboot-postgresql-hibernate
Description: School Management System using Spring Boot, PostgreSQL JPA, and Hibernate. CRUD API Example
Package: com.dracodess.springboot
.> Next  (New Spring Starter Project Dependencies)
Spring Boot Version: (Choose Stable) 2.2.6
Selected: 
Spring Data JPA
PostgreSQL
Spring Web
> Finish

2. Create project/package structure

Create a Controller Package
>Right click on package found under src/main/java : com.dracodess.springboot 
>New > Package

>Edit Name: com.dracodess.springboot.controller
> Finish

Create a Model Package:
>Right click on package found under src/main/java : com.dracodess.springboot 
>New > Package

>Edit Name: com.dracodess.springboot.model
> Finish

Create a Repository Package: (To handle persistence)
>Right click on package found under src/main/java : com.dracodess.springboot 
>New > Package

>Edit Name: com.dracodess.springboot.repository
> Finish

Create an Exception Package: (For handling exceptions)
>Right click on package found under src/main/java : com.dracodess.springboot 
>New > Package

>Edit Name: com.dracodess.springboot.exception
> Finish

3.Configure PostgreSQL in the Spring Boot Project
>src/main/resources > double click application.properties

Add the following: (PostgreSQL information) (only first 3 are required for postgresql)
spring.datasource.url = jdbc:postgresql://localhost:5432/students  
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.show-sql=true

##Hibernate Properties
# The SQL dialect makes Hibernate generate better SQL for the chose database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
Spring.jpa.hibernate.ddl-auto = update

Create the database
>Windows Start > Postgres >SQL Shell
>Hit Enter for the following:
Server [localhost]:
Database [postgres]:
Port [5432]:
Username [postgres]
Password for user postgres: *****

For the following include:
postgres=# create database students; >Enter

When you see:
CREATE DATABASE
postgres=#

Then close terminal

Then:
>PostgreSQL > SQL shell
Server [localhost]: >Enter
Database [postgres]: students >Enter
Port [5432]: >Enter
Username [postgres]: >Enter
Password for user postgres: *****

Then you will see:

students=#

Now it is pointing to the students database

4.Define domain entity - Student.java
>Right click on model package: com.dracodess.springboot.model 
>New >Class

Name: Student  >Finish

Create Properties inside Student class:
	private long id;
	private String firstName;
	private String lastName;
	private String cohort;
	private String email;

>Press Alt+Shift+S
>Generate Getters and Setters
>Select All
>Generate

A Java Persistence API (JPA) Entity class is an ordinary Java class that is marked (annotated) as having the ability to represent objects in the database.

To make this class a JPA Entity, do the following:

-Add Entity Annotation above Student class: (import javax.persistence.Entity)
package com.dracodess.springboot.model;

import javax.persistence.Entity;

@Entity
public class Student {
	private long id;
	private String firstName;
	private String lastName;
	private String cohort;
	private String email;
...
To be able to use a table we add the Table Annotation and name the table:

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	private long id;
	private String firstName;
	private String lastName;
	private String cohort;
	private String email;
…

Create a constructor
>Press Alt+Shift+S
>Generate a Constructor using Fields (unselect “id”)
>Generate
...
@Entity
@Table(name = "students")
public class Student {
	private long id;
	private String firstName;
	private String lastName;
	private String cohort;
	private String email;
	
	
	public Student(String firstName, String lastName, String cohort, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.cohort = cohort;
		this.email = email;
	}

Create a Default Constructor as well:
…
	private String cohort;
	private String email;
	
	
	public Student() {
		super();
	}
	public Student(String firstName, String lastName, String cohort, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.cohort = cohort;
		this.email = email;
	}
…

Annotate the id field to set it as a primary key and set strategy:
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
…

Annotate the other fields to set them as columns:

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "cohort")
	private String cohort;
	
	@Column(name = "email")
	private String email;


5. Create Spring Data Repository - StudentRepository.java
>Right click on Repository package: com.dracodess.springboot.repository
>New >Interface
Name: StudentRepository
> Finish

Extend the JpaRepository<Type, ID> to StudentRepository interface:

package com.dracodess.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dracodess.springboot.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}

Annotate StudentRepository interface with @Repository annotation to make it a Spring component

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dracodess.springboot.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}

6.Create Spring Rest Controller - StudentController.java

>Right click the controller package: com.dracodess.springboot.controller
>New >Class
Name: StudentController
>Finish

Annotate the StudentController class with the @RestController and @RequestMapping(“/path/”) annotations:

package com.dracodess.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

}

Create a REST API skeleton:
@RestController
@RequestMapping("/api/v1/")
public class StudentController {

	//Get all students
	//Get one student by id
	//Create/save student
	//Update student
	//Delete student
}

Autowire the StudentRepository interface (to export the CRUD APIs for database operations): 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dracodess.springboot.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	//Get all students
	//Get one student by id
	//Create/save student
	//Update student
	//Delete student
}

Create a GET request method to request all Students:
package com.dracodess.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dracodess.springboot.model.Student;
import com.dracodess.springboot.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	//Get all students
	@GetMapping("students") // <- creates the endpoint for this API call
	public List<Student> getAllStudents(){
		return this.studentRepository.findAll();
	}
Create an GET method to request a student by ID:
package com.dracodess.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
				.orElseThrow(() -> new ResourceNotFoundException("Student not found using this id ::" + studentId));
		return ResponseEntity.ok().body(student);
	}

Create the ResourceNotFoundException class:
>Right click exception package: com.dracodess.springboot.exception
>New >Class
Name: ResourceNotFoundException
>Finish

package com.dracodess.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

		private static final long serialVersionUID = 1L;		
		
		public ResourceNotFoundException(String message) {
			super(message); 
		}
		 
}

Make create/save method:
Using the @RequestBody annotation to directly bind the request body data to the Student object and @PostMapping annotation to handle the POST method routing:

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dracodess.springboot.exception.ResourceNotFoundException;
import com.dracodess.springboot.model.Student;
import com.dracodess.springboot.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {
...
	
	//Create/save a new student
	@PostMapping("students")
	public Student createStudent(@RequestBody Student student) {
		return this.studentRepository.save(student);
	}

}

Create Update/PUT method: 
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

...
	//Update student
	@PutMapping("students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value ="id") Long studentId,
			@Valid @RequestBody Student studentDetails) throws ResourceNotFoundException { //we use @RequestBody to bind the body of the request to studentDetails variable
		
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
}

Implement DELETE student request:
…
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
...
	//Delete student
	@DeleteMapping("students/{id}")
	public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Long studentId) throws ResourceNotFoundException {
	
		//Reusing this logic from getStudentById method to GET the student object from the Student database
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found using this id: " + studentId));
		
		// DELETE student object by ID
		this.studentRepository.delete(student);
			
		//Instantiate a HashMap and return a Boolean
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted",  Boolean.TRUE);
		
		return response;
	}
}


7. Exception(Error) Handling for RESTful Services

>Right click exception package: com.dracodess.springboot.exception
>New >Class
Name: ErrorDetails
>Finish

package com.dracodess.springboot.exception;

import java.util.Date;

public class ErrorDetails {
	//Create Fields
	private Date timestamp;
	private String message;
	private String details;
	
	//Create a Constructor
	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	//Create Getters and Setters to access fields
	public Date getTimestamp() {
		return timestamp;
	}	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}

Create a class to handle global exceptions
>Right click exception package: com.dracodess.springboot.exception
>New >Class
Name: GlobalExceptionHandler
>Finish

package com.dracodess.springboot.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	//Handling ResourceNotFoundException 
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		//Handling Error Details and returning them to the client
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	//Handling other Exceptions globally
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		//Handling Error Details and returning them to the client
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

8. Run the Application:
>Go to the main package: src/main/java > com.dracodess.springboot
>Right click on SchoolSpringbootPostresqlHibernateApplication.java
>Run As >Java Application

9. Test REST API via Postman Client:
Open Postman

In Postman, make a POST Request to create a new Student
>New > Request  >v POST 
Enter request URL: http://localhost:8080/api/v1/students

>Body 
>Select raw  >Select JSON
{
	"firstName": "John",
	"lastName": "Smith",
	"cohort": "11",
	"email": "john@careerdevs.com"
}

>Hit Send to send the request

Response should be like: 
{
   "id": 1,
   "firstName": "John",
   "lastName": "Smith",
   "cohort": "11",
   "email": "john@careerdevs.com"
}

Create a few more new students to the GET all and GET student by ID requests

Check Database entries through the SQL Shell terminal

Server [localhost]:
Database [postgres]: students
Port [5432]:
Username [postgres]:
Password for user postgres:
psql (12.2)
WARNING: Console code page (437) differs from Windows code page (1252)
         8-bit characters might not work correctly. See psql reference
         page "Notes for Windows users" for details.
Type "help" for help.

Then enter the following command to see List of Relations in the database:
students=# \d

Response:
               List of relations
 Schema |      Name       |   Type   |  Owner
--------+-----------------+----------+----------
 public | students        | table    | postgres
 public | students_id_seq | sequence | postgres
(2 rows)

Then enter the following command to see all students added in the database:
students=# select * from students;

Response:
 id | cohort |         email         | first_name | last_name
----+--------+-----------------------+------------+-----------
  1 | 11     | aaron@careerdevs.com  | John      | Smith
  2 | 10     | marlon@careerdevs.com | Marlon     | Smith
  3 | 10     | tj@careerdevs.com     | TJ         | Smith
  4 | 10     | shamar@careerdevs.com | Shamar     | Smith
(4 rows)


In Postman, make a GET all Request
>New > Request  >v GET
Enter request URL: http://localhost:8080/api/v1/students/
 
>Hit Send to send the request

Response should be like: 
[
   {
       "id": 1,
       "firstName": "John",
       "lastName": "Smith",
       "cohort": "11",
       "email": "john@careerdevs.com"
   },
   {
       "id": 2,
       "firstName": "Marlon",
       "lastName": "Smith",
       "cohort": "10",
       "email": "marlon@careerdevs.com"
   },
   {
       "id": 3,
       "firstName": "TJ",
       "lastName": "Smith",
       "cohort": "10",
       "email": "tj@careerdevs.com"
   },
   {
       "id": 4,
       "firstName": "Shamar",
       "lastName": "Smith",
       "cohort": "10",
       "email": "shamar@careerdevs.com"
   }
]
 
 
In Postman, make a GET Request by ID
>New > Request  >v GET
Enter request URL: http://localhost:8080/api/v1/students/1
 
>Hit Send to send the request

Response should be like: 
{
   "id": 1,
   "firstName": "John",
   "lastName": "Smith",
   "cohort": "11",
   "email": "john@careerdevs.com"
}
Try requesting other entries by Id.

In Postman, test the Exception Handling by trying to make a GET request for a student by an Id that does not exist. You should get the following response:
{
   "timestamp": "2020-04-23T05:29:44.390+0000",
   "message": "Student not found using this id: 6",
   "details": "uri=/api/v1/students/6"
}


In Postman, make a PUT Request by ID
>New > Request  >v PUT 
Enter request URL: http://localhost:8080/api/v1/students/1
 {
	"firstName": "John",
	"lastName": "Smithsonian",
	"cohort": "10",
	"email": "john@careerdevs.com"
}
>Hit Send to send the request

Response should be like: 
{
   "id": 1,
   "firstName": "John",
   "lastName": "Smithsonian",
   "cohort": "10",
   "email": "john@careerdevs.com"
}

In Postman, make a DELETE Request by ID
>New > Request  >v DELETE 
Enter request URL: http://localhost:8080/api/v1/students/1

Response should be like:
{
   "deleted": true
}

