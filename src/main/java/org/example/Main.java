package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Article Manager ===");

        int lastArticleId = 0;

        List<Article> articles = new ArrayList<>();

        while (true){
            System.out.print("명령어 ) ");
            String cmd =  sc.nextLine().trim();

            if (cmd.equals("exit")){
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (cmd.length() == 0){
                System.out.println("명령어를 입력하세요.");
                continue;
            }

            if (cmd.equals("article write")){
                System.out.println("== 게시글 작성 ==");

                int id = lastArticleId + 1;

                System.out.print("제목 : ");
                String title = sc.nextLine().trim();

                System.out.print("내용 : ");
                String body = sc.nextLine().trim();

                Article article = new Article(id, title, body);
                articles.add(article);

                lastArticleId++;

                System.out.println(id + "번 글이 작성 되었습니다.");
            }

            else if (cmd.equals("article list")){
                System.out.println("== 게시글 목록 ==");
                System.out.println("번호   /   제목   /   내용   ");

                if (articles.isEmpty()){
                    System.out.println("게시글이 없습니다");
                } else {
                    for (int i = articles.size() - 1; i >= 0; i--){
                        Article article = articles.get(i);
                        System.out.printf("%d   /   %s   /   %s   \n", article.getId(), article.getTitle(), article.getBody());
                    }

                }

            }

            else if (cmd.startsWith("article detail")){
                System.out.println("== 게시글 상세 ==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = null;

                for (Article article : articles){
                    if (article.getId() == id){
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null){
                    System.out.println("없는 게시글입니다");
                }
                else {
                    System.out.println("번호 : " + foundArticle.getId());
                    System.out.println("제목 : " + foundArticle.getTitle());
                    System.out.println("내용 : " + foundArticle.getBody());
                }
            }

            else if (cmd.startsWith("article delete")){
                System.out.println("== 게시글 삭제 ==");
                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = null;

                for (Article article : articles){
                    if (article.getId() == id){
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null){
                    System.out.println("없는 게시글입니다");
                }
                else {
                    articles.remove(foundArticle);
                    System.out.println(id + "번 게시글이 삭제 되었습니다.");
                }

            }

            else if (cmd.startsWith("article modify")){
                System.out.println("== 게시글 수정 ==");
                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = null;

                for (Article article : articles){
                    if (article.getId() == id){
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null){
                    System.out.println("없는 게시글입니다");
                }
                else {
                    System.out.println("기존 title : " + foundArticle.getTitle());
                    System.out.println("기존 body : " + foundArticle.getBody());

                    System.out.print("새 제목 : ");
                    String newtitle = sc.nextLine().trim();

                    System.out.print("새 내용 : ");
                    String newbody = sc.nextLine().trim();

                    foundArticle.setTitle(newtitle);
                    foundArticle.setBody(newbody);

                    System.out.println(id + "번 게시글이 수정되었습니다");
                }
            }

            else {
                System.out.println("사용 할 수 없는 명령어입니다");
            }


        }
    }
}

class Article{
    private int id;
    private String title;
    private String body;


    public int getId() {
        return id;
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

    public Article(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}