package com.pinterestscheduler.pinterest.Repository;

import com.pinterestscheduler.pinterest.Entities.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {

}
