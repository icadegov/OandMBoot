package in.OAndM.requests;
import lombok.Data;

import java.util.Map;

@Data
public class PaginationRequest {
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id"; // Default sort field
    private Boolean ascending = true;

    private Map<String, String> filters;

}
