package de.fherfurt.rest.controllers;

import de.fherfurt.rest.domains.Person;
import de.fherfurt.rest.domains.errors.PersonNotFoundException;
import de.fherfurt.rest.storages.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/persons")
public class PersonControllers {

    private final PersonRepository personRepository;

    @Autowired
    public PersonControllers(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<Person> findById(@PathVariable(value = "id") Long id) throws PersonNotFoundException {
        return ResponseEntity.ok(this.personRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("No Persons found for id " + id)));
    }

    @GetMapping()
    ResponseEntity<Person> findByName(@RequestParam(value = "name") String name) throws PersonNotFoundException {
        return ResponseEntity.ok(this.personRepository
                .findByLastName(name)
                .orElseThrow(() -> new PersonNotFoundException("No Persons found for name " + name)));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Person> save(@RequestBody Person person) {
        return ResponseEntity.ok(this.personRepository.save(person));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Person> update(@RequestBody Person person) {
        return ResponseEntity.ok(this.personRepository.save(person));
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        this.personRepository.deleteById(id);
    }
}
