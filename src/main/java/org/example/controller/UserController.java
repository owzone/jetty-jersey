package org.example.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.example.controller.exception.UserException;
import org.example.dto.UserDTO;
import org.example.dto.translator.UserTranslator;
import org.example.entity.UserEntity;
import org.example.util.PersistenceUtil;

public class UserController {

	public UserController() {
	}

	public void createUser(final UserDTO user) throws UserException {
		UserEntity userEntity = UserTranslator.toEntity(user);
		EntityManagerFactory sessionFactory = PersistenceUtil.getSessionFactory();
		EntityManager entityManager = sessionFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(userEntity);
			entityManager.getTransaction().commit();
		} catch (PersistenceException e) {
			throw new UserException("A user with username: " + user.getUserName() + " already exists.");
		} finally {
			entityManager.close();
		}
	}

	public UserDTO getUser(final String userName) throws UserException {
		EntityManagerFactory sessionFactory = PersistenceUtil.getSessionFactory();
		EntityManager entityManager = sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		List<UserEntity> resultList = entityManager
				.createQuery("SELECT u FROM UserEntity u WHERE u.username = '" + userName + "'",
						UserEntity.class)
				.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		if (resultList.size() == 0) {
			throw new UserException("No user found with username: " + userName);
		}
		UserEntity userEntity = resultList.get(0);
		UserDTO user = UserTranslator.toDTO(userEntity);
		return user;
	}

}
