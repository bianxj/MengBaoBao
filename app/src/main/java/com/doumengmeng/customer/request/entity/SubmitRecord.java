package com.doumengmeng.customer.request.entity;

import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 记录提交数据格式
 * 创建日期: 2018/1/9 10:19
 */
public class SubmitRecord {
    private final String userId;
    private final BabyRecord babyRecord;

    public SubmitRecord(String userId) {
        this.userId = userId;
        babyRecord = new BabyRecord();
    }

    public void setHeight(String height) {
        babyRecord.setHeight(height);
    }

    public void setWeight(String weight) {
        babyRecord.setWeight(weight);
    }

    public void setHeadcircumference(String headcircumference) {
        babyRecord.setHeadcircumference(headcircumference);
    }

    public void setChestcircumference(String chestcircumference) {
        babyRecord.setChestcircumference(chestcircumference);
    }

    public void setCacation(String cacation) {
        babyRecord.setCacation(cacation);
    }

    public void setCacationdays(String cacationdays) {
        babyRecord.setCacationdays(cacationdays);
    }

    public void setUrinate(String urinate) {
        babyRecord.setUrinate(urinate);
    }

    public void setNighttimeSleep(String nighttimeSleep) {
        babyRecord.setNighttimeSleep(nighttimeSleep);
    }

    public void setDaytimesleep(String daytimesleep) {
        babyRecord.setDaytimesleep(daytimesleep);
    }

    public void setBreastfeedingml(String breastfeedingml) {
        babyRecord.setBreastfeedingml(breastfeedingml);
    }

    public void setBreastfeedingcount(String breastfeedingcount) {
        babyRecord.setBreastfeedingcount(breastfeedingcount);
    }

    public void setMilkfeedingml(String milkfeedingml) {
        babyRecord.setMilkfeedingml(milkfeedingml);
    }

    public void setMilkfeedingcount(String milkfeedingcount) {
        babyRecord.setMilkfeedingcount(milkfeedingcount);
    }

    public void setOther(String other) {
        babyRecord.setOther(other);
    }

    public void setMilk(String milk) {
        babyRecord.setMilk(milk);
    }

    public void setRiceflour(String riceflour) {
        babyRecord.setRiceflour(riceflour);
    }

    public void setPasta(String pasta) {
        babyRecord.setPasta(pasta);
    }

    public void setCongee(String congee) {
        babyRecord.setCongee(congee);
    }

    public void setRice(String rice) {
        babyRecord.setRice(rice);
    }

    public void setMeat(String meat) {
        babyRecord.setMeat(meat);
    }

    public void setEggs(String eggs) {
        babyRecord.setEggs(eggs);
    }

    public void setSoyproducts(String soyproducts) {
        babyRecord.setSoyproducts(soyproducts);
    }

    public void setVegetables(String vegetables) {
        babyRecord.setVegetables(vegetables);
    }

    public void setFruit(String fruit) {
        babyRecord.setFruit(fruit);
    }

    public void setWater(String water) {
        babyRecord.setWater(water);
    }

    public void setVitaminaddose(String vitaminaddose) {
        babyRecord.setVitaminaddose(vitaminaddose);
    }

    public void setFeature(List<String> feature) {
        babyRecord.setFeature(feature);
    }

    public void setCalciumdose(String calciumdose) {
        babyRecord.setCalciumdose(calciumdose);
    }

    public void setOtherfood(String otherfood) {
        babyRecord.setOtherfood(otherfood);
    }

    public void setOrexia(String orexia) {
        babyRecord.setOrexia(orexia);
    }

    private class BabyRecord{

        private String height;
        private String weight;
        private String headcircumference;
        private String chestcircumference;
        private String cacation;
        private String cacationdays;
        private String urinate;
        private String nighttimeSleep;
        private String daytimesleep;
        private String breastfeedingml;
        private String breastfeedingcount;
        private String milkfeedingml;
        private String milkfeedingcount;
        private String other;
        private String milk;
        private String riceflour;
        private String pasta;
        private String congee;
        private String rice;
        private String meat;
        private String eggs;
        private String soyproducts;
        private String vegetables;
        private String fruit;
        private String water;
        private String vitaminaddose;
        private List<String> feature;
        private String calciumdose;
        private String otherfood;
        private String orexia;

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getHeadcircumference() {
            return headcircumference;
        }

        public void setHeadcircumference(String headcircumference) {
            this.headcircumference = headcircumference;
        }

        public String getChestcircumference() {
            return chestcircumference;
        }

        public void setChestcircumference(String chestcircumference) {
            this.chestcircumference = chestcircumference;
        }

        public String getCacation() {
            return cacation;
        }

        public void setCacation(String cacation) {
            this.cacation = cacation;
        }

        public String getCacationdays() {
            return cacationdays;
        }

        public void setCacationdays(String cacationdays) {
            this.cacationdays = cacationdays;
        }

        public String getUrinate() {
            return urinate;
        }

        public void setUrinate(String urinate) {
            this.urinate = urinate;
        }

        public String getNighttimeSleep() {
            return nighttimeSleep;
        }

        public void setNighttimeSleep(String nighttimeSleep) {
            this.nighttimeSleep = nighttimeSleep;
        }

        public String getDaytimesleep() {
            return daytimesleep;
        }

        public void setDaytimesleep(String daytimesleep) {
            this.daytimesleep = daytimesleep;
        }

        public String getBreastfeedingml() {
            return breastfeedingml;
        }

        public void setBreastfeedingml(String breastfeedingml) {
            this.breastfeedingml = breastfeedingml;
        }

        public String getBreastfeedingcount() {
            return breastfeedingcount;
        }

        public void setBreastfeedingcount(String breastfeedingcount) {
            this.breastfeedingcount = breastfeedingcount;
        }

        public String getMilkfeedingml() {
            return milkfeedingml;
        }

        public void setMilkfeedingml(String milkfeedingml) {
            this.milkfeedingml = milkfeedingml;
        }

        public String getMilkfeedingcount() {
            return milkfeedingcount;
        }

        public void setMilkfeedingcount(String milkfeedingcount) {
            this.milkfeedingcount = milkfeedingcount;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getMilk() {
            return milk;
        }

        public void setMilk(String milk) {
            this.milk = milk;
        }

        public String getRiceflour() {
            return riceflour;
        }

        public void setRiceflour(String riceflour) {
            this.riceflour = riceflour;
        }

        public String getPasta() {
            return pasta;
        }

        public void setPasta(String pasta) {
            this.pasta = pasta;
        }

        public String getCongee() {
            return congee;
        }

        public void setCongee(String congee) {
            this.congee = congee;
        }

        public String getRice() {
            return rice;
        }

        public void setRice(String rice) {
            this.rice = rice;
        }

        public String getMeat() {
            return meat;
        }

        public void setMeat(String meat) {
            this.meat = meat;
        }

        public String getEggs() {
            return eggs;
        }

        public void setEggs(String eggs) {
            this.eggs = eggs;
        }

        public String getSoyproducts() {
            return soyproducts;
        }

        public void setSoyproducts(String soyproducts) {
            this.soyproducts = soyproducts;
        }

        public String getVegetables() {
            return vegetables;
        }

        public void setVegetables(String vegetables) {
            this.vegetables = vegetables;
        }

        public String getFruit() {
            return fruit;
        }

        public void setFruit(String fruit) {
            this.fruit = fruit;
        }

        public String getWater() {
            return water;
        }

        public void setWater(String water) {
            this.water = water;
        }

        public String getVitaminaddose() {
            return vitaminaddose;
        }

        public void setVitaminaddose(String vitaminaddose) {
            this.vitaminaddose = vitaminaddose;
        }

        public String getCalciumdose() {
            return calciumdose;
        }

        public void setCalciumdose(String calciumdose) {
            this.calciumdose = calciumdose;
        }

        public String getOtherfood() {
            return otherfood;
        }

        public void setOtherfood(String otherfood) {
            this.otherfood = otherfood;
        }

        public String getOrexia() {
            return orexia;
        }

        public void setOrexia(String orexia) {
            this.orexia = orexia;
        }

        public List<String> getFeature() {
            return feature;
        }

        public void setFeature(List<String> feature) {
            this.feature = feature;
        }
    }
}
