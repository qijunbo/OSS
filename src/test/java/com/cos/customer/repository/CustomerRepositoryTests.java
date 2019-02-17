/*
 * Copyright 2002-2016 the original author or authors.
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

package com.cos.customer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oss.customer.repository.Customer;
import com.oss.customer.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customers;

    @Test
    public void testFindByName() {
        Customer customer = new Customer("first last", "tony@cn.bim.com", null);
        entityManager.persist(customer);

        Page<Customer> findByLastName = customers.findByName(customer.getName(), new PageRequest(0, 10));
        assertThat(findByLastName.getContent()).extracting(Customer::getName).containsOnly(customer.getName());
    }
}