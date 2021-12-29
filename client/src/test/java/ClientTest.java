import com.itdfq.entity.Result;
import com.itdfq.service.ClientService;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author GocChin
 * @Date 2021/12/29 11:27
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @mark:
 */
public class ClientTest {

    /**
     * 获取信息
     */
    @Test
    public void doGet() {
        String s = ClientService.getByCode("1640761912534");
        System.out.println(s);
        //{"code":10000,"msg":"Success","data":{"name":"test.png","size":138875,"type":"image/png",
        // "addTime":"2021-12-29 15:11:53","code":"1640761912534",
        // "saveAddress":"/project/upload/data/20211229/test_1640761912534.png"},"success":true}

    }

    /**
     * 上传
     */
    @Test
    public void upload() {
        Map<String, String> map = new HashMap<>();
        map.put("file", "test.png");
        String s = ClientService.uplodByPost(map, new File("src/main/resources/test.png"));
        System.out.println(s);//{"code":10000,"msg":"Success","data":"1640767525488","success":true}
    }

    /**
     * 下载
     */
    @Test
    public void doGetForStream() throws IOException {
        Result<InputStream> inputStreamResult =
                ClientService.doGetForStream("1640767178429");
        File targetFile = new File("src/main/resources/123.png");
        System.out.println(inputStreamResult.isSuccess());
        ClientService.copyInputStreamToFile(inputStreamResult.getData(), targetFile);
        inputStreamResult.getData().close();


    }
}
