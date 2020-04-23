package com.dracodess.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dracodess.springboot.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
