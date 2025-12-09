package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Article> articles = new ArrayList<>();
    static List<Member> members = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Article Manager ===");

        makeTestData();
        makeUserData();

        int lastArticleId = 3;
        int lastMemberId = 3;

        while (true) {
            System.out.print("명령어 ) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요.");
                continue;
            }

            if (cmd.equals("member join")) {
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

            else if (cmd.equals("article write")) {
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();
                String regDate = Util.getNowStr();
                String updateDate = Util.getNowStr();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 작성되었습니다.");
                lastArticleId++;
            }

            else if (cmd.startsWith("article list")) {
                System.out.println("==게시글 목록==");
                if (articles.size() == 0) {
                    System.out.println("아무것도 없음");
                    continue;
                }

                String searchkeyword = cmd.substring("article list".length()).trim();

                List<Article> forPrintArticles = articles;

                if (searchkeyword.length() > 0) {
                    System.out.println("검색어 : " + searchkeyword);
                    forPrintArticles = new ArrayList<>();

                    for (Article article : articles) {
                        if (article.getTitle().contains(searchkeyword)) {
                            forPrintArticles.add(article);
                        }
                    }
                    if (forPrintArticles.isEmpty()) {
                        System.out.println("검색 결과 없음");
                        continue;
                    }
                }

                System.out.println("   번호  /       날짜       /       제목     /   내용  ");
                for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
                    Article article = forPrintArticles.get(i);
                    if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                        System.out.printf("   %d     /    %s          /    %s     /     %s   \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                    } else {
                        System.out.printf("   %d     /    %s          /    %s     /     %s   \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                    }
                }

            }

            else if (cmd.startsWith("article detail")) {
                System.out.println("== 게시글 상세 ==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("없는 게시글입니다");
                } else {
                    System.out.println("번호 : " + foundArticle.getId());
                    System.out.println("작성 날짜 : " + foundArticle.getRegDate());
                    System.out.println("수정 날짜 : " + foundArticle.getUpdateDate());
                    System.out.println("제목 : " + foundArticle.getTitle());
                    System.out.println("내용 : " + foundArticle.getBody());
                }
            }

            else if (cmd.startsWith("article delete")) {
                System.out.println("== 게시글 삭제 ==");
                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("없는 게시글입니다");
                } else {
                    articles.remove(foundArticle);
                    System.out.println(id + "번 게시글이 삭제 되었습니다.");
                }

            }

            else if (cmd.startsWith("article modify")) {
                System.out.println("== 게시글 수정 ==");
                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("없는 게시글입니다");
                } else {
                    System.out.println("기존 title : " + foundArticle.getTitle());
                    System.out.println("기존 body : " + foundArticle.getBody());

                    System.out.print("새 제목 : ");
                    String newtitle = sc.nextLine().trim();

                    System.out.print("새 내용 : ");
                    String newbody = sc.nextLine().trim();

                    foundArticle.setTitle(newtitle);
                    foundArticle.setBody(newbody);

                    foundArticle.setUpdateDate(Util.getNowStr());

                    System.out.println(id + "번 게시글이 수정되었습니다");
                }
            }

            else {
                System.out.println("사용 할 수 없는 명령어입니다");
            }
        }
    }

    /**
     * 회원 아이디 찾기
     **/
    private static boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 아이디 찾기
     **/
    private static Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    /**
     * 테스트 게시글
     */
    private static void makeTestData() {
        System.out.println("테스트 게시글 만들기");
        articles.add(new Article(1, Util.getNowStr(), Util.getNowStr(), "테스트 제목", "테스트 제목"));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), "테스트 제목", "테스트 제목"));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), "테스트 제목", "테스트 제목"));
    }

    /**
     * 테스트 유저
     */
    private static void makeUserData() {
        System.out.println("테스트 유저 만들기");
        members.add(new Member(1, "aaa", "aaa", "테스트1", "regDate", "updateDate"));
        members.add(new Member(2, "bbb", "bbb", "테스트2", "regDate", "updateDate"));
        members.add(new Member(3, "ccc", "bbb", "테스트3", "regDate", "updateDate"));
    }
}
// ---------------------------------------------------------------

class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private String title;
    private String body;

    public Article(int id, String regDate, String updateDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}

class Member {
    private int memid;
    private String loginId;
    private String password;
    private String name;
    private String regDate;
    private String updateDate;

    public Member(int memid, String loginId, String password, String name, String regDate, String updateDate) {
        this.memid = memid;
        this.loginId = loginId;
        this.password = password;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemid() {
        return memid;
    }

    public void setMemid(int memid) {
        this.memid = memid;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}