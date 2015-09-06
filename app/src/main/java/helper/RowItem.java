package helper;

/**
 * Created by Ramazan Cinardere} on 01.04.2015.
 */

//This class is used to store the data for each row in ListView.
public class RowItem {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private int imageId;
    private String title;
    private String desc;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public RowItem(int imageId, String title, String desc) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString() {
        return title + "\n" +desc;
    }
}
