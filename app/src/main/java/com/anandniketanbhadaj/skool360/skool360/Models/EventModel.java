package com.anandniketanbhadaj.skool360.skool360.Models;

import java.util.ArrayList;

public class EventModel {

    private String CreateDate;
    private String Description;
    private ArrayList<EventImage> eventImages;

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<EventImage> getEventImages() {
        return eventImages;
    }

    public void setEventImages(ArrayList<EventImage> eventImages) {
        this.eventImages = eventImages;
    }

    public EventModel() {

    }

    public class EventImage {

        private String ImagePath;

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public EventImage() {
        }
    }
}
