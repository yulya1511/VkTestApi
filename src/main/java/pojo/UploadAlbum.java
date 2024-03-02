package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UploadAlbum {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("upload_url")
    private String uploadUrl;
    @JsonProperty("album_id")
    private int albumId;
}
