package idu.cs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idu.cs.entity.QuestionEntity;
import idu.cs.entity.UserEntity;

@Repository
public interface QuestionRepository 
	extends JpaRepository<QuestionEntity, Long> {
	//findById, save, delete
	

	QuestionEntity findByWriter(String writer);
}
