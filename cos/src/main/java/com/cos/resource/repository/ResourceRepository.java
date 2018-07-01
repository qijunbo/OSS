package com.cos.resource.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, String> {

    List<Resource> findByTagsLike(String lastName);
    
    List<Resource> findByLegal(boolean legal);

    Resource findByMd5code(String md5code);
}
