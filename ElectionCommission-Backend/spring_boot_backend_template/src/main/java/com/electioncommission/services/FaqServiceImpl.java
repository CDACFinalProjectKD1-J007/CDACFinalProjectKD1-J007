package com.electioncommission.services;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electioncommission.dao.FaqDao;
import com.electioncommission.dto.FaqReqDto;
import com.electioncommission.pojos.Faq;

@Service
@Transactional
public class FaqServiceImpl implements FaqService {
	
	@Autowired
    private FaqDao faqDao;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String Addfaq(FaqReqDto faq) {
		Faq f=modelMapper.map(faq, Faq.class);
		Faq persistentFaq = faqDao.save(f);
		return "Added New Faq";
	}

}
