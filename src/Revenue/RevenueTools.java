package Revenue;

import java.util.Scanner;

public class RevenueTools {






    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        MenuManager.loadDishByAdmin();
        OrderManager.load();
        System.out.println("欢迎来到收银系统：");
        while(true){
            System.out.println("请输入1查看菜单,输入2创建菜单");
            String str = scanner.nextLine();
            if(str.equals("1")){
                OrderManager.viewTheDish();
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
            OrderManager.addList(number,amount);
            System.out.println("是否结账？y/n");
            String s = scanner.next();
            if(s.equals("y")){
                OrderManager.save();
                System.out.println("您的订单总额为" + OrderManager.total() + "元，请扫码付款");
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
                MenuManager.addDish(name,price);
            }else if(num.equals("2")) {
                System.out.println("请输入需要删除的菜单名称：");
                int id = scanner.nextInt();
                MenuManager.deleteDish(id);
            }else if(num.equals("3")){
                OrderManager.dishOrder();
            }else if(num.equals("4")){
                OrderManager.todayRevenue();
            }else if(num.equals("5")){
                MenuManager.todayOrderDishCount();
            }else if(num.equals("6")){
                MenuManager.top5();
            }else if(num.equals("q")){
                break;
            }else if(num.equals("t")){
                MenuManager.saveDishByAdmin();
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
     * 展示菜单
     */


}
