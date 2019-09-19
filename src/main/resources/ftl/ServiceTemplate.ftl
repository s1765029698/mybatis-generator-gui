package ${func_pkg}.service;

import com.gjwl.core.base.service.IBaseService;
import ${func_pkg}.entity.${model_name};

/**
* @ClassName ${model_name}服务
* @Description
* @Author fliquan
* @Date 2019/6/18 14:09
* @Version V1.0.0
*/
public interface I${model_name}Service extends IBaseService {

    /**
    * 根据id查询
    * @param id
    * @return
    */
    ${model_name} getById(Integer id);

    /**
    * 根据id查询
    * @param id
    * @return
    */
    void deleteById(Integer id);

    /**
    * 保存，如果id为空，则新增，否则更新
    * @param ${model_name_first_lower_case}
    */
    ${model_name} save(${model_name} ${model_name_first_lower_case});
}
