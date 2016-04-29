package com.jhy.myspaceshopping.myspaceshopping.objectmode;

import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class CityArea {
    private int errno;
    private String msg;
    private List<Districts> districts;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Districts> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Districts> districts) {
        this.districts = districts;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public class Districts {
        private String district_name;
        private int district_id;
        private List<Biz_areas> biz_areas;

        public int getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(int district_id) {
            this.district_id = district_id;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public List<Biz_areas> getBiz_areas() {
            return biz_areas;
        }

        public void setBiz_areas(List<Biz_areas> biz_areas) {
            this.biz_areas = biz_areas;
        }

        public class Biz_areas {
            private String biz_area_name;
            private int biz_area_id;

            public int getBiz_area_id() {
                return biz_area_id;
            }

            public void setBiz_area_id(int biz_area_id) {
                this.biz_area_id = biz_area_id;
            }

            public String getBiz_area_name() {
                return biz_area_name;
            }

            public void setBiz_area_name(String biz_area_name) {
                this.biz_area_name = biz_area_name;
            }
        }
    }
}
