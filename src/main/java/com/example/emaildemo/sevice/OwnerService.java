package com.example.emaildemo.sevice;

import com.example.emaildemo.dto.Owner;

import java.util.List;


public interface OwnerService {

    Owner getOwner(long ownerId);

    List<Owner> getOwners();

    void delete(long ownerId);

    Owner update(Owner owner);

    Owner create(Owner owner);

}
