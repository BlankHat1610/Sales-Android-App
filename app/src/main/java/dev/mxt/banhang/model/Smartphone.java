package dev.mxt.banhang.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dev.mxt.banhang.api.ApiConnect;

public class Smartphone {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pro_name")
    @Expose
    private String proName;
    @SerializedName("pro_slug")
    @Expose
    private String proSlug;
    @SerializedName("pro_category_id")
    @Expose
    private Integer proCategoryId;
    @SerializedName("pro_price")
    @Expose
    private Integer proPrice;
    @SerializedName("pro_author_id")
    @Expose
    private Integer proAuthorId;
    @SerializedName("pro_sale")
    @Expose
    private Integer proSale;
    @SerializedName("pro_active")
    @Expose
    private Integer proActive;
    @SerializedName("pro_hot")
    @Expose
    private Integer proHot;
    @SerializedName("pro_view")
    @Expose
    private Integer proView;
    @SerializedName("pro_description")
    @Expose
    private String proDescription;
    @SerializedName("pro_avatar")
    @Expose
    private String proAvatar;
    @SerializedName("pro_description_seo")
    @Expose
    private String proDescriptionSeo;
    @SerializedName("pro_keyword_seo")
    @Expose
    private Object proKeywordSeo;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("pro_title_seo")
    @Expose
    private String proTitleSeo;
    @SerializedName("pro_content")
    @Expose
    private String proContent;
    @SerializedName("pro_pay")
    @Expose
    private Integer proPay;
    @SerializedName("pro_number")
    @Expose
    private Integer proNumber;
    @SerializedName("pro_total_rating")
    @Expose
    private Integer proTotalRating;
    @SerializedName("pro_total_number")
    @Expose
    private Integer proTotalNumber;
    @SerializedName("pro_suggestion")
    @Expose
    private Integer proSuggestion;
    @SerializedName("pro_new")
    @Expose
    private Integer proNew;
    @SerializedName("c_name")
    @Expose
    private String cName;
    @SerializedName("c_slug")
    @Expose
    private String cSlug;
    @SerializedName("c_icon")
    @Expose
    private String cIcon;
    @SerializedName("c_avatar")
    @Expose
    private Object cAvatar;
    @SerializedName("c_active")
    @Expose
    private Integer cActive;
    @SerializedName("c_total_product")
    @Expose
    private Integer cTotalProduct;
    @SerializedName("c_title_seo")
    @Expose
    private String cTitleSeo;
    @SerializedName("c_description_seo")
    @Expose
    private String cDescriptionSeo;
    @SerializedName("c_keyword_seo")
    @Expose
    private Object cKeywordSeo;
    @SerializedName("c_home")
    @Expose
    private Integer cHome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProSlug() {
        return proSlug;
    }

    public void setProSlug(String proSlug) {
        this.proSlug = proSlug;
    }

    public Integer getProCategoryId() {
        return proCategoryId;
    }

    public void setProCategoryId(Integer proCategoryId) {
        this.proCategoryId = proCategoryId;
    }

    public Integer getProPrice() {
        return proPrice;
    }

    public void setProPrice(Integer proPrice) {
        this.proPrice = proPrice;
    }

    public Integer getProAuthorId() {
        return proAuthorId;
    }

    public void setProAuthorId(Integer proAuthorId) {
        this.proAuthorId = proAuthorId;
    }

    public Integer getProSale() {
        return proSale;
    }

    public void setProSale(Integer proSale) {
        this.proSale = proSale;
    }

    public Integer getProActive() {
        return proActive;
    }

    public void setProActive(Integer proActive) {
        this.proActive = proActive;
    }

    public Integer getProHot() {
        return proHot;
    }

    public void setProHot(Integer proHot) {
        this.proHot = proHot;
    }

    public Integer getProView() {
        return proView;
    }

    public void setProView(Integer proView) {
        this.proView = proView;
    }

    public String getProDescription() {
        return proDescription;
    }

    public void setProDescription(String proDescription) {
        this.proDescription = proDescription;
    }

    public String getProAvatar() {
        String yearSubfolder = proAvatar.substring(0,4);
        String monthSubfolder = proAvatar.substring(5,7);
        String daySubfolder = proAvatar.substring(8,10);
        ApiConnect.setSubfolderImageUrl(yearSubfolder, monthSubfolder, daySubfolder);
        return ApiConnect.getImageUrl() + ApiConnect.getSubfolderImageUrl() + proAvatar;
    }

    public void setProAvatar(String proAvatar) {
        this.proAvatar = proAvatar;
    }

    public String getProDescriptionSeo() {
        return proDescriptionSeo;
    }

    public void setProDescriptionSeo(String proDescriptionSeo) {
        this.proDescriptionSeo = proDescriptionSeo;
    }

    public Object getProKeywordSeo() {
        return proKeywordSeo;
    }

    public void setProKeywordSeo(Object proKeywordSeo) {
        this.proKeywordSeo = proKeywordSeo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProTitleSeo() {
        return proTitleSeo;
    }

    public void setProTitleSeo(String proTitleSeo) {
        this.proTitleSeo = proTitleSeo;
    }

    public String getProContent() {
        return proContent;
    }

    public void setProContent(String proContent) {
        this.proContent = proContent;
    }

    public Integer getProPay() {
        return proPay;
    }

    public void setProPay(Integer proPay) {
        this.proPay = proPay;
    }

    public Integer getProNumber() {
        return proNumber;
    }

    public void setProNumber(Integer proNumber) {
        this.proNumber = proNumber;
    }

    public Integer getProTotalRating() {
        return proTotalRating;
    }

    public void setProTotalRating(Integer proTotalRating) {
        this.proTotalRating = proTotalRating;
    }

    public Integer getProTotalNumber() {
        return proTotalNumber;
    }

    public void setProTotalNumber(Integer proTotalNumber) {
        this.proTotalNumber = proTotalNumber;
    }

    public Integer getProSuggestion() {
        return proSuggestion;
    }

    public void setProSuggestion(Integer proSuggestion) {
        this.proSuggestion = proSuggestion;
    }

    public Integer getProNew() {
        return proNew;
    }

    public void setProNew(Integer proNew) {
        this.proNew = proNew;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCSlug() {
        return cSlug;
    }

    public void setCSlug(String cSlug) {
        this.cSlug = cSlug;
    }

    public String getCIcon() {
        return cIcon;
    }

    public void setCIcon(String cIcon) {
        this.cIcon = cIcon;
    }

    public Object getCAvatar() {
        return cAvatar;
    }

    public void setCAvatar(Object cAvatar) {
        this.cAvatar = cAvatar;
    }

    public Integer getCActive() {
        return cActive;
    }

    public void setCActive(Integer cActive) {
        this.cActive = cActive;
    }

    public Integer getCTotalProduct() {
        return cTotalProduct;
    }

    public void setCTotalProduct(Integer cTotalProduct) {
        this.cTotalProduct = cTotalProduct;
    }

    public String getCTitleSeo() {
        return cTitleSeo;
    }

    public void setCTitleSeo(String cTitleSeo) {
        this.cTitleSeo = cTitleSeo;
    }

    public String getCDescriptionSeo() {
        return cDescriptionSeo;
    }

    public void setCDescriptionSeo(String cDescriptionSeo) {
        this.cDescriptionSeo = cDescriptionSeo;
    }

    public Object getCKeywordSeo() {
        return cKeywordSeo;
    }

    public void setCKeywordSeo(Object cKeywordSeo) {
        this.cKeywordSeo = cKeywordSeo;
    }

    public Integer getCHome() {
        return cHome;
    }

    public void setCHome(Integer cHome) {
        this.cHome = cHome;
    }
}
