package Revenue;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class OrderManager {


    static int[] orderDishIds =new int[50];
    static int[] orderDishCounts = new int[50];
    static int orderDishCount = 0;

    static File menuOrderFile = new File(FileUtils.getUserDirectoryPath() + "/learn/restaurant/menuMoney.dat");


    /**
     * 展示菜单
     */
    public static void viewTheDish(){
        for(int i = 0; i < MenuManager.dishCount; i++){
            System.out.println(MenuManager.dishNames[i]+"\t"+ MenuManager.dishPrices[i]);
        }
    }


    /**
     * 对顾客下单扩容
     */
    public static void enCapacityOrder(){
        int[] newOrderDishIds = new int[orderDishIds.length + 50];
        int[] newOrderDishCounts = new int[orderDishCounts.length + 50];
        for(int i = 0;i < MenuManager.dishNames.length;i++){
            newOrderDishIds[i] = orderDishIds[i];
            newOrderDishCounts[i] = orderDishCounts[i];
        }
        orderDishIds = newOrderDishIds;
        orderDishCounts = newOrderDishCounts;
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
     * 顾客下单的菜单金额
     * @return 总金额
     */
    public static double total(){
        double sum = 0;
        for(int i = 0;i < orderDishCount;i++){
            sum = sum + (MenuManager.dishPrices[orderDishIds[i]] * orderDishCounts[i]);
        }
        return sum;
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
