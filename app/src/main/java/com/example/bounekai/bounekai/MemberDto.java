package com.example.bounekai.bounekai;

public class MemberDto {

    private int num;
    private String kanaName;
    private String name;
    private String syaban;
    private int yotei;
    private int sanka;
    private int money;
    private int hit;
    private int hage;

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
    }

    public String getKanaName() {
        if(kanaName==null) return "ﾔﾏﾀﾞ ﾀﾛｳ";
        return kanaName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        if(name==null) return "山田　太郎";
        return name;
    }

    public void setSyaban(String syaban) {
        this.syaban = syaban;
    }

    public String getSyaban() {
        return syaban;
    }

    public void setYotei(int yotei) {
        this.yotei = yotei;
    }

    public int getYotei() {
        return yotei;
    }

    public void setSanka(int sanka) {
        this.sanka = sanka;
    }

    public String getSanka() {

        if(sanka==1){
            return "出席済";
        }else if(yotei ==1){
            return "未出席";
        }else {
            return "欠席";
        }

    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getMoney() {

        if(this.money==1){
            return "支払済";
        }else {
            return "未支払";
        }
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getHit() {
        return hit;
    }

    public void setHage(int hage) {
        this.hage = hage;
    }

    public int getHage() {
        return hage;
    }
}
