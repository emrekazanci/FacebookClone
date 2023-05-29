package com.emre.service;

import com.emre.repository.IUserRolesRepository;
import com.emre.repository.entity.UserRoles;
import com.emre.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class UserRolesService extends ServiceManager<UserRoles,String> {
    private final IUserRolesRepository repository;
    public UserRolesService(IUserRolesRepository repository){
        super(repository);
        this.repository=repository;
    }

}