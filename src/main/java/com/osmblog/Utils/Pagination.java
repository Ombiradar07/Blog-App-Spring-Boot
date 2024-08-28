package com.osmblog.Utils;

import com.osmblog.Payload.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class Pagination {

    private  List<PostDto> posts ;
    private Integer pageNumber ;
    private Integer pageSize ;
    private Integer totalPages ;
    private Long totalElements ;
    private boolean isLastPage ;
}
