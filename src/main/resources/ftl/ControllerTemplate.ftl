package ${func_pkg}.web;

import ${func_pkg}.entity.${model_name};
import ${func_pkg}.service.I${model_name}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName ${model_name}
 * @Description
 * @Author fliquan
 * @Date 2019/6/18 18:45
 * @Version V1.0.0
 */
@Api("${model_name}")
@RestController
@RequiresAuthentication
@RequestMapping("${uri}")
@Slf4j
public class ${model_name}Controller {

    @Resource
    private I${model_name}Service ${model_name_first_lower_case}ServiceImpl;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    // @RequiresPermissions("")
    public ${model_name} getById(@PathVariable("id") Integer id) {
        return this.${model_name_first_lower_case}ServiceImpl.getById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除", notes = "根据id删除")
    // @RequiresPermissions("")
    public void deleteById(@PathVariable("id") Integer id) {
        this.${model_name_first_lower_case}ServiceImpl.deleteById(id);
    }

    @PostMapping("")
    @ApiOperation(value = "新增或修改", notes = "新增或修改")
    // @RequiresPermissions("")
    public ${model_name} save(@RequestBody ${model_name} ${model_name_first_lower_case}) {
        return this.${model_name_first_lower_case}ServiceImpl.save(${model_name_first_lower_case});
    }
}