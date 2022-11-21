/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.pucbr.exemplo.service.service;

import br.pucbr.exemplo.service.entity.*;
import br.pucbr.exemplo.service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class ClinicService implements ClinicServiceRepository {

    @Autowired
	private PetRepository petRepository;
	@Autowired
    private VetRepository vetRepository;
    @Autowired
	private OwnerRepository ownerRepository;
    @Autowired
	private AppointmentRepository appointmentRepository;
    @Autowired
	private SpecRepository specRepository;
	@Autowired
	private PetTypeRepository petTypeRepository;

	private final EntityManager em;

    @Autowired
     public ClinicService(
       		 PetRepository petRepository,
    		 VetRepository vetRepository,
    		 OwnerRepository ownerRepository,
    		 AppointmentRepository appointmentRepository,
    		 SpecRepository specRepository,
			 PetTypeRepository petTypeRepository,
			 EntityManager em) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.appointmentRepository = appointmentRepository;
        this.specRepository = specRepository;
		this.petTypeRepository = petTypeRepository;
		this.em = em;
    }

	@Override
	@Transactional(readOnly = true)
	public List<Pet> findAllPets() throws DataAccessException {
		return petRepository.findAll();
	}

	@Override
	@Transactional
	public void deletePet(Pet pet) throws DataAccessException {
		petRepository.delete(pet);
	}

	@Override
	@Transactional(readOnly = true)
	public Appointment findVisitById(int visitId) throws DataAccessException {
		Appointment appointment;
		try {
			appointment = appointmentRepository.findById(visitId).get();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return appointment;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Appointment> findAllVisits() throws DataAccessException {
		return appointmentRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteVisit(Appointment appointment) throws DataAccessException {
		appointmentRepository.delete(appointment);
	}

	@Override
	@Transactional(readOnly = true)
	public Vet findVetById(int id) throws DataAccessException {
		Vet vet;
		try {
			vet = vetRepository.findById(id).get();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return vet;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Vet> findAllVets() throws DataAccessException {
		return vetRepository.findAll();
	}

	@Override
	@Transactional
	public void saveVet(Vet vet) throws DataAccessException {
		vetRepository.save(vet);
	}

	@Override
	@Transactional
	public void deleteVet(Vet vet) throws DataAccessException {
		vetRepository.delete(vet);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Owner> findAllOwners() throws DataAccessException {
		return ownerRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteOwner(Integer id) throws DataAccessException {
		ownerRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public PetType findPetTypeById(int petTypeId) {
		PetType petType = null;
		try {
			petType = petTypeRepository.findById(petTypeId).get();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return petType;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PetType> findAllPetTypes() throws DataAccessException {
		return petTypeRepository.findAll();
	}

	@Override
	@Transactional
	public void savePetType(PetType petType) throws DataAccessException{
		petTypeRepository.save(petType);
	}

	@Override
	@Transactional
	public void deletePetType(PetType petType) throws DataAccessException {
		petTypeRepository.delete(petType);
	}

	@Override
	@Transactional(readOnly = true)
	public Spec findSpecialtyById(int specialtyId) {
		Spec specialty = null;
		try {
			specialty = specRepository.findById(specialtyId).get();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return specialty;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Spec> findAllSpecialties() throws DataAccessException {
		return specRepository.findAll();
	}

	@Override
	@Transactional
	public void saveSpecialty(Spec specialty) throws DataAccessException {
		specRepository.save(specialty);
	}

	@Override
	@Transactional
	public void deleteSpecialty(Spec specialty) throws DataAccessException {
		specRepository.delete(specialty);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PetType> findPetTypes() throws DataAccessException {
		return petTypeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		Owner owner = null;
		try {
			owner = ownerRepository.findById(id).get();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return owner;
	}
	@Override
	@Transactional(readOnly = true)
	public Owner findOwnerByPetId(Integer pet_id) throws DataAccessException {
		Owner owner = null;
		try {
			em.createQuery("SELECT DISTINCT owner  FROM Owner owner  WHERE owner.pet =: pet_id")
					.setParameter("pet_id", pet_id)
					.getResultList();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return owner;
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		Pet pet = null;
		try {
			pet = petRepository.findById(id).get();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return pet;
	}

	@Override
	@Transactional
	public void savePet(Pet pet) throws DataAccessException {
		petRepository.save(pet);
	}

	@Override
	@Transactional
	public void saveVisit(Appointment appointment) throws DataAccessException {
		appointmentRepository.save(appointment);
	}

	@Override
	@Transactional(readOnly = true)
    @Cacheable(value = "vets")
	public List<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}


	@Override
	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		ownerRepository.save(owner);
	}

	@Override
	@Transactional(readOnly = true)
	public List findOwnerByLastName(String lastName) throws DataAccessException {
		return em.createQuery("SELECT DISTINCT owner  FROM Owner owner  WHERE owner.lastName LIKE :last_Name")
				.setParameter("last_Name", lastName)
				.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List findVisitsByPetId(int petId) {

		return em.createQuery("SELECT v FROM Appointment v where v.pet.id =:id")
				.setParameter("id", petId)
				.getResultList();
	}
	@Transactional
	public void updatePetType(PetType newPetType, Integer petTypeId) {
	em.createQuery("update PetType p set p.name = :name where p.id = :id")
			.setParameter("name",newPetType.getName())
			.setParameter("id",petTypeId).executeUpdate();
	}


}
