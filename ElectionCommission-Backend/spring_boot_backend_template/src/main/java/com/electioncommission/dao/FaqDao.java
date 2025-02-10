package com.electioncommission.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electioncommission.pojos.Faq;

public interface FaqDao extends JpaRepository<Faq, Long> {

}
