import com.atguigu.commonutils.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{
    private Integer code;

    private String msg;

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody//为了返回数据
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
