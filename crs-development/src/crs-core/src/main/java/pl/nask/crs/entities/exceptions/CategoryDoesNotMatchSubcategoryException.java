package pl.nask.crs.entities.exceptions;

public class CategoryDoesNotMatchSubcategoryException extends Exception {
    private final String holderCategory;
    private final String holderSubcategory;

    public CategoryDoesNotMatchSubcategoryException(String holderCategory, String holderSubcategory) {
        this.holderCategory = holderCategory;
        this.holderSubcategory = holderSubcategory;
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public String getHolderSubcategory() {
        return holderSubcategory;
    }

    @Override
    public String getMessage() {
        return String.format("Category (%s) doesn't match subcategory (%s)", holderCategory, holderSubcategory);
    }
}
