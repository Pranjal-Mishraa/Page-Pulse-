package com.pranjal.PagePulse.service;

import com.pranjal.PagePulse.entity.Member;
import com.pranjal.PagePulse.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // Retrieve all members
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    // Retrieve member by ID
    public Optional<Member> getById(Long id) {
        return memberRepository.findById(id);
    }

    // Create  member
    public ResponseEntity<String> saveMember(Member member) {
        if(member.getName().isEmpty()  || member.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("Name or Email is missing");
        }

                memberRepository.save(member);
        return ResponseEntity.ok("New member has been added");
    }

    // Delete member by ID
    public void deleteMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            memberRepository.deleteById(id);
        } else {
            throw new RuntimeException("Member not found.");
        }
    }
}
