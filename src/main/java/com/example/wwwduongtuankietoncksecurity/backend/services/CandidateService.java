package com.example.wwwduongtuankietoncksecurity.backend.services;

import com.example.wwwduongtuankietoncksecurity.backend.models.Candidate;

import java.util.Collection;


public interface CandidateService {
    Collection<Candidate> getAllCandidate();
    Candidate getCandidateById(long id);
    Collection<Candidate> getCandidateReport(String company);
    Collection<Candidate>  findCandidateThan5Exp();
    Candidate saveCandidate(Candidate candidate);
    boolean deleteCandidateById(Long id);
    boolean updateCandidate(Candidate candidate);
}
