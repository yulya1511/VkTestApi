package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListPhotos {

    private int date;
    private List<PhotoSize> sizes;
    @JsonProperty("owner_id")
    private int ownerId;
    @JsonProperty("access_key")
    private String accessKey;
    @JsonProperty("album_id")
    private int albumId;
    @JsonProperty("has_tags")
    private boolean hasTags;
    private int id;
    private String text;
}
