package com.demo.mileage_service.mileage.hst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MileageHstService {
    
    @Autowired
    private MileageHstRepository mileageHstRepository;
}
