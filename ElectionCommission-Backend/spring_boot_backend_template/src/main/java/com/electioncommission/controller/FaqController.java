package com.electioncommission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electioncommission.dto.FaqReqDto;
import com.electioncommission.services.FaqService;

@RestController
@RequestMapping("/faq")
@Validated
public class FaqController {

    @Autowired
    private FaqService faqService;

    @PostMapping("/addfaq")
    public ResponseEntity<?> addFaq(@RequestBody FaqReqDto faq) {
		return ResponseEntity.status(HttpStatus.CREATED).body(faqService.Addfaq(faq));

    }
}