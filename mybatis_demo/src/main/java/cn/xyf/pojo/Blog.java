package cn.xyf.pojo;

import com.sun.istack.internal.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "makeBlog")
@Builder
public class Blog implements Serializable {
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String content;

    private StarUser user;
}
