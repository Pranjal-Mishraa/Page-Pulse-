package com.pranjal.PagePulse.controller;

import com.pranjal.PagePulse.entity.Member;
import com.pranjal.PagePulse.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // Get all members
    @Operation(summary = "Get all members", description = "Retrieve a list of all library members")

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAll();
    }

    // Get member by Id
    @Operation(summary = "Get a member by ID", description = "Retrieve a member's details using their ID")

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> member = memberService.getById(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new member
    @Operation(summary = "Create a new member", description = "Add a new member to the library")


    @PostMapping
    public ResponseEntity<String> createMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }


    // Delete member
    @Operation(summary = "Delete a member", description = "Delete a member from the library by their ID")


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMemberById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @Operation(summary = "Update a member", description = "Update an existing member's information by their ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Member successfully updated"),
//            @ApiResponse(responseCode = "404", description = "Member not found"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
//        if (memberService.getById(id).isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        member.setId(id);
//        return ResponseEntity.ok(memberService.saveMember(member));
//    }


}
