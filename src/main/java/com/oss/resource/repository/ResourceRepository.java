package com.oss.resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, String> {

    List<Resource> findByTagsLike(String lastName);
    
    List<Resource> findByLegal(boolean legal);

    Resource findByMd5code(String md5code);
}
