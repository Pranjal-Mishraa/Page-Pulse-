package com.pranjal.PagePulse.repository;

import com.pranjal.PagePulse.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
