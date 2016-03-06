package vn.edu.techkids.pomodoro.entity;

/**
 * Created by TienVV on 12/7/15.
 */
public class MenuItem {

    private int id;
    private int titleResource;
    private int iconResource;

    private MenuItem() {
        // Prevent create empty menu item
    }

    public MenuItem(int id, int titleResource, int iconResource) {
        this.id = id;
        this.titleResource = titleResource;
        this.iconResource = iconResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitle() {
        return titleResource;
    }

    public void setTitle(int titleResource) {
        this.titleResource = titleResource;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}
