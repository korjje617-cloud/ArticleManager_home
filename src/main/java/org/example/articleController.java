package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class articleController {

    Scanner sc;

    int lastArticleId = 3;
    List<Article> articles;

    public articleController(Scanner sc) {
        this.sc = sc;
        articles = new ArrayList<>();
    }

    public void doWrite() {
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

    public void viewList() {
        System.out.println("==게시글 목록==");

        if (articles.size() == 0) {
            System.out.println("아무것도 없음");
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

    public void viewDetail() {
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

    public void doDelete() {
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

    public void doModify() {
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

    /**
     * 아이디 찾기
     **/
    Article getArticleById(int id) {
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
    void makeTestData() {
        System.out.println("테스트 게시글 만들기");
        articles.add(new Article(1, Util.getNowStr(), Util.getNowStr(), "테스트 제목", "테스트 제목"));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), "테스트 제목", "테스트 제목"));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), "테스트 제목", "테스트 제목"));
    }
}
