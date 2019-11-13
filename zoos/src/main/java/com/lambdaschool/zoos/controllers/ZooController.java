package com.lambdaschool.zoos.controllers;

import com.lambdaschool.zoos.models.Zoo;
import com.lambdaschool.zoos.services.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/zoos")
public class ZooController {

    @Autowired
    ZooService zooService;

    @GetMapping(value = "/zoos",
                produces = {"application/json"})
    public ResponseEntity<?> listAllZoos() {
        List<Zoo> myZoos = zooService.findAll();
        return new ResponseEntity<>(myZoos, HttpStatus.OK);
    }

    @GetMapping(value = "/zoo/namelink/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getZooByNameLike(@PathVariable String name) {
        List<Zoo> myZoos = zooService.findByNameContaining(name);
        return new ResponseEntity<>(myZoos, HttpStatus.OK);
    }

    @GetMapping(value = "/zoo/{zooid}",
                produces = {"application/json"})
    public ResponseEntity<?> getZooById(@PathVariable long zooid) {
        Zoo myZoo = zooService.findZooById(zooid);
        return new ResponseEntity<>(myZoo, HttpStatus.OK);
    }

    @PostMapping(value = "/zoo",
                 consumes = {"application/json"})
    public ResponseEntity<?> addZoo(@Valid @RequestBody Zoo newZoo) {
        newZoo = zooService.save(newZoo);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newZooURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{zooid}")
                .buildAndExpand(newZoo.getZooid())
                .toUri();
        responseHeaders.setLocation(newZooURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/zoo/{zooid}",
                consumes = {"application/json"})
    public ResponseEntity<?> updateZoo(@RequestBody Zoo updateZoo,
                                       @PathVariable long zooid) {
        zooService.update(updateZoo, zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/zoo/{zooid}")
    public ResponseEntity<?> deleteZooById(@PathVariable long zooid) {
        zooService.delete(zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity<?> postZooAnimalById(@PathVariable long zooid, @PathVariable long animalid) {
        zooService.addZooAnimal(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity<?> deleteZooAnimalByIds(@PathVariable long zooid, @PathVariable long animalid) {
        zooService.deleteZooAnimal(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
