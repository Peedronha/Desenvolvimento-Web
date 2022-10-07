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
package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.*;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

public interface ClinicServiceRepository {

	Pet findPetById(int id) throws DataAccessException;
	Collection<Pet> findAllPets() throws DataAccessException;
	void savePet(Pet pet) throws DataAccessException;
	void deletePet(Pet pet) throws DataAccessException;

	List<Appointment> findVisitsByPetId(int petId);
	Appointment findVisitById(int visitId) throws DataAccessException;
	List<Appointment> findAllVisits() throws DataAccessException;
	void saveVisit(Appointment appointment) throws DataAccessException;
	void deleteVisit(Appointment appointment) throws DataAccessException;

	Vet findVetById(int id) throws DataAccessException;
	Collection<Vet> findVets() throws DataAccessException;
	Collection<Vet> findAllVets() throws DataAccessException;
	void saveVet(Vet vet) throws DataAccessException;
	void deleteVet(Vet vet) throws DataAccessException;

	Owner findOwnerById(int id) throws DataAccessException;
	List<Owner> findAllOwners() throws DataAccessException;
	void saveOwner(Owner owner) throws DataAccessException;
	void deleteOwner(Integer id) throws DataAccessException;
	List<Owner> findOwnerByLastName(String lastName) throws DataAccessException;




}
