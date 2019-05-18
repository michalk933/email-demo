package com.example.emaildemo.controller;


import com.example.emaildemo.dto.Owner;
import com.example.emaildemo.sevice.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerlService;

    @GetMapping("/{ownerId}")
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        return ownerlService.getOwner(ownerId);
    }

    @GetMapping
    public List<Owner> getOwner() {
        return ownerlService.getOwners();
    }

    @PostMapping("/create")
    public Owner create(@RequestBody Owner owner) {
        return ownerlService.create(owner);
    }

    @PostMapping("/post")
    public Owner update(@RequestBody Owner owner) {
        return ownerlService.update(owner);
    }

    @DeleteMapping("/{ownerId}")
    public void delete(@PathVariable("ownerId") long ownerId) {
        ownerlService.delete(ownerId);
    }


}
