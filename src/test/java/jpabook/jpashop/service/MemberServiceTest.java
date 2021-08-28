package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
// MemberService 에서 ctrl + shift + t 누르면 test 자동으로 생성
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    // rollback을 해놔야 나가는 query를 볼 수 이씅ㅁ
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        
        //when

        Long saveId = memberService.join(member);


        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    /*@Test*/
    // 이렇게 작성해주면 하단에 try catch로 안 작성해줘도 괜찮음
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외처리() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");
        //when

        memberService.join(member1);
        memberService.join(member2);
        /*try{
            memberService.join(member2);
        } catch (IllegalStateException e){
            return;
        }*/

        //then
        fail("예외가 발생해야 한다.");
        
    }
}