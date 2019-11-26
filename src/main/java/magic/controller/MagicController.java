package magic.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import magic.methods.Magics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Narcssus
 * @date : 2019/11/25 22:09
 */
@RestController
@RequestMapping("/magic")
@Api(tags = "魔法")
public class MagicController {

    @ApiOperation(value = "json生成代码", notes = "利用json报文生成Java代码", httpMethod = "GET")
    @ApiImplicitParam(name = "json", value = "param", required = true, dataType = "String")
    @GetMapping(value = "genJsonCode")
    public String genJsonCode(String json, String objName) {
        return Magics.genJsonCode(json, objName == null ? "myObject" : objName);
    }
}
