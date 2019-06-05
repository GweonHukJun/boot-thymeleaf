package idu.cs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idu.cs.entity.UserEntity;

@Repository
public interface UserRepository 
	extends JpaRepository<UserEntity, Long> {
	//findById, save, delete
	
	// 아래 메소드들은 선언해야 구현됨
	// find - select문, by - Where, OrderBy - order by, ASC, DESC
	List<UserEntity> findByName(String name);
	
	//id 내림차순
	//List<UserEntity> findByNameOrderByIdDESC(String name);
	List<UserEntity> findByCompany(String company);
	
	UserEntity findByUserid(String userId);
}
