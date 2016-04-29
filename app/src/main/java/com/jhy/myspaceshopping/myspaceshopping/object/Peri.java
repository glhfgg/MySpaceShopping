package com.jhy.myspaceshopping.myspaceshopping.object;

import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */


  public class Peri{
    private  data data;

    public Peri.data getData() {
        return data;
    }
    public void setData(Peri.data data) {
        this.data = data;
    }

     public class data{
        private List<shops> shops;
        public List<shops> getShops() {
                    return shops;
                }
        public void setShops(List<shops> shops) {
            this.shops = shops;
        }


    }

    public class shops{
        private String shop_name; //商户名字
        private String distance; //距离
        List<deals> deals;

        public String getShop_name() {
            return shop_name;
        }
        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }
        public String getDistance() {
            return distance;
        }
        public void setDistance(String distance) {
            this.distance = distance;
        }
        public List<deals> getDeals() {
            return deals;
        }
        public void setDeals(List<deals> deals) {
            this.deals = deals;
        }
    }

    public class deals{

        private String image;  // 商户图片
        private String description; //团购内容
        private String score;       //用户评分
        private double market_price; //打折前的价格
        private String sale_num;      //销量
        private double current_price; //折后

        public String getImage() { return image;}
        public void setImage(String image) {this.image = image; }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getScore() {
            return score;
        }
        public void setScore(String score) {
            this.score = score;
        }
        public double getMarket_price() {
            return market_price;
        }
        public void setMarket_price(double market_price) {
            this.market_price = market_price;
        }
        public String getSale_num() {
            return sale_num;
        }
        public void setSale_num(String sale_num) {
            this.sale_num = sale_num;
        }
        public double getCurrent_price() {
            return current_price;
        }
        public void setCurrent_price(double current_price) {
            this.current_price = current_price;
        }
    }

}
