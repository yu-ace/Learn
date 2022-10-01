package Revenue;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RevenueTools {

    static String[] dishNames =new String[50];
    static double[] dishPrices = new double[50];
    static int dishCount = 0;

    static int[] orderDishIds =new int[50];
    static int[] orderDishCounts = new int[50];
    static int orderDishCount = 0;

    static File menuOrderFile = new File(FileUtils.getUserDirectoryPath() + "/learn/restaurant/menuMoney.dat");
    static File menuAdminFile =new File(FileUtils.getUserDirectoryPath() + "/learn/restaurant/GL/menuMoney.dat");

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadDishByAdmin();
        load();
        System.out.println("欢迎来到收银系统：");
        while(true){
            System.out.println("请输入1查看菜单,输入2创建菜单");
            String str = scanner.nextLine();
            if(str.equals("1")){
                viewTheDish();
            }else if(str.equals("2")){
                addOrder();
            }else if(str.equals("s")){
               // 管理员模式
                admin();
            }
        }

    }

    private static void addOrder() {
        while (true){
            System.out.println("请输入需要的菜单序号：");
            int number = scanner.nextInt();
            System.out.println("请输入需要的数量：");
            int amount = scanner.nextInt();
            addList(number,amount);
            System.out.println("是否结账？y/n");
            String s = scanner.next();
            if(s.equals("y")){
                save();
                System.out.println("您的订单总额为" + total() + "元，请扫码付款");
                break;
            }
        }
    }

    /**
     * 管理员系统
     */
    public static void admin(){
        while(true){
            menuHelp();
            String num = scanner.next();
            if(num.equals("1")){
                System.out.println("请输入需要添加的菜单名称：");
                String name = scanner.next();
                System.out.println("请输入菜单价格：");
                double price = scanner.nextDouble();
                addDish(name,price);
            }else if(num.equals("2")) {
                System.out.println("请输入需要删除的菜单名称：");
                int id = scanner.nextInt();
                deleteDish(id);
            }else if(num.equals("3")){
                dishOrder();
            }else if(num.equals("4")){
                todayRevenue();
            }else if(num.equals("5")){
                todayOrderDishCount();
            }else if(num.equals("6")){
                top5();
            }else if(num.equals("q")){
                break;
            }else if(num.equals("t")){
                saveDishByAdmin();
            }
        }
    }

    /**
     * 管理员菜单
     */
    public static void menuHelp(){
        System.out.println("欢迎来到管理员系统");
        System.out.println("输入1上架菜单");
        System.out.println("输入2下架菜品");
        System.out.println("输入3查看订单列表");
        System.out.println("输入4统计今日营收");
        System.out.println("输入5统计客单价");
        System.out.println("输入6统计今天前五的菜品");
        System.out.println("输入t保存菜单");
        System.out.println("输入q退出系统");
    }


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
        for(int i = 0; i < orderDishCount; i++){
            System.out.println(dishNames[orderDishIds[i]] + "\t"
                    + orderDishCounts[i] + "\t"
                    + (orderDishCounts[i] * dishPrices[orderDishIds[i]]));
        }
    }

    /**
     * 将菜单名转变成编号
     * @param name 菜单名称
     * @return 菜单编号
     */
 /*   private static int getId(String name) {
        int id = -1;
        for (int j = 0; j < dishCount; j++) {
            if(dishNames[j].equals(name)){
                id = j;
            }
        }
        return id;
    }*/

    /**
     * 查看总收入
     */
    public static void todayRevenue(){
        double sum = 0;
        for(int i = 0; i < orderDishCount; i++){
            int id = orderDishIds[i];
            sum = sum + (dishPrices[id] * orderDishCounts[i]);
        }
        System.out.println("今日总收入为：" + sum);
    }

    /**
     * 输出客单量
     */
    public static void todayOrderDishCount(){
        double sum = 0;
        for(int i = 0; i < orderDishCount; i++){
            int orderDishId = orderDishIds[i];
            double a = orderDishCounts[i] * dishPrices[orderDishId];
            sum = sum + a;
        }
        System.out.println(sum/orderDishCount);;
    }

    /**
     * 订单前五的菜品
     */
    public static void top5(){
        int [] a = new int[50];
        for(int i = 0; i < orderDishCount; i++){
            a[i] = a[i] + orderDishCounts[i];
        }
        for(int i = 0; i < orderDishCount; i++){
            int max = a[i];
            for(int j = i; j < orderDishCount; j++){
                if(max < a[j]){
                    max = a[j];
                    a[j] = a[i];
                    a[i] = max;
                }
            }
        }
        int[] c = new int[50];
        for(int i = 0;i < c.length;i++){
            c[i] = orderDishCounts[i];
        }
        for(int i = 0;i < 5;i++){
            for(int j = 0; j < orderDishCount; j++){
                if(c[j] == a[i]){
                    System.out.println(dishNames[orderDishIds[j]]);
                    c[j] = 0;
                    break;
                }
            }
        }
    }

    /**
     * 展示菜单
     */
    public static void viewTheDish(){
        for(int i = 0; i < dishCount; i++){
            System.out.println(dishNames[i]+"\t"+ dishPrices[i]);
        }
    }


    /**
     * 对顾客下单扩容
     */
    public static void enCapacityOrder(){
        int[] newOrderDishIds = new int[orderDishIds.length + 50];
        int[] newOrderDishCounts = new int[orderDishCounts.length + 50];
        for(int i = 0;i < dishNames.length;i++){
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
            sum = sum + (dishPrices[orderDishIds[i]] * orderDishCounts[i]);
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


    /**
     * 为管理员添加顾客点的菜单
     * @param number 菜品编号
     * @param amount 菜品数量
     */
    public static void addDishForAdmin(int number, int amount){
        orderDishIds[orderDishCount] = number;
        orderDishCounts[orderDishCount] = amount;
        orderDishCount++;
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
                addDishForAdmin(number,amount);
            }
        } catch (IOException e) {
            //避免第一次启动程序时，因为系统中没有创建文件而报错的情况。
        }
    }

}
