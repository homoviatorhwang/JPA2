package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//생성자를 만들어줌
/*@AllArgsConstructor*/
// 파이널이 있는 필드만 가지고 생성자를 만들어줌
@RequiredArgsConstructor
public class MemberService {


    // 원래 여기에 autowired 해서 써주는데 private으로 생성되어 있고, access 하기에 힘들기 때문에
    // 아래처럼 생성자 생성해주고 생성자에 autowired annotation 작성해줌
    // testcate 작성하기가 좋음
    // 최신 스프링에서는 생성자가 하나만 잇으면 그냥 자동으로 autowired 어노테이션이 붙어서
    // autowired를 안 붙여도 된다
    // final 꼭 붙여줘야 한다~
    private final MemberRepository memberRepository;

    
    /*@Autowired*/
/*    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        // 변수 자동 생성은 ctrl + shift + v
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            // semicolon 자동으로 찍는거는 ctrl + shift + enter
            // 줄바꿈은 ctrl + enter
            throw new IllegalStateException("이미 존재하는 회원입니다!");
        }
    }

    // 회원 전체 조회
    // readOnly 트루로 해주면 조회문이라는거 알기 때문에 메모리 사용 최적화해줌
    // 조회성에서는 이렇게 해주는게 좋다
    /*@Transactional(readOnly = true)*/
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
