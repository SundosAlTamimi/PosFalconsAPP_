package com.falconssoft.app_pos.models;

public class CategoryModel {

    private String categoryName;
    private String categoryPic;

    public CategoryModel(String categoryName, String categoryPic) {
        this.categoryName = categoryName;
        this.categoryPic = categoryPic;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPic() {
        return categoryPic;
    }

    public void setCategoryPic(String categoryPic) {
        this.categoryPic = categoryPic;
    }
}
