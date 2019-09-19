package ${func_pkg}.service.impl;

import ${func_pkg}.entity.${model_name};
import ${func_pkg}.dao.${model_name}Mapper;
import ${func_pkg}.service.I${model_name}Service;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
* @ClassName ${model_name}服务
* @Description
* @Author fliquan
* @Date 2019/6/18 14:08
* @Version V1.0.0
*/
@Service
public class ${model_name}ServiceImpl implements I${model_name}Service {

    @Resource
    private ${model_name}Mapper ${model_name_first_lower_case}Mapper;

    @Override
    public ${model_name} getById(Integer id) {
        return this.${model_name_first_lower_case}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        this.${model_name_first_lower_case}Mapper.deleteByPrimaryKey(id);
    }

    @Override
    public ${model_name} save(${model_name} ${model_name_first_lower_case}) {
        if(${model_name_first_lower_case}.getId() == null) {
            this.${model_name_first_lower_case}Mapper.insert(${model_name_first_lower_case});
        }else {
            this.${model_name_first_lower_case}Mapper.updateByPrimaryKey(${model_name_first_lower_case});
        }
        return ${model_name_first_lower_case};
    }
}
