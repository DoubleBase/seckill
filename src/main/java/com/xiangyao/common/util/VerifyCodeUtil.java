package com.xiangyao.common.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author xianggua
 * @description 随机验证码工具
 * @date 2019-7-20 17:57
 * @since 1.0
 */
public class VerifyCodeUtil {

    private static char[] ops = new char[]{'+', '-', '*'};

    /**
     * 绘制验证码表达式图片
     *
     * @param verifyCode
     * @return
     */
    public static BufferedImage createVerifyCode(String verifyCode) {
        int width = 80;
        int height = 32;
        //创建图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        //设置背景色
        graphics.setColor(new Color(0xDCDCDC));
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width - 1, height - 1);

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.drawOval(x, y, 0, 0);
        }
        //生成随机验证码
        graphics.setColor(new Color(0, 100, 0));
        graphics.setFont(new Font("Candara", Font.BOLD, 24));
        graphics.drawString(verifyCode, 8, 24);
        graphics.dispose();
        return bufferedImage;
    }

    /**
     * 计算验证码表达式的值
     *
     * @param exp
     * @return
     */
    public static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            Integer catch1 = (Integer) engine.eval(exp);
            return catch1.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String generateVerifyCode() {
        Random random = new Random();
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);
        char op1 = ops[random.nextInt(3)];
        char op2 = ops[random.nextInt(3)];
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        return exp;
    }

}
