package com.agrosmart.repository;

import com.agrosmart.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm,Long> {

}
