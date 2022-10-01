package Revenue;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class MenuManager {


    static String[] dishNames =new String[50];
    static double[] dishPrices = new double[50];
    static int dishCount = 0;

    static File menuAdminFile =new File(FileUtils.getUserDirectoryPath() + "/learn/restaurant/GL/menuMoney.dat");


    /**
     * 对菜单扩容
     */
    public static void enCapacity(){
        String[] newDishNames = new String[dishNames.length + 50];
        double[] newDishPrices = new double[dishPrices.length + 50];
        for(int i = 0;i < dishNames.length;i++){
            newDishNames[i] = dishNames[i];
            newDishPrices[i] = dishPrices[i];
        }
        dishNames = newDishNames;
        dishPrices = newDishPrices;
    }

    /**
     * 添加菜品
     * @param name
     * @param price
     */
    public static void addDish(String name, double price){
        if(dishCount > dishNames.length / 2){
            enCapacity();
        }
        dishNames[dishCount] = name;
        dishPrices[dishCount] = price;
        dishCount++;
    }

    /**
     * 管理员删除菜品
     * @param id 菜品的编号
     */
    public static void deleteDish(int id){
        for(int i = id; i < dishCount; i++){
            dishNames[i] = dishNames[i+1];
            dishPrices[i] = dishPrices[i+1];
        }
        dishCount--;
    }


    /**
     * 管理员查看顾客订单列表
     */
    public static void dishOrder(){
        for(int i = 0; i < OrderManager.orderDishCount; i++){
            System.out.println(dishNames[OrderManager.orderDishIds[i]] + "\t"
                    + OrderManager.orderDishCounts[i] + "\t"
                    + (OrderManager.orderDishCounts[i] * dishPrices[OrderManager.orderDishIds[i]]));
        }
    }


    /**
     * 查看总收入
     */
    public static void todayRevenue(){
        double sum = 0;
        for(int i = 0; i < OrderManager.orderDishCount; i++){
            int id = OrderManager.orderDishIds[i];
            sum = sum + (dishPrices[id] * OrderManager.orderDishCounts[i]);
        }
        System.out.println("今日总收入为：" + sum);
    }

    /**
     * 输出客单量
     */
    public static void todayOrderDishCount(){
        double sum = 0;
        for(int i = 0; i < OrderManager.orderDishCount; i++){
            int orderDishId = OrderManager.orderDishIds[i];
            double a = OrderManager.orderDishCounts[i] * dishPrices[orderDishId];
            sum = sum + a;
        }
        System.out.println(sum/OrderManager.orderDishCount);;
    }

    /**
     * 订单前五的菜品
     */
    public static void top5(){
        int [] a = new int[50];
        for(int i = 0; i < OrderManager.orderDishCount; i++){
            a[i] = a[i] + OrderManager.orderDishCounts[i];
        }
        for(int i = 0; i < OrderManager.orderDishCount; i++){
            int max = a[i];
            for(int j = i; j < OrderManager.orderDishCount; j++){
                if(max < a[j]){
                    max = a[j];
                    a[j] = a[i];
                    a[i] = max;
                }
            }
        }
        int[] c = new int[50];
        for(int i = 0;i < c.length;i++){
            c[i] = OrderManager.orderDishCounts[i];
        }
        for(int i = 0;i < 5;i++){
            for(int j = 0; j < OrderManager.orderDishCount; j++){
                if(c[j] == a[i]){
                    System.out.println(dishNames[OrderManager.orderDishIds[j]]);
                    c[j] = 0;
                    break;
                }
            }
        }
    }


    /**
     * 管理人员保存菜单
     */
    public static void saveDishByAdmin(){
        FileUtils.deleteQuietly(menuAdminFile);
        try {
            for(int i = 0; i < dishCount; i++){
                String danList = dishNames[i] + "\t" + dishPrices[i];
                FileUtils.write(menuAdminFile,danList + "\n","utf8",true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 管理系统读取菜单
     */
    public static void loadDishByAdmin(){
        try {
            String menuInformationFile = FileUtils.readFileToString(menuAdminFile,"utf8");
            String[] menuLines = menuInformationFile.split("\n");
            for(int i = 0;i < menuLines.length;i++){
                String[] menuInformationStr = menuLines[i].split("\t");
                String name = menuInformationStr[0];
                double price = Double.parseDouble(menuInformationStr[1]);
                addDish(name, price);
            }
        } catch (IOException e) {
            //避免第一次启动程序时，因为系统中没有创建文件而报错的情况。
        }
    }


}
