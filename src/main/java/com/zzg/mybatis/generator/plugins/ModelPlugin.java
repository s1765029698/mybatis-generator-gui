package com.zzg.mybatis.generator.plugins;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

public class ModelPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");

        // 父类
        topLevelClass.addImportedType("com.gjwl.core.base.entity.Base");
        FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType("Base");
        topLevelClass.setSuperClass(superInterface);

        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");

        // serialVersionUID
        Field field = new Field();
        field.setFinal(true);
        field.setInitializationString("1L"); //$NON-NLS-1$
        field.setName("serialVersionUID"); //$NON-NLS-1$
        field.setStatic(true);
        field.setType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
        field.setVisibility(JavaVisibility.PRIVATE);
        context.getCommentGenerator().addFieldComment(field, introspectedTable);
        topLevelClass.getFields().add(0, field);

        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if(isCommonColumn(introspectedColumn)){
            return false;
        }
        field.addAnnotation("@ApiModelProperty(value = \"" + introspectedColumn.getRemarks() + "\")");
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    private boolean isCommonColumn(IntrospectedColumn introspectedColumn){
        String columnName = introspectedColumn.getActualColumnName();
        return "id".equalsIgnoreCase(columnName) || "created_by".equalsIgnoreCase(columnName) || "created_date".equalsIgnoreCase(columnName) || "updated_by".equalsIgnoreCase(columnName) || "updated_date".equalsIgnoreCase(columnName);
    }



}
