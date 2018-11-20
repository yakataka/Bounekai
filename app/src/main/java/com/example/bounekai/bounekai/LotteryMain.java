package com.example.bounekai.bounekai;

public class LotteryMain {

    private static final int WINNING_NUM = 3;

    public static int[] lottery(int lotteryTimes) {
        int[] nums = new int[3];
        int[] member = {1,2,3};
        System.out.println("//// 抽選開始 " + lotteryTimes + " ////");
        int idx = 0;
        for(int i = 0; i < WINNING_NUM; i++, idx++) {
            // この処理は本来不要
//            if(lotteryNumList.get(idx).getSettledFlg() == 1) {
//                System.out.println(lotteryNumList.get(idx).getLotteryNum() + ":" + lotteryNumList.get(idx).getName() + "(当選済みのため除外)");
//                i--;
//                continue;
//            }

//            System.out.println(lotteryNumList.get(idx).getLotteryNum() + ":" + lotteryNumList.get(idx).getName());
            nums[i] = member[i];
//            lotteryNumList.get(idx).setSettledFlg(1);
        }
//        System.out.println("//// 抽選終了 " + lotteryTimes + " ////");
        return nums;
    }
}
