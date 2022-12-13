package day27workshop.workshop.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private String comment;
    private int rating;
}
