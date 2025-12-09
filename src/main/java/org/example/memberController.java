package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class memberController {

    Scanner sc;

    int lastMemberId = 3;
    List<Member> members;

    public memberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doJoin() {
        System.out.println("==회원 가입==");
        int memId = lastMemberId + 1;

        String loginId = null;
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();
            if (isJoinableLoginId(loginId) == false) {
                System.out.println("이미 사용중인 loginId");
                continue;
            }
            break;
        }

        String password = null;
        while (true) {
            System.out.print("비밀번호 : ");
            password = sc.nextLine().trim();
            System.out.print("비밀번호 재확인 : ");
            String passwordConfirm = sc.nextLine().trim();

            if (password.equals(passwordConfirm) == false) {
                System.out.println("비밀번호가 다릅니다");
                continue;
            }
            break;
        }

        System.out.print("이름 : ");
        String name = sc.nextLine().trim();
        String regDate = Util.getNowStr();
        String updateDate = Util.getNowStr();

        Member member = new Member(memId, loginId, password, name, regDate, updateDate);
        members.add(member);

        System.out.println(memId + "번 회원 가입 완료.");
        lastMemberId++;
    }

    /**
     * 회원 아이디 찾기
     **/
    private boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 테스트 유저
     */
    void makeUserData() {
        System.out.println("테스트 유저 만들기");
        members.add(new Member(1, "aaa", "aaa", "테스트1", "regDate", "updateDate"));
        members.add(new Member(2, "bbb", "bbb", "테스트2", "regDate", "updateDate"));
        members.add(new Member(3, "ccc", "bbb", "테스트3", "regDate", "updateDate"));
    }
}
