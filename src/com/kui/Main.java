package com.kui;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * <p>
 * 主程序
 *
 * @author WangKuikui
 * @since 2022-03-27
 */
public class Main {

    // 字体颜色工具
    private static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getFormatLogString("=========================【 ", 36, 0) +
                getFormatLogString("给定文法如下", 34, 1) +
                getFormatLogString(" 】=========================", 36, 0));
        System.out.println("\t\t\t\t\t\t   " +
                getFormatLogString("[ A -> BaC ]", 32, 0));
        System.out.println("\t\t\t\t\t\t   " +
                getFormatLogString("[ A -> CbB ]", 32, 0));
        System.out.println("\t\t\t\t\t\t   " +
                getFormatLogString("[ B -> cAc ]", 32, 0));
        System.out.println("\t\t\t\t\t\t   " +
                getFormatLogString("[ B ->  c  ]", 32, 0));
        System.out.println("\t\t\t\t\t\t   " +
                getFormatLogString("[ C -> dBb ]", 32, 0));
        System.out.println("\t\t\t\t\t\t   " +
                getFormatLogString("[ C ->  b  ]", 32, 0));

        System.out.println();
        System.out.print("请输入一个句子：");
        String sent = new String();
        Scanner sc = new Scanner(System.in);
        sent = sc.nextLine();

        // 第一步：获取 LL(1)文法
        ArrayList<String> gsArray = new ArrayList<String>();
        Grammar grammar = new Grammar();

        //初始化
        initGs(gsArray);

        grammar.setGsArray(gsArray);
        grammar.getNvNt();
        grammar.initExpressionMaps();
        grammar.getFirst();

        // 设置开始符
        grammar.setS('A');
        grammar.getFollow();
        grammar.getSelect();

        // 创建一个分析器
        Analyzer analyzer = new Analyzer();

        // 设定开始符号
        analyzer.setStartChar('A');
        analyzer.setLl1Grammar(grammar);

        // 待分析的字符串
        analyzer.setStr(sent + "#");

        // 执行分析, 打印分析步骤, 保存文件
        analyzer.analyze();
    }

    /**
     * 初始化文法，设定产生式
     *
     * @param gsArray
     */
    private static void initGs(ArrayList<String> gsArray) {
        gsArray.add("A->BaC");
        gsArray.add("A->CbB");
        gsArray.add("B->cAc");
        gsArray.add("B->c");
        gsArray.add("C->dBb");
        gsArray.add("C->b");

    }
}
