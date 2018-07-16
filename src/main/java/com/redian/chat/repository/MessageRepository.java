package com.redian.chat.repository;

import com.redian.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

    @Query(value = "select m from Message as m where m.id>=:lastId")
    List<Message> findAllByLastId(@Param("lastId") Long lastId);
}
