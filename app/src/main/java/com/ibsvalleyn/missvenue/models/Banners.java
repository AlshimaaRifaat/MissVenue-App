package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Banners {

    @Expose
    @SerializedName("MainBanners")
    private List<MainBanners> MainBanners;
    @Expose
    @SerializedName("SubBanner")
    private SubBanner SubBanner;
    @Expose
    @SerializedName("PromoBanner")
    private PromoBanner PromoBanner;

    public List<MainBanners> getMainBanners() {
        return MainBanners;
    }

    public void setMainBanners(List<MainBanners> MainBanners) {
        this.MainBanners = MainBanners;
    }

    public SubBanner getSubBanner() {
        return SubBanner;
    }

    public void setSubBanner(SubBanner SubBanner) {
        this.SubBanner = SubBanner;
    }

    public PromoBanner getPromoBanner() {
        return PromoBanner;
    }

    public void setPromoBanner(PromoBanner PromoBanner) {
        this.PromoBanner = PromoBanner;
    }

    public static class MainBanners {
        @Expose
        @SerializedName("BannerType")
        private String BannerType;
        @Expose
        @SerializedName("DisplayOrder")
        private int DisplayOrder;
        @Expose
        @SerializedName("BannerId")
        private int BannerId;
        @Expose
        @SerializedName("PictureUrl")
        private String PictureUrl;
        @Expose
        @SerializedName("MetaTitle")
        private String MetaTitle;

        public String getMetaTitle() {
            return MetaTitle;
        }

        public void setMetaTitle(String metaTitle) {
            MetaTitle = metaTitle;
        }

        public String getBannerType() {
            return BannerType;
        }

        public void setBannerType(String BannerType) {
            this.BannerType = BannerType;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public int getBannerId() {
            return BannerId;
        }

        public void setBannerId(int BannerId) {
            this.BannerId = BannerId;
        }

        public String getPictureUrl() {
            return PictureUrl;
        }

        public void setPictureUrl(String PictureUrl) {
            this.PictureUrl = PictureUrl;
        }
    }

    public static class SubBanner {
        @Expose
        @SerializedName("MetaTitle")
        private String MetaTitle;

        public String getMetaTitle() {
            return MetaTitle;
        }

        public void setMetaTitle(String metaTitle) {
            MetaTitle = metaTitle;
        }
        @Expose
        @SerializedName("BannerType")
        private String BannerType;
        @Expose
        @SerializedName("DisplayOrder")
        private int DisplayOrder;
        @Expose
        @SerializedName("BannerId")
        private int BannerId;
        @Expose
        @SerializedName("PictureUrl")
        private String PictureUrl;

        public String getBannerType() {
            return BannerType;
        }

        public void setBannerType(String BannerType) {
            this.BannerType = BannerType;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public int getBannerId() {
            return BannerId;
        }

        public void setBannerId(int BannerId) {
            this.BannerId = BannerId;
        }

        public String getPictureUrl() {
            return PictureUrl;
        }

        public void setPictureUrl(String PictureUrl) {
            this.PictureUrl = PictureUrl;
        }
    }

    public static class PromoBanner {
        @Expose
        @SerializedName("MetaTitle")
        private String MetaTitle;

        public String getMetaTitle() {
            return MetaTitle;
        }

        public void setMetaTitle(String metaTitle) {
            MetaTitle = metaTitle;
        }
        @Expose
        @SerializedName("BannerType")
        private String BannerType;
        @Expose
        @SerializedName("DisplayOrder")
        private int DisplayOrder;
        @Expose
        @SerializedName("BannerId")
        private int BannerId;
        @Expose
        @SerializedName("PictureUrl")
        private String PictureUrl;

        public String getBannerType() {
            return BannerType;
        }

        public void setBannerType(String BannerType) {
            this.BannerType = BannerType;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public int getBannerId() {
            return BannerId;
        }

        public void setBannerId(int BannerId) {
            this.BannerId = BannerId;
        }

        public String getPictureUrl() {
            return PictureUrl;
        }

        public void setPictureUrl(String PictureUrl) {
            this.PictureUrl = PictureUrl;
        }
    }
}
