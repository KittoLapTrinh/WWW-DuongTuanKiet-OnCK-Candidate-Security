package com.example.wwwduongtuankietoncksecurity.frontend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.example.wwwduongtuankietoncksecurity.backend.enums.Roles;
import com.example.wwwduongtuankietoncksecurity.backend.models.Candidate;
import com.example.wwwduongtuankietoncksecurity.backend.models.Experience;
import com.example.wwwduongtuankietoncksecurity.backend.repositories.CandidateRepository;
import com.example.wwwduongtuankietoncksecurity.backend.services.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.wwwduongtuankietoncksecurity.backend.services.ExperienceService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CandidateController {

//    private static final Logger log = LoggerFactory.getLogger(CandidateController.class);
    private final CandidateService candidateService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/candidate")
    public String getCandidate(Model model, @RequestParam(required = false) String rolefilterStr){
        Collection<Candidate> candidates = candidateService.getAllCandidate();

        if(rolefilterStr != null && !rolefilterStr.isEmpty()) {
            try {
                Roles rolefilter = Roles.valueOf(rolefilterStr);
                candidates = candidates.stream()
                        .filter(candidate -> candidate.getExperiences().stream()
                                .anyMatch(exp -> exp.getRole() == rolefilter))
                        .collect(Collectors.toList());
                model.addAttribute("rolefilter", rolefilter);
            } catch (IllegalArgumentException e) {
                model.addAttribute("errorMessage", "Invalid role value");
            }
        }
        model.addAttribute("candidates", candidates);
        model.addAttribute("roles", Roles.values());

        return "candidate/index";
    }



    @PostMapping("/candidate/insert")
    public String insertCandidate(Model model,
                                  @RequestParam("email") String email,
                                  @RequestParam("fullName") String fullName,
                                  @RequestParam("phone") String phone){
        candidateService.saveCandidate(new Candidate(email, fullName,phone));
        model.addAttribute("candidates", candidateService.getAllCandidate());
        return "redirect:/candidate";
    }

    @PostMapping("/candidate/delete/{id}")
    public String deleteCandidate(@PathVariable Long id){
        candidateService.deleteCandidateById(id);
        return "redirect:/candidate";
    }

    @GetMapping("/candidate/report1")
    public String getReport(Model model){
        model.addAttribute("showTable", false);
        return "candidate/report1";
    }

    @PostMapping("/candidate/report1")
    public String postReport1(Model model, @RequestParam("company") String company){
        model.addAttribute("showTable", true);
        model.addAttribute("candidates", candidateService.getCandidateReport(company));
        System.out.println("Company : "+company);
        return "candidate/report1";
    }

    @GetMapping("/candidate/report2")
    public String getReport2(Model model){
        model.addAttribute("candidates", candidateService.findCandidateThan5Exp());
        return "candidate/report2";
    }

    //edit
    @GetMapping("/candidate/edit/{id}")
    public String editCandidate(Model model, @PathVariable Long id){
        Candidate candidate = candidateService.getCandidateById(id);
        model.addAttribute("candidate", candidate);
        return "candidate/edit";
    }

    @PostMapping("/candidate/edit/{id}")
    public String updateCandidate(@PathVariable long id,
                                  @RequestParam("email") String email,
                                  @RequestParam("fullName") String fullName,
                                  @RequestParam("phone") String phone,
                                  Model model){
        Candidate candidate = candidateService.getCandidateById(id);
        if(candidate == null) {
            model.addAttribute("errorMessage", "Candidate not found");
            return "error";
        }

        candidate.setEmail(email);
        candidate.setFullName(fullName);
        candidate.setPhone(phone);

        candidateService.saveCandidate(candidate);
        return "redirect:/candidate";
    }

    @PostMapping("/candidate/update")
    public String updateCandidate(@ModelAttribute Candidate candidate){
       candidateService.updateCandidate(candidate);
       return "redirect:/candidate";
    }

    @GetMapping("/candidate/detail/{id}")
    public String getCandidateDetail(Model model, @PathVariable long id){
        Candidate candidate = candidateService.getCandidateById(id);
        if(candidate == null){
            model.addAttribute("errorMessage", "Candidate not found");
            return "error";
        }
        model.addAttribute("candidate", candidate);
        model.addAttribute("roles", Roles.values());
        model.addAttribute("experiences",candidate.getExperiences());
        return "candidate/detail";
    }

//    @GetMapping("/candidate/detail/{id}/filter")
//    public String filterExperiencesByRole(@PathVariable long id,
//                                          @RequestParam("role") Roles role,
//                                          Model model){
//        Candidate candidate = candidateService.getCandidateById(id);
//        if(candidate == null){
//            model.addAttribute("errorMessage", "Candidate not found");
//            return "error";
//        }
//
//
//        List<Experience> filteredExperiences = candidate.getExperiences().stream()
//                .filter(exp -> exp.getRole() == role) // So sánh trực tiếp với giá trị int
//                .collect(Collectors.toList());
//        model.addAttribute("roles", Roles.values());
//        model.addAttribute("candidate", candidate);
//        model.addAttribute("experience", filteredExperiences);
//        model.addAttribute("rolefilter", role);
//        return "candidate/detail";
//
//
//    }
}
