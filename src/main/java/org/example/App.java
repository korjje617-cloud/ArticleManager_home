package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Article Manager ===");

        articleController articleController = new articleController(sc);
        memberController memberController = new memberController(sc);

        articleController.makeTestData();
        memberController.makeUserData();

        int lastArticleId = 3;

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
                memberController.doJoin();
            }
            else if (cmd.equals("article write")) {
                articleController.doWrite();
            }

            else if (cmd.startsWith("article list")) {
                articleController.viewList();
            }

            else if (cmd.startsWith("article detail")) {
                articleController.viewDetail();
            }

            else if (cmd.startsWith("article delete")) {
                articleController.doDelete();
            }

            else if (cmd.startsWith("article modify")) {
                articleController.doModify();
            }

            else {
                System.out.println("사용 할 수 없는 명령어입니다");
            }
        }
    }
}