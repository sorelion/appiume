package com.cmit.clouddetection.socket;

/*
 * 执行脚本参数部分
 * Created by admin on 2018/7/10.
 */

public class Parameters {
    private String strategy = "";
    private String selector = "";
    private String context = "";
    private boolean multiple = false;
    private String elementId ="";
    private String x = "";
    private String y = "";
    private String text = "";
    private String name = "";
    private boolean replace = false;
    private boolean unicodeKeyboard = true;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int steps; //滑动速度

    public Parameters() {
    }

    /*find name和xpath的操作元素*/
    public Parameters(String strategy, String selector, String context, boolean multiple) {
        this.strategy = strategy;
        this.selector = selector;
        this.context = context;
        this.multiple = multiple;
    }

    /*find id的操作元素*/
    public Parameters(String strategy, String selector, String context, String name, boolean multiple) {
        this.strategy = strategy;
        this.selector = selector;
        this.context = context;
        this.name = name;
        this.multiple = multiple;
    }

    /*click操作元素*/
    public Parameters(String elementId) {
        this.elementId = elementId;
    }

    /*setText操作元素*/
    public Parameters(String elementId, String text, boolean replace, boolean unicodeKeyboard) {
        this.elementId = elementId;
        this.text = text;
        this.replace = replace;
        this.unicodeKeyboard = unicodeKeyboard;
    }

    /*操作坐标*/
    public Parameters(String x, String y) {
        this.x = x;
        this.y = y;
    }

    /*操作坐标*/
    public Parameters(int startX, int startY, int endX, int endY, int steps) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.steps = steps;
    }

    public String getStrategy() {
        return strategy;
    }
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getSelector() {
        return selector;
    }
    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getContext(){
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }

    public String getElementId() {
        return elementId;
    }
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public boolean isMultiple() {
        return multiple;
    }
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReplace() {
        return replace;
    }
    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public boolean isUnicodeKeyboard() {
        return unicodeKeyboard;
    }
    public void setUnicodeKeyboard(boolean unicodeKeyboard) {
        this.unicodeKeyboard = unicodeKeyboard;
    }

    public int getStartX() {
        return startX;
    }
    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }
    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }
    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }
    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }
}
