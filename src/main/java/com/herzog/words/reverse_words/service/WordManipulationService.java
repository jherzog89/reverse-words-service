package com.herzog.words.reverse_words.service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.herzog.words.reverse_words.model.ManipulatedString;
import com.herzog.words.reverse_words.repository.ManipulatedStringRepository;

import jakarta.transaction.Transactional;

@Service
public class WordManipulationService {

    @Autowired
    ManipulatedStringRepository repo;


    @Transactional
    public ManipulatedString reverseString(ManipulatedString maniStr){
        //populate microserviceId
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String processId = runtimeMXBean.getName(); // Extracts PID from format "pid@hostname"
        maniStr.setMicroserviceId(processId);

        //populate manipulatedString
        maniStr.setManipulatedString(this.reverseWordsUtil(maniStr.getOriginalString()));

        //populate timeCompleted
        maniStr.setTimeCompleted(new Date());
        return repo.save(maniStr);
    }

    public String reverseWordsUtil(String s) {
        // Split string into words
        String[] words = s.trim().split("\\s+");
        
        // Reverse the words array
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) result.append(" ");
        }
        
        return result.toString();
    }

    public List<ManipulatedString> getLastTenReversedStrings() {
        Pageable pageable = PageRequest.of(0, 10); // Page 0, size 10
        return repo.findLast10ByTimeCompleted(pageable);
    }

    public void deleteAllManipulatedStrings() {
        repo.deleteAll();
    }

}
