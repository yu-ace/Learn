package Revenue;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class orderManager {
    static int[] orderDishIds =new int[50];
    static int[] orderDishCounts = new int[50];
    static int orderDishCount = 0;

    static File menuOrderFile = new File(FileUtils.getUserDirectoryPath() + "/learn/restaurant/menuMoney.dat");


    public static void viewTheDish(){
        for(int i = 0; i < menuManager.dishCount; i++){
            System.out.println(menuManager.dishNames[i]+"\t"+ menuManager.dishPrices[i]);
        }
    }

    /**
     * 对顾客下单扩容
     */
    public static void enCapacityOrder(){
        int[] newOrderDishIds = new int[orderDishIds.length + 50];
        int[] newOrderDishCounts = new int[orderDishCounts.length + 50];
        for(int i = 0;i < menuManager.dishNames.length;i++){
            newOrderDishIds[i] = orderDishIds[i];
            newOrderDishCounts[i] = orderDishCounts[i];
        }
        orderDishIds = newOrderDishIds;
        orderDishCounts = newOrderDishCounts;
    }


    /**
     * 管理员查看顾客订单列表
     */
    public static void dishOrder(){
        for(int i = 0; i < orderDishCount; i++){
            System.out.println(menuManager.dishNames[orderDishIds[i]] + "\t"
                    + orderDishCounts[i] + "\t"
                    + (orderDishCounts[i] * menuManager.dishPrices[orderDishIds[i]]));
        }
    }

    /**
     * 查看总收入
     */
    public static void todayRevenue(){
        double sum = 0;
        for(int i = 0; i < orderDishCount; i++){
            int id = orderDishIds[i];
            sum = sum + (menuManager.dishPrices[id] * orderDishCounts[i]);
        }
        System.out.println("今日总收入为：" + sum);
    }


    /**
     * 顾客下单的菜单金额
     * @return 总金额
     */
    public static double total(){
        double sum = 0;
        for(int i = 0;i < orderDishCount;i++){
            sum = sum + (menuManager.dishPrices[orderDishIds[i]] * orderDishCounts[i]);
        }
        return sum;
    }

    /**
     * 顾客点单列表
     * @param number 菜品编号
     * @param amount 菜品数量
     */
    public static void addList(int number,int amount){
        if(orderDishCount > orderDishIds.length / 2){
            enCapacityOrder();
        }
        orderDishIds[orderDishCount] = number;
        orderDishCounts[orderDishCount] = amount;
        orderDishCount++;
    }



    /**
     * 顾客下单
     */
    public static void save(){
        try {
            for(int i = 0; i < orderDishCount; i++){
                String danList = orderDishIds[i] + "\t" + orderDishCounts[i];
                FileUtils.write(menuOrderFile,danList + "\n","utf8",true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 为管理员读取顾客的点单
     */
    public static void load(){
        try {
            String orderDishInformation =FileUtils.readFileToString(menuOrderFile,"utf8");
            String[] menuOrderLines = orderDishInformation.split("\n");
            for(int i = 0;i < menuOrderLines.length;i++){
                String[] menuMessages = menuOrderLines[i].split("\t");
                int number = Integer.parseInt(menuMessages[0]);
                int amount = Integer.parseInt(menuMessages[1]);
                addList(number,amount);
            }
        } catch (IOException e) {
            //避免第一次启动程序时，因为系统中没有创建文件而报错的情况。
        }
    }



}
