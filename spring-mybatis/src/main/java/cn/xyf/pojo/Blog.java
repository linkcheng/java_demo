package cn.xyf.pojo;

import com.sun.istack.internal.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "makeBlog")
@Builder
public class Blog {
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String content;

    private StarUser user;
}
