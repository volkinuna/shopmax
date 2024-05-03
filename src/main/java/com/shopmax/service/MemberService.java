package com.shopmax.service;

import com.shopmax.config.MemberContext;
import com.shopmax.entity.Member;
import com.shopmax.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional //하나의 메소드가 트랜잭션으로 묶인다. (DB Exception 혹은 다른 Exception 발생시 롤백)
@RequiredArgsConstructor //상수를 의존성 주입하기 위해..
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //회원가입
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member); //회원정보 insert 후 해당 회원정보 다시 리턴
    }

    //회원 중복체크
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        //해당 email 계정을 가진 사용자가 있는지 확인
//        Member member = memberRepository.findByEmail(email);
//
//        if (member == null) { //사용자가 없다면
//            throw new UsernameNotFoundException(email);
//        }
//
//        //사용자가 있다면 DB에서 가져온 값으로 userDetails 객체를 만들어서 반환
//        return User.builder()
//                .username(member.getEmail())
//                .password(member.getPassword())
//                .roles(member.getRole().toString())
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //해당 email 계정을 가진 사용자가 있는지 확인
        Member member = memberRepository.findByEmail(email);

        if (member == null) { //사용자가 없다면
            throw new UsernameNotFoundException(email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("ADMIN".equals(member.getRole().toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new MemberContext(member, authorities); //User 객체를 상속받은 MemberContext을 넣어주면 스프링이 알아서 처리한다.
    }
}
