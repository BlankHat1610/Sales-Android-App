package dev.mxt.banhang.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("c_name")
    @Expose
    private String cName;
    @SerializedName("c_total_product")
    @Expose
    private Integer cTotalProduct;

    public Category(Integer id, String cName, Integer cTotalProduct) {
        this.id = id;
        this.cName = cName;
        this.cTotalProduct = cTotalProduct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcTotalProduct() {
        return cTotalProduct;
    }

    public void setcTotalProduct(Integer cTotalProduct) {
        this.cTotalProduct = cTotalProduct;
    }
}
