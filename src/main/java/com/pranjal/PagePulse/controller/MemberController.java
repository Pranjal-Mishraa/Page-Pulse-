package com.pranjal.PagePulse.controller;

import com.pranjal.PagePulse.entity.Member;
import com.pranjal.PagePulse.service.BookService;
import com.pranjal.PagePulse.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {

        return memberService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> member = memberService.getById(id);
        if (member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {

        return memberService.saveMember(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        if (memberService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        member.setId(id);
        return ResponseEntity.ok(memberService.saveMember(member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (memberService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberService.deleteMemberById(id);
        return ResponseEntity.noContent().build();
    }
}