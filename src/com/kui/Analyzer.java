package com.kui;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <p>
 * 主程序 句子分析器
 *
 * @author Wang Kuikui
 * @since 2022-03-27
 */
public class Analyzer {

    public Analyzer() {
        super();
        analyzeStack = new Stack<Character>();
        // 结束符进栈
        analyzeStack.push('#');
    }

    private ArrayList<AnalyzeProduce> analyzeProduces;

    // 文法
    private Grammar ll1Grammar;

    // 开始符
    private Character startChar;

    // 分析栈
    private Stack<Character> analyzeStack;

    // 剩余输入串
    private String str;

    // 推导所用产生或匹配
    private String useExp;

    public Grammar getLl1Grammar() {
        return ll1Grammar;
    }

    public void setLl1Grammar(Grammar ll1Grammar) {
        this.ll1Grammar = ll1Grammar;
    }

    public ArrayList<AnalyzeProduce> getAnalyzeProduces() {
        return analyzeProduces;
    }

    public void setAnalyzeProduces(ArrayList<AnalyzeProduce> analyzeProduces) {
        this.analyzeProduces = analyzeProduces;
    }

    public Character getStartChar() {
        return startChar;
    }

    public void setStartChar(Character startChar) {
        this.startChar = startChar;
    }

    public Stack<Character> getAnalyzeStack() {
        return analyzeStack;
    }

    public void setAnalyzeStack(Stack<Character> analyzeStack) {
        this.analyzeStack = analyzeStack;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getUseExp() {
        return useExp;
    }

    public void setUseExp(String useExp) {
        this.useExp = useExp;
    }

    /**
     * 分析
     */
    public void analyze() {
        analyzeProduces = new ArrayList<AnalyzeProduce>();

        // 开始符进栈
        analyzeStack.push(startChar);

        System.out.println(getFormatLogString("=========================【 ", 36, 0) +
                getFormatLogString("文法分析过程", 33, 1) +
                getFormatLogString(" 】=========================", 36, 0));
        System.out.println("序号\t\t符号栈\t\t\t\t\t输入串\t\t\t\t\t所用产生式");
        int index = 0;
        // 开始分析
        while (!analyzeStack.empty()) {
            index++;
            if (analyzeStack.peek() != str.charAt(0)) {
                // 到分析表中找到这个产生式
                String nowUseExpStr = TextUtils.findUseExp(ll1Grammar.getSelectMap(), analyzeStack.peek(), str.charAt(0));

                //打印表格注意, 制表符的个数
                int sp = analyzeStack.size() + (analyzeStack.size() - 1) * 2 + 2;
                String tmp = "";
                if(str.length() < 4) tmp = "\t\t\t\t\t\t";
                else if(str.length() < 8) tmp = "\t\t\t\t\t";
                else if(str.length() < 12) tmp = "\t\t\t\t";
                else if(str.length() < 16) tmp = "\t\t\t";
                else tmp = "\t\t";

                if (sp < 4) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t\t\t\t" + str + tmp
                            + analyzeStack.peek() + "->" + nowUseExpStr);
                } else if (sp < 8) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t\t\t" + str + tmp
                            + analyzeStack.peek() + "->" + nowUseExpStr);
                } else if (sp < 12) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t\t" + str + tmp
                            + analyzeStack.peek() + "->" + nowUseExpStr);
                } else if (sp < 16) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t" + str + tmp
                            + analyzeStack.peek() + "->" + nowUseExpStr);
                } else {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t" + str + tmp
                            + analyzeStack.peek() + "->" + nowUseExpStr);
                }

                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStack.toString());
                produce.setStr(str);
                if (null == nowUseExpStr) {
                    produce.setUseExpStr("无法匹配!");
                } else {
                    produce.setUseExpStr(analyzeStack.peek() + "->" + nowUseExpStr);
                }
                analyzeProduces.add(produce);
                // 将之前的分析栈中的栈顶出栈
                analyzeStack.pop();
                // 将要用到的表达式入栈,反序入栈
                if (null != nowUseExpStr && nowUseExpStr.charAt(0) != 'ε') {
                    for (int j = nowUseExpStr.length() - 1; j >= 0; j--) {
                        char currentChar = nowUseExpStr.charAt(j);
                        analyzeStack.push(currentChar);
                    }
                }
                continue;
            }
            // 如果可以匹配,分析栈出栈，串去掉一位
            if (analyzeStack.peek() == str.charAt(0)) {
                int sp = analyzeStack.size() + (analyzeStack.size() - 1) * 2 + 2;

                String tmp = "";
                if(str.length() < 4) tmp = "\t\t\t\t\t\t";
                else if(str.length() < 8) tmp = "\t\t\t\t\t";
                else if(str.length() < 12) tmp = "\t\t\t\t";
                else if(str.length() < 16) tmp = "\t\t\t";
                else tmp = "\t\t";

                if (sp < 4) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t\t\t\t" + str + tmp + "“"
                            + str.charAt(0) + "”匹配");
                } else if (sp < 8) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t\t\t" + str + tmp + "“"
                            + str.charAt(0) + "”匹配");
                } else if (sp < 12) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t\t" + str + tmp + "“"
                            + str.charAt(0) + "”匹配");
                } else if (sp < 16) {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t\t" + str + tmp + "“"
                            + str.charAt(0) + "”匹配");
                } else {
                    System.out.println(index + "\t\t" + analyzeStack.toString() + "\t\t" + str + tmp + "“"
                            + str.charAt(0) + "”匹配");
                }

                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStack.toString());
                produce.setStr(str);
                produce.setUseExpStr("“" + str.charAt(0) + "”匹配");
                analyzeProduces.add(produce);
                analyzeStack.pop();
                str = str.substring(1);
            }
        }
    }

    // 字体颜色工具
    private static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }
}
