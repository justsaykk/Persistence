package workshop26.day26workshop.model;

import lombok.Data;

@Data
public class Comments {
    
    private String _id;
    private String c_id;
    private String user;
    private int rating;
    private String c_text;
    private int gid;

}
