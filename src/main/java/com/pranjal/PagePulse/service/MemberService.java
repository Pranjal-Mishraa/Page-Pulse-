package com.pranjal.PagePulse.service;

import com.pranjal.PagePulse.entity.Member;
import com.pranjal.PagePulse.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAll(){
        return memberRepository.findAll();}

    public Optional<Member> getById(Long id){
        return memberRepository.findById(id);}

    public Member saveMember(Member member){
        return memberRepository.save(member);}

    public void deleteMemberById(Long id){
        memberRepository.deleteById(id);}
}
