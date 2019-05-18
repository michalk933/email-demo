package com.example.emaildemo.sevice.Impl;


import com.example.emaildemo.dto.Owner;
import com.example.emaildemo.exception.GeneralSqlException;
import com.example.emaildemo.repository.OwnerRepository;
import com.example.emaildemo.sevice.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public Owner getOwner(long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new GeneralSqlException(String.format("Not found owner by id: %1$s", ownerId)));
    }

    @Override
    public List<Owner> getOwners() {
        return Optional
                .ofNullable(ownerRepository.findAll())
                .orElseThrow(() -> new GeneralSqlException("Not found owners"));
    }

    @Override
    public void delete(long ownerId) {
        log.info(String.format("Delete owner: %1$s", ownerId));
        ownerRepository.delete(this.getOwner(ownerId));
    }

    @Override
    public Owner update(Owner owner) {
        log.info(String.format("Update owner: %1$s", owner));
        return null;
    }

    @Override
    public Owner create(Owner owner) {
        log.info(String.format("Create owner: %1$s", owner));
        return ownerRepository.save(owner);
    }
}
