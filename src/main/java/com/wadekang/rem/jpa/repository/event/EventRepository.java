package com.wadekang.rem.jpa.repository.event;

import com.wadekang.rem.jpa.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

}
